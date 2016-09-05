#include "Cleric.h"
#include <iostream>
using namespace std;


Cleric::Cleric(string name_in, string type_in, int mph_in, int strength_in, int speed_in, int magic_in): Fighter(name_in,type_in, mph_in, strength_in, speed_in, magic_in)
{
	maxmana=5*magic;
	mana=5*magic;
}


Cleric::~Cleric(void)
{
}

int Cleric::getDamage()
	{
		return magic;
	}
void Cleric::reset()
{
	mana=maxmana;
}
void Cleric::regenerate()
{
	int reg;
	reg = ((1/5) * magic);
	if (reg<1)
	{
		reg =1;
	}
	mana = mana+ reg;
	if (mana > (5*magic))
	{
		mana = 5*magic;
	}
}
bool Cleric::useAbility()
{
	if (mana>=CLERIC_ABILITY_COST)
	{
		currenthp=currenthp + (1/3)*magic;
		mana=mana-CLERIC_ABILITY_COST;
		return true;
	}
	else
	{
		return false;
	}
}