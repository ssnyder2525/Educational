#include "Rule.h"
#include <iostream>
#include <string>
#include <vector>
#include "Token.h"
#include "Predicate.h"

using namespace std;


Rule::Rule()
{
}


Rule::~Rule()
{
}

void Rule::getRule(vector<Token> &tokens, unsigned int &tokenCounter, set<string> &Values)
{
	predHead.match("ID", tokens[tokenCounter]);
	predHead.name = tokens[tokenCounter].getValue();
	tokenCounter++;
	predHead.match("LPAR", tokens[tokenCounter]);
	tokenCounter++;
	while (tokens[tokenCounter].getType() != 4)
	{
		Parameter param;
		param.getParam(tokens, tokenCounter, Values);
		predHead.addParam(param);
	}
	if ((predHead.params.size()) == 0 || (tokens[tokenCounter - 1].getType() == 0))
	{
		error(tokens[tokenCounter]);
	}
	predHead.match("RPAR", tokens[tokenCounter]);
	tokenCounter++;

	if (tokens[tokenCounter].getType() == 6)
	{
		tokenCounter++;
	}
	else
	{
		error(tokens[tokenCounter]);
	}

	while (tokens[tokenCounter].getType() == 11)
	{
		getRuleDefs(tokens, tokenCounter, Values);
	}
	if ((params.size() == 0) || (tokens[tokenCounter - 1].getType() == 0))
	{
		error(tokens[tokenCounter]);
	}
}

void Rule::getRuleDefs(vector<Token> &tokens, unsigned int &tokenCounter, set<string> &Values)
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
	if ((Pred.params.size() == 0) || (tokens[tokenCounter - 1].getType() == 0))
	{
		error(tokens[tokenCounter]);
	}
	Pred.match("RPAR", tokens[tokenCounter]);
	tokenCounter++;
	if (tokens[tokenCounter].getType() == 0)
	{
		tokenCounter++;
	}
	params.push_back(Pred);
}

void Rule::error(Token &token)
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