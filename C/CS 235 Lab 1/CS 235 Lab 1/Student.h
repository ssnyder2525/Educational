#pragma once
#include <string>
using namespace std;
class Student
{
public:
	Student(int studentid_in, string name_in, string address_in, string phonenumber_in);
	~Student();

	int getStudentid();

	void toString();

	string getname();

	void studentinfotostring();

	void gpatostring(int id, double gpa, string name);

private:
	int studentid;
	string name;
	string address;
	string phonenumber;
};

