#include <iostream>
#include <string>
#include <vector>

using namespace std;

int* maker (int size)
{
	int*newstuff=new int[size];//new int means this is my memory until I delete it! Don't use it, computer!
	for (int i = 0; i<size; i++)
	{
		newstuff[i] =i*2;
	}
	return newstuff;
}

//NEVER Do this!!!! Makes a pointer that is free to be used in the time it takes to print it. It will probably come out a random number.
int* evilmaker()
{
	const int SIZE = 10;
	int stuff[SIZE];
	for (int i=0;i<SIZE;i++)
	{
		stuff[i] = i*2;
	}
	return stuff;
}

int main()
{/*
	string s = "hi class";
	char c[5];
	string os = "";
	os = os+c;
	cout<<"the letter is: "<<c<<".\n";

	int i=c;
	cout<< "the corresponding number is "<<i<<endl;
	vector<int> v;
	v.push_back(i);
	i =98;
	cout << (char) i << endl;


	

	for (int i = 98; i<198; i++)
	{
		cout <<(char)i;
}*/

	int friends_at_home=2;
	int friends_at_work=3;
	int a[] = {3,2,1};
	int* friends_where_I_am;
	cout<<"Beginning ...\n\n";

	cout<< "friends at home is stored at "<< &friends_at_home<<endl;
	cout<< "friends at work is stored at "<< &friends_at_work<<endl;

	friends_where_I_am=&friends_at_home;
	cout<< "friends where I am is: " << friends_where_I_am << endl;
	cout << "when pointner is pointing at friends at home the value is: " << *friends_where_I_am<< endl;

	cout << "change friends_where_I_am\n";
	friends_where_I_am =&friends_at_work;

	cout << "friends where I am is: " << friends_where_I_am<<endl;
	cout << "when pointer is pointing at friends at work the value is:" << *friends_where_I_am<<endl;

	cout<<"\nchange the number pointed to by friends where I am\n\n";
	*friends_where_I_am=4;

	cout<<"friends_where_I_am is: "<<friends_where_I_am<<endl;
	cout<<"when pointer is pointing at friends_at_work the value is: "<<*friends_where_I_am<<endl;
	cout<<"friends_at_work is: " <<friends_at_work<<endl;

	cout<<"\nArrays are different\n\n";

	cout<<"change friendswhereiam to point at an array\n";
	friends_where_I_am =a;

	cout<<"friends_where i am is: "<<friends_where_I_am<< endl;
	cout<<"when pointer is pointing at arroay the value is: "<<*friends_where_I_am<<endl;
	friends_where_I_am++;

	cout<<"friends_where_I_am is: " <<friends_where_I_am<<endl;
	cout<<"when pointer is pointing at array the value is: "<<*friends_where_I_am<<endl;

	cout<<"\nI'll manage my own memory, thank you!\n\n";

	cout<<"\nI'll manage my own memory, thank you!, first with integers:\n\n";

	cout<<"get some memory\n";
	friends_where_I_am = new int;
	cout<<"Set int...\n";
	*friends_where_I_am =42;

	cout<<"..and print it out\n";
	cout<<"friends_where_I_am is: "<< friends_where_I_am<<endl;
	cout<< "the value there is: "<<*friends_where_I_am<<endl;

	cout<< "Now delete the pointer.\n";
	delete friends_where_I_am;

	cout<< "Begin of BAD BAD BAD!!!!!!!!!! This is a dangling pointer!!\n";
		cout << "friends where I am is: " << *friends_where_I_am << endl;
		friends_where_I_am++;

		cout<< "friends_where_I_am is: " << friends_where_I_am<<endl;
		cout<<"when pointer is pointing at array the value is: " << *friends_where_I_am<<endl;
		cout<< "END of bad dangling pointer!!\n";

		cout <<"Get some memory, first, I need to know how much.";
		int mysize;
		cin>>mysize;
		friends_where_I_am = new int[mysize];
		cout<<"fill the array with stuff...\n";
		for (int i=0; i<mysize;i++)
		{
			friends_where_I_am[i] = i*2;
		}
	cout<<"friends_where_I_am is: "<< friends_where_I_am<< ")\n";

	cout<<"...and print it out\n";
	for (int i=0; i<mysize; i++)
	{
		cout<< friends_where_I_am[i]<<endl;
	}

	cout<<"Now delete the pointer.\n";
	delete[] friends_where_I_am;

	friends_where_I_am++;

	cout<<"Now using a function!!\n\n";
	mysize=4;
	friends_where_I_am = maker(mysize);
	//display it if you want.

	cout<<"Someone should free the memory, I guess I will.\n\n";
	delete friends_where_I_am;


	cin.ignore();
	cin.ignore();
	return 0;
}