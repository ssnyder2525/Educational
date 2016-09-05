import errno
import select
import socket
import sys
import traceback
import time
import os
from getRequest import getRequest
try:
    from http_parser.parser import HttpParser
except ImportError:
    from http_parser.pyparser import HttpParser

class Poller:
    """ Polling server """
    def __init__(self,port,debug):
        self.host = ""
        self.debug = debug
        self.port = port
        self.open_socket()
        self.clients = {}
        self.serviceTimes = {}
        self.size = 1024
        self.hostLocations = {}
        self.mediaAccepted = {}
        self.timeout = 1
        self.cache = {}
        self.directory = "./web"

    def open_socket(self):
        """ Setup the socket for incoming clients """
        try:
            self.server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self.server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR,1)
            self.server.bind((self.host,self.port))
            self.server.listen(5)
            self.server.setblocking(0)
        except socket.error, (value,message):
            if self.server:
                self.server.close()
            print "Could not open socket: " + message
            sys.exit(1)

    def run(self):
        self.parseConfFile()
        """ Use poll() to handle each incoming client."""
        self.poller = select.epoll()
        self.pollmask = select.EPOLLIN | select.EPOLLHUP | select.EPOLLERR
        self.poller.register(self.server,self.pollmask)
        mark = time.time()
        while True:
            # poll sockets
            try:
                fds = self.poller.poll(timeout=1)
            except:
                return
            for (fd,event) in fds:
                # handle errors
                if event & (select.POLLHUP | select.POLLERR):
                    self.handleError(fd)
                    continue
                # handle the server socket
                if fd == self.server.fileno():
                    self.handleServer()
                    continue
                # handle client socket
                result = self.handleClient(fd)
                # update the last time this client was served.
                self.serviceTimes[fd] = time.time()
            if time.time() - mark >= self.timeout:
                mark = time.time()
                for client in self.serviceTimes:
                    if self.serviceTimes[client] - mark > 1:
                        self.poller.unregister(client)
                        self.clients[client].close()
                        del self.clients[client]

    def parseConfFile(self):
        with open('web.conf') as f:
            lines = f.readlines()
        for line in lines:
            words = line.split(' ')
            if len(words) == 0:
                pass
            if words[0] == "host":
                self.hostLocations[words[1]] = words[2].rstrip('\r\n')
            elif words[0] == "media":
                self.mediaAccepted[words[1]] = words[2].rstrip('\r\n')
            elif words[0] == "parameter":
                if words[1] == "timeout":
                    if words[2] != "1":
                        self.timeout = int(words[2].rstrip('\r\n'))

    def handleError(self,fd):
        self.poller.unregister(fd)
        if fd == self.server.fileno():
            # recreate server socket
            self.server.close()
            self.open_socket()
            self.poller.register(self.server,self.pollmask)
        else:
            # close the socket
            self.clients[fd].close()
            del self.clients[fd]
            del self.cache[fd]

    def handleServer(self):
        # accept as many clients as possible
        while True:
            try:
                (client,address) = self.server.accept()
            except socket.error, (value,message):
                # if socket blocks because no clients are available,
                # then return
                if value == errno.EAGAIN or errno.EWOULDBLOCK:
                    return
                print traceback.format_exc()
                sys.exit()
            # set client socket to be non blocking
            client.setblocking(0)
            self.clients[client.fileno()] = client
            self.poller.register(client.fileno(),self.pollmask)
            self.serviceTimes[client.fileno()] = time.time()
            self.cache[client.fileno()] = ""

    def handleClient(self,fd):
        try:
            data = self.clients[fd].recv(self.size)
        except socket.error, (value,message):
            # if no data is available, move on to another client
            if value == errno.EAGAIN or errno.EWOULDBLOCK:
                return
            print traceback.format_exc()
            sys.exit()

        if data:
            # This will check for stuff
            if self.cache[fd] != "":
                data = self.cache[fd] + data
            if data.find('\r\n\r\n')!=-1:
                self.cache[fd] = ""
                p = HttpParser()
                nparsed = p.execute(data,len(data))
                # print fd
                # print data
                t = time.time()
                date = self.get_date(t)
                headers = p.get_headers()
                self.directory = self.hostLocations['default']
                if 'Host' in headers:
                    if headers['Host'].split(':')[0] in self.hostLocations:
                        self.directory = self.hostLocations[headers['Host'].split(':')[0]]
                path = self.directory + p.get_path()
                print data
                if p.get_method() == '':
                    response = "Http/1.1 400 Bad Request\r\nDate: " + date + "\r\nServer: Balderdash\r\nContent-Length:37\r\nContent-Type:text/plain\r\n\r\n400: Bad Request: No Method Specified"
                    self.sendResponse(fd,response)
                if p.get_method() == 'GET':
                    if p.get_path() == '/':
                        path = "./web/index.html"
                    gReq = getRequest(path, headers, self.mediaAccepted, date)
                    response = gReq.handleRequest()
                    self.sendResponse(fd, response)
                elif p.get_method() == 'POST' or p.get_method() == 'DELETE':
                    response = "Http/1.1 501 Not Implimented\r\nDate: " + date + "\r\nServer: Balderdash\r\nContent-Length:6\r\nContent-Type:text/plain\r\n\r\nFailed"
                    self.sendResponse(fd,response)
                else:
                    response = "Http/1.1 400 Bad Request\r\nDate: " + date + "\r\nServer: Balderdash\r\nContent-Length:37\r\nContent-Type:text/plain\r\n\r\n400: Bad Request: No Method Specified"
                    self.sendResponse(fd,response)
            else:
                self.cache[fd] = data
        else:
            self.poller.unregister(fd)
            self.clients[fd].close()
            del self.clients[fd]
            del self.cache[fd]

    def get_date (self, t):
        gmt = time.gmtime(t)
        format = "%a, %d %b %Y %H :%M :%S GMT"
        time_string = time.strftime(format, gmt)
        return time_string

    def sendResponse(self, fd, response):
        self.clients[fd].send(response)
