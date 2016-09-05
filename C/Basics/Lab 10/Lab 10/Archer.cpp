#include "Archer.h"
#include <iostream>
using namespace std;


Archer::Archer(string name_in, string type_in, int mhp_in, int strength_in, int speed_in, int magic_in): Fighter(name_in,type_in, mhp_in, strength_in, speed_in, magic_in)
{
	speed=speed_in;
	originalspeed=speed_in;
	bonus =0;
}


Archer::~Archer(void)
{
}

int Archer::getDamage()
	{
		
		damage = speed;
		return damage;
	}

void Archer::reset()
{
	currenthp = mhp;
	speed=originalspeed;
}

bool Archer::useAbility()
{
	
	speed++;
	return true;
}