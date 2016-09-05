#pragma once
#include <iostream>
#include <set>
#include <fstream>

using namespace std;
class Dictionary
{
public:
	Dictionary(string name_in);
	~Dictionary();

	void builddictionary(ifstream &ifs);
	void tostring(ostream &out);
	bool checkprefix(string prefix, int found);
	set<string> words;
	set<string> foundwords;
private:
	
	
};

