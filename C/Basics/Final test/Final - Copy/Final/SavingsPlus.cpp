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
		int interest= (Balance * (interestrate))/12;
		Balance= Balance+interest;
	}
	else
	{
	int interest= (Balance * (interestrate))/12;
	Balance= Balance+interest;
	}



}

bool SavingsPlus::withdrawFromSavings(double amount)
{
	if((Balance-amount)>0)
			{
				Balance=Balance-5;
				return false;
			}
	else
	{
			if ((Balance-amount)<1000)
			{
			Balance=Balance-amount;
			interestrate=.01;
			return true;
			}

			else
			{
				Balance=Balance-amount;
				interestrate=.0125;
				return true;
			}
	}
}
