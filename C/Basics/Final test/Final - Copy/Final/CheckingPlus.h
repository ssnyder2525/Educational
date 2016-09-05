#pragma once
#include "account.h"
class CheckingPlus :
	public Account
{
public:
	CheckingPlus(double Balance_in, string Name_in);
	~CheckingPlus(void);

	void CheckingPlus::advanceMonth();

	bool writeCheck(double amount);
};

