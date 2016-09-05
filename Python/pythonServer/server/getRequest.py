import errno
import select
import socket
import sys
import traceback
import time
import os
import datetime
try:
    from http_parser.parser import HttpParser
except ImportError:
    from http_parser.pyparser import HttpParser

class getRequest:
    def __init__(self, path, headers, mediaAccepted, date):
        self.responseCode = ""
        self.respHeaders = []
        self.path = path
        self.headers = headers
        self.contentTypes = mediaAccepted
        self.date = date

    def handleRequest(self):
        self.respHeaders.append("Date: " + self.date)
        self.respHeaders.append("Server: Balderdash")
        try :
            fileToRead = open(self.path, 'rb')
            respBody = ""
            if "Range" in self.headers:
                self.responseCode = "HTTP/1.1 206 OK\r\n"
                start = int(self.headers['Range'].split('=')[1].split('-')[0])
                stop = int(self.headers['Range'].split('=')[1].split('-')[1])
                fileToRead.seek(start)
                respBody = fileToRead.read(stop-start+1)
            else:
                respBody = fileToRead.read()
                self.responseCode = "HTTP/1.1 200 OK\r\n"
            statbuf = os.stat(self.path)
            gmt = time.gmtime(statbuf.st_mtime)
            format = "%a, %d %b %Y %H :%M :%S GMT"
            modified = time.strftime(format, gmt)
            self.respHeaders.append("Last-Modified: " + str(modified))
            pieces = self.path.split(".");
            codec = pieces[len(pieces)-1]
            if codec in self.contentTypes:
                self.respHeaders.append("Content-Type: " + self.contentTypes[codec])
            else:
                self.respHeaders.append("Content-Type: text/plain")
            self.body = respBody
            self.respHeaders.append("Content-Length: " + str(len(self.body)))
            return self.buildResponse()
        except IOError as (errno, strerror):
            if errno == 13:
                self.responseCode = "HTTP/1.1 403 Forbidden\r\n"
                self.respHeaders.append("Content-Type: text/html")
                self.body = "403: Forbidden"
                self.respHeaders.append("Content-Length: " + str(len(self.body)-1))
                return self.buildResponse()
            elif errno == 2:
                self.responseCode = "HTTP/1.1 404 Not Found\r\n"
                self.respHeaders.append("Content-Type: text/html")
                self.body = "404: Could not find the file " + self.path
                self.respHeaders.append("Content-Length: " + str(len(self.body)-1))
                return self.buildResponse()
            else:
                self.responseCode = "HTTP/1.1 500 Internal Server Error\r\n"
                self.respHeaders.append("Content-Type: text/html")
                self.body = "500: Server Error"
                self.respHeaders.append("Content-Length: " + str(len(self.body)-1))
                return self.buildResponse()

    def buildResponse(self):
        response = self.responseCode
        for i in range(0,len(self.respHeaders)):
            response += self.respHeaders[i] + "\r\n"
        response += "\r\n" + self.body
        return response
