#pragma once
#include <string>
using namespace std;
class Grade
{
public:
	Grade(string name_in, int studentnumber_in, string grade_in);
	~Grade();

	void gradestostring();
	int getstudentid();
	string getgrade();
	string getname();
	int test();
	double calcgpa();
	bool operator < (Grade g) const;

private:
	int studentnumber;
	string grade;
	string name;
};

