#include "Predicate.h"
#include <iostream>
#include <string>
#include <vector>
#include "Token.h"

using namespace std;


Predicate::Predicate()
{
}


Predicate::~Predicate()
{
}

bool Predicate::match(string expectedType, Token &token)
{
	if (expectedType == "ID")
	{
		return compare(11, token);
	}
	else if (expectedType == "LPAR")
	{
		return compare(3, token);
	}
	else if (expectedType == "RPAR")
	{
		return compare(4, token);
	}
	else if (expectedType == "STRING")
	{
		return compare(12, token);
	}
	else if (expectedType == "COMMA")
	{
		return compare(0, token);
	}
}

bool Predicate::compare(int expectedType, Token &token)
{
	if (token.getType() == expectedType)
	{
		return true;
	}
	else
	{
		return false;
	}
}

void Predicate::addParam(Parameter &param)
{
	params.push_back(param);
}