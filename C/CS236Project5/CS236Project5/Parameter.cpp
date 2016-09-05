#include "Parameter.h"
#include <iostream>
#include "Token.h"
#include "DatalogProgram.h"
#include <set>


using namespace std;


Parameter::Parameter()
{
}


Parameter::~Parameter()
{
}

void Parameter::getParam(vector<Token> &tokens, unsigned int &tokenCounter, set<string> &Values)
{
		if (match(tokens[tokenCounter].getType(), 11))
		{
			name = tokens[tokenCounter].getValue();
			tokenCounter++;
		}
		else if (match(tokens[tokenCounter].getType(), 12))
		{
			value = "\'" + tokens[tokenCounter].getValue() + "\'";
			Values.insert(value);
			tokenCounter++;
		}
		else
		{
			error(tokens[tokenCounter]);
		}
		if (match(tokens[tokenCounter].getType(), 0))
		{
			tokenCounter++;
		}
		else if (tokens[tokenCounter].getType() != 4)
		{
			error(tokens[tokenCounter]);
		}
}

bool Parameter::match(int type, int match)
{
		if (type == match)
		{
			return true;
		}
		else
		{
			return false;
		}
}

void Parameter::error(Token &token)
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

