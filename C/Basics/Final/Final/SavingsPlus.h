#pragma once
#include "account.h"
class SavingsPlus :
	public Account
{
public:
	SavingsPlus(double Balance_in, string Name_in);
	~SavingsPlus(void);

	void SavingsPlus::advanceMonth();

	bool withdrawFromSavings(double amount);
};


