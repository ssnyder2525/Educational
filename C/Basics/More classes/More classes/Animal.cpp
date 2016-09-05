#include "Animal.h"
#include <iostream>

Animal::Animal(string new_name)
{
	name = new_name;
}
 
string Animal::toString() {
	return(name);
}