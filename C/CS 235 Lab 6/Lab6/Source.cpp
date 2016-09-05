#include <iostream>
#include <fstream>
#include <string>
#include "Tree.h"

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


	Tree <string> tree;

	while (ifs >> cmd)
	{
		if (cmd == "clear")
		{
			tree.clear();
			output << "clear" << endl;
		}

		if (cmd == "add")
		{
			ifs >> cmd3;
			tree.add(cmd3);
			
			output << "add " << cmd3 << endl;
		}

		if (cmd == "remove")
		{
			ifs >> cmd3;
			string item = tree.remove(cmd3);
			output << "remove " << cmd3 << endl;
		}

		if (cmd == "find")
		{
			ifs >> cmd3;
			
			if (tree.find(tree.root, cmd3))
				output << "find " << cmd3 << " "<< "true" << endl;
			else
				output << "find " <<cmd3<<" "<< "false" << endl;
		}

		if (cmd == "print")
		{
			output << "print" << endl;
			tree.Print(output);
		}
	}

	return 0;
}
