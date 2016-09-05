#include "Fighter.h"
#include "Arena.h"


Fighter::Fighter(string name_in, string type_in, int mph_in, int strength_in, int speed_in, int magic_in)
{
	string name=name_in;
	string type=type_in;
	int mph=mph_in;
	int strength=strength_in;
	int speed=speed_in;
	int magic=magic_in;
	
	

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
	return mph;
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
	return damagetaken;
}
	
void Fighter::takeDamage(int damage)
{
	damage = (damage - (speed *(1/4)));
	if (damage <1)
	{
		damage = 1;
	}
	currenthp=currenthp - damage;
	
}

void Fighter::reset()
{
	currenthp = mph;
}

void Fighter::regenerate()
{
	int reg;
	reg= ((1/6) * strength);
	currenthp = currenthp +reg;
}

bool Fighter::useAbility()
{
	return false;
}
		
