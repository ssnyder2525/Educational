#include "Grade.h"
#include <iostream>
using namespace std;


Grade::Grade(string name_in, int studentnumber_in, string grade_in)
{
	studentnumber = studentnumber_in;
	name = name_in;
	grade = grade_in;
}

Grade::~Grade()
{
}

int Grade::getstudentid()
{
	return studentnumber;
}

string Grade::getgrade()
{
	return grade;
}

string Grade::getname()
{
	return name;
}

void Grade::gradestostring()
{
		cout << studentnumber << "    ";
		cout << grade << "    ";
		cout << name << "\n";
}

int Grade::test()
{
	if (grade == "A")
		return 1;

	else if (grade == "A-")
		return 1;

	else if (grade == "B+")
		return 1;

	else if (grade == "B")
		return 1;

	else if (grade == "B-")
		return 1;

	else if (grade == "C+")
		return 1;

	else if (grade == "C")
		return 1;

	else if (grade == "C-")
		return 1;

	else if (grade == "D+")
		return 1;

	else if (grade == "D")
		return 1;

	else if (grade == "D-")
		return 1;

	else if (grade == "E")
		return 1;
	else
		return 0;
}

double Grade::calcgpa()
{
	if (grade == "A")
		return 4.0;

	else if (grade == "A-")
		return 3.7;

	else if (grade == "B+")
		return 3.4;

	else if (grade == "B")
		return 3.0;

	else if (grade == "B-")
		return 2.7;

	else if (grade == "C+")
		return 2.4;

	else if (grade == "C")
		return 2.0;

	else if (grade == "C-")
		return 1.7;

	else if (grade == "D+")
		return 1.4;

	else if (grade == "D")
		return 1.0;

	else if (grade == "D-")
		return 0.7;

	else if (grade == "E")
		return 0.0;
}

bool Grade::operator < (Grade g) const {
	return studentnumber < g.studentnumber ||
		(studentnumber == g.studentnumber && name < g.name) ||
		(studentnumber == g.studentnumber && name == g.name && grade < g.grade);
}