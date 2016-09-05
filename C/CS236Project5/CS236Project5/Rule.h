#pragma once
#include <string>
#include <iostream>
#include "Token.h"
#include "Predicate.h"
using namespace std;
class Rule
{
public:
	Rule();
	~Rule();
	Predicate predHead;
	vector<Predicate> params;
	void getRule(vector<Token> &tokens, unsigned int &tokenCounter, set<string> &Values);
	void getRuleDefs(vector<Token> &tokens, unsigned int &tokenCounter, set<string> &Values);
	void error(Token &token);
};

