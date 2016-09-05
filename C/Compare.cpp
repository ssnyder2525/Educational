#include <iostream>
#include <fstream>
#include <string>

using namespace std;

int main()
{
	string file1;
	string file2;
	cout << "Please type in the names of the files you want to compare." << endl;
	cin >> file1 >> file2;
	ifstream ifs;
	ifs.open("C:/Users/Stephen/Desktop/" + file1);
	ifstream ifs2;
	ifs2.open("C:/Users/Stephen/Desktop/" + file2);
	ofstream out;
	out.open("C:/Users/Stephen/Desktop/Results.txt");

	string ifsLine;
	string ifs2Line;
	bool failed = false;
	while (getline(ifs, ifsLine))
	{
		int lengthOfIfs = ifsLine.length();
		if (lengthOfIfs != 0)
		{
			lengthOfIfs--;
		}
		while (ifsLine[lengthOfIfs] == ' ')
		{
			ifsLine = ifsLine.substr(0, lengthOfIfs);
			lengthOfIfs = lengthOfIfs - 1;
		}
		if (getline(ifs2, ifs2Line))
		{
			int lengthOfIfs2 = ifs2Line.length();
			if (lengthOfIfs2 != 0)
			{
				lengthOfIfs2--;
			}
			while (ifs2Line[lengthOfIfs2] == ' ')
			{
				ifs2Line = ifs2Line.substr(0, lengthOfIfs2);
				lengthOfIfs2 = lengthOfIfs2 - 1;
			}
			if (ifsLine != ifs2Line)
			{
				//no
				out << "Failed!\n" << ifsLine << "  !=  " << ifs2Line << endl;
				failed = true;
			}
		}
		else
		{
			//no
			out << "Failed!\n" << ifsLine << "  !=  " << "End of File" << endl;
			failed = true;
		}
	}
	if (getline(ifs2, ifs2Line))
	{
		//no
		out << "Failed!\n" << "End of File" << "  !=  " << ifs2Line << endl;
		failed = true;
	}
	else
	{
		//YES!!!
		if (failed == false)
		{
			out << "The Documents are Identical!!";
		}
	}

	return 0;
}