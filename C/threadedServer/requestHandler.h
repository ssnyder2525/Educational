#pragma once

#include "storage.h"
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

  void setStorage(Storage*);
	void reset();
	void decrementBytesToRead();
	void clear();
	void cache(string&);
	string getAndClearCache();
	string handleRequest(string&);
	string buildWord(istringstream&);
	string parseCommand(istringstream&);
	string parseMessage(istringstream&);
	string put(istringstream&);
	string list(istringstream&);
	string read(istringstream&);
	void cacheCommand(istringstream&);

	Storage* storage;
  string tempRecipient;
  string tempSubject;
  string tempMessage;
  int count;
  };
