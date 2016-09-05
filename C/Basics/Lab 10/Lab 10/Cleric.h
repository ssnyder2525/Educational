#pragma once
#include "Fighter.h"
class Cleric :
	public Fighter
{
public:
	Cleric(string name_in, string type_in, int mhp_in, int strength_in, int speed_in, int magic_in);
	~Cleric(void);

	virtual int getDamage();
	
	void reset();
	void regenerate();
	bool useAbility();

private:
	int maxmana;
	int mana;
};

