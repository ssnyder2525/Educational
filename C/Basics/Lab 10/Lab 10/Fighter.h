#pragma once
#include "FighterInterface.h"
#include "Arena.h"
#include "Fighter.h"
class Fighter :
	public FighterInterface
{
public:
	Fighter::Fighter(void);
	Fighter::Fighter(string name_in, string type_in, int mhp_in, int strength_in, int speed_in, int magic_in);
	~Fighter();
		string getName();

		int getMaximumHP();

		int getCurrentHP();

		int getStrength();

		int getSpeed();

		int getMagic();

		virtual int getDamage();
	
		virtual void takeDamage(int damage);

		virtual void reset();

		virtual void regenerate();
		
		virtual bool useAbility()=0;

protected:
	string name;
			string type;
			int mhp;
			int strength;
			int speed;
			int magic;
			int currenthp;
			int damage;
			
};

