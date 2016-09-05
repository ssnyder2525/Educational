#include "Scanner.h"
#include <iostream>
#include <string>
#include "Token.h"
#include <list>
#include <vector>
using namespace std;



Scanner::Scanner()
{
}


Scanner::~Scanner()
{
	for (list<Token*>::iterator i = tokens.begin(); i != tokens.end(); i++)
	{
		delete *i;
	}
}

vector<Token> Scanner::scanDocument(ifstream &filein)
{
	skipWhiteSpaceAndComments(filein);
	return tokensV;
}

void Scanner::skipWhiteSpaceAndComments(ifstream &filein)
{
	char c;
	string s;
	int length;
	int place;
	
	while ((getline(filein, s)) && (inputError != 1))
	{
		place = 0;
		lineNum++;
		length = s.length();
		while ((place <= length) && (inputError != 1))
		{
			c = s[place];
			if (isspace(c))
			{
			}
			else if (c == '#')
			{
				break;
			}
			else if (c == '\0')
			{
			}
			else
			{
				getToken(s, place, c);
			}
			//Always remember that the place is moved forward after scanning.
			place++;
		}
	}	
}

void deletetokenerror(Token* token, int inputError)
{
	if (inputError == 1)
	{
		delete token;
	}
}

void Scanner::getToken(string &s, int &place, char c)
{
	Token* token = new Token;
	int tempPlace = place;
	string expectedString;
	string returnedString;

	switch (c)
	{
	case',':
		token->lineNum = lineNum;
		token->value = "\",\"";
		token->type = COMMA;
		tokens.push_back(token);


		break;
	case'.':
		token->lineNum = lineNum;
		token->value = "\".\"";
		token->type = PERIOD;
		tokens.push_back(token);
		break;
	case'?':
		token->lineNum = lineNum;
		token->value = "\"?\"";
		token->type = Q_MARK;
		tokens.push_back(token);
		break;
	case'(':
		token->lineNum = lineNum;
		token->value = "\"(\"";
		token->type = LEFT_PAREN;
		tokens.push_back(token);
		break;
	case')':
		token->lineNum = lineNum;
		token->value = "\")\"";
		token->type = RIGHT_PAREN;
		tokens.push_back(token);
		break;
	case':':
		if (s[place+1] == '-')
		{
			place = place + 1;
			token->lineNum = lineNum;
			token->value = "\":-\"";
			token->type = COLON_DASH;
			tokens.push_back(token);
			break;
		}
		else
		{
			token->lineNum = lineNum;
			token->value = "\":\"";
			token->type = COLON;
			tokens.push_back(token);
			break;
		}
	case'\'':
		//scan the string
		 returnedString = scanString(s, place);
		 if (s[place] == '\'')
		 {
			 token->lineNum = lineNum;
			 token->value = returnedString;
			 token->type = STRING;
			 tokens.push_back(token);
		 }
		break;
	case'S':
		//read in the expected number of chars.
		tempPlace = place;
		expectedString = "chemes";

		if (readFutureChars(s, tempPlace, expectedString, 6))
		{
			token->lineNum = lineNum;
			token->value = "\"Schemes\"";
			token->type = SCHEMES;
			tokens.push_back(token);
			place = place + 6;
		}
		else
		{
			//go back
			tempPlace = place;
			//Read the IDENT
			returnedString = scanIdent(s, place);
			token->lineNum = lineNum;
			token->value = returnedString;
			token->type = ID;
			tokens.push_back(token);
		}	
		break;
	case'F':
		//read in the expected number of chars.
		tempPlace = place;
		expectedString = "acts";

		if (readFutureChars(s, tempPlace , expectedString, 4))
		{
			token->lineNum = lineNum;
			token->value = "\"Facts\"";
			token->type = FACTS;
			tokens.push_back(token);
			place = place + 4;
		}
		else
		{
			//go back
			tempPlace = place;
			//Read the IDENT
			returnedString = scanIdent(s, place);
			token->lineNum = lineNum;
			token->value = returnedString;
			token->type = ID;
			tokens.push_back(token);
		}
		break;
	case'R':
		//read in the expected number of chars.
		tempPlace = place;
		expectedString = "ules";

		if (readFutureChars(s, tempPlace, expectedString, 4))
		{
			token->lineNum = lineNum;
			token->value = "\"Rules\"";
			token->type = RULES;
			tokens.push_back(token);
			place = place + 4;
		}
		else
		{
			//go back
			tempPlace = place;
			//Read the IDENT
			returnedString = scanIdent(s, place);
			token->lineNum = lineNum;
			token->value = returnedString;
			token->type = ID;
			tokens.push_back(token);
		}
		break;
	case'Q':
		//read in the expected number of chars.
		tempPlace = place;
		expectedString = "ueries";

		checkForQueries(s, place, tempPlace, expectedString, token, returnedString);
		
		break;
	default:
		
		returnedString = scanIdent(s, place);
		defaultcase(token, s, place, returnedString);
		break;
	};
	deletetokenerror(token, inputError);
	if (token)
	{
		Token realToken = *token;
		tokensV.push_back(realToken);
	}
}

void Scanner::defaultcase(Token* &token, string &s, int &place, string &returnedString)
{
	if(inputError != 1)
		{
			token->lineNum = lineNum;
			token->value = returnedString;
			token->type = ID;
			tokens.push_back(token);
		}
}



/*void Scanner::printData()
{
	for (list<Token*>::iterator i = tokens.begin(); i != tokens.end(); i++)
	{
		out << "(";
		switch ((*i)->type){
		case(0) :
			out << "COMMA" << "," << "\",\"" << "," << (*i)->getLineNum() << ")" << endl;
			break;
		case(1) :
			out << "PERIOD" << "," << "\".\"" << "," << (*i)->getLineNum() << ")" << endl;
			break;
		case(2) :
			out << "Q_MARK" << "," << "\"?\"" << "," << (*i)->getLineNum() << ")" << endl;
			break;
		case(3) :
			out << "LEFT_PAREN" << "," << "\"(\"" << "," << (*i)->getLineNum() << ")" << endl;
			break;
		case(4) :
			out << "RIGHT_PAREN" << "," << "\")\"" << "," << (*i)->getLineNum() << ")" << endl;
			break;
		case(5) :
			out << "COLON" << "," << "\":\"" << "," << (*i)->getLineNum() << ")" << endl;
			break;
		case(6) :
			out << "COLON_DASH" << "," << "\":-\"" << "," << (*i)->getLineNum() << ")" << endl;
			break;
		case(7) :
			out << "SCHEMES" << "," << "\"Schemes\"" << "," << (*i)->getLineNum() << ")" << endl;
			break;
		case(8) :
			out << "FACTS" << "," << "\"Facts\"" << "," << (*i)->getLineNum() << ")" << endl;
			break;
		case(9) :
			out << "RULES" << "," << "\"Rules\"" << "," << (*i)->getLineNum() << ")" << endl;
			break;
		case(10) :
			out << "QUERIES" << "," << "\"Queries\"" << "," << (*i)->getLineNum() << ")" << endl;
			break;
		case(11) :
			out << "ID" << "," << "\"" << (*i)->getValue() << "\"" << "," << (*i)->getLineNum() << ")" << endl;
			break;
		case(12) :
			out << "STRING" << "," << "\"" << (*i)->getValue() << "\"" << "," << (*i)->getLineNum() << ")" << endl;
			break;
		}
	}
	if (inputError == 1)
	{
		out << "Input Error on Line " << errorLineNum;
	}
	else
	{
		out << "Total Tokens = " << tokens.size();
	}
}*/


bool Scanner::readFutureChars(string &s, int &tempPlace, string &expectedString, int n)
{
	int place = 0;
	while ((s[tempPlace+1] == expectedString[place]) && (place != n))
	{
		tempPlace++;
		place++;
	}
	if ((place == n)&&(!isalnum(s[tempPlace+1])))
	{
		return true;
	}
	else
	{
		return false;
	}
}

string Scanner::scanString(string &s, int &place)
{
	place = place + 1;
	char ch = s[place];
	string outputString;
	while ((s[place] != '\'')&&(s[place] != '\0'))
	{
		ch = s[place];
		outputString = outputString + ch;
		place = place + 1;
	}
	if (s[place] != '\'')
	{
		inputError = 1;
		errorLineNum = lineNum;
		return "";
	}
	return outputString;
}
string Scanner::scanIdent(string &s, int &place)
{
	string ident;
	if (!isalpha(s[place]))
	{
		inputError = 1;
		errorLineNum = lineNum;
	}
	while (isalnum(s[place]))
	{
		ident = ident + s[place];
		place++;
	}
	if (ident.length() > 0)
	{
		place--;
	}
	else
	{
		inputError = 1;
		errorLineNum = lineNum;
	}
	return ident;
}

void Scanner::checkForQueries(string &s, int &place, int &tempPlace, string &expectedString, Token* &token, string &returnedString)
{
	if (readFutureChars(s, tempPlace, expectedString, 6))
	{
		token->lineNum = lineNum;
		token->value = "\"Queries\"";
		token->type = QUERIES;
		tokens.push_back(token);
		place = place + 6;
	}
	else
	{
		//go back
		tempPlace = place;
		//read the Ident
		returnedString = scanIdent(s, place);
		token->lineNum = lineNum;
		token->value = returnedString;
		token->type = ID;
		tokens.push_back(token);
	}
}