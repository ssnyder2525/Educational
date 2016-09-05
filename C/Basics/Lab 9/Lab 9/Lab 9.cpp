/*
Test Cases

1. Inputted Bob as a player
Added Bob to the lineup
Added Bob to the lineup
Started the fight.
There was an automatic draw.

2. Started the program
Selected the option to add a player
Added Bob
Selected the option to add a player
Added Bob
There was an error because Bob is still on the list

3. Started the program
Selected the option to add a player
Added Bob
Selected the option to add a player
Added Rufus
Added Bob to the lineup
Added Rufus to the lineup
Started the fight
Bob threw rock
Rufus threw scissors
Bob won
I displayed the players
Bob had one victory
Rufus had one defeat



*/


#include <iostream>
#include <string>
#include <vector>
#include "Players.h"
#include <random>
#include <ctime>

using namespace std;

void showplayers(vector <player> vec)
{
	for (unsigned int i=0; i<vec.size();i++)
	{
		cout<<vec[i].toString();
	}
}

void addplayer(vector <player> &vec)
{
	string name;
	int victories = 0;
	int defeats = 0;
	int draws = 0;
	cout<<"Enter new player's name: ";
	cin.ignore(100,'\n');
	getline(cin,name);
	int check=0;
	for (unsigned int i=0; i< vec.size(); i++)
	{
		if (name==vec[i].getName())
		{
			check=1;
			break;
		}
		else
		{
			check =0;
		}
	}
	if (check == 0)
	{
		player newplayer(name,victories,defeats,draws);
		vec.push_back(newplayer);
		cout<< name<<" has been added to the game.\n\n\n";
	}
	else
	{
		cout<< "That name already appears on the list.\n\n\n";
	}
}

void addtolineup(vector <player> allplayers, vector <player> &lineup)
{
	string name;
	cout<<"Who would you like to add to the Line-up?\n";
	cin.ignore(100,'\n');
	getline(cin,name);
	int check;
	int j=0;

	for (unsigned int i=0; i<allplayers.size();i++)
	{
		if (name == allplayers[i].getName())
		{
			check=1;
			j=i;
			break;
		}
		else
		{
			check=0;
		}
	}

	if (check == 1)
	{
		player *apntr = &allplayers[j];
		lineup.push_back(*apntr);
		cout<<name << " has been added to the Line-up.\n\n\n";
	}

	else
	{
		cout<<"Error: Name does not match any on the list.\n\n\n";
	}
}

void showlineup(vector <player> lineup)
{
	for (unsigned int i=0; i<lineup.size();i++)
	{
		cout<<lineup[i].toString();
	}
}

void clearlineup(vector<player> &lineup)
{
	for (unsigned int i = 0; i<=1;i++)
	{
		lineup.erase(lineup.begin());
	}
}


void fight(vector <player> &lineup, vector <player> &allplayers)
{
	int a=0;
	int b=0;
	int t=0;
if (lineup.size()>1)
	{



	for (unsigned int i = 0; i<allplayers.size();i++)
	{
		
		if (lineup[0].getName() == allplayers[i].getName())
		{
			a=i;
			break;
		}
	}

	for (unsigned int i = 0; i<allplayers.size();i++)
	{
		if (lineup[1].getName() == allplayers[i].getName())
		{
			b=i;
			break;
		}
	}
	
		cout<<lineup[0].getName()<< " VS "<<lineup[1].getName()<<"\n\n";
		if (lineup[0].getName() == lineup[1].getName())
		{
			cout<< "Automatic draw.\n\n\n";
			allplayers[a].adddraw();
			
		 t=1;
		}

		if (t!=1)
		{
		string throw1;
		string throw2;
		throw1 = lineup[0].getRPSthrow();
		throw2 = lineup[1].getRPSthrow();
		cout<<lineup[0].getName()<< " threw "<<throw1<<endl;
		cout<<lineup[1].getName()<< " threw "<<throw2<<endl;
		if ((throw1 == "Rock") && (throw2 =="Rock"))
		{
			allplayers[a].adddraw();
			allplayers[b].adddraw();
			cout<< "The battle is a draw.\n\n\n";
			
		}

		if ((throw1 == "Rock") && (throw2 =="Paper"))
		{
			allplayers[a].adddefeat();
			allplayers[b].addvictory();
			cout<<lineup[1].getName()<< " is the winner.\n\n\n";
		}

		if ((throw1 == "Rock") && (throw2 =="Scissors"))
		{
			allplayers[a].addvictory();
			allplayers[b].adddefeat();
			cout<<lineup[1].getName()<< " is the winner.\n\n\n";
		}

		if ((throw1 == "Paper") && (throw2 =="Rock"))
		{
			allplayers[a].addvictory();
			allplayers[b].adddefeat();
			cout<<lineup[0].getName()<< " is the winner.\n\n\n";
		}

		if ((throw1 == "Paper") && (throw2 =="Paper"))
		{
			allplayers[a].adddraw();
			allplayers[b].adddraw();
			cout<< "The battle is a draw.\n\n\n";
		}

		if ((throw1 == "Paper") && (throw2 =="Scissors"))
		{
			allplayers[a].adddefeat();
			allplayers[b].addvictory();
			cout<<lineup[1].getName()<< " is the winner.\n\n\n";
		}

		if ((throw1 == "Scissors") && (throw2 =="Rock"))
		{
			allplayers[a].adddefeat();
			allplayers[b].addvictory();
			cout<<lineup[1].getName()<< " is the winner.\n\n\n";
		}

		if ((throw1 == "Scissors") && (throw2 =="Paper"))
		{
			allplayers[a].addvictory();
			allplayers[b].adddefeat();
			cout<<lineup[0].getName()<< " is the winner.\n\n\n";
		}

		if ((throw1 == "Scissors") && (throw2 =="Scissors"))
		{
			allplayers[a].adddraw();
			allplayers[b].adddraw();
			cout<< "The battle is a draw.\n\n\n";
		}
		}
		clearlineup(lineup);
		}
		
	

	else
	{
		cout<< "There must be at least two players in the Line-Up.\n\n\n";
	}

}


void displaystats(vector <player> allplayers)
{
	double winrecord;
	string name;
	cout<<"Whose stats would you like to see?\n";
	cin.ignore(100,'\n');
	getline(cin,name);
	int check;
	int j=0;

	for (unsigned int i=0; i<allplayers.size();i++)
	{
		if (name == allplayers[i].getName())
		{
			check=1;
			j=i;
			break;
		}
		else
		{
			check=0;
		}
	}

	if (check == 1)
	{
		winrecord = allplayers[j].getwinrecord();
			cout << winrecord<<"\n\n\n";
	}
	else
	{
		cout<<"There is no "<<allplayers[j].getName()<< " on the list\n\n\n";
	}
}

int main()
{
	//menu
	srand(time(0));
	int menuloop=0;
	int choice;
	vector <player> allplayers;
	vector <player> lineup;
	

while (menuloop==0)
{
	cout << "What would you like to do?\n\n"
		"0) Show Players\n"
		"1) Add Player\n"
		"2) Add To Line-UP\n"
		"3) Show Line-up\n"
		"4) Fight\n"
		"5) Quit Program\n\n";
	
	
	cin>>choice;
	cout << "\n\n";

//Show Players 
	if (choice == 0)
	{
		showplayers(allplayers);

	}

//Add Player 
	else if (choice == 1)
	{
		addplayer(allplayers);

	}


//Add To Line-UP
	else if (choice == 2)
	{
		addtolineup(allplayers,lineup);
	}


//Show Line-up
	else if (choice == 3)
	{
		showlineup(lineup);
	}


//Fight
	else if (choice == 4)
	{
		fight(lineup, allplayers);
		
	}


//Quit
	/*else if (choice == 5)
	{
		displaystats(allplayers);
	}*/

	else if (choice == 5)
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
