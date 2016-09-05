#include "Robot.h"
#include <iostream>
using namespace std;



Robot::Robot(string name_in, string type_in, int mph_in, int strength_in, int speed_in, int magic_in) : Fighter(name_in,type_in, mph_in, strength_in, speed_in, magic_in)
{
	maximum_energy = 2*speed;
	current_energy = 2*speed;
	bonusdamage=0;
}


Robot::~Robot(void)
{
}

int Robot::getDamage()
	{
		return strength + bonusdamage;
		bonusdamage=0;
	}

void Robot::reset()
{
	current_energy= 2*speed;
	bonusdamage = 0;
}

bool Robot::useAbility()
{
	if(current_energy>=ROBOT_ABILITY_COST)
	{
	bonusdamage = (strength  * ((current_energy/maximum_energy)^4));
	current_energy=current_energy-ROBOT_ABILITY_COST;
	return true;
	}
	else
	{
		return false;
	}
}