#include <iostream>
#include <string>
using namespace std;

int main(){
	
 /*ONE*/

 string ans;
	
	cout<<"\"Q\" --quit\n";
	cout<< "\"S\" -- sing\n";
	cout<<"What do you want me to do?\n";
	getline(cin,ans);
	while(ans !="Q"){

for(int i=9;i>0;i--)  //i-- means grab i, use it, then suptract one from it. --i would mean subract one, then use it.
	{
	if (ans !="Q")
	{

	if (i==2)
		{
		cout<<i<<" bottles of beer on the wall.\n";
		cout<<i<<" bottles of beer.\n";
		cout<<"Take one down pass it around.\n";
		cout<< "1 more bottle of beer on the wall.\n\n";
	}

	else if (i == 1)
		{
		cout<<i<<" bottle of beer on the wall.\n";
		cout<<i<<" bottle of beer.\n";
		cout<<"Take it down pass it around.\n";
		cout<< "No more bottles of beer on the wall.\n\n";
	}

	
	else
		{
		cout<<i<<" bottles of beer on the wall.\n";
		cout<<i<<" bottles of beer.\n";
		cout<<"Take one down pass it around.\n";
		cout<<i-1<< " bottles of beer on the wall.\n\n";
		}
	
	getline(cin,ans);
	if (ans =="Q")
		{ans = "Q";
	}}
}
	}

/*TWO*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
system ("pause");
return 0;
}