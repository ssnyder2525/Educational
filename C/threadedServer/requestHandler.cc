#include "requestHandler.h"
#include "message.h"
#include <iostream>
#include <string>
#include <sstream>

using namespace std;

RequestHandler::RequestHandler() {
}

RequestHandler::~RequestHandler() {}

void RequestHandler::setStorage(Storage* st)
{
	storage = st;
}

void RequestHandler::reset() {
	tempRecipient = "";
	tempSubject = "";
	tempMessage = "";
	storage->messages_.clear();
}

void RequestHandler::decrementBytesToRead() {
	storage->bytesToRead--;
}

void RequestHandler::clear() {
	tempRecipient = "";
	tempSubject = "";
	tempMessage = "";
}

void RequestHandler::cache(string &value) {
	storage->cacheValue = value;
}

string RequestHandler::getAndClearCache() {
	string ret = storage->cacheValue;
	storage->cacheValue = "";
	return ret;
}

string RequestHandler::buildWord(istringstream &ss) {
  	string word;
  	char c;

  	//parse a word
  	while(ss.peek() != ' ' && ss.peek() != '\n' && ss.peek() != EOF) {
    	c = ss.get();
    	word = word + c;
  	}
  	//skip the ' '.
  	if(ss.peek() == ' ') {
    	ss.get();
  	}

	  return word;
}

string RequestHandler::parseMessage(istringstream &ss) {
  	string message = tempMessage;
  	//write to the file for the indicated amount of bytes
  	while(storage->bytesToRead > 0) {
    	if(ss.peek() == EOF) {
			break;
		}
		char c = ss.get();
    	message += c;
    	decrementBytesToRead();
  	}
  	tempMessage = message;
	if(storage->bytesToRead == 0) {
  		// build message
  		Message message(tempRecipient, tempSubject, tempMessage);
		//store the message
  		storage->addMessage(message);
  		clear();
		return "OK\n";
	}
  	return "";
}

void RequestHandler::cacheCommand(istringstream &ss) {
	if(ss.peek() == '\n') {
		ss.get();
	}
	string command;
	while(ss.peek() != EOF) {
		char c = ss.get();
		command += c;
	}
	cache(command);
}

string RequestHandler::put(istringstream &ss) {
  //read the recipient
  tempRecipient = buildWord(ss);
  if(ss.peek() == '\n' || tempRecipient == "") {
    return "error I did not recognize that command\n";
  }

  //read the subject
  tempSubject = buildWord(ss);
  if(ss.peek() == '\n' || tempSubject == "") {
    return "error I did not recognize that command\n";
  }

  //read the number of bytes to read.
  string bytes = buildWord(ss);
  if(ss.peek() != '\n' || bytes == "") {
    return "error I did not recognize that command\n";
  }

  storage->bytesToRead = stoi(bytes);
  if(storage->bytesToRead < 0) {
    return "error invalid number of bytes.\n";
  }

  //skip the \n character.
  ss.get();
  //read and store the message
  return parseMessage(ss);
}

string RequestHandler::list(istringstream &ss) {
  string recipient;
//read the recipient
  recipient = buildWord(ss);
  if(ss.peek() != '\n' || recipient == "") {
    return "error I did not recognize that command\n";
  }
  string response = storage->getMessageList(recipient);
  return response;
}

string RequestHandler::read(istringstream &ss) {
 string recipient;
 string indexS;
 int index;
//read the recipient
  recipient = buildWord(ss);
  if(ss.peek() == '\n' || recipient == "") {
    return "error I did not recognize that command\n";
  }
//read the index
  indexS = buildWord(ss);
  if(ss.peek() != '\n' || indexS == "") {
    return "error I did not recognize that command\n";
  }

  index = stoi(indexS);
  if(index <= 0) {
    return "error invalid number of bytes.\n";
  }

  string response = storage->getMessage(recipient, index);
  return response;
}

string RequestHandler::parseCommand(istringstream &ss) {
	string command;
	string response;
	//read the command
  	command = buildWord(ss);
 	if(command == "put") {
   	response = put(ss);
  	}
  	else if(command == "list") {
   	 response = list(ss);
  	}
  	else if(command == "get") {
   	 response = read(ss);
  	}
  	else if(command == "reset") {
    	reset();
    	response = "OK\n";
  	}
  	else {
    	response = "error invalid command!\n";
  	}
  	return response;
}

string RequestHandler::handleRequest(string &request) {
	istringstream ss;
	string response = "";
	ss.str(request);
	if(storage->bytesToRead == 0) {
		response = parseCommand(ss);
	}
	else {
		response = parseMessage(ss);
	}
	if(response != "") {
		if(ss.peek() != EOF) {
			cacheCommand(ss);
		}
	}
	return response;
}
