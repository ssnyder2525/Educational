#include "Fighter.h"
#include "Arena.h"


Fighter::Fighter(string name_in, string type_in, int mhp_in, int strength_in, int speed_in, int magic_in)
{
	name=name_in;
	type=type_in;
	mhp=mhp_in;
	strength=strength_in;
	speed=speed_in;
	magic=magic_in;
	currenthp=mhp;
	damage=0;
}


Fighter::~Fighter(void)
{
}

string Fighter::getName()
{
	return name;
}

int Fighter::getMaximumHP()
{
	return mhp;
}

int Fighter::getCurrentHP()
{
	return currenthp;
}

int Fighter::getStrength()
{
	return strength;
}

int Fighter::getSpeed()
{
	return speed;
}

int Fighter::getMagic()
{
	return magic;
}

int Fighter::getDamage()
{
	return false;
}
	
void Fighter::takeDamage(int damage)
{
	damage = (damage - (speed/4));
	if (damage <1)
	{
		damage = 1;
	}
	currenthp=currenthp - damage;
	
}

void Fighter::reset()
{
	
}

void Fighter::regenerate()
{
	int reg;
	reg= (strength/6);
	if (reg<1)
	{
		reg=1;
	}
	
	currenthp = currenthp +reg;
	
	if (currenthp>mhp)
	{
		currenthp=mhp;
	}
	
}

bool Fighter::useAbility()
{
	return false;
}
		
