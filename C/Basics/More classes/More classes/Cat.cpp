#include "Cat.h"
#include <iostream>
 
Cat::Cat(string new_name) : Animal(new_name){mouse = 4;}
 
void Cat::do_act() const { cout <<"Scratch Scratch! "<<mouse<<endl; }