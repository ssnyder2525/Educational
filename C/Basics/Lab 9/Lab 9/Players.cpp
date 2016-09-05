#include "Players.h"
#include <random>
#include <ctime>
using namespace std;

player::player(string name_in, int victories_in, int defeats_in, int draws_in)

{
	name=name_in;
	victories=victories_in;
	defeats=defeats_in;
	draws=draws_in;
	
}

string player::getName()
{
	return name;
}

int player::getVictories()
{
	return victories;
}

int player::getDefeats()
{
	return defeats;
}

int player::getDraws()
{
	return draws;
}

void player::addvictory()
{
	victories+=1;
}

void player::adddefeat()
{
	defeats+=1;
}

void player::adddraw()
{
	draws+=1;
}

string player::getRPSthrow()
{
	string throws;
	
	Throw=rand() % 3;
	if (Throw==0)
	{
		throws="Rock";
	}

	if (Throw==1)
	{
		throws="Paper";
	}

	if (Throw==2)
	{
		throws="Scissors";
	}
	return throws;
	throws='b';
}

double player::getwinrecord()
{
	double winrecord;
	if (victories == 0)
	{
		winrecord = 0;
	}
	else
	{
	winrecord=((double) (victories)/((double)victories+(double)defeats+(double)draws))*100.00;
	}
	return winrecord;
}

string player::toString()
{
	stringstream ss;
	ss << "Name: " << name << endl;
	ss << "Victories: " << victories << endl;
	ss << "Defeats: " << defeats << endl;
	ss << "Draws: " << draws << endl;
	ss<< "Win record: "<<getwinrecord()<<endl<<endl;

	return ss.str();
}