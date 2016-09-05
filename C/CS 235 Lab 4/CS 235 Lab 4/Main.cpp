#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include "Student.h"
#include <algorithm>
using namespace std;


void createstudentvector(vector <Student> &allstudents, char* input)
{
	string name, address, phonenumber;
	string studentid;
	ifstream s;
	s.open(input);
			while (!s.eof())
			{
				s>>studentid;
				s.ignore(1, 1);
				if (s.eof())
				{
					break;
				}
				getline(s,name);
				if (s.eof())
				{
					break;
				}
				getline(s, address);
				if (s.eof())
				{
					break;
				}
				getline(s, phonenumber);
				Student newstudent (studentid, name, address, phonenumber);
				allstudents.push_back(newstudent);
			}
	s.close();
}

void createqueryvector(vector<string> &queries, char* input)
{
	string query;
	ifstream s;
	s.open(input);
	while (getline(s, query))
	{
		queries.push_back(query);
	}
	s.close();
}
	
void Printstudents(vector <Student> allstudents, vector <string> v2, ofstream& output)
	{
		allstudents[1].studentidstostring(v2, output);
		output<<"\n";
	}

void Getname(string tempstudentid, string &name, vector <Student> allstudents, int &check)
	{
		for (unsigned int i = 0; i < allstudents.size(); i++)
		{
			if (tempstudentid == allstudents[i].getStudentid())
			{
				name = allstudents[i].getname();
				check = 1;
				break;
			}

			else if (tempstudentid != allstudents[i].getStudentid())
			{
				check = 0;
			}
		}
	}

void Printqueries(vector<string> queries, ofstream &output)
{
	for (unsigned int i = 0; i < queries.size(); i++)
	{
		output << queries[i]<<"\n";
	}
}

void v2quarter(vector <Student> allstudents, vector<Student> &allids, vector<string> &v2)
{
	int size = allstudents.size() / 4;
	for (unsigned int i = 0; i < size; i++)
	{
		allids.push_back(allstudents[i]);
	}
	for (unsigned int j = 0; j < allids.size(); j++)
	{
		v2.push_back(allids[j].getStudentid());
	}
}

void q2quarter(vector <string> &q2, vector<string> queries)
{
	int size = queries.size() / 4;
	for (unsigned int i = 0; i < size; i++)
	{
		q2.push_back(queries[i]);
	}
}

void v2half(vector <Student> allstudents, vector<Student> &allids, vector<string> &v2)
{
	allids.clear();
	v2.clear();
	int size = allstudents.size() / 2;
	for (unsigned int i = 0; i < size; i++)
	{
		allids.push_back(allstudents[i]);
	}
	for (unsigned int j = 0; j < allids.size(); j++)
	{
		v2.push_back(allids[j].getStudentid());
	}
}

void q2half(vector <string> &q2, vector <string> queries)
{
	q2.clear();
	int size = queries.size() / 2;
	for (unsigned int i = 0; i < size; i++)
	{
		q2.push_back(queries[i]);
	}
}

void v2whole(vector <Student> allstudents, vector<Student> &allids, vector<string> &v2)
{
	allids.clear();
	v2.clear();
	int size = allstudents.size();
	for (unsigned int i = 0; i < size; i++)
	{
		allids.push_back(allstudents[i]);
	}
	for (unsigned int j = 0; j < allids.size(); j++)
	{
		
		v2.push_back(allids[j].getStudentid());
	}
}

void q2whole(vector <string> &q2, vector <string> queries)
{
	q2.clear();
	int size = queries.size();
	for (unsigned int i = 0; i < size; i++)
	{
		
		q2.push_back(queries[i]);
	}
}



int main(int argc, char* argv[])
{
	ofstream output;
	output.open(argv[3]);
	vector <Student> allstudents;
	vector <Student> allids;
	vector <string> queries;
	vector <string> v2;
	vector <string> q2;

	createstudentvector(allstudents, argv[1]);
	createqueryvector(queries, argv[2]);

	

	//takes a quarter of the vectors
	
	v2quarter(allstudents, allids, v2);
	q2quarter(q2, queries);
	int quartersize = v2.size();
	//Printstudents(allstudents, v2, output);
	//Printqueries(queries, output);


	int selsort = allstudents[1].selectionsort(v2);
	int inssort = allstudents[1].insertionsort(allids);
	int stsort = allstudents[1].standardsort(allids);
	int lstsort = allstudents[1].listsort(allids);
	int linsearch = allstudents[1].linearsearch(v2, q2);
	int stfind = allstudents[1].standardfind(allids, q2);
	int setfind = allstudents[1].setfind(allids, q2);
	
	

	v2half(allstudents, allids, v2);
	q2half(q2, queries);
	int halfsize = v2.size();
	int selsort2 = allstudents[1].selectionsort(v2);
	int inssort2 = allstudents[1].insertionsort(allids);
	int stsort2 = allstudents[1].standardsort(allids);
	int lstsort2 = allstudents[1].listsort(allids);
	int linsearch2 = allstudents[1].linearsearch(v2, q2);
	int stfind2 = allstudents[1].standardfind(allids, q2);
	int setfind2 = allstudents[1].setfind(allids, q2);

	
	
	v2whole(allstudents, allids, v2);
	q2whole(q2, queries);
	int wholesize = v2.size();
	int selsort3 = allstudents[1].selectionsort(v2);
	int inssort3 = allstudents[1].insertionsort(allids);
	int stsort3 = allstudents[1].standardsort(allids);
	int lstsort3 = allstudents[1].listsort(allids);
	int linsearch3 = allstudents[1].linearsearch(v2, q2);
	int stfind3 = allstudents[1].standardfind(allids, q2);
	int setfind3 = allstudents[1].setfind(allids, q2);

	output << "Selection Sort" << endl << "size: " << quartersize << "    " << "compares: " << selsort << endl << "size: " << halfsize << "    " << "compares: " << selsort2 << endl << "size: " << wholesize << "    " << "compares: " << selsort3 << endl
		<< "Insertion Sort" << endl << "size: " << quartersize << "    " << "compares: " << inssort << endl << "size: " << halfsize << "    " << "compares: " << inssort2 << endl << "size: " << wholesize << "    " << "compares: " << inssort3 << endl
		<< "std::sort" << endl << "size: " << quartersize << "    " << "compares: " << stsort << endl << "size: " << halfsize << "    " << "compares: " << stsort2 << endl << "size: " << wholesize << "    " << "compares: " << stsort3 << endl
		<< "std::list.sort" << endl << "size: " << quartersize << "    " << "compares: " << lstsort << endl << "size: " << halfsize << "    " << "compares: " << lstsort2 << endl << "size: " << wholesize << "    " << "compares: " << lstsort3 << endl
		<< "Linear Search" << endl << "size: " << quartersize << "    " << "compares: " << linsearch << endl << "size: " << halfsize << "    " << "compares: " << linsearch2 << endl << "size: " << wholesize << "    " << "compares: " << linsearch3 << endl
		<< "std::find" << endl << "size: " << quartersize << "    " << "compares: " << stfind << endl << "size: " << halfsize << "    " << "compares: " << stfind2 << endl << "size: " << wholesize << "    " << "compares: " << stfind3 << endl
		<< "std::set.find" << endl << "size: " << quartersize << "    " << "compares: " << setfind << endl << "size: " << halfsize << "    " << "compares: " << setfind2 << endl << "size: " << wholesize << "    "<<"compares: " << setfind3;



	output.close();

	return 0;
}
