#pragma once
#include<string>
using namespace std;

class Account 
{
public:
	~Account(void);

	Account::Account(double Balance_in, string Name_in);

	int getAccountNumber();

	double getCurrentBalance();

	string getAccountOwner();

	virtual void advanceMonth();

	void deposit(double amount);

	virtual bool withdrawFromSavings(double amount);

	virtual bool writeCheck(double amount);

	

protected:
	const int accountNumber;
	static int nextaccountNumber;
	double Balance;
	string Name;
	bool TorF;
	int passedmonths;
	double interestrate;
	bool cdwith;//checks if cd has had a withdraw.
};

