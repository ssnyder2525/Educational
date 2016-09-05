#include "SavingsPlus.h"


SavingsPlus::SavingsPlus(double Balance_in, string Name_in): Account(Balance_in, Name_in)
{
}


SavingsPlus::~SavingsPlus(void)
{
}


void SavingsPlus::advanceMonth()
{
	if(Balance >= 1000)
	{
		double interest= (tempbalance * (.0125))/12;
		tempbalance= tempbalance+interest;
		Balance=tempbalance;
		
	}
	else
	{
	int interest= (tempbalance * (.1))/12;
	tempbalance= tempbalance+interest;
	Balance=tempbalance;
	
	}



}

bool SavingsPlus::withdrawFromSavings(double amount)
{
	if((tempbalance-amount)<0)
			{
				tempbalance=tempbalance-5;
				Balance=tempbalance;
				return false;
			}
	else
	{
			if ((tempbalance-amount)<1000)
			{
			tempbalance=tempbalance-amount;
			Balance=tempbalance;
			interestrate=.01;
			return true;
			}

			else
			{
				tempbalance=tempbalance-amount;
				Balance=tempbalance;
				interestrate=.0125;
				return true;
			}
	}
}
