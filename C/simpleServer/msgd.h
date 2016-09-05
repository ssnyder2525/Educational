#pragma once

#include <errno.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include "requestHandler.h"

using namespace std;

class Server {
public:
    Server(int port);
    ~Server();
 
    void run();
    
private:
    void create();
    void close_socket();
    void serve();
    void handle(int, RequestHandler&);
    string get_request(int);
    bool send_response(int, string);

    int port_;
    int server_;
    int buflen_;
    char* buf_;
};