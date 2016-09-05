#pragma once
#include "Motor.h"
class Car :
	public Motor
{
public:
	Car(string name, int nuwheels, int miles, int enginesize);
	~Car(void);
private:
	int startmiles;
	int currentmiles;
	int enginesize;

};

