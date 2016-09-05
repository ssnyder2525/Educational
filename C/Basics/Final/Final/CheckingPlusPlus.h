#pragma once
#include "account.h"
class CheckingPlusPlus :
	public Account
{
public:
	CheckingPlusPlus(double Balance_in, string Name_in);
	~CheckingPlusPlus(void);

	void CheckingPlusPlus::advanceMonth();

	bool writeCheck(double amount);

};

