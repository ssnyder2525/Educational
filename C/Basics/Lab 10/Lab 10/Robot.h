#pragma once
#include <iostream>
#include "Fighter.h"
#include "FighterInterface.h"

using namespace std;
class Robot : 
	public Fighter
{
public:
	Robot(string name_in, string type_in, int mhp_in, int strength_in, int speed_in, int magic_in);
	
	~Robot(void);

	

	int getDamage();
	
	void reset();

	bool useAbility();


private:
	double current_energy;
	double maximum_energy;
	double bonusdamage;
	int usebonusdamage;
	int damage;
};

