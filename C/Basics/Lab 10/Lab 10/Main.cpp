#include <iostream>
#include <iostream>
#include <string>
#include "Arena.h"
#include "ArenaInterface.h"

using namespace std;

int main()
{
	//menu
	int menuloop=0;
	int choice;
	

while (menuloop==0)
{
	cout << "What would you like to do?\n\n"
		"0) add fighter\n"
		"1) remove fighter\n"
		"2) get fighter\n"
		"3) total fighters\n"
		"4) \n"
		"5) \n"
		"6) \n"
		"7) Quit Program\n\n";
	
	cin>>choice;
	cout << "\n\n";

//
	if (choice == 0)
	{
		string name;
		getline(cin,name);
		Arena::addFighter();

	}


//
	else if (choice == 2)
	{

	}


//
	else if (choice == 3)
	{

	}


//
	else if (choice == 4)
	{

	}


//
	else if (choice == 5)
	{

	}


//
	else if (choice == 6)
	{

	}


//
	else if (choice == 7)
	{
		
	}

	else
	{
		cout << "\nERROR: Invalide input.\n\n";
	}

}


	system ("pause");
	return 0;
}

