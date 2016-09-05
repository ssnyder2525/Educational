#pragma once
#include <iostream>
#include <vector>
#include <fstream>
#include "Dictionary.h"

using namespace std;
class Board
{
public:
	Board(double size_in);
	~Board();
	void makecheckboard();
	void getboard(ifstream &in);
	void tocube(ostream &out);
	void Test(int r,int c);
	void converttolower(string &input);
	string returnletterat(int position1, int position2);
	int returnsize();
	bool checknextletter(int nr, int nc);
	void searchallpaths(int r, int c, Dictionary &dictionary, string letters, int found);
	void checkforwords(Dictionary &dictionary);
	//

private:
	vector<vector<string>> board;
	double size;
	int sqsize;
	vector<vector<bool>> usedwords;
	vector<bool> first;
};



