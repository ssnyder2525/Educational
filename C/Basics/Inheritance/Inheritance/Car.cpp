#include "Car.h"

const int wheelsinacar=4;
Car::Car(string name, int nuwheels, int miles, int enginesize) : Motor(name, wheelsinacar, miles, size)
{
}


Car::~Car(void)
{
}

int Car::taxreport()
{
	if (enginesize)
	{

	}