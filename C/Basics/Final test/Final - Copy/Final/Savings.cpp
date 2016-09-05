#include "Savings.h"


Savings::Savings(double Balance_in, string Name_in): Account(Balance_in, Name_in)
{
}


Savings::~Savings(void)
{
}

void Savings::advanceMonth()
{
	double interest= (Balance * (.01))/12;
	Balance= Balance+interest;
}

bool Savings::withdrawFromSavings(double amount)
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