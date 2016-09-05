#include <iostream>
#include "Dog.h"
 
 
Dog::Dog(string new_name) : Animal(new_name){};
void Dog::do_act() const { cout <<"Bark Bark!"<<endl; };