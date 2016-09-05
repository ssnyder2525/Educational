#pragma once
#include <string>
#include <vector>
#include "Animal.h"
 
class House {
public:
	House(string new_name);
	void add_pet(Animal *new_pet);
	string get_familyname();
	string toString();
	void do_act();
private:
	string familyname;
	vector <Animal *> pets;
};