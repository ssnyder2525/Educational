#pragma once
#include "bankinterface.h"
#include <vector>
#include "Account.h"
#include "AccountInterface.h"

class Bank :
	public BankInterface
{
public:
	Bank(void);
	~Bank(void);

	bool openAnAccount(string info);

	bool closeAnAccount(int accountNumber);

	AccountInterface* getAnAccount(int accountNumber);

	void advanceMonth();

	int getNumberOfAccounts();


private:
	vector <AccountInterface*> allaccounts;
	int positionmarker;
};

