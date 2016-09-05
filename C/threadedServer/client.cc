#include "client.h"
#include <sstream>
#include <iostream>
#include <string>

using namespace std;

Client::Client(string host, int port) {
    // setup variables
    host_ = host;
    port_ = port;
    buflen_ = 1024;
    buf_ = new char[buflen_+1];
}

Client::~Client() {
}

void Client::run() {
    // connect to the server and run echo program
    create();
    echo();
}

void
Client::create() {
    struct sockaddr_in server_addr;

    // use DNS to get IP address
    struct hostent *hostEntry;
    hostEntry = gethostbyname(host_.c_str());
    if (!hostEntry) {
        cout << "No such host name: " << host_ << endl;
        exit(-1);
    }

    // setup socket address structure
    memset(&server_addr,0,sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(port_);
    memcpy(&server_addr.sin_addr, hostEntry->h_addr_list[0], hostEntry->h_length);

    // create socket
    server_ = socket(PF_INET,SOCK_STREAM,0);
    if (!server_) {
        perror("socket");
        exit(-1);
    }

    // connect to server
    if (connect(server_,(const struct sockaddr *)&server_addr,sizeof(server_addr)) < 0) {
        perror("connect");
        exit(-1);
    }
}

void
Client::close_socket() {
    close(server_);
}

string send(istringstream &iss) {
  string request;
  string user;
  string subject;
  string message = "";
  char newline;
  iss >> user;
  iss >> subject;
  newline = iss.get();
  if(newline != '\n') {
    cout << "did not recognize the command\n";
    return "";
  }
  cout << "- Type your message. End with a blank line -\n";
  string messagePiece;
  while(getline(cin,messagePiece)) {
    if(messagePiece.empty()) {
      break;
    }
    message = message + messagePiece;
  }
  int messageSize = message.size();
  request = "put " + user + " " + subject + " " + to_string(messageSize) + "\n" + message;
  return request;  
}

string list(istringstream &iss) {
  string request;
  string user;
  char newline;
  iss >> user;
  newline = iss.get();
  if(newline != '\n') {
    cout << "did not recognize the command\n";
    return "";
  }
  
  request = "list " + user + "\n";
  return request;
}

string read(istringstream &iss) {
  string request;
  string name;
  string index;
  char newline;
  iss >> name;
  iss >> index;
  newline = iss.get();
  if(newline != '\n') {
    cout << "did not recognize the command\n";
    return "";
  }
 
  request = "get " + name + " " + index + "\n";
  return request; 
}

string checkCommand(string command) {
   string request;
   istringstream iss(command);
   string word;
   iss >> word;
   if(word == "send") {
     request = send(iss);
   }
   else if(word == "list") {
     request = list(iss);
   }
   else if(word == "read") {
     request = read(iss);
   }
   else if(word == "quit") {
     exit(0);
   }
   else {
     cout << "did not recognize the command\n";
   }
   return request;
}

void
Client::echo() {
    string command;
    cout<<'%';
    // loop to handle user interface
    while (getline(cin,command)) {
        // append a newline
        command += "\n";
	//check the user's input
	string request = checkCommand(command);
        // send request
	if(request != "") {
	  bool success = send_request(request);
	  // break if an error occurred
	  if (not success)
            break;
	  // get a response
	  success = get_response();
	  // break if an error occurred
	  if (not success)
            break;
	}
	cout<<"\n%";
    }
    close_socket();
}

bool
Client::send_request(string request) {
    // prepare to send request
    const char* ptr = request.c_str();
    int nleft = request.length();
    int nwritten;
    // loop to be sure it is all sent
    while (nleft) {
      cout<<"request: " + request + "()\n";
        if ((nwritten = send(server_, ptr, nleft, 0)) < 0) {
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
Client::get_response() {
    string response = "";
    // read until we get a newline
    while (response.find("\n") == string::npos) {
        int nread = recv(server_,buf_,1024,0);
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
        response.append(buf_,nread);
    }
    // a better client would cut off anything after the newline and
    // save it in a cache
    cout << response;
    return true;
}
