#include <iostream>
#include <string>
#include <list>
#include <fstream>
#include <cctype>
#include <algorithm>
#include <sstream>
#include <set>
#include <map>

using namespace std;

string condense(string input){
	char temp;
	string newInput;
	int i = 0;
	int newI = 0;
	int size;
	size = input.size();

	do {
		if (isalpha(input[i])){
			temp = input[i];
			temp =tolower(temp);
			newInput = newInput + temp;
		}
		i++;

	} while (i <= size - 1);

	return newInput;
}

string condense2(string input){
	char temp=0;
	string newInput;
	int i = 0;
	int newI = 0;
	int size;
	size = input.size();
	do {
		if (isalpha(input[i])){
			temp = input[i];
			temp = tolower(temp);
			newInput = newInput + temp;
		}
		i++;

	} while (i <= size - 1);

	return newInput;
}

string condense3(string input){
	char temp = 0;
	string newInput;
	int i = 0;
	int newI = 0;
	int size;
	size = input.size();
	do {
		
		if (isalpha(input[i]) || (isspace(input[i]))){
			temp = input[i];
			temp = tolower(temp);
			newInput = newInput + temp;
		}
		i++;

	} while (i <= size - 1);

	return newInput;
}


void makelist(list<string> &file, string document)
{
	string word;
	ifstream s;
	s.open(document);
	
	while (!s.eof())
	{
		s>> word;
		s.ignore(1,'\n');
		if (s.eof()){ break; }
		file.push_back(word);
		
	}

}


void makeset(set<string> &file, string document)
{
	string word;
	ifstream s;
	s.open(document);

	while (!s.eof())
	{
		getline(s, word);
		s.ignore(1, '\n');
		file.insert(word);
	}

}

void getdocument(list<string> &file, string document)
{
	string words;
	ifstream s;
	s.open(document);
	while(!s.eof())
	{
		getline(s, words);
		s.ignore(1, '\n');
		file.push_back(words);
	}
}

void printlist(list<string> file, ofstream &output)
{
	list<string>::iterator i = file.begin();
	while (i != file.end())
	{
		output << *i << "\n";
		i++;
	}
	output << "\n";
}

void printset(set<string> file, ofstream &output)
{
	set<string>::iterator i = file.begin();
	while (i != file.end())
	{
		output << *i << "\n";
		i++;
	}
	output << "\n";
}

void findinstancesandprintset(map<string, list<int>> &mispelled, ofstream &output, list<string> document)
{
	list<string> lowercase;
	string convert;
	string word;
	string word2;
	int line = 0;
	list<string>::iterator i = document.begin();
	while (i != document.end())
	{
		convert = condense3(*i);
		lowercase.push_back(convert);
		i++;
	}
	
	map<string, list<int>>::iterator i2 = mispelled.begin();
	
	{
		i = lowercase.begin();
		while (i != lowercase.end())
		{
			stringstream ss(*i);
			line++;
			while (ss >> word)
			{
				if (mispelled.find(word) != mispelled.end())
				{
					mispelled[word].push_back(line);
				}
			}
			i++;

		}
		if ((mispelled.size() != 0) && (document.size()!=0))
		{

			typedef map<string, list<int>>::const_iterator MapIterator;
			for (MapIterator iter = mispelled.begin(); iter != mispelled.end(); iter++)
			{
				output << iter->first << ":";
				typedef list<int>::const_iterator ListIterator;
				for (ListIterator list_iter = iter->second.begin(); list_iter != iter->second.end(); list_iter++)
				{
					output << " " << *list_iter;
				}
				output << "\n";
			}

			line = 0;
			i2++;
		}
	}
}


void checkword(set<string> dictionary, map<string, list<int>> &mispelled, ofstream &output, list<string> document)
{

	string word;
	string mispelledword;
	int checker = 0;
	set<string>::iterator i = dictionary.begin();
	list<string>::iterator it2 = document.begin();
	while (it2 != document.end())
	{
		word = *it2;
		
		if (dictionary.find(word) != dictionary.end())
		{
			checker = 0;
		}

		else
		{
			checker = 1;
		}
		it2++;

		if (checker == 1)
		{
			mispelledword = word;
			mispelled[word];
		}
		else
		{

		}
	}
}



int main(int argc, char* argv[])
{
	//makes lists
	set<string> fdictionary;
	set<string> dictionary;
	list<string> fdocument;
	list<string> tdocument;
	list<string> document;
	map<string, list<int>> mispelled;
	ofstream output;
	string condensed;
	string documentone = "C:\\Users\\Stephen\\Desktop\\Tests\\in22b.txt";
	string documenttwo = "C:\\Users\\Stephen\\Desktop\\Tests\\in22a.txt";
	output.open("C:\\Users\\Stephen\\Desktop\\myreport.txt");
	
	
	makeset(fdictionary, documentone);
	set<string>::iterator i = fdictionary.begin();
	for (i = fdictionary.begin(); i != fdictionary.end(); i++)
	{
		condensed = condense(*i);
		dictionary.insert(condensed);
	}
	//printset(dictionary, output);
	getdocument(fdocument, documenttwo);
	makelist(tdocument, documenttwo);
	//printlist(tdocument, output);
	list<string>::iterator it = tdocument.begin();
	while (it != tdocument.end())
	{
		condensed = condense2(*it);
		document.push_back(condensed);
		it++;	
	}
	//printlist(document, output);

	

	//compares words
	checkword(dictionary, mispelled, output, document);
	findinstancesandprintset(mispelled, output,fdocument);
	return 0;
}