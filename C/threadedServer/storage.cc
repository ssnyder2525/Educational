#include "storage.h"
#include <iostream>
#include <string>

using namespace std;

Storage::Storage(){
  sem_init(&queueLock,0,1);
  sem_init(&clientsAvailable,0,0);
  sem_init(&messageLock, 0, 1);
}
Storage::~Storage(){}

void Storage::addClientToQueue(int client) {
  sem_wait(&queueLock);
  sem_post(&clientsAvailable);
  clientQueue.push(client);
  sem_post(&queueLock);
}
int Storage::getClientFromQueue() {
  sem_wait(&clientsAvailable);
  sem_wait(&queueLock);
  int client = clientQueue.front();
  clientQueue.pop();
  sem_post(&queueLock);
  return client;
}

void Storage::addMessage(Message &message){
    sem_wait(&messageLock);

    if(messages_.count(message.getRecipient())) {
      map<string,vector<Message>>::iterator it = messages_.find(message.getRecipient());
      it->second.push_back(message);
    }
    else {
      vector<Message> newVec;
      newVec.push_back(message);
      messages_.insert(pair<string, vector<Message>>(message.getRecipient(), newVec));
    }
    sem_post(&messageLock);
  }

  string Storage::getMessage(string &recipient, int index){
    sem_wait(&messageLock);
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

   sem_post(&messageLock);
   return message;
}

string Storage::getMessageList(string &recipient){
  sem_wait(&messageLock);
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
  sem_post(&messageLock);
  return list;
}
