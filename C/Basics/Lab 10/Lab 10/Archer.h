#pragma once
#include "Fighter.h"
class Archer :
	public Fighter
{
public:
	Archer(string name_in, string type_in, int mhp_in, int strength_in, int speed_in, int magic_in);
	~Archer(void);
	
	int getDamage();
	
	void reset();

	bool useAbility();
	
private: 
	int currentspeed;
	int bonus;
	int originalspeed;
};


