#pragma once

#include <iostream>
#include <string>
#include <list>
#include <map>
#include <sstream>
#include <vector>
#include <queue>
#include <string>
#include "message.h"

#include <pthread.h>
#include <semaphore.h>

using namespace std;


class Storage {
  public:
      Storage();
      ~Storage();
      //istringstream ss;
      map<string, vector<Message>> messages_;
      int bytesToRead = 0;
      string cacheValue;
      sem_t clientsAvailable;
      sem_t queueLock;
      sem_t messageLock;
      int numClients = 0;
      queue<int> clientQueue;

      void addClientToQueue(int);
      int getClientFromQueue();

      void addMessage(Message&);
      string getMessage(string&, int);
      string getMessageList(string&);

  };
