#pragma once
#include <iostream>
#include <string>
using namespace std;
class Token
{
public:
	string value;
	int type;
	int lineNum;
	Token();
	~Token();

	string getValue();

	int getType();

	int getLineNum();

	void setValue(string value);

	void setType(string type);

	void setLineNum(int lineNum);
};

