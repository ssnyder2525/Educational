#pragma once
#include <vector>
#include "Token.h"
#include <set>
class Parameter
{
public:
	Parameter();
	~Parameter();
	string name;
	string value;
	vector<string> params;
	void getParam(vector<Token> &tokens, unsigned int &tokenCounter, set<string> &Values);
	bool match(int type, int match);
	void error(Token &token);
};

