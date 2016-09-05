#include "DatalogProgram.h"
#include <iostream>
#include <string>
#include "Parameter.h"
#include "Predicate.h"
#include "Rule.h"
#include "Token.h"
#include <vector>
#include <set>
#include <sstream>
#include <fstream>
using namespace std;


DatalogProgram::DatalogProgram()
{
}


DatalogProgram::~DatalogProgram()
{
}

void DatalogProgram::Parse(vector<Token> &tokens)
{
	stringstream ss;
	unsigned int tokenCounter = 0;

	getSchemes(tokens, tokenCounter);
	getFacts(tokens, tokenCounter);
	getRules(tokens, tokenCounter);
	getQueries(tokens, tokenCounter);
	if ((tokenCounter != tokens.size()))
	{
		error(tokens[tokenCounter]);
	}
		
}

void DatalogProgram::getSchemes(vector<Token> &tokens, unsigned int &tokenCounter)
{
	if (tokens[tokenCounter].getType() == 7)
	{
		tokenCounter++;
		checkForColon(tokens[tokenCounter].getType(), tokens[tokenCounter]);
		tokenCounter++;
		if (tokens[tokenCounter].getType() != 11)
		{
			error(tokens[tokenCounter]);
		}
		while (tokens[tokenCounter].getType() == 11)
		{
			Predicate Pred = getPred(tokens, tokenCounter);
			Schemes.push_back(Pred);
		}
	}
	else
	{
		error(tokens[tokenCounter]);
	}
}

void DatalogProgram::getFacts(vector<Token> &tokens, unsigned int &tokenCounter)
{
	if (tokens[tokenCounter].getType() == 8)
	{
		tokenCounter++;
		checkForColon(tokens[tokenCounter].getType(), tokens[tokenCounter]);
		tokenCounter++;
		while (tokens[tokenCounter].getType() == 11)
		{
			Predicate Pred = getPred(tokens, tokenCounter);
			Facts.push_back(Pred);
			if (tokens[tokenCounter].getType() == 1)
			{
				tokenCounter++;
			}
			else
			{
				error(tokens[tokenCounter]);
			}
		}
	}
	else
	{
		error(tokens[tokenCounter]);
	}
}

void DatalogProgram::getRules(vector<Token> &tokens, unsigned int &tokenCounter)
{
	if (tokens[tokenCounter].getType() == 9)
	{
		tokenCounter++;
		checkForColon(tokens[tokenCounter].getType(), tokens[tokenCounter]);
		tokenCounter++;
		while (tokens[tokenCounter].getType() == 11)
		{
			Rule rule = getRulePred(tokens, tokenCounter);
			Rules.push_back(rule);
			if (tokens[tokenCounter].getType() == 0)
			{
				tokenCounter++;
			}
			else if (tokens[tokenCounter].getType() == 1)
			{
				tokenCounter++;
				//break;
			}
			else
			{
				error(tokens[tokenCounter]);
			}
		}
	}
	else
	{
		error(tokens[tokenCounter]);
	}
}

void DatalogProgram::getQueries(vector<Token> &tokens, unsigned int &tokenCounter)
{
	if (tokens[tokenCounter].getType() == 10)
	{
		tokenCounter++;
		checkForColon(tokens[tokenCounter].getType(), tokens[tokenCounter]);
		tokenCounter++;
		if (tokens[tokenCounter].getType() != 11)
		{
			error(tokens[tokenCounter]);
		}
		while (tokens[tokenCounter].getType() == 11)
		{
			Predicate Pred = getPred(tokens, tokenCounter);
			Queries.push_back(Pred);
			if ((tokens[tokenCounter].getType() == 2) && (tokenCounter + 1 != tokens.size()))
			{
				tokenCounter++;
			}
			else if (tokens[tokenCounter].getType() == 2)
			{
				tokenCounter++;
				break;
			}
			else
			{
				error(tokens[tokenCounter]);
			}
		}
	}
	else
	{
		error(tokens[tokenCounter]);
	}
}

bool DatalogProgram::checkForColon(int value, Token &token)
{
	if (value == 5)
	{
		return true;
	}
	else
	{
		error(token);
	}
}

Predicate DatalogProgram::getPred(vector<Token> &tokens, unsigned int &tokenCounter)
{
	Predicate Pred;
	Pred.match("ID", tokens[tokenCounter]);
	Pred.name = tokens[tokenCounter].getValue();
	tokenCounter++;
	Pred.match("LPAR", tokens[tokenCounter]);
	tokenCounter++;
	while (tokens[tokenCounter].getType() != 4)
	{
		Parameter param;
		param.getParam(tokens, tokenCounter, Values);
		Pred.addParam(param);
	}
	if ((Pred.params.size() == 0) || (tokens[tokenCounter-1].getType() == 0))
		{
			error(tokens[tokenCounter]);
		}
			Pred.match("RPAR", tokens[tokenCounter]);
			tokenCounter++;
		return Pred;
}

Rule DatalogProgram::getRulePred(vector<Token> &tokens, unsigned int &tokenCounter)
{
	Rule rule;
	rule.getRule(tokens, tokenCounter, Values);
	return rule;
}

void DatalogProgram::error(Token &token)
{
	string e;
	int i = token.getType();
	int LineNum = token.getLineNum();
	string LineNumc = to_string(LineNum);
	switch (i){
	case(0) :
		e = e + "(COMMA,\",\"," + LineNumc + ")";
		break;
	case(1) :
		e = e + "(PERIOD,\".\"," + LineNumc + ")";
		break;
	case(2) :
		e = e + "(Q_MARK,\"?\"," + LineNumc + ")";
		break;
	case(3) :
		e = e + "(LEFT_PAREN,\"(\"," + LineNumc + ")";
		break;
	case(4) :
		e = e + "(RIGHT_PAREN,\")\"," + LineNumc + ")";
		break;
	case(5) :
		e = e + "(COLON,\":\"," + LineNumc + ")";
		break;
	case(6) :
		e = e + "(COLON_DASH,\":-\"," + LineNumc + ")";
		break;
	case(7) :
		e = e + "(SCHEMES,\"Schemes\"," + LineNumc + ")";
		break;
	case(8) :
		e = e + "(FACTS,\"Facts\"," + LineNumc + ")";
		break;
	case(9) :
		e = e + "(RULES,\"Rules\"," + LineNumc + ")";
		break;
	case(10) :
		e = e + "(QUERIES,\"Queries\"," + LineNumc + ")";
		break;
	case(11) :
		e = e + "(ID,\"" + token.getValue() + "\"," + LineNumc + ")";
		break;
	case(12) :
		e = e + "(STRING,\"" + token.getValue() + "\"," + LineNumc + ")";
		break;
	}
	throw e;
}


void DatalogProgram::print(ofstream &out)
{
	out << "Success!" << endl;
	printSchemes(out);
	printFacts(out);
	out << "Rules(" << Rules.size() << "):" << endl;
	printRules(out);
	printQueries(out);
	out << "Domain(" << Values.size() << "):" << endl;
	set<string>::iterator it = Values.begin();
	while (it != Values.end())
	{
		out << "  " << *it << endl;
		it++;
	}
}

void DatalogProgram::printSchemes(ofstream &out)
{
	out << "Schemes(" << Schemes.size() << "):" << endl;
	int i = 0;
	int i2 = 0;
	while (i < Schemes.size())
	{
		out << "  " << Schemes[i].name << "(";
		i2 = 0;
		while (i2 < Schemes[i].params.size())
		{
			out << Schemes[i].params[i2].name << Schemes[i].params[i2].value;
			if (i2 != Schemes[i].params.size() - 1)
			{
				out << ",";
			}
			i2++;
		}
		out << ")" << endl;
		i++;
	}
}

void DatalogProgram::printFacts(ofstream &out)
{
	out << "Facts(" << Facts.size() << "):" << endl;
	int i = 0;
	while (i < Facts.size())
	{
		out << "  " << Facts[i].name << "(";
		int i2 = 0;
		while (i2 < Facts[i].params.size())
		{
			out << Facts[i].params[i2].name << Facts[i].params[i2].value;
			if (i2 != Facts[i].params.size() - 1)
			{
				out << ",";
			}
			i2++;
		}
		out << ")" << endl;
		i++;
	}
}

void DatalogProgram::printRules(ofstream &out)
{
	
	int i = 0;
	while (i < Rules.size())
	{
		out << "  " << Rules[i].predHead.name << "(";
		int i2 = 0;
		while (i2 < Rules[i].predHead.params.size())
		{
			out << Rules[i].predHead.params[i2].name << Rules[i].predHead.params[i2].value;
			if (i2 != Rules[i].predHead.params.size() - 1)
			{
				out << ",";
			}
			i2++;
		}
		out << ") :- ";
		i2 = 0;
		while (i2 < Rules[i].params.size())
		{
			out << Rules[i].params[i2].name << "(";
			int i3 = 0;
			while (i3 < Rules[i].params[i2].params.size())
			{
				out << Rules[i].params[i2].params[i3].name << Rules[i].params[i2].params[i3].value;
				if (i3 != Rules[i].params[i2].params.size() - 1)
				{
					out << ",";
				}
				i3++;
			}
			out << ")";
			if (i2 != Rules[i].params.size() - 1)
			{
				out << ",";
			}
			i2++;
		}
		out << endl;
		i++;
	}
}

void DatalogProgram::printQueries(ofstream &out)
{
	out << "Queries(" << Queries.size() << "):" << endl;
	int i = 0;
	while (i < Queries.size())
	{
		out << "  " << Queries[i].name << "(";
		int i2 = 0;
		while (i2 < Queries[i].params.size())
		{
			out << Queries[i].params[i2].name << Queries[i].params[i2].value;
			if (i2 != Queries[i].params.size() - 1)
			{
				out << ",";
			}
			i2++;
		}
		out << ")" << endl;
		i++;
	}
}

void DatalogProgram::printRule(ofstream &out, Rule &rule)
{
	out << rule.predHead.name << "(";
	int i2 = 0;
	while (i2 < rule.predHead.params.size())
	{
		out << rule.predHead.params[i2].name << rule.predHead.params[i2].value;
		if (i2 != rule.predHead.params.size() - 1)
		{
			out << ",";
		}
		i2++;
	}
	out << ") :- ";
	i2 = 0;
	while (i2 < rule.params.size())
	{
		out << rule.params[i2].name << "(";
		int i3 = 0;
		while (i3 < rule.params[i2].params.size())
		{
			out << rule.params[i2].params[i3].name << rule.params[i2].params[i3].value;
			if (i3 != rule.params[i2].params.size() - 1)
			{
				out << ",";
			}
			i3++;
		}
		out << ")";
		if (i2 != rule.params.size() - 1)
		{
			out << ",";
		}
		i2++;
	}
}

void DatalogProgram::printRules2(ofstream &out)
{
	string toPrint;
	int i = 0;
	while (i < Rules.size())
	{
		toPrint = Rules[i].predHead.name + "(";
		int i2 = 0;
		while (i2 < Rules[i].predHead.params.size())
		{
			toPrint = toPrint + Rules[i].predHead.params[i2].name + Rules[i].predHead.params[i2].value;
			if (i2 != Rules[i].predHead.params.size() - 1)
			{
				toPrint = toPrint + ",";
			}
			i2++;
		}
		toPrint = toPrint + ") :- ";
		i2 = 0;
		while (i2 < Rules[i].params.size())
		{
			toPrint = toPrint + Rules[i].params[i2].name + "(";
			int i3 = 0;
			while (i3 < Rules[i].params[i2].params.size())
			{
				toPrint = toPrint + Rules[i].params[i2].params[i3].name + Rules[i].params[i2].params[i3].value;
				if (i3 != Rules[i].params[i2].params.size() - 1)
				{
					toPrint = toPrint + ",";
				}
				i3++;
			}
			toPrint = toPrint + ")";
			if (i2 != Rules[i].params.size() - 1)
			{
				toPrint = toPrint + ",";
			}
			i2++;
		}
		i++;
		out << toPrint << "\n";
	}
}

bool DatalogProgram::isUnique(vector<string> &rules, string &rule)
{
	int i = 0;
	while (i != rules.size())
	{
		if (rule == rules[i])
		{
			return false;
		}
		i++;
	}
	return true;
}