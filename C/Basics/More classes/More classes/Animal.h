#pragma once
#include <string>
 
using namespace std;
 
class Animal
{
public:
	Animal(string new_name);
	virtual void do_act() const = 0;
	string toString();
private:
	string name;
};