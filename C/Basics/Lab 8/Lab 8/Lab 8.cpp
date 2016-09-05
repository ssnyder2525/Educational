#include <iostream>
#include <string>
#include <vector>
#include <fstream>

using namespace std;

class Cars
{
public:

private:
}


void addcar (vector <string> &cars, vector<string> &colors, vector <double> &prices,double &money)
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
	if (name == cars[i])
	{
		check=1;
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
			cars.push_back(name);
			prices.push_back(price);
			colors.push_back(color);
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

void printvector (vector <string>  cars, vector <string> colors, vector <double> prices)
{
	for (unsigned int i=0; i<cars.size();i++)
	{
		cout << cars[i]<<"  "<< colors[i]<< "  " << prices[i]<<"\n\n";
	}

}

void sellcar(vector <string>  &cars, vector <string> &colors, vector <double> &prices, double &money)
{
	cout<<"Which car would you like to sell?\n";
	cin.ignore(100,'\n');
	string cartosell;
	getline(cin,cartosell);
	int check=0;
	int carnumber;
for (unsigned int i=0; i<cars.size();i++)
{	
	if (cartosell == cars[i])
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
		cout<<"How much money is the car worth?\n";
		double price;
		cin>>price;

			cout << cars[carnumber]<<" has been sold.\n\n\n";
			money=money+price;
			cars.erase(cars.begin()+carnumber);
			colors.erase(colors.begin()+carnumber);
			prices.erase(prices.begin()+carnumber);
	}
	else
	{
		cout<<"Error: Car not on the list.\n\n";
	}
}

void paintcar (vector <string> cars, vector <string> &colors)
{
	cout<<"Which car would you like to paint?\n";
	cin.ignore(100,'\n');
	string cartopaint;
	getline(cin,cartopaint);
	int check=0;
	int carnumber;
for (unsigned int i=0; i<cars.size();i++)
{	
	if (cartopaint == cars[i])
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
		cout<<"What color would you like to paint your " << cars[carnumber] << "?\n";
		getline(cin,color);
		colors.insert(colors.begin()+carnumber,color);
		colors.erase(colors.begin()+carnumber+1);
}
	else
	{
		cout << "Error: Car does not appear on the list.\n\n\n";
	}
}

void savefile (vector <string>  cars, vector <string> colors, vector <double> prices, double money)
{
	string filename;
	cout<<"Please enter your file name.\n";
	cin>>filename;
	ifstream in_file;
	in_file.open(filename.c_str());
	in_file>>money;
	for (unsigned int i=0; i<cars.size();i++)
	{
	in_file>>cars[i]>>colors[i]>>prices[i];
	}
}
void loadfile (vector <string>  &cars, vector <string> &colors, vector <double> &prices, double &money)
{
	string filename;
	cout<<"Please enter your file name.\n";
	cin>>filename;
	ofstream out_file;
	out_file.open(filename.c_str());
	out_file<<money;
	for (unsigned int i=0; i<out_file.good();i++)
	{
	out_file<<cars[i]<<colors[i]<<prices[i];
	}
}

int main()
{
	//menu
	int menuloop=0;
	int choice;
	double money = 10000;
	vector <string> cars;
	vector <string> colors;
	vector <double> prices;

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
		printvector(cars, colors, prices);

	}

//Show Current Balance
	else if (choice == 1)
	{
		cout<< "You have $"<<money<<"\n\n";

	}


//Buy a Car
	else if (choice == 2)
	{
		
		addcar(cars,colors,prices,money);
		

	}


//Sell a Car
	else if (choice == 3)
	{
		sellcar(cars, colors, prices, money);
	}


//Paint a Car
	else if (choice == 4)
	{
		paintcar(cars, colors);
	}


//Load File
	else if (choice == 5)
	{
		loadfile(cars,colors,prices,money);
	}


//Save File
	else if (choice == 6)
	{
		savefile(cars,colors,prices,money);
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