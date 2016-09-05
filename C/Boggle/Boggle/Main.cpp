#include <iostream>
#include <fstream>
#include <string>
#include "Dictionary.h"
#include <set>
#include "Board.h"

using namespace std;




int main()
{
	srand(time(0));
	ifstream ifs;
	//ifs.open("C:\\Users\\Stephen\\Desktop\\Tests\\in35a.txt");
	ifs.open("Dictionary.txt");
	ifstream ifs2;
	
	ofstream out;
	out.open("C:\\Users\\Stephen\\Desktop\\Boggle Answers.txt");
	Dictionary dictionary("dictionary");

	dictionary.builddictionary(ifs);
	

	Board board(0);
	int choice;
	cout << "\t\t\t\tBoggle\n\n\nMain Menue\n\n1. Choose my own board\n2. Create random board\n\n";
	cin >> choice;
	board.createnewboard(choice);
	board.tocube(out);
	board.makecheckboard();
	//board.Test(r,c);
	cout << "\t\t\tLoading Words. Please Wait...\n\n\n";
	board.checkforwords(dictionary);
	dictionary.tostring(out);
	cout << "Press any key when ready!\n";
	cin.ignore();
	cin.ignore();
	board.begingame(dictionary);
	
	




	return 0;
}