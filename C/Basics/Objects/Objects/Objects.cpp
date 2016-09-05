#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include <string>
 
using namespace std;
 
class Employee
{//template of what an employee knows and can do.
public://What an employee can do.
	Employee();//birth or constructor
	Employee(string Name, double total);//birth
	string get_name();//stuff about them.
	double get_pay();
	void add_pay(double pay);
	string toString();//writes it all to a string so you can see it. toString is not a function. It's a name of a string.
 
private://what an employee can remember
	string name;
	double total_pay;
};//; is required.
 
Employee::Employee(string Name, double Total)
{
	name = Name;
	total_pay = Total;
}
void Employee::add_pay(double pay)
{
	total_pay += pay;
}
string Employee::get_name()
{
	return(name);
}
double Employee::get_pay()
{
	return(total_pay);
}
string Employee::toString() //This simply displays the logged stuff within the object.
{
	stringstream str;//str is an object too.
	str << "Name "<<name<<" total "<<total_pay<<endl;
	return(str.str());//this pulls out the string.
}
/* This program reads in a file with columns: 
  * Name Rate Hours
  * It then computes the paycheck for each employee and the total payroll expense
  */
int main()
{
	//Now read in the data from the file
	string line;
	cout << "Enter the file name you would like to read from"<<endl;
	// We use a getline since the file name may have spaces in it.
	getline(cin, line);
	ifstream file_in;
	cout << "opening "<<line<<endl;
	//Open the file and make sure the file exists
	file_in.open(line.c_str());
	if(file_in.fail()) {
		cout << "open failed"<<endl;
		system("pause");
		return(-1);
	}
	// Get a line at a time, then parse out the fields and put the object in the vector
 
	vector <Employee> employees;
	while(getline(file_in, line)) 
	{
		istringstream strm;
		// Put the line into a string stream so we can easily parse out items in the line
		strm.str(line);
 
		string name;
		double rate, hours;
		// Read in the strings from the line
		if (!(strm>>name>>rate>>hours))
		{
			cout<<"Didn't work... "<<line<< " went wrong\n";
		}
		else
		{
		strm >> name; strm >> rate; strm >> hours;
		cout << "Name "<<name<<" Rate "<<rate<<" Hours "<<hours<<endl;
 
		bool found = false; //Set this variable when we find the new person in the vector
		for(int i = 0; i < employees.size(); i++) {
			if(employees[i].get_name() == name) {
				employees[i].add_pay(rate*hours);
				found = true;
				cout << "Found employee and adding to pay "<<name<<endl;
			}
		}
		if(!found) {
			cout << "Creating new employee "<<name<<endl;
			Employee new_employee(name, rate*hours);
			employees.push_back(new_employee);
		}
		}
	}
 
	double total_payroll = 0;
	cout << "Payroll checks for each employee"<<endl;
	for(int i = 0; i < employees.size(); i++) {
		total_payroll += employees[i].get_pay();
		cout << employees[i].toString()<<endl;
	}
	cout << "total payroll "<<total_payroll<<endl;
	system("pause");
}