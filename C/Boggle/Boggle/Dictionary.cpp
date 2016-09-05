#include <iostream>
#include "Dictionary.h"
#include <string>
#include <set>
using namespace std;
Dictionary::Dictionary(string name_in)
{
}

Dictionary::~Dictionary()
{
}

void Dictionary::builddictionary(ifstream &ifs)
{
	while (!ifs.eof())
	{
		string word;
		ifs >> word;
		words.insert(word);
	}
}

void Dictionary::tostring(ostream &out)
{
	set<string>::iterator i = foundwords.begin();
	string word;
	while (i != foundwords.end())
	{
		word = *i;
		out << word;
		if (i != foundwords.end())
		{
			out << "\n";
		}
		i++;
	}
}

bool Dictionary::checkprefix(string prefix, int found)
{
	set<string>::iterator i = words.lower_bound(prefix);
	if (i != words.end())
	{

		int size = prefix.size();
		string prefix2 = *i;
		string prefixp;
		if (prefix2.size() >= prefix.size())
		{
			for (unsigned int ii = 0; ii < size; ii++)
			{
				prefixp = prefixp + prefix2[ii];
			}
			if (prefix == prefixp)
			{
				if ((prefix == *i) && (prefix.size() >= 3))//letter size
				{
					string copy = *i;
					foundwords.insert(copy);
					words.erase(*i);
				}
				found++;
				return true;

			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	else
	{
		return false;
	}
}

bool Dictionary::checkplayersword(string word)
{
	set<string>::iterator i = foundwords.begin();
	i = foundwords.find(word);

	if (i != foundwords.end())
	{

		if (word == *i)
		{
			playerswords.insert(*i);
			foundwords.erase(*i);
			return true;
		}
		else
			return false;
	}
	else
		return false;
}