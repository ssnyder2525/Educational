#pragma once
#include "Fighter.h"
class Archer :
	public Fighter
{
public:
	Archer(string name_in, string type_in, int mph_in, int strength_in, int speed_in, int magic_in);
	~Archer(void);
	
	virtual int getDamage();
	virtual void reset();

	virtual bool useAbility();
	
private: 
	int currentspeed;
};


