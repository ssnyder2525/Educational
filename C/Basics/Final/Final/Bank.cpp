#include "Bank.h"
#include <sstream>
#include <iostream>
#include <fstream>
#include "Checking.h"
#include "CheckingPlus.h"
#include "CheckingPlusPlus.h"
#include "Savings.h"
#include "SavingsPlus.h"
#include "C_D.h"
#include "Account.h"

Bank::Bank(void)
{
}


Bank::~Bank(void)
{
}
	string type;
	double balance;
	string name;
	string n;

bool Bank::openAnAccount(string info)
{
	
	
	stringstream s(info);
	s>>type>>balance>>name>>n;

	
	if (s.fail())
	{
		if (s.eof())
		{
			
			if ((type=="Checking") && (balance>0))
			{
				AccountInterface* newaccount = new Checking(balance, name);
				allaccounts.push_back(newaccount);
				
				return true;
				
			}

			else if ((type=="Checking+") && (balance>=300))
			{
				AccountInterface* newaccount = new CheckingPlus(balance, name);
				allaccounts.push_back(newaccount);
				return true;
			}

			else if ((type=="Checking++") && (balance>=800))
			{
				AccountInterface* newaccount = new CheckingPlusPlus(balance, name);
				allaccounts.push_back(newaccount);
				return true;
			}

			else if ((type=="Saving") && (balance>0))
			{
				AccountInterface* newaccount = new Savings(balance, name);
				allaccounts.push_back(newaccount);
				return true;
			}

			else if ((type=="Saving+") && (balance>=1000))
			{
				AccountInterface* newaccount = new SavingsPlus(balance, name);
				allaccounts.push_back(newaccount);
				return true;
			}

			else if ((type=="CD") && (balance>0))
			{
				AccountInterface* newaccount = new C_D(balance, name);
				allaccounts.push_back(newaccount);
				return true;
			}

			else
			{
				
				return false;
			}
		}

		else
		{
			return false;
		}
	}

	else
	{
		return false;
	}
}

bool Bank::closeAnAccount(int accountNumber)
{
	for (unsigned int i=0; i<allaccounts.size();i++)
	{
		if (accountNumber == allaccounts[i]->getAccountNumber())
		{
			double cash = allaccounts[i]->getCurrentBalance();
			if (cash >0)
			{
			allaccounts.erase(allaccounts.begin()+i);
			return true;
			}
			else
			{
				return false;
			}
		}
		
	}
	return false;
}

AccountInterface* Bank::getAnAccount(int accountNumber)
{
	for (unsigned int i=0; i<allaccounts.size();i++)
	{
		if (accountNumber == allaccounts[i]->getAccountNumber())
		{
			return allaccounts[i];
		}
		
	}
	return NULL;
}

void Bank::advanceMonth()
{
	for (unsigned int i=0; i<allaccounts.size(); i++)
	{
		allaccounts[i]->advanceMonth();
	}
}

int Bank::getNumberOfAccounts()
{
	int number=allaccounts.size();
	return number;
}