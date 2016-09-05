#include <iostream>
using namespace std;

int main(){
	int x, y=0;
		cin>>x;
	while (x>=30)
	{x=x-30;
	y=y+1;
	}
	cout<<"Classrooms: "<< y<<"\n";
	cout<<"Remainder: "<<x<<"\n\n";
	

system("pause");
return 0;
}