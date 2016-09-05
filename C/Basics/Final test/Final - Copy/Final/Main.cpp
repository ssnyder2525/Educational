#include<iostream>
#include<string>
#include "Account.h"
#include "Bank.h"

 
using namespace std;
 
int main() {
	new Bank mybank;
	mybank::openAnAccount("Checking 100 Bob false");


	system("pause");
	return 0;
}