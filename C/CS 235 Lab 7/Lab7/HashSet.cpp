#include <iostream>
#include <fstream>
#include <string>
#include "HashSet.h"
#include <list>

using namespace std;

int main(int argc, char* argv[])
{
	ifstream ifs;
	ifs.open(argv[1]);
	ofstream output;
	output.open(argv[2]);

	string cmd;
	string cmd3;
	string cmd2;


	HashSet <string> set1;

	while (ifs >> cmd)
	{
		if (cmd == "clear")
		{
			set1.clear();
			output << "clear" << endl;
		}

		if (cmd == "add")
		{
			ifs >> cmd3;
			set1.add(cmd3);

			output << "add " << cmd3 << endl;
		}

		if (cmd == "remove")
		{
			ifs >> cmd3;
			set1.remove(cmd3);
			output << "remove " << cmd3 << endl;
		}

		if (cmd == "find")
		{
			ifs >> cmd3;

			if (set1.find(cmd3))
				output << "find " << cmd3 << " " << "true" << endl;
			else
				output << "find " << cmd3 << " " << "false" << endl;
		}
		
		if (cmd == "print")
		{
			output << "print" << endl;
			set1.print(output);
		}
	}

	return 0;
}