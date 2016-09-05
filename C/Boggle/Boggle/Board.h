#pragma once
#include <iostream>
#include <vector>
#include <fstream>
#include "Dictionary.h"
#include <time.h>
#include <sstream>

using namespace std;
class Board
{
public:
	Board(double size_in);
	~Board();
	void createnewboard(int choice);
	void makecheckboard();
	void getboard(stringstream &in,int choice);
	void tocube(ostream &out);
	void Test();
	void converttolower(string &input);
	string returnletterat(int position1, int position2);
	int returnsize();
	bool checknextletter(int nr, int nc);
	void searchallpaths(int r, int c, Dictionary &dictionary, string letters, int found);
	void checkforwords(Dictionary &dictionary);
	void begingame(Dictionary dictionary);

private:
	vector<vector<string>> board;
	double size;
	int sqsize;
	vector<vector<bool>> usedwords;
	vector<bool> first;
	string newboard;
};



