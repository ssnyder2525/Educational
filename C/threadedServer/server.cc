#include "server.h"
#include "threadQueue.h"
#include <iostream>
#include <pthread.h>
#include <semaphore.h>
#include "requestHandler.h"



using namespace std;

Server::Server(int port) {
    // setup variables
    port_ = port;
    buflen_ = 1024;
    buf_ = new char[buflen_+1];
}

Server::~Server() {
    delete buf_;
}

void
Server::run() {
    // create and run the server
    create();
    serve();
}

void
Server::create() {
    struct sockaddr_in server_addr;

    // setup socket address structure
    memset(&server_addr,0,sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(port_);
    server_addr.sin_addr.s_addr = INADDR_ANY;

    // create socket
    server_ = socket(PF_INET,SOCK_STREAM,0);
    if (!server_) {
        perror("socket");
        exit(-1);
    }

    // set socket to immediately reuse port when the application closes
    int reuse = 1;
    if (setsockopt(server_, SOL_SOCKET, SO_REUSEADDR, &reuse, sizeof(reuse)) < 0) {
        perror("setsockopt");
        exit(-1);
    }

    // call bind to associate the socket with our local address and
    // port
    if (bind(server_,(const struct sockaddr *)&server_addr,sizeof(server_addr)) < 0) {
        perror("bind");
        exit(-1);
    }

    // convert the socket to listen for incoming connections
    if (listen(server_,SOMAXCONN) < 0) {
        perror("listen");
        exit(-1);
    }
}

void
Server::close_socket() {
    close(server_);
}

void
Server::serve() {
    rh.setStorage(&storage);
    void *getClient(void *);

    for(int i = 0; i < 10; i++)
    {
      pthread_t tidA;
      pthread_create(&tidA, NULL, &getClient, this);
    }

    // setup client
    int client;
    struct sockaddr_in client_addr;
    socklen_t clientlen = sizeof(client_addr);

      // accept clients
    while ((client = accept(server_,(struct sockaddr *)&client_addr,&clientlen)) > 0) {
        //add client to queue.
        storage.addClientToQueue(client);
    }
    close_socket();
}

void * getClient(void * serverPtr)
{
  Server* server;
  server = (Server*) serverPtr;

  while(1)
  {
    //getClient
    int client = server->storage.getClientFromQueue();
    //handleClient
    bool keepClient = server->Server::handle(client);
    if(keepClient)
    {
      server->storage.addClientToQueue(client);
    }
  }
}

string
Server::get_request(int client) {
    string request = "";
    // read until we get a newline
    while (1) {
        int nread = recv(client,buf_,1024,0);
        if (nread < 0) {
            if (errno == EINTR)
                // the socket call was interrupted -- try again
                continue;
            else
                // an error occurred, so break out
                return "";
        } else if (nread == 0) {
            // the socket is closed
            return "";
        }
        // be sure to use append in case we have binary data
        request.append(buf_,nread);
		if(nread < 1024) {
			break;
		}
    }
    // a better server would cut off anything after the newline and
    // save it in a cache
    return request;
}

bool
Server::send_response(int client, string response) {
    // prepare to send response
    const char* ptr = response.c_str();
    int nleft = response.length();
    int nwritten;
    // loop to be sure it is all sent
    while (nleft) {
        if ((nwritten = send(client, ptr, nleft, 0)) < 0) {
            if (errno == EINTR) {
                // the socket call was interrupted -- try again
                continue;
            } else {
                // an error occurred, so break out
                perror("write");
                return false;
            }
        } else if (nwritten == 0) {
            // the socket is closed
            return false;
        }
        nleft -= nwritten;
        ptr += nwritten;
    }
    return true;
}

bool
Server::handle(int client) {
    // get a request
    string request = get_request(client);
		string cache = "";
		// break if client is done or an error occurred
		if (request.empty() && cache == "") {
			//cout<<"error request failed: empty request recieved\n";
	     return false;
		}
		if(cache != "") {
			request = cache + request;
		}
		string response = rh.handleRequest(request);

		// send response
		bool success = send_response(client,response);
		// break if an error occurred
		if (not success)
		{
        close(client);
        return false;
    }
      return true;
}
