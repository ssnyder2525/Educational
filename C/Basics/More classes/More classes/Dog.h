#pragma once
#include "animal.h"
 
class Dog : public Animal {
public:
	Dog(string new_name);
	virtual void do_act() const;
};