#pragma once
#include <string>

using namespace std;

class TaxiInterface
{
	public:
		virtual string tostring() const = 0;
		virtual string taxreport() =0;
		virtual bool trip (int miles) = 0;
		virtual bool trip () = 0;

}