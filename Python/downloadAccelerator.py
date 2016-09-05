import argparse
import os
import requests
import threading
import time

''' Downloader for a set of files '''
class Downloader:
    def __init__(self):
        ''' initialize the file where the list of URLs is listed, and the
        directory where the downloads will be stored'''
        self.args = None
        self.threads = 1
        self.url = "http://projectsnyder.net/"
        self.parse_arguments()

    def parse_arguments(self):
        parser = argparse.ArgumentParser(prog='Mass downloader', description='A simple script that downloads multiple files from a list of URLs specified in a file', add_help=True)
        parser.add_argument("threads", type=int, action='store')
        parser.add_argument("url", type=str, action='store')
        args = parser.parse_args()
        self.threads = args.threads
        self.url = args.url

    def download(self):
        r = requests.head(self.url)
        fileType = r.headers['content-type']
        contentLength = int(r.headers['content-length'])
        contentPerThread = contentLength/self.threads
        returnContent = ""
        start = time.clock()
        # create threads
        threads = []
        for i in range(0, self.threads):
            rangeHeader = ""
            if i == 0:
                rangeHeader = {'Range' : 'bytes=' + "0" + "-" + str(contentPerThread*(i+1)), "Accept-Encoding": "identity"}
            elif i == self.threads - 1:
                rangeHeader = {'Range' : 'bytes=' + str(contentPerThread*i + 1) + "-" + str(contentLength), "Accept-Encoding": "identity"}
            else:
                rangeHeader = {'Range' : 'bytes=' + str(contentPerThread*i + 1) + "-" + str(contentPerThread*(i+1)), "Accept-Encoding": "identity"}
            d = DownThread(self.url, rangeHeader)
            threads.append(d)
        for t in threads:
            t.start()
        for t in threads:
            t.join()
            returnContent += t.content
        stop = time.clock()
        timeSpent = stop - start
        extension = fileType.split("/")[1];
        with open("myfile." + extension, "w") as my_file:
            my_file.write(returnContent)
            my_file.close()
        print self.url + " " + str(self.threads) + " " + str(contentLength) + " " + str(timeSpent)

class DownThread(threading.Thread):
    def __init__(self,url, rangeHeader):
        self.url = url
        self.rangeHeader = rangeHeader
        self.content = ""
        threading.Thread.__init__(self)
        self._content_consumed = False

    def run(self):
        r = requests.get(self.url, headers=self.rangeHeader, stream=True)
        self.content = r.content

if __name__ == '__main__':
    d = Downloader()
    d.download()
