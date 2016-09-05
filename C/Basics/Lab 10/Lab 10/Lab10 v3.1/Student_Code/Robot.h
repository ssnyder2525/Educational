#pragma once
#include <iostream>
#include "Fighter.h"
#include "FighterInterface.h"

using namespace std;
class Robot : 
	public Fighter
{
public:
	Robot(string name_in, string type_in, int mph_in, int strength_in, int speed_in, int magic_in);
	
	~Robot(void);

	

	virtual int getDamage();
	virtual void reset();

	virtual bool useAbility();


private:
	int current_energy;
	int maximum_energy;
	int bonusdamage;
};

