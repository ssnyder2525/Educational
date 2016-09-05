#include "Student.h"
#include <iostream>
#include <iomanip>
#include <fstream>

using namespace std;


Student::Student(string studentid_in, string name_in, string address_in, string phonenumber_in)
{
	studentid = studentid_in;
	name = name_in;
	address = address_in;
	phonenumber = phonenumber_in;
}


	Student::~Student()
	{
	}

	string Student::getStudentid()
	{
		return studentid;
	}

	void Student::toString(ofstream& output)
	{
		output << name << "\n";
		output << studentid << "\n";
		output << phonenumber << "\n";
		output << address << "\n";

	}

	

	void Student::studentidstostring(vector<string> v2, ofstream& output)
	{
		for (unsigned int i = 0; i > v2.size() - 1; i++)
		{
			output << v2[i] << "\n";
		}
	}

	string Student::getname()
	{
		return name;
	}

bool Student::operator < (Student s) const {
	compares++;
    return studentid < s.studentid;
  }
  
  bool Student::operator > (Student s) const {
	compares++;
    return studentid > s.studentid;
  }

bool Student::operator == (Student s) const{
	compares++;
	return studentid == s.studentid;
}

void Student::setstudentid(string item)
{
	studentid = item;
}

int Student::selectionsort(vector<string> v2)
{
	
	compares = 0;
	int min;
	for (unsigned int i = 0; i<v2.size() - 1; i++)
	{
		
		min = i;
		for (unsigned int j=i + 1; j < v2.size(); j++)
		{
			compares++;
			if (v2[j] < v2[min])
			{
				min = j;
			}
		}
		(v2[i]).swap(v2[min]);
	}
	int t = compares;
	return t;
}

int Student::insertionsort(vector<Student> v2)
{
	compares = 0;
	int j;
	string item;
	
	for (unsigned int i = 1; i < v2.size(); i++)
	{
		item = v2[i].getStudentid();
		j = i;
		Student newstudent(item,"s","s","s");
		while ((j > 0) && (v2[j - 1] > newstudent))
		{
			v2[j] = v2[j - 1];
			j--;
		}
		v2[j].setstudentid(item);
	}
	int t = compares;
	return t;
}

int Student::standardsort(vector<Student> v2)
{
	compares = 0;
	sort(v2.begin(), v2.end());
	int t = compares;
	return t;
}

int Student::listsort(vector<Student> v2)
{
	compares = 0;
	list<Student> copy;
	for (unsigned int i = 0; i<v2.size(); i++)
	{
		copy.push_back(v2[i]);
	}

	copy.sort();
	int t = compares;
	return t;
}

int Student::linearsearch(vector<string> v2, vector<string> q2)
{
	Student::compares = 0;
	for (unsigned int i = 0; i < q2.size(); i++)
	{
		
		for (unsigned int j = 0; j < v2.size(); j++)
		{
			
			compares++;
			if (q2[i] == v2[j])
			{
				
				break;
			}
		}
	}
	{
		int t = compares / q2.size();
		return t;
	}
}

int Student::standardfind(vector<Student> v2, vector<string> q2)
{
	totalcompares = 0;
	compares = 0;
	vector<Student>::iterator i = v2.begin();
	vector<Student>::iterator i2 = v2.end();
	for (unsigned int j = 0; j < q2.size(); j++)
	{
		Student newstudent(q2[j], "s", "s", "s");
		find(i, i2, newstudent);
		totalcompares = totalcompares + compares;
		compares = 0;
	}
	compares = totalcompares / q2.size();
	int t = compares;
	return t;
	
}

int Student::setfind(vector<Student> v2, vector<string> q2)
{
	found = 0;
	totalcompares = 0;
	compares = 0;
	set<Student> copy;
	for (unsigned int i = 0; i<v2.size(); i++)
	{
		copy.insert(v2[i]);
	}
	for (unsigned int j = 0; j < q2.size(); j++)
	{
		Student newstudent(q2[j], "s", "s", "s");
		compares = 0;
		copy.find(newstudent);
		totalcompares = totalcompares + compares;
		compares = 0;
		found++;
	}
	if (found == 0)
	{
		return compares;
	}
	compares = totalcompares / found;
	int t = compares;
	return t;
}


int Student::compares;
