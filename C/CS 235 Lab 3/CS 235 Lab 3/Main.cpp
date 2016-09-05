#include <iostream>
#include <fstream>
#include <string>
#include "Dictionary.h"
#include <set>
#include "Board.h"

using namespace std;




int main()
{
	
	ifstream ifs;
	//ifs.open("C:\\Users\\Stephen\\Desktop\\Tests\\in35a.txt");
	ifs.open("C:\\Users\\Stephen\\Desktop\\dictionary.txt");
	ifstream ifs2;
	//ifs2.open("C:\\Users\\Stephen\\Desktop\\Tests\\in35b.txt");
	ifs2.open("C:\\Users\\Stephen\\Desktop\\board.txt");
	ofstream out;
	out.open("C:\\Users\\Stephen\\Desktop\\Boggle Answers.txt");
	Dictionary dictionary("dictionary");

	dictionary.builddictionary(ifs);
	

	Board board(0);
	board.getboard(ifs2);
	board.tocube(out);
	board.makecheckboard();
	int r = 1;
	int c = 1;
	//board.Test(r,c);
	board.checkforwords(dictionary);
	dictionary.tostring(out);
	
	




	return 0;
}