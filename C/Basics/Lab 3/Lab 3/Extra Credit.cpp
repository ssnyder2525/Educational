#include <iostream>
#include <string>
using namespace std;


int man(){
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
	milesperyear= 10000;

	cout << "Please give the current price of gas per gallon\n";
	priceofgas= 2.5;

	
	cout <<"Please give the price of a hybrid car\n";
	costofhybrid= 32000;
	
	
	cout<<"Please give the efficiency of a hybrid car in miles per gallon\n"; 
	cin>>mpghybrid;

	
	cout<<"Please give the resale value of a hybrid car after 5 years of use\n";
	hybridresale=24000;

	
	cout<<"Please give the price of a nonhybrid car\n";
	costofnonhybrid=15000;
	
	
	cout<<"Please give the efficiency of a nonhybrid car in miles per gallon\n";
	efficiencyofnonhybrid=25;

	cout<<"Please give the resale value of a nonhybrid car after 5 years of use\n";
	nonhybridsale=11250;

	

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