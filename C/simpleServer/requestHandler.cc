#include "requestHandler.h"
#include "message.h"
#include <iostream>
#include <string>

using namespace std;

RequestHandler::RequestHandler() {}

RequestHandler::~RequestHandler() {}

void RequestHandler::reset() {
	tempRecipient = "";
	tempSubject = "";
	tempMessage = "";
	this->messages_.clear();
}

string RequestHandler::getMessage(string recipient, int index) {
   string message;
  if(messages_.count(recipient)) {
     map<string,vector<Message>>::iterator it = messages_.find(recipient);
     try {
       message= "message " + it->second[index-1].getSubject() + " " + to_string(it->second[index -1].getMessage().size()) + "\n" + it->second[index-1].getMessage();
     }
     catch (int e) {
       return "error No message at index: " + to_string(index) + "\n";
     }
  }
  else {
    return "error There are no messages for " + recipient + "\n";
  }
  return message;
}

string RequestHandler::getMessageList(string recipient) {
  string list = "";
  if(messages_.count(recipient)) {
     map<string,vector<Message>>::iterator it = messages_.find(recipient);
     list += "list " + to_string(it->second.size());
     list += "\n";
     for(unsigned int i = 0; i < it->second.size();i++) {
       list += to_string(i+1) + " " + it->second[i].getSubject() + "\n";
     }
  }
  else {
    return "error There are no messages for " + recipient + "\n";
  }
  return list;
}	

void RequestHandler::addMessage(Message message) {
	//cout<<"adding: " + message.getMessage();
  	if(messages_.count(message.getRecipient())) {
    	map<string,vector<Message>>::iterator it = messages_.find(message.getRecipient());
    	it->second.push_back(message);
  	}
  	else {
    	vector<Message> newVec;
    	newVec.push_back(message);
    	messages_.insert(pair<string, vector<Message>>(message.getRecipient(), newVec)); 
  	}
}

void RequestHandler::decrementBytesToRead() {
	this->bytesToRead--;
}

void RequestHandler::clear() {
	tempRecipient = "";
	tempSubject = "";
	tempMessage = "";
}

void RequestHandler::cache(string value) {
	this->cacheValue = value;
}

string RequestHandler::getAndClearCache() {
	string ret = this->cacheValue;
	this->cacheValue = "";
	return ret;
}

string RequestHandler::buildWord() {
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

string RequestHandler::parseMessage() {
  	string message = tempMessage;
  	//write to the file for the indicated amount of bytes
  	while(bytesToRead > 0) { 
    	if(ss.peek() == EOF) {
			break;	
		}
		char c = ss.get();
    	message += c;
		//count++;
    	decrementBytesToRead();
  	}
  	tempMessage = message;
	if(bytesToRead == 0) {
  		// build message
  		Message message(tempRecipient, tempSubject, tempMessage);
		//store the message
  		addMessage(message);
		//cout<<"added " + to_string(count) + "characters";
		count = 0;
  		clear();
		return "OK\n";
	}
  	return "";
}

void RequestHandler::cacheCommand() {
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

string RequestHandler::put() { 
  //read the recipient
  tempRecipient = buildWord();
  if(ss.peek() == '\n' || tempRecipient == "") {
    return "error I did not recognize that command\n";
  }
	
  //read the subject
  tempSubject = buildWord();
  if(ss.peek() == '\n' || tempSubject == "") {
    return "error I did not recognize that command\n";
  }
	
  //read the number of bytes to read.
  string bytes = buildWord();
  if(ss.peek() != '\n' || bytes == "") {
    return "error I did not recognize that command\n";
  }

  bytesToRead = stoi(bytes);
  if(bytesToRead < 0) {
    return "error invalid number of bytes.\n";
  }
	
  //skip the \n character.
  ss.get();
  //read and store the message
  return parseMessage();
}

string RequestHandler::list() {
  string recipient;
//read the recipient
  recipient = buildWord();
  if(ss.peek() != '\n' || recipient == "") {
    return "error I did not recognize that command\n";
  }
  string response = getMessageList(recipient);
  return response;
}

string RequestHandler::read() {
 string recipient;
 string indexS;
 int index;
//read the recipient
  recipient = buildWord();
  if(ss.peek() == '\n' || recipient == "") {
    return "error I did not recognize that command\n";
  }
//read the index
  indexS = buildWord();
  if(ss.peek() != '\n' || indexS == "") {
    return "error I did not recognize that command\n";
  }

  index = stoi(indexS);
  if(index <= 0) {
    return "error invalid number of bytes.\n";
  }

  string response = getMessage(recipient, index);
  return response;
}

string RequestHandler::parseCommand() {
	string command;
	string response;
	//read the command
  	command = buildWord();
 	if(command == "put") {
   	response = put();
  	}
  	else if(command == "list") {
   	 response = list();
  	}
  	else if(command == "get") {
   	 response = read();
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
	ss.clear();
	string response = "";
	ss.str(request);
	if(bytesToRead == 0) {
		response = parseCommand();
	}
	else {
		response = parseMessage();	
	}
	if(response != "") {
		if(ss.peek() != EOF) {
			cacheCommand();
		}  
	}
	return response;
}