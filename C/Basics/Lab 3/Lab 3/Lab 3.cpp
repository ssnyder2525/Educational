/*
Test Cases:
	1.
		Miles per year:500
		Price of gas:3.00
		Cost of hybrid:20000
		MPG of hybrid:50
		Hybrid resale:10000
		Cost of Non-hybrid:15000
		MPG of Non-hybrid:25
		Non-hybrid resale:7500

		Gas Consumption

		Gas Consumption of Hybrid:50
		Total Cost:10150

		Gas Consumption of Non-hybrid:100
		Total Cost:7800

	2.
	Miles per year:10000
		Price of gas:2.00
		Cost of hybrid:15000
		MPG of hybrid:45
		Hybrid resale:10000
		Cost of Non-hybrid:10000
		MPG of Non-hybrid:20
		Non-hybrid resale:5000

		Total cost

		Gas Consumption of Hybrid:1111.1111111
		Total Cost:7222.22

		Gas Consumption of Non-hybrid:2500
		Total Cost:10000

	3.
		Miles per year:12000
		Price of gas:3.5
		Cost of hybrid:12000
		MPG of hybrid:40
		Hybrid resale:7000
		Cost of Non-hybrid:10000
		MPG of Non-hybrid:30
		Non-hybrid resale:5000

		Gas consumption

		Gas Consumption of Hybrid:10250
		Total Cost:1500

		Gas Consumption of Non-hybrid:12000
		Total Cost:2000

*/

#include <iostream>
#include <string>
using namespace std;


int main(){
	double milesperyear;
	double priceofgas;
	double costofhybrid;
	double mpghybrid;
	double hybridresale;
	double costofnonhybrid;
	double efficiencyofnonhybrid;
	double nonhybridsale;
	double gasconsumption;
	double totalcost;

	cout << "Please give the estimated miles driven per year\n";
	cin >> milesperyear;

	if (milesperyear <0)
	{
	cout << "Only positive numbers, please\n";
	cin>>milesperyear;
	}


	cout << "Please give the current price of gas per gallon\n";
	cin>>priceofgas;

	if (priceofgas <0)
	{
	cout << "Only positive numbers, please\n";
	cin>>priceofgas;
	}

	cout <<"Please give the price of a hybrid car\n";
	cin>> costofhybrid;
	
	if ( costofhybrid<0)
	{
	cout << "Only positive numbers, please";
	cin>>costofhybrid;
	}
	
	cout<<"Please give the efficiency of a hybrid car in miles per gallon\n"; 
	cin>>mpghybrid;

	if ( mpghybrid<0)
	{
	cout << "Only positive numbers, please\n";
	cin>>mpghybrid;
	}

	cout<<"Please give the resale value of a hybrid car after 5 years of use\n";
	cin>>hybridresale;

	if ( hybridresale<0)
	{
	cout << "Only positive numbers, please\n";
	cin>>hybridresale;
	}

	cout<<"Please give the price of a nonhybrid car\n";
	cin>>costofnonhybrid;
	
	if ( costofnonhybrid<0)
	{
	cout << "Only positive numbers, please\n";
	cin>>costofnonhybrid;
	}

	cout<<"Please give the efficiency of a nonhybrid car in miles per gallon\n";
	cin>>efficiencyofnonhybrid;

	if ( efficiencyofnonhybrid<0)
	{
	cout << "Only positive numbers, please\n";
	cin>>efficiencyofnonhybrid;
	}

	cout<<"Please give the resale value of a nonhybrid car after 5 years of use\n";
	cin>>nonhybridsale;

	if ( nonhybridsale<0)
	{
	cout << "Only positive numbers, please\n";
	cin>>nonhybridsale;
	}


	//math

	double hybridgallons = (milesperyear/mpghybrid)*5;
	double hybridexpense = hybridgallons*priceofgas;
	double hybridtotalcost = hybridexpense + (costofhybrid-hybridresale);

	double normalgallons = (milesperyear/efficiencyofnonhybrid)*5;
	double normalexpense = normalgallons*priceofgas;
	double normaltotalcost = (costofnonhybrid-nonhybridsale) + normalexpense;

	cin.get();
	string choice1;
	cout<<"Do you value low \"gas consumption\" or \"total cost\" more?\n";
	label1:
	getline(cin,choice1);
	

if (choice1 == "gas consumption")
{ 
		if (hybridgallons <= normalgallons)
		{
			cout<<"Hybrid\n"<<"Gallons of gas used in five years: "<<hybridgallons<<"\n"<<"Total cost: "<<hybridtotalcost<<"\n\n";

			cout<<"Non-Hybrid\n"<<"Gallons of gas used in five years: "<<normalgallons<<"\n"<<"Total cost: $"<<normaltotalcost<<"\n\n";	
		}
		else 
		{
			cout<<"Non-Hybrid\n"<<"Gallons of gas used in five years: "<<normalgallons<<"\n"<<"Total cost: $"<<normaltotalcost<<"\n\n";
			cout<<"Hybrid\n"<<"Gallons of gas used in five years: "<<hybridgallons<<"\n"<<"Total cost: $"<<hybridtotalcost<<"\n\n";
		}
}


	if(choice1 == "total cost")
	{
		if (hybridtotalcost <= normaltotalcost)
		{
			cout<<"Hybrid\n"<<"Total cost in five years: $"<<hybridtotalcost<<"\n"<<"Total gallons used: $"<<hybridgallons<<"\n\n";

			cout<<"Non-Hybrid\n"<<"Total cost in five years: $"<<normaltotalcost<<"\n"<<"Total gallons used: $"<<normalgallons<<"\n\n";
		}
		else{
			cout<<"Non-Hybrid\n"<<"Total cost in five years: $"<<normaltotalcost<<"\n"<<"Total gallons used: $"<<normalgallons<<"\n\n";

			cout<<"Hybrid\n"<<"Total cost in five years: $"<<hybridtotalcost<<"\n"<<"Total gallons used: $"<<hybridgallons<<"\n\n";
		}
	
	
	}

	system("pause");
	return 0;

}