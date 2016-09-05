#include "Account.h"
#include <sstream>
#include <string>


Account::Account(double Balance_in, string Name_in):accountNumber(nextaccountNumber++)
{
	Balance=Balance_in;
	Name= Name_in;
	
	passedmonths=0;
	interestrate=0;
	cdwith=false;
}


Account::~Account(void)
{
}

	int Account::getAccountNumber()
	{
		return accountNumber;
	}

	double Account::getCurrentBalance()
	{
		return Balance;
	}

	string Account::getAccountOwner()
	{
		return Name;
	}

	void Account::advanceMonth()
	{
		passedmonths++;
	}

	void Account::deposit(double amount)
	{
		Balance = Balance + amount;
	}

	bool Account::withdrawFromSavings(double amount)
	{
		return false;
	}

	bool Account::writeCheck(double amount)
	{
		return false;
	}

	int Account::nextaccountNumber=0;

	