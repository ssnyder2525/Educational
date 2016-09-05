/*#include <iostream>
#include <string>
using namespace std;

int mai(){
	int first;
	int second;

		cout<< "Please give an int: ";
		cin>>first;
	while (cin.fail()){
		cin.clear();
		cout<<"please try again: ";
		cin.ignore(3,'/n');
		cin>>first;
			}
			
		cout<< "Please give another int: ";
		cin >>second;


	while (cin.fail()){
		cin.clear();
		cout<<"please try again: ";
		cin.ignore(3,'/n');
		cin>>second;
	}
	cout<< "first is: "<<first<<endl<< "second is: " << second<< endl;

	

}*/



/*#include <iostream>
#include <string>
using namespace std;

void Dude()
{
	int x;
	cin>>x;
	x=x*8;
	cout<<x;
	


}

int mai;

}