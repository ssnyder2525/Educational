#include "C_D.h"



C_D::C_D(double Balance, string Name): Account(Balance, Name)
{
}


C_D::~C_D(void)
{
}


void C_D::advanceMonth()
{
	Account::advanceMonth();
	if (cdwith=true)
	{
		
	}

	else
	{
		double interest = (tempbalance * (.02))/12;
		tempbalance = tempbalance + interest;
		Balance=tempbalance;
	}

}

bool C_D::withdrawFromSavings(double amount)
	{
		if(passedmonths<9)
		{
			cdwith=true;
			if((tempbalance-amount)>0)
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
		else{Balance=0;
		return true;}
	}