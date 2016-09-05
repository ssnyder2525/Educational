#pragma once
#include <iostream>
#include <set>
#include <fstream>
#include <cmath>

using namespace std;
class Dictionary
{
public:
	Dictionary(string name_in);
	~Dictionary();

	void builddictionary(ifstream &ifs);
	void tostring(ostream &out);
	bool checkprefix(string prefix, int found);
	bool checkplayersword(string word);
	set<string> words;
	set<string> foundwords;
	set<string> playerswords;
private:
	
	
};

