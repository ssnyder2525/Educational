#pragma once
#include "account.h"
class Checking :
	public Account
{
public:
	Checking(double Balance_in, string Name_in);
	~Checking(void);

	void Checking::advanceMonth();

	bool writeCheck(double amount);
	
};

