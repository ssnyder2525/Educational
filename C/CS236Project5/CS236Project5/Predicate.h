#pragma once
#include <string>
#include <iostream>
#include "Token.h"
#include "Parameter.h"
using namespace std;
class Predicate
{
public:
	Predicate();
	~Predicate();
	string name;
	vector<Parameter> params;
	bool match(string expectedType, Token &token);
	bool compare(int expectedType, Token &token);
	void addParam(Parameter &param);
};

