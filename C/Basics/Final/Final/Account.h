#pragma once
#include "accountinterface.h"
class Account :
	public AccountInterface
{
public:
	~Account(void);

	Account::Account(double Balance_in, string Name_in);

	int getAccountNumber();

	double getCurrentBalance();

	string getAccountOwner();

	virtual void AccountInterface::advanceMonth();

	void deposit(double amount);

	virtual bool withdrawFromSavings(double amount);

	virtual bool writeCheck(double amount);

protected:
	int accountNumber;
	static int nextaccountNumber;
	int Balance;
	double tempbalance;
	string Name;
	bool TorF;
	int passedmonths;
	double interestrate;
	bool cdwith;//checks if cd has had a withdraw.
};

