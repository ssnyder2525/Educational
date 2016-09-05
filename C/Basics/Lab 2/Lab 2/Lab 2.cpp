/*
Test 1:
	17 guests
		2 large pizzas
		1 medium pizza
		0 small pizzas

		Total area of pizzas:829.38
		Area per guest:48.787

	Tip: 10%
		Total Price Rounded: $45

Test 2:
	594 guests
		84 large pizzas
		2 medium pizza
		0 small pizzas

		Total area of pizzas:26791.5
		Area per guest:45.1035

	Tip: 30%
		Total Price Rounded: $1633

Test 3:
	5 guests
		0 large pizzas
		1 medium pizza
		2 small pizzas

		Total area of pizzas:427.256
		Area per guest:85.4512

	Tip: 20%
		Total Price Rounded: $31





*/


#include <iostream>
#include <string>
#include <cmath>
#include <iomanip>
using namespace std;

int main(){
//This calculates how many of each type of pizza.

	cout << "How many guests will be attending?\n";
	int guests;
	int guests2;
	int large=0;
	int medium=0;
	int small=0;
	cin >> guests;
	guests2=guests;
		while (guests2 >= 7)
		{
			guests2=guests2-7;
			large=large+1;
		}
		while (guests2 >=3)
		{
			guests2=guests2-3;
			medium=medium +1;
		}
		while (guests2 >=1)
		{
			guests2=guests2-1;
			small = small + 1;
		}

	cout << "Number of large pizzas: " <<large << endl;
	cout << "Number of medium pizzas: "<<medium<< endl;
	cout << "Number of small pizzas: "<<small << endl;

//This Calculates the Area
	const double n=3.14159;
	const double largearea= n * (10*10);
	const double mediumarea= n * (8*8);
	const double smallarea= n *(6*6);
	
	double totallargearea=largearea*large;
	double totalmediumarea=mediumarea*medium;
	double totalsmallarea=smallarea*small;

	double totalarea=totallargearea+totalmediumarea+totalsmallarea;
	
	cout <<"\nTotal Area of Pizza: "<<totalarea<<"\n";
	
//This calculates area per guest

	double areaperperson= (totalarea/guests);

	cout<<"Area of pizza per guest: "<< areaperperson<<"\n";
	
//This calculates the price

	const double largeprice = 14.68;
	const double mediumprice = 11.48;
	const double smallprice = 7.28;

	double price= (largeprice*large) + (mediumprice*medium) + (smallprice*small);

//This calculates the tip

	double tip;
	double totalprice;
	cout<<"\nEnter percentage of tip\n";
	cin>>tip;
	tip = tip*.01;
	tip = tip*price;

//This gives the total price

	totalprice = tip + price;
	int roundedtotalprice=totalprice + .5;
	cout << "\nYou spent $"<<roundedtotalprice<<"\n\n";

	


	system ("pause");
	return 0;

}