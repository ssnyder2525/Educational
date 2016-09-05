#include "CheckingPlus.h"


CheckingPlus::CheckingPlus(double Balance_in, string Name_in): Account(Balance_in, Name_in)
{
}


CheckingPlus::~CheckingPlus(void)
{
}

void CheckingPlus::advanceMonth()
{
	if (Balance<300)
	{
		Balance = Balance-6;
	}
}

bool CheckingPlus::writeCheck(double amount)
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