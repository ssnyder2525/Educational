#include "Archer.h"
#include <iostream>
using namespace std;


Archer::Archer(string name_in, string type_in, int mph_in, int strength_in, int speed_in, int magic_in): Fighter(name_in,type_in, mph_in, strength_in, speed_in, magic_in)
{
	currentspeed=speed;
}


Archer::~Archer(void)
{
}

int Archer::getDamage()
	{
		return speed;
	}
void Archer::reset()
{
	currentspeed=speed;
}

bool Archer::useAbility()
{
	currentspeed=currentspeed+1;
	return true;
}