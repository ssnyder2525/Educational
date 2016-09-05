#pragma once

#include <iostream>
#include <string>
#include "message.h"
#include <map>
#include <vector>
#include <sstream>

using namespace std;


class RequestHandler {
  public:
	RequestHandler();
	~RequestHandler();
	
	void reset();
	string getMessage(string, int);
	string getMessageList(string);	
	void addMessage(Message);
	void decrementBytesToRead();
	void clear();
	void cache(string);
	string getAndClearCache();
	string handleRequest(string&);
	string buildWord();
	string parseCommand();
	string parseMessage();
	string put();
	string list();
	string read();
	void cacheCommand();
	
	//Server server;
	istringstream ss;
	map<string, vector<Message>> messages_;
	string tempRecipient;
	string tempSubject;
	string tempMessage;
	int bytesToRead = 0;
	string cacheValue;
	int count = 0;
  };

