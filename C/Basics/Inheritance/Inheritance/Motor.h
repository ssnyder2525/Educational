#pragma once
#include "Taxi.h"
class Motor :
	public Taxi
{
public:
	Motor(string aname, int nuwheels, int miles,int size);
	
	virtual string tostring() const;

	bool trip(int miles);
	
	~Motor(void);

protected:
	int startmiles;
	int endmiles;
	const int enginesize;
	

};

