#pragma once
#include <iostream>
#include <string>
#include <sstream>
using namespace std;

class player
{
public:
	player(string name_in, int victories_in, int defeats_in, int draws_in);
	string getName();
	int getVictories();
	int getDefeats();
	int getDraws();
	void addvictory();
	void adddefeat();
	void adddraw();
	string getRPSthrow();
	double getwinrecord();
	string toString();

private:
	string name;
	int victories;
	int defeats;
	int draws;
	int Throw;

};