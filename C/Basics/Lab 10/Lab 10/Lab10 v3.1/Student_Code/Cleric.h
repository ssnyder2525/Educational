#pragma once
#include "Fighter.h"
class Cleric :
	public Fighter
{
public:
	Cleric(string name_in, string type_in, int mph_in, int strength_in, int speed_in, int magic_in);
	~Cleric(void);

	virtual int getDamage();
	virtual void reset();
	virtual void regenerate();
	virtual bool useAbility();

private:
	int maxmana;
	int mana;
};

