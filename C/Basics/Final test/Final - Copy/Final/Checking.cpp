#include "Checking.h"


Checking::Checking(double Balance_in, string Name_in): Account(Balance_in, Name_in)
{
}


Checking::~Checking(void)
{
}

void Checking::advanceMonth()
{
	Balance = Balance -6;

}

bool Checking::writeCheck(double amount)
{
	if((Balance-amount)>0)
			{
				Balance=Balance-5;
				return false;
			}
	else
	{
			Balance=Balance-amount;
			return true;
	}
}