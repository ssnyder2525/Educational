#pragma once
#include "Animal.h"
 
class Cat : public Animal {
public:
	Cat(string new_name);
	virtual void do_act() const;
private:
	int mouse;
};