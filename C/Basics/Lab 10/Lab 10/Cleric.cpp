#include "Cleric.h"
#include <iostream>
using namespace std;


Cleric::Cleric(string name_in, string type_in, int mhp_in, int strength_in, int speed_in, int magic_in): Fighter(name_in,type_in, mhp_in, strength_in, speed_in, magic_in)
{
	maxmana=5*magic;
	mana=5*magic;
}


Cleric::~Cleric(void)
{
}

int Cleric::getDamage()
	{
		int damage = magic;
		return damage;
	}

void Cleric::reset()
{
	currenthp = mhp;
	mana=maxmana;
}
void Cleric::regenerate()
{
	Fighter::regenerate();
	int reg;
	reg = (magic/5);
	if (reg<1)
	{
		reg =1;
	}
	mana = mana + reg;
	if (mana > (5*magic))
	{
		mana = (5*magic);
	}
	
}
bool Cleric::useAbility()
{
	if (mana>=CLERIC_ABILITY_COST)
	{
		currenthp=currenthp + (magic/3);
		if (currenthp>mhp)
		{
			currenthp=mhp;
		}
		mana=mana-CLERIC_ABILITY_COST;
		return true;
	}
	else
	{
		return false;
	}
}