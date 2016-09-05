/*
Test Cases

1. I chose to add a restaurant to the list.

I inputed Subway, which is already on the list.

The program informed me that Subway is already listed and did not add it.

2. I chose to add a restaurant

I inputed "Bob"

I tried to start the tournament

The program informed me that the number of restaurants was not correct.

3. I removed restaurants until eight remained.

I started the tournament.

The battle started.



*/

#include <iostream>
#include <iomanip>
#include <fstream>
#include <vector>
#include <algorithm>
#include <string>
#include <cmath>

using namespace std;

void initializevector(vector<string>&rest)
{
	string name;
	name = "Applebees";
	rest.push_back(name);
	name = "Cafe Rio";
	rest.push_back(name);
	name = "Culvers";
	rest.push_back(name);
	name = "Dennys";
	rest.push_back(name);
	name = "Famous Daves";
	rest.push_back(name);
	name = "McDonalds";
	rest.push_back(name);
	name = "Burger King";
	rest.push_back(name);
	name = "Olive Garden";
	rest.push_back(name);
	name = "Panda Express";
	rest.push_back(name);
	name = "Salt City";
	rest.push_back(name);
	name = "Chopstix";
	rest.push_back(name);
	name = "Red Robin";
	rest.push_back(name);
	name = "Subway";
	rest.push_back(name);
	name = "Chick-fil-A";
	rest.push_back(name);
	name = "Red Lobster";
	rest.push_back(name);
	name = "Texas Roadhouse";
	rest.push_back(name);
}

void printvector(vector<string> vec)
{
	cout << "Restaurants:\n";
	for(unsigned int i=0;i<vec.size();i++)
	{
		if (i == vec.size()-1)
		{
				cout<<vec[i]<<"\n";
		}
		else
		{
			cout<<vec[i]<<",\n";
		}
	}
	cout << endl;
}

string addrestaurant (string &name)
{
	cin.ignore();
	cout<<"please give the name of the restaurant\n";
	getline(cin,name);
	return name;
}

bool removerestaurant(vector <string> restaurants, string name, int &position)
{
		
		for (unsigned int i=0;i<restaurants.size();i++)
		{
			if (restaurants[i] == name)
			{
			position=i;
			return true;
			break;
			}	
			else
			{
				
			}
		}
		return false;
}

void shufflevector(vector <string> &restaurants)
{
	random_shuffle(restaurants.begin(),restaurants.end());
	printvector(restaurants);
}

void tournament(vector <string> restaurants)
{
	while (restaurants.size() > 1)
	{
		for (unsigned int i=0; i<(restaurants.size()-1);i++)
	{
		int position;
		cin.ignore();
		cout << "Which of these two retaurants is your favorite?\n\n"<<
		"1)"<<restaurants[i]<<"\n"<<"2)"<<restaurants[i+1]<<"\n";
		string choice;
		getline(cin,choice);
	
		if ((choice == restaurants[i])||(choice == "1"))
		{
			cout<<restaurants[i]<<" is the winner!\n\n";
			restaurants.erase(restaurants.begin()+(i+1));
		}
		else if ((choice == restaurants[1])||(choice == "2"))
		{
			cout<<restaurants[i+1]<<" is the winner!\n\n";
			restaurants.erase(restaurants.begin()+i);
		}
		else
		{
			cout<< "Please choose one of the two listed\n\n";
		}
	}
	}
	cout<<"This tournament's winner is: "<<restaurants[0]<<"!\n\n";
}


void check(vector <string> restaurants, int &yesorno)
{
	int i=restaurants.size();
	for (float x=0;x<5;x++)
	{
		if (pow(2,x)==restaurants.size())
		{
			yesorno = 1;
			break;
		}
		else
		{
			yesorno = 2;
		}
	}
}

int main()
{
	
	srand (unsigned (time(0)));
	vector<string> restaurants;
	initializevector(restaurants);
	int choice=0;
	
while (choice == 0)
{
	cout<<" What would you like to do?\n"
	"1)Display all restaurants\n"
	"2)Add a restaurant\n"
	"3)Remove a restaurant\n"
	"4)Shuffle the vector\n"
	"5)Begin the tournament\n"
	"6)Quit program\n";
	cin>>choice;
	if (choice == 1)
	{
		printvector(restaurants);
		choice =0;
	}
	if(choice == 2)
	{
		int test;
		string newrestaurant;
		addrestaurant(newrestaurant);
		for (unsigned int i=0;i<restaurants.size();i++)
		{
			if (restaurants[i] == newrestaurant)
		{
			cout << "Error: This name already exists on the list\n\n";
			test=2;
			break;
			
		}
	
		else
		{
			test=1;
		}
			
	}
		if (test==1)
		{
		restaurants.push_back(newrestaurant);
		cout<< newrestaurant<< " has been added to the list!\n\n";
		}
		choice = 0;
	}
	if(choice == 3)
	{
		int position;
		cout<< "Which restaurant would you like to delete?\n";
		printvector(restaurants);
		string name;
		cin.ignore();
		getline(cin,name);
		removerestaurant(restaurants, name,position);
		
		if(removerestaurant (restaurants,name,position))
		{
			cout<<restaurants[position]<<" has been removed from the list.\n\n";
			restaurants.erase(restaurants.begin()+position);
		}
		else
		{
			cout<<name<< " doesn't appear on the list.\n\n";
		}
			
		choice = 0;
	}
	if(choice == 4)
	{
		shufflevector(restaurants);
		choice = 0;
	}
	if(choice == 5)
	{
		int yesorno=0;
		check(restaurants,yesorno);
		if (yesorno==1)
		{
		cout << "Let the battle begin!\n\n\n";
		tournament(restaurants);
		}
		else
		{
			cout << "Error: Incorrect number of restaurants.\n\n";
		}
		choice = 0;
	}
	if (choice == 6)
	{
		break;
	}
	if ((choice!=1)&&(choice!=2)&&(choice!=3)&&(choice!=4)&&(choice!=5)&&(choice!=6) && (choice!=0))
	{
		cout << "Error: Not a valid response\n\n";
		choice=0;
	}
}
system("pause");
return 0;
}