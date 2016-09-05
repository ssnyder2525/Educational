#include "Student.h"
#include <iostream>
#include <iomanip>
using namespace std;


Student::Student(int studentid_in, string name_in, string address_in, string phonenumber_in)
{
	studentid = studentid_in;
	name = name_in;
	address = address_in;
	phonenumber = phonenumber_in;

}


	Student::~Student()
	{
	}

	int Student::getStudentid()
	{
		return studentid;
	}

	void Student::toString()
	{
		cout << name << "\n";
		cout << studentid << "\n";
		cout << phonenumber << "\n";
		cout << address << "\n";

	}

	

	void Student::studentinfotostring()
	{
		cout << name << "\n" << studentid << "\n" << phonenumber << "\n" << address << "\n\n";
	}

	void Student::gpatostring(int id, double gpa, string name)
	{
		cout << id << "    ";
		cout << fixed << setprecision(2) << setfill('0') << gpa << "    ";
		cout << name << "\n";
	}

	string Student::getname()
	{
		return name;
	}