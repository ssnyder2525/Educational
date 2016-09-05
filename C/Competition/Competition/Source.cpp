#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <sstream>

using namespace std;

class candybar
{
public:
	string id;
	string name;
	double cost;
	int quantity;
};

class changeslot2
{
public:
	string slot;
	double value;
	int changequantity;
};

void pressbutton(string buttonpressed)
{

}

void changeback()
{

}

void insertchange();


void parseActions(string &line)
{
	int place = 0;
	string buttonpressed;
	double changein = 0;

	while (line[place])
	{
		if (isalnum(line[place]))
		{
			buttonpressed == buttonpressed + line[place];
			if (buttonpressed.size() == 2)
			{
				pressbutton(buttonpressed);
			}
		}
		else if (line[place] == '#')
		{
			changeback();
		}

		else if (line[place] == '$')
		{
			insertchange();
		}
	}
}



int main()
{
	string id;
	string name;
	string cost;
	string quantity;
	double cost2;
	int quantity2;

	string changeslot;
	string value2;
	double value = 0;
	string changequantity2;
	int changequantity = 0;
	

	string line;
	int place = 0;
	string number;

	vector<candybar*> candybars;
	vector<changeslot2*> changeslots;

	double changein;
	double leftoverchange;

	ifstream in;
	in.open("C:\\Users\\Stephen\\Desktop\\PracticeInput.txt");
	while (getline(in, line))
	{
		place = 0;
		if ((isalpha(line[0])) && (isdigit(line[1])))
		{
			number = line[1];
			id = line[0]+number;
			place = 3;
			while (line[place] != ',')
			{
				name = name + line[place];
				place++;
			}
			place++;
			while (line[place] != ',')
			{
				cost = cost + line[place];
				place++;
			}
			place++;
			while (isdigit(line[place]))
			{
				quantity = quantity + line[place];
				place++;
			}
			stringstream ss(cost);
			ss>>cost2;
			stringstream ss2(quantity);
			ss >> quantity2;
			candybar* cb = new candybar();
			cb->id = id;
			cb->cost = cost2;
			cb->name = name;
			cb->quantity = quantity2;
			candybars.push_back(cb);
		}
		else if (line[0] == '$')
		{
			while (line[place] != ',')
			{
				changeslot = changeslot + line[place];
				place++;
			}
			place++;
			while (line[place] != ',')
			{
				value2 = value2 + line[place];
				place++;
			}
			while (line[place] != ',')
			{
				changequantity2 = changequantity2 + line[place];
				place++;
			}
			stringstream ss(value2);
			ss >> value;
			stringstream ss(changequantity2);
			ss >> changequantity;
			changeslot2* cs = new changeslot2();
			cs->slot = changeslot;
			cs->value = value;
			cs->changequantity = changequantity;
			changeslots.push_back(cs);
		}
		else if (line[0] == 'A')
		{
			parseActions(line);
		}

	return 0;
}