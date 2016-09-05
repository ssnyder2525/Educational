#include "Taxi.h"
#include <sstream>


Taxi::Taxi(string anowner, int nuwheels): wheels(nuwheels), owner(anowner), id(nextid++)//These are for const variables. Change a const variable.
{
	this->trips=0;// this is a pointer that points to a current objects. Not necessary in this case. Specifies which taxi's trips. Ex. trips = trips? No! this->trips = trips.
}


Taxi::~Taxi(void)
{
}


string Taxi::tostring() const
{
	stringstream s;
	s<<"Owner: " << owner << "id: " << id << " trips: "<< trips << "wheels: " << wheels;
	return s.str();
}

bool Taxi::trip()//in case I don't want to give the miles.
{
	return false;
}

bool Taxi::trip(int miles)
{
	return false;
}

void Taxi::reset()
{
	trips = 0;
}
int Taxi::nextid=0;