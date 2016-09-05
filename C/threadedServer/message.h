#pragma once

#include <iostream>
#include <string>

using namespace std;


class Message {
      public:
	Message(string, string, string);
	~Message();
	string getRecipient();
	string getSubject();
	string getMessage();

	void setRecipient(string);
	void setSubject(string);
	void setMessage(string);

      private:
	string recipient_;
	string subject_;
	string message_;
  };

