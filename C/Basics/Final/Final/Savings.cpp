#include "Savings.h"


Savings::Savings(double Balance_in, string Name_in): Account(Balance_in, Name_in)
{
}


Savings::~Savings(void)
{
}

void Savings::advanceMonth()
{
	double interest= (tempbalance * (.01))/12;
	tempbalance= tempbalance+interest;
	Balance=tempbalance;
}

bool Savings::withdrawFromSavings(double amount)
{
	if((tempbalance-amount)<0)
			{
				tempbalance=tempbalance-5;
				Balance=tempbalance;
				return false;
			}
	else
	{
			tempbalance=tempbalance-amount;
			Balance=tempbalance;
			return true;
	}
}