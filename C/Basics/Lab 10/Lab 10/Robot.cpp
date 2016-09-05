#include "Robot.h"
#include <iostream>
using namespace std;



Robot::Robot(string name_in, string type_in, int mhp_in, int strength_in, int speed_in, int magic_in) : Fighter(name_in,type_in, mhp_in, strength_in, speed_in, magic_in)
{
	maximum_energy = 2*magic;
	current_energy = maximum_energy;
	bonusdamage=0;
	usebonusdamage=0;
	damage =0;
}


Robot::~Robot(void)
{
}

int Robot::getDamage()
	{
		damage=(strength + bonusdamage);
		//usebonusdamage=bonusdamage;
		bonusdamage=0;
		return damage;
		
	}



void Robot::reset()
{
	currenthp = mhp;
	current_energy= maximum_energy;
	bonusdamage = 0;
}

bool Robot::useAbility()
{
	if(current_energy>=ROBOT_ABILITY_COST)
	{
	bonusdamage = strength  * (pow((current_energy/maximum_energy),4));
	current_energy=current_energy-ROBOT_ABILITY_COST;
	return true;
	}
	else
	{
		return false;
	}
}