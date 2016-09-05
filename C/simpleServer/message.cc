#include "message.h"
#include <iostream>
#include <string>

using namespace std;

Message::Message(string recipient, string subject, string message) {
  setRecipient(recipient);
  setSubject(subject);
  setMessage(message);
}
Message::~Message() {}

  string Message::getRecipient() {
    return recipient_;
  }
  string Message::getSubject() {
    return subject_;
  }
  string Message::getMessage() {
    return message_;
  }

  void Message::setRecipient(string recipient) {
    recipient_ = recipient;
  }
void Message::setSubject(string subject) {
    subject_ = subject;
  }
  void Message::setMessage(string message) {
    message_ = message;
  }

