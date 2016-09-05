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
		double interest = (Balance * (.02))/12;
		Balance = Balance + interest;
	}

}

bool C_D::withdrawFromSavings(double amount)
	{
		if(passedmonths<9)
		{
			cdwith=true;
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
		else{Balance=0;
		return true;}
	}