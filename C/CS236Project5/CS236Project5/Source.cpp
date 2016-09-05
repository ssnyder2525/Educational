#include <iostream>
#include <string>
#include <fstream>
#include "Scanner.h"
#include "Token.h"
#include "DatalogProgram.h"
#include <vector>
#include <sstream>
#include "Database.h"
using namespace std;

int main(int argc, char* argv[])
{
	//set up an output
	ofstream out2;
	out2.open(argv[2]);
	
	//read in file
	ifstream filein;
	filein.open(argv[1]);
	
	//Scan
	Scanner scanner;
	vector<Token> tokens = scanner.scanDocument(filein);

	//Parse
	DatalogProgram dLP;
	try
	{
		dLP.Parse(tokens);
		//dLP.print(out2);
	}

	catch (string e)
	{
		//out2 << "Failure!" << endl << "  " << e;
	}


	//Evaluate
	Database database;
	database.init(out2,dLP);
	
	filein.close();
	out2.close();

	return 0;
}
