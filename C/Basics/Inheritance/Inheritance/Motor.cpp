#include "Motor.h"
#include <sstream>



Motor::Motor(string aname, int nuwheels, int miles, int size) : Taxi(aname,nuwheels), startmiles(miles), enginesize(size)
{
}


Motor::~Motor(void)
{
}

string Motor::tostring() const
{
	stringstream s;
	s<< Taxi::tostring()<<"startmiles " <<startmiles<<" id: "<<id<<" endmiles: "<<endmiles<<" enginesize: "<<enginesize;
}

bool Motor::trip(int miles)
{
	trip++;
	endmiles=endmiles+miles;
}

