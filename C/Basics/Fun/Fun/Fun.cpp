#include <iostream>

using namespace std;

void variables(int& x)
{
	cin >> x;
}

int main()
{
	int x;
	variables(x);
	cout<<x;

system ("pause");
return 0;
}