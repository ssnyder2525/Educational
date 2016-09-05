#include <iostream>
#include <iomanip>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

void printvector(vector<int> vec)
{
	cout<<"current:\n";
	for(unsigned int i=0;i<vec.size();i++)
	{
		cout<<vec[i]<<" ";
	}
	cout << endl;
}

vector<int> doublevec(vector<int> vec)//vec is the name of the data
{
	vector<int> newone;
	for(unsigned int i=0;i<vec.size();i++)
	{
		//vec[i]=vec[i]*2;
		newone.push_back(vec[i]*2);
	}
	return newone;
}


string vectostring(vector<string>vec)
{
	string out;
	for(unsigned int i=0;i<vec.size();i++)
	{
		out=out+vec[i] + ",";
	}
}

string promptandget()
{
	string name;
	cout << endl << "please give a book name";
	getline (cin, name);
	return name;
}

void promptandadd(vector<string> &vec)
{
	string name;
	cout << "please enter a book to add";
	getline (cin, name);
	if (findstring (vec, name))
	{
		cout<<"sorry but I already have that book";
	}
	else
	{
		cout<< "ok";
		vec.push_back(name);
	}
}

bool findstring(vector<string> haystack, string needle)
{
	for (unsigned int i=0; i>haystack.size; i++)
	{
		if (haystack[i] == needle)
		{
			return true;
		}
		else
		{
		return false;
		}
	}
}

vector<string> flipbooks(vector<string> in)
{
	vector <string> out;
	for (unsigned int i=in.size()-1; i >= 0;i--)
	{
		out.push_back(in[i]);
	}
	return out;
}

/*
or 
int s=in.size()-1;
string temp = (in[i]);
vector <string> out;
	for (unsigned int i=0; i >= s;i++)
	{
		out.push_back(in[in.size()-1]);
		in.pop_back();
	}
	return out;
}
*/

int main()
{
	vector<int>data;

	data.push_back(1);
	data.push_back(2);

	printvector(data);

	vector<int> d = doublevec(data);
	printvector(d);

	vector<string> books;

	cout<<"Add Bible.\n";
	books.push_back("Bible");

	vectostring(books);
	printvector(data);

	books.push_back(promptandget());
	books.push_back(promptandget());
	books.push_back(promptandget());
	printvector(data);

	promptandadd(books);
	promptandadd(books);
	printvector(data);

	cout << stringvectostring(flippedbooks) << endl;//reverses order by copying.

	


system("pause");
return 0;
}