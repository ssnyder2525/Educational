#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include "Student.h"
#include "Grade.h"
#include <algorithm>
using namespace std;


void createstudentvector(vector <Student> &allstudents, char* input)
{
	string name, address, phonenumber;
	string inputfile = "C:\\Users\\Stephen\\Desktop\\Tests\\in12a.txt";
	int studentid;
	ifstream s;
	s.open(inputfile);

	if (s.fail())
	{

		cout << "File not found: \"" << inputfile << "\"" << endl;
	}
	else
	{
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
	}
	s.close();
}
	void creategradevector(vector <Grade> &allgrades, char* input)
{
		string inputfile1 = "C:\\Users\\Stephen\\Desktop\\Tests\\in12b.txt";
			int id;
			string class_;
			string grade;
			ifstream st;
			st.open(inputfile1);//input

			if (st.fail())
			{

				cout << "File not found: \"" << inputfile1 << "\"" << endl;
			}
			else
			{
					while (!st.eof())
					{
						st >> class_;
						if (st.eof())
						{
							
							break;
						}
						st >> id; 
						if (st.eof())
						{
						
							break;
						}
						st >> grade;
							Grade newgrade = Grade(class_, id, grade);
							int t = newgrade.test();
							if (t == 1)
							{
								allgrades.push_back(newgrade);
							}
							else
							{
								
							}
					}
					sort(allgrades.begin(),allgrades.end());
			}
			st.close();
}

	void calculategpa(vector <Grade> allgrades, vector <Student> allstudents, char* input)
	{
		vector <int> done;
		int check=0;
		int tempstudentid;
		double addgpa=0;
		double tempgpa=0;
		string name;
		int counter = 0;
		string inputfile1 = "C:\\Users\\Stephen\\Desktop\\Tests\\in12c.txt";
		ifstream str;
		str.open(inputfile1);//input

		if (str.fail())
		{

			cout << "File not found: \"" << inputfile1 << "\"" << endl;
		}
		else
		{
			while (!str.eof())
			{
				str >> tempstudentid;
				if (str.fail())
				{
					
					break;
				}
				else
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
						if (check == 1)
						{
							addgpa = 0;
							counter = 0;
							for (unsigned int i = 0; i < allgrades.size(); i++)
							{
								if (tempstudentid == allgrades[i].getstudentid())
								{
									addgpa = addgpa + allgrades[i].calcgpa();
									counter++;
								}
							}
							
							if (counter == 0)
							{
								tempgpa = 0;
							}
							else
							{
								tempgpa = addgpa / counter;
							}
							check = 0;
							for (unsigned int i = 0; i < done.size(); i++)
							{
								if (done[i] == tempstudentid)
								{
									check = 1;
									break;
								}
								else
								{
									check = 0;
									
								}
							}
							if (check == 0)
							{
								allstudents[0].gpatostring(tempstudentid, tempgpa, name);
								done.push_back(tempstudentid);
							}
						}
						else
						{
					
						}
					}
				}
				str.close();
			}
		}
	
	

	void Printstudents(vector <Student> allstudents)
	{
		for (unsigned int i=0; i < allstudents.size(); i++)
			
			allstudents[i].studentinfotostring();
	}

	void Printgrades(vector <Grade> allgrades)
	{
		for (unsigned int i = 0; i < allgrades.size(); i++)

			allgrades[i].gradestostring();
	}

int main(int argc, char* argv[])
{
	vector <Student> allstudents;

	vector <Grade> allgrades;

	createstudentvector(allstudents, argv[1]);

	Printstudents(allstudents);

	creategradevector(allgrades, argv[2]);

	Printgrades(allgrades);
	cout << "\n";

	calculategpa(allgrades, allstudents, argv[3]); 

	

	system("pause");
	return 0;
}