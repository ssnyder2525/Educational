#pragma once
#include <string>
#include "TaxiInterface.h"

using namespace std;

class Taxi : public TaxiInterface
{
public:
	Taxi(string anowner, int nuwheels);
	~Taxi(void);
	virtual string tostring() const;
	virtual bool trip (int miles);
	virtual bool trip ();

protected:
	protected:
	const string owner;
	const int id;
	int trips;
	const int wheels;
	static int nextid;//means every class shares it.
	virtual void reset();

};

