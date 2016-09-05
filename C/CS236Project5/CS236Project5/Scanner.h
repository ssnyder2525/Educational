#pragma once
#include <fstream>
#include <list>
#include <vector>
#include "Token.h"
using namespace std;
class Scanner
{
public:
	Scanner();
	~Scanner();
	int codeLineNum;
	enum tokenType {
		COMMA = 0,
		PERIOD = 1,
		Q_MARK = 2,
		LEFT_PAREN = 3,
		RIGHT_PAREN = 4,
		COLON = 5,
		COLON_DASH = 6,
		SCHEMES = 7,
		FACTS = 8,
		RULES = 9,
		QUERIES = 10,
		ID = 11,
		STRING = 12,
	};
	vector<Token> scanDocument(ifstream &filein);
	void skipWhiteSpaceAndComments(ifstream &filein);
	void getToken(string &s, int &place, char c);
	//void printData();
	string scanString(string &s, int &place);
	string scanIdent(string &s, int &place);
	bool readFutureChars(string &s, int &tempPlace, string &expectedString, int n);
	void defaultcase(Token* &token, string &s, int &place, string &returnedString);
	list<Token*> tokens;
	int lineNum = 0;
	int inputError = 0;
	int errorLineNum = 0;
	vector<Token> tokensV;
	void checkForQueries(string &s, int &place, int &tempPlace, string &expectedString, Token* &token, string &returnedString);
};

