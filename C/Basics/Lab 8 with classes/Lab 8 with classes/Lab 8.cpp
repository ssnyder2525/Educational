/*
Test Cases

1. 
I started the program
I selected the first third option.
I inputted a Carolla as the name of the car
I inputted black as the color
I inputted 7500 as the price
It indicated that the car had been added to my list
I selected the first option
It showed my Carolla on the list.

2.
I started the program
I selected the paint option.
I inputted Ranger as the name of my car
My program responded with an error, because Ranger did not appear on the list.

3.
I started my program.
I added a Black Carolla (2300) and a Blue Ranger(5500).
I selected the save option.
I inputted a file on the desktop named bob.
My file contained both cars.



*/



#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <iomanip>
#include "Car.h"

using namespace std;

void addcar (vector <Car> &cars, double &money)
{

	string name;
	string color;
	double price=0;
	cin.ignore(100,'\n');
	cout<< "Please enter the name of the car.\n";
	getline(cin,name);
	int check=0;
for (unsigned int i=0; i<cars.size();i++)
{	
	if (cars[i].getName() == name)
	{
		check=1;
		break;
	}
	else
	{
		check = 0;
	}
}
	if (check==0)
	{
		cout<<"Please enter the color of the car.\n";
		cin>>color;
		cout<<"Please enter the price of the car.\n";
		cin>> price;
		if (price<=money)
		{
			cout<<"You have purchased the "<< name<< ".\n\n\n";
			Car my_car(name,color,price);
			cars.push_back(my_car);
			money=money-price;
		}
		else
		{
			cout<<"Error: Insufficient funds.\n\n";
		}
	}
	else
	{
		cout<< "Error: This name already exists on your list.\n\n";
	}
}

void printvector (vector <Car>  cars)
{
	for (unsigned int i=0; i<cars.size();i++)
	{
		cout << cars[i].toString()<<"\n";
	}

}

void sellcar(vector <Car>  &cars, double &money)
{
	cout<<"Which car would you like to sell?\n";
	cin.ignore(100,'\n');
	string cartosell;
	getline(cin,cartosell);
	int check=0;
	int carnumber;
for (unsigned int i=0; i<cars.size();i++)
{	
	if (cartosell == cars[i].getName())
	{
		check=1;
		carnumber=i;
		break;
	}
	else
	{
		check = 0;
	}
}
	if (check==1)
	{
		//cout<<"How much money is the car worth?\n";
		double price;
		//cin>>price;

			cout << cars[carnumber].getName()<<" has been sold.\n\n\n";
			money=money+cars[carnumber].getPrice();
			cars.erase(cars.begin()+carnumber);
			
	}
	else
	{
		cout<<"Error: Car not on the list.\n\n";
	}
}

void paintcar (vector <Car> &cars)
{
	string cartopaint;
	
	cout<<"Which car would you like to paint?\n";
	cin>>cartopaint;
	int check=0;
	int carnumber;
for (unsigned int i=0; i<cars.size();i++)
{	
	if (cars[i].getName()==cartopaint)
	{
		check=1;
		carnumber=i;
		break;
	}
	else
	{
		check = 0;
	}
}
	if (check==1)
	{
		cin.ignore(100,'\n');
		string color;
		cout<<"What color would you like to paint your " << cars[carnumber].getName() << "?\n";
		getline(cin,color);
		cars[carnumber].paint(color);
		cout<<cars[carnumber].getName()<< " has been painted.\n\n";
}
	else
	{
		cout << "Error: Car does not appear on the list.\n\n\n";
	}
}

void savefile (vector <Car>  cars, double money)
{
	
}
void loadfile (vector <Car>  &cars, double &money)
{
	
}
int main()
{
	//menu
	int menuloop=0;
	int choice;
	double money = 10000.00;
	vector <Car> cars;
	
while (menuloop==0)
{
	cout << "What would you like to do?\n\n"
		"0) Show Current Inventory\n"
		"1) Show Current Balance\n"
		"2) Buy a Car\n"
		"3) Sell a Car\n"
		"4) Paint a Car\n"
		"5) Load File\n"
		"6) Save File\n"
		"7) Quit Program\n\n";
	
	cin>>choice;
	cout << "\n\n";

//Show Current Inventory
	if (choice == 0)
	{
		printvector(cars);

	}

//Show Current Balance
	else if (choice == 1)
	{
		cout<< "You have $"<<money<<"\n\n";

	}


//Buy a Car
	else if (choice == 2)
	{
		
		addcar(cars,money);
		

	}


//Sell a Car
	else if (choice == 3)
	{
		sellcar(cars, money);
	}


//Paint a Car
	else if (choice == 4)
	{
		paintcar(cars);
	}


//Load File
	else if (choice == 5)
	{
	string line;
	cout << "Enter the file name you would like to read from"<<endl;
	cin.ignore(100,'\n');
	getline(cin, line);
	ifstream file_in;
	cout << "opening "<<line<<endl;
	file_in.open(line.c_str());
	if(file_in.fail()) {
		cout << "open failed"<<endl;
		system("pause");
		return(-1);
	}
 	
		
	int crap=0;
	while(getline(file_in, line)) 
	{
		istringstream strm;
		strm.str(line);
 
		string name;
		string color;
		double price;
		
		if (crap==0)
		{
		double addmoney=0;
		strm>>addmoney;
		
		money=money+addmoney;
		addmoney=0;
		
		strm.str(line);
		}
		if (crap!=0)
		{
		if (!(strm>>name>>color>>price))
		{
			cout<<"Didn't work... "<<line<< " went wrong\n";
		}
		else
		{
		strm >> name; strm >> color; strm >> price;
		Car bob (name,color,price);
		cars.push_back(bob);

		cout << "Name: "<<name<<" Color: "<<color<<" Price: "<<price<<endl;
		}
		}
		crap=crap+1;

	}
	}


//Save File
	else if (choice == 6)
	{
		savefile(cars,money);

	//string filename;
	//cout << "Enter the file name you would like to save"<<endl;
	//cin>>filename;
	//ofstream Fileout (filename);
	
	cout<< "Where would you like to write the file?\n";
	string filelocation;
	cin >> filelocation;
	
	if (filelocation.substr(0,1) == "\"") 
	{
		filelocation = filelocation.substr(1,filelocation.length()-2);
	}
	ofstream Fileout (filelocation);
	//ofstream myfile (filelocation);
	if (Fileout.is_open())
	{
		Fileout<<money<<"\n";
		for(unsigned int i=0;i<cars.size();i++)
		{
			Fileout<<cars[i].getName()<<" "<<cars[i].getColor()<<" "<<cars[i].getPrice()<<"\n";
		}
		Fileout.close();	
	}
	else
	{
		cout<<"Error\n";
	}
	}


//Quit
	else if (choice == 7)
	{
		break;
	}

	else
	{
		cout << "\nERROR: Invalide input.\n\n";
	}

}


	system ("pause");
	return 0;
}