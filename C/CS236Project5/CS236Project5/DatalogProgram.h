#pragma once
#include <iostream>
#include <string>
#include "Parameter.h"
#include "Predicate.h"
#include "Rule.h"
#include "Token.h"
#include <vector>
#include <set>
#include <sstream>
using namespace std;

class DatalogProgram
{
public:
	DatalogProgram();
	~DatalogProgram();
	vector<Predicate> Schemes;
	vector<Predicate> Facts;
	vector<Rule> Rules;
	vector<Predicate> Queries;
	set<string> Values;
	void Parse(vector<Token> &tokens);
	void getSchemes(vector<Token> &tokens, unsigned int &tokenCounter);
	void getFacts(vector<Token> &tokens, unsigned int &tokenCounter);
	void getRules(vector<Token> &tokens, unsigned int &tokenCounter);
	void getQueries(vector<Token> &tokens, unsigned int &tokenCounter);
	bool checkForColon(int value, Token &token);
	void error(Token &token);
	Predicate getPred(vector<Token> &tokens, unsigned int &tokenCounter);
	Rule getRulePred(vector<Token> &tokens, unsigned int &tokenCounter);
	void print(ofstream &out);
	void printSchemes(ofstream &out);
	void printFacts(ofstream &out);
	void printRules(ofstream &out);
	void printQueries(ofstream &out);
	void printRule(ofstream &out, Rule &rule);
	void printRules2(ofstream &out);
	bool isUnique(vector<string> &rules, string &rule);
};

