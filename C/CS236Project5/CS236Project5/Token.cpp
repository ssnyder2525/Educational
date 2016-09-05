#include "Token.h"
#include <iostream>
#include <string>
using namespace std;


Token::Token()
{
}


Token::~Token()
{
}

string Token::getValue()
{
	return value;
}

int Token::getType()
{
	int thisTokenType = this->type;
	return thisTokenType;
}

int Token::getLineNum()
{
	return lineNum;
}

void Token::setValue(string value)
{
	this->value = value;
}

/*void Token::setType(string type)
{
	this->type = FACTS;
}*/

void Token::setLineNum(int lineNum)
{
	this->lineNum = lineNum;
}