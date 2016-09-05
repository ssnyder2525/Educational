#pragma once
#include <string>
#include <vector>
#include <list>
#include <set>
#include <algorithm>
using namespace std;
class Student
{
public:
	Student(string studentid_in, string name_in, string address_in, string phonenumber_in);
	~Student();

	string getStudentid();
	void toString(ofstream &output);
	string getname();
	void setstudentid(string item);
	void studentidstostring(vector<string> v2, ofstream& output);
	bool operator < (Student s) const;
	bool operator > (Student s) const;
	bool operator == (Student s) const;
	int selectionsort(vector<string> v2);
	int insertionsort(vector<Student> v2);
	int standardsort(vector<Student> v2);
	int listsort(vector<Student> v2);
	int linearsearch(vector<string> v2, vector<string> q2);
	int standardfind(vector<Student> v2, vector<string> q2);
	int setfind(vector<Student> v2, vector<string> q2);
	static int compares;


private:
	string studentid;
	string name;
	string address;
	string phonenumber;
	vector<string> ids;
	int totalcompares;
	int found;
};

