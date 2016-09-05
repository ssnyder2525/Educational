#pragma once
#include "account.h"
class Savings :
	public Account
{
public:
	Savings(double Balance_in, string Name_in);
	~Savings(void);

	void Savings::advanceMonth();

	bool withdrawFromSavings(double amount);
};

