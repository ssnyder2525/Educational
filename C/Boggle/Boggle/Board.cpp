#include <iostream>
#include "Board.h"
#include <fstream>
#include <string>

using namespace std;

Board::Board(double size_in)
{
	size = size_in;
}


Board::~Board()
{
}

void Board::createnewboard(int choice)
{
	if (choice == 1)
	{
		
		cout << "Please enter each letter followed by a space. The number of letters must make a square. Ex. 4,9,16,25, etc.";
		cin.ignore(1, 1);
		getline(cin,newboard);
			stringstream b;
			while(b << newboard)

			getboard(b,choice);
	}

	else if (choice == 2)
	{
		
		char let;
		string let2;
		stringstream s;
		for (unsigned int i = 0; i < 16; i++)
		{
			let = rand() % 25 + 97;
			if (let == 113)
			{
				let2 = "qu";
				s << let2;
				s << ' ';
			}
			else
			{
				let2 = let;
				s << let2;
				s << ' ';
			}
		}
		getboard(s, choice);
	}
}

void Board::makecheckboard()
{
	for (unsigned int ii = 0; ii < sqsize; ii++)
	{
		for (unsigned int i = 0; i < sqsize; i++)
		{
			first.push_back(false);

		}
		usedwords.push_back(first);
	}

}

void Board::getboard(stringstream &in, int choice)
{
	vector <string> temp;
	vector <string> temp2;
	string letters;
	int tempsize=0;
	while (in >> letters)
	{
		converttolower(letters);
		temp.push_back(letters);
	}
	size = temp.size();
	double size2 = sqrt(size);
	int intsize = size2;
	if (size2 == intsize)
	{				
		sqsize = size2;
					for (unsigned int c = 0; c < size; c=c+size2)
					{
						tempsize = 0;
						while (tempsize<size2)
						{
							temp2.push_back(temp[c + tempsize]);
							tempsize++;
						}
						board.push_back(temp2);
						temp2.clear();
					}
					
				
				
			
		
	}
	else
	{
		cout << "Incorrect size of board.";
		createnewboard(choice);
	}
}

void Board::tocube(ostream &out)
{
	for (unsigned int r = 0; r < board.size(); r++)
	{
		for (unsigned int c = 0; c < board.size(); c++)
		{
			out << board[r][c] << " ";
		}
		out << endl;
	}
	out << endl;
}

void Board::Test()
{
	for (unsigned int r = 0; r < board.size(); r++)
	{
		for (unsigned int c = 0; c < board.size(); c++)
		{
			if (board[r][c] == "qu")
			{
				cout << board[r][c] << " ";
			}
			else
			cout << board[r][c] << "  ";
		}
		cout << endl;
	}
	cout << endl;


}

void Board::converttolower(string &input){
	char temp;
	string newInput;
	int i = 0;
	int size;
	size = input.size();

	do {
		if (isalpha(input[i])){
			temp = input[i];
			temp = tolower(temp);
			newInput = newInput + temp;
		}
		i++;

	} while (i <= size - 1);
	input = newInput;
}

string Board::returnletterat(int position1, int position2)
{
	string output;
	output = board[position1][position2];
	return output;
}

int Board::returnsize()
{
	return sqsize;
}

bool Board::checknextletter(int nr, int nc)
{
	if ((nr>=0) && (nr<sqsize) && (nc>=0) && (nc<sqsize))
	{
		if (usedwords[nr][nc] == false)
		{
			return true;
		}
	}
	else
	{
		return false;
	}
}

void Board::searchallpaths(int r, int c, Dictionary &dictionary, string letters, int found)
{
	letters = letters + board[r][c];
	usedwords[r][c] = true;
		if (checknextletter(r, c + 1))
		{
			if (dictionary.checkprefix(letters + board[r][c + 1], found))
			{
				searchallpaths(r, c + 1, dictionary, letters, found);
			}
			usedwords[r][c+1] = false;
		}
		if (checknextletter(r+1, c + 1))
		{
			if (dictionary.checkprefix(letters + board[r + 1][c + 1], found))
			{
				searchallpaths(r + 1, c + 1, dictionary, letters, found);
			}
			usedwords[r+1][c+1] = false;
		}
		if (checknextletter(r+1, c))
		{
			if (dictionary.checkprefix(letters + board[r + 1][c], found))
			{
				searchallpaths(r + 1, c, dictionary, letters, found);
			}
			usedwords[r+1][c] = false;
		}
		if (checknextletter(r, c - 1))
		{
			if (dictionary.checkprefix(letters + board[r][c - 1], found))
			{
				searchallpaths(r, c - 1, dictionary, letters, found);
			}
			usedwords[r][c-1] = false;
		}
		if (checknextletter(r-1, c - 1))
		{
			if (dictionary.checkprefix(letters + board[r - 1][c - 1], found))
			{
				searchallpaths(r - 1, c - 1, dictionary, letters, found);
			}
			usedwords[r-1][c-1] = false;
		}
		if (checknextletter(r-1, c + 1))
		{
			if (dictionary.checkprefix(letters + board[r - 1][c + 1], found))
			{
				searchallpaths(r - 1, c + 1, dictionary, letters, found);
			}
			usedwords[r-1][c+1] = false;
		}
		if (checknextletter(r+1, c - 1))
		{
			if (dictionary.checkprefix(letters + board[r + 1][c - 1], found))
			{
				searchallpaths(r + 1, c - 1, dictionary, letters, found);
			}
			usedwords[r+1][c-1] = false;
		}
		if (checknextletter(r-1, c))
		{
			if (dictionary.checkprefix(letters + board[r - 1][c], found))
			{
				searchallpaths(r - 1, c, dictionary, letters, found);
			}
			usedwords[r-1][c] = false;
		}
}

void Board::checkforwords(Dictionary &dictionary)
{
	int found = 0;
	for (unsigned int r = 0; r < sqsize; r++)
	{
		for (unsigned int c = 0; c < sqsize; c++)
		{
			string letters;
			Board::searchallpaths(r, c, dictionary, letters, found);
			usedwords[r][c] = false;
		}
	}
}

void Board::begingame(Dictionary dictionary)
{
	time_t timer;
	double starttime = time(&timer);
	int points=0;
	string word;
	bool temp = true;
	int a;
	while (temp == true)
	{
		cout << "\n\n\n";
		Test();
		cin >> word;
		
		if (dictionary.checkplayersword(word))
		{
			points++;
		}

		
		
		double seconds = time(&timer)-starttime; 
		
		if (seconds >= 120)
		{
			temp = false;
		}
	}
	cout << "You found " << points << " words.";
	cin.ignore();
}