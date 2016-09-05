#include "CheckingPlusPlus.h"


CheckingPlusPlus::CheckingPlusPlus(double Balance_in, string Name_in): Account(Balance_in, Name_in)
{
}


CheckingPlusPlus::~CheckingPlusPlus(void)
{
}

void CheckingPlusPlus::advanceMonth()
{
	if (Balance<300)
	{
		Balance = Balance-6;
	}

	if (Balance >= 800)
	{
		double interest = (Balance * (.005))/12;
		Balance = Balance + interest;
	}
}

bool CheckingPlusPlus::writeCheck(double amount)
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