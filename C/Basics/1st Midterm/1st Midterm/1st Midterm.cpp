//Stephen Snyder     26 826 1194                          CS 142 Fall 2013 Midterm 1

/*  TEST CASES:

1.
	Menu

	Please Choose one of the following option:
	1) Exchange US Dollars to C-MASMAS coins
	2)Show a list of equivalents
	3)Exit the program
	4)Exchange US Dollars to C-MASMAS coins in mostly one coin

	1

	Enter the amount of money (U.S. dollars) you would like to exchange for coins:
	0
	ERROR: NOT A VALID AMOUNT

	Menu

	Please Choose one of the following option:
	1) Exchange US Dollars to C-MASMAS coins
	2)Show a list of equivalents
	3)Exit the program
	4)Exchange US Dollars to C-MASMAS coins in mostly one coin

	1

	Enter the amount of money (U.S. dollars) you would like to exchange for coins:
	
	10541

	THANK YOU. HERE IS YOUR CHANGE:

	Burton: 239
	Seppi: 0
	Clement: 3
	Child: 0
	Tee-A: 1



2.
	Menu

	Please Choose one of the following option:
	1) Exchange US Dollars to C-MASMAS coins
	2)Show a list of equivalents
	3)Exit the program
	4)Exchange US Dollars to C-MASMAS coins in mostly one coin

	2

	Enter the amount of money (U.S. dollars) you would like to start your list 
	with:

	0

	ERROR: NOT A VALID AMOUNT

	Menu

	Please Choose one of the following option:
	1) Exchange US Dollars to C-MASMAS coins
	2)Show a list of equivalents
	3)Exit the program
	4)Exchange US Dollars to C-MASMAS coins in mostly one coin

	2

	Enter the amount of money (U.S. dollars) you would like to start your list 
	with:

	40

	LIST:

	HERE IS YOUR CHANGE FOR 40 DOLLAR(S).
	Burton: 0
	Seppi: 1
	Clement: 1
	Child: 1
	Tee-A: 0

	........

	HERE IS YOUR CHANGE FOR 1 DOLLAR(S).
	Burton: 0
	Seppi: 0
	Clement: 0
	Child: 0
	Tee-A: 1

	DONE!

3.
	Menu

	Please Choose one of the following option:
	1) Exchange US Dollars to C-MASMAS coins
	2)Show a list of equivalents
	3)Exit the program
	4)Exchange US Dollars to C-MASMAS coins in mostly one coin

	4

	Enter the amount of money (U.S. dollars) you would like to exchange for 
	coins:

	40

	What coin would you like the most of?

	burton
	
	ERROR: COIN VALUE IS GREATER THAN MONEY AVAILABLE

	Menu

	Please Choose one of the following option:
	1) Exchange US Dollars to C-MASMAS coins
	2)Show a list of equivalents
	3)Exit the program
	4)Exchange US Dollars to C-MASMAS coins in mostly one coin

	4

	Enter the amount of money (U.S. dollars) you would like to exchange for 
	coins:

	28

	What coin would you like the most of?

	seppi
	
	ERROR: COIN VALUE IS GREATER THAN MONEY AVAILABLE

	Menu

	Please Choose one of the following option:
	1) Exchange US Dollars to C-MASMAS coins
	2)Show a list of equivalents
	3)Exit the program
	4)Exchange US Dollars to C-MASMAS coins in mostly one coin

	4

	Enter the amount of money (U.S. dollars) you would like to exchange for 
	coins:

	85

	What coin would you like the most of?

	clement
	
	THANK YOU. HERE IS YOU CHANGE:

	Burton: 0
	Seppi: 0
	Clement: 10
	Child: 1
	Tee-A: 2

	(10*8=80
	1*3=3
	2*1=2
	Total=85)

*/
#include <iostream>
#include <string>

using namespace std;



int calcburtons(int money1, int Burton)
{
	while (money1>=44)
	{
		Burton = Burton +1;
		money1=money1-44;
	}
	return Burton;
	return money1;
					
}//end of option1burton function

int calcseppis(int money1, int Seppi)
{
	while (money1>=29)
	{
		Seppi=Seppi+1;
		money1=money1-29;
	}
	return Seppi;
	return money1;
}//end of option1seppi function

int calcclements(int money1, int Clement)
{
	while (money1>=8)
	{
		Clement=Clement+1;
		money1=money1-8;
	}
	return Clement;
	return money1;
}//end of option1clement function

int calcchilds(int money1, int Child)
{
	while (money1>=3)
	{
		Child=Child+1;
		money1=money1-3;
	}
	return Child;
	return money1;
}//end of option1child function

int calcteeas(int money1, int Teea)
{
	while (money1>=1)
	{
		Teea=Teea+1;
		money1=money1-1;
	}
	return Teea;
	return money1;
}//end of option1teea function




int main()
{
	int option=1;
	while (option=1)
	{
	//variables
		int money=0;
		int originalmoney=0;
		int Burton=0;
		int Seppi=0;
		int Clement=0;
		int Child=0;
		int Teea=0;
	//MENU
		cout << "Menu\n\n";
		cout << "Please enter one of the following options:\n";
		cout << "1) Exchange US Dollars to C-MASMAS coins\n";
		cout << "2) Show a list of equivalents\n";
		cout << "3) Exit the program\n";
		cout << "4) Exchange US Dollars to C-MASMAS coins in mostly one coin\n\n";
		cin >> option;

		
//Exchange US Dollars		
		
		if (option == 1)
		{
			
			cout << "\n\nEnter the amount of money (U.S. dollars) you would like to exchange for coins:\n\n";
			cin >> money;
			if (money >0)
			{
				Burton=calcburtons(money, 0); //gets number of burtons
					money= money - (44*Burton);  //subtracts the money used in exchanging to burtons
				Seppi=calcseppis(money,0);		//etc........
					money= money - (29*Seppi);
				Clement=calcclements(money,0);
					money= money - (8*Clement);
				Child=calcchilds(money,0);
					money= money - (3*Child);
				Teea=calcteeas(money,0);
					money= money - Teea;

				
				cout << "\nTHANK YOU. HERE IS YOUR CHANGE:\n\n";
				cout << "Burton: "<<Burton<<"\nSeppi: "<<Seppi<<"\nClement: "<<Clement<<"\nChild: "<<Child<<"\nTee-A: "<<Teea<<"\n\n";
				
				option=1;


			}//end of money>0 if

			else
			{
				cout << "ERROR: NOT A VALID AMOUNT\n\n";
				option = 1;
			}//end of else to money>0

		}//end of option 1 if statement


//Show a list of equivalences

		if (option == 2)
		{
			cout << "\n\nEnter the amount of money (U.S. dollars) you would like to start your list \nwith:\n\n";
			cin >> money;
			originalmoney=money;//used to keep track of the original money input.
			if (money>0)
			{
				Burton=calcburtons(money, 0);
					money= money - (44*Burton);
				Seppi=calcseppis(money,0);
					money= money - (29*Seppi);
				Clement=calcclements(money,0);
					money= money - (8*Clement);
				Child=calcchilds(money,0);
					money= money - (3*Child);
				Teea=calcteeas(money,0);
					money= money - Teea;

				
				cout << "\nLIST:\n\n";
				cout << "HERE IS YOUR CHANGE FOR "<<originalmoney<<" DOLLAR(S).\n";
				cout << "Burton: "<<Burton<<"\nSeppi: "<<Seppi<<"\nClement: "<<Clement<<"\nChild: "<<Child<<"\nTee-A: "<<Teea<<"\n\n";
				money=originalmoney-1;
				originalmoney=originalmoney-1;
				
				while(originalmoney>0) //continues the list of exchanges
				{
					Burton=calcburtons(money, 0);
						money= money - (44*Burton);
					Seppi=calcseppis(money,0);
						money= money - (29*Seppi);
					Clement=calcclements(money,0);
						money= money - (8*Clement);
					Child=calcchilds(money,0);
						money= money - (3*Child);
					Teea=calcteeas(money,0);
						money= money - Teea;

				
				
					cout << "HERE IS YOUR CHANGE FOR "<<originalmoney<<" DOLLAR(S).\n";
					cout << "Burton: "<<Burton<<"\nSeppi: "<<Seppi<<"\nClement: "<<Clement<<"\nChild: "<<Child<<"\nTee-A: "<<Teea<<"\n\n";
					money=originalmoney-1;
					originalmoney=originalmoney-1;
				}//end of while originalmoney>1
			cout<<"DONE!\n\n";
				
			option=1;
			}//end of if money>0

			else
			{
				cout << "ERROR: NOT A VALID AMOUNT\n\n";
				option = 1;
			}//end of else for money >0
		}//end of option 2 if statement


//Exit the program

		if (option == 3)
		{
			break;

		}//end of option 3 if statement


//Extra Credit option

		if (option == 4)
		{
			cout << "\n\nEnter the amount of money (U.S. dollars) you would like to exchange for \ncoins:\n\n";
			cin >> money;
			cout << "\n";
			if (money>0)
			{
				cout << "What coin would you like the most of?\n\n";
				string coin;
				cin>>coin;
				cout << "\n";
				if ((coin == "Burton") || (coin =="burton"))
				{

				if (money>=44)
					{
					Burton=calcburtons(money, 0);
						money= money - (44*Burton);
					Seppi=calcseppis(money,0);
						money= money - (29*Seppi);
					Clement=calcclements(money,0);
						money= money - (8*Clement);
					Child=calcchilds(money,0);
						money= money - (3*Child);
					Teea=calcteeas(money,0);
						money= money - Teea;

					cout << "\nTHANK YOU. HERE IS YOUR CHANGE:\n\n";
					cout << "Burton: "<<Burton<<"\nSeppi: "<<Seppi<<"\nClement: "<<Clement<<"\nChild: "<<Child<<"\nTee-A: "<<Teea<<"\n\n";
					
					option=1;
					}
				
					else
					{
						cout <<"ERROR: COIN VALUE IS GREATER THAN MONEY AVAILABLE\n\n";
						option =1;
					}
				}//end of if burton coin input

				else if ((coin == "Seppi") || (coin == "seppi"))
				{
					if (money >=29)
					{
					Seppi=calcseppis(money,0);
						money= money - (29*Seppi);
					Clement=calcclements(money,0);
						money= money - (8*Clement);
					Child=calcchilds(money,0);
						money= money - (3*Child);
					Teea=calcteeas(money,0);
						money= money - Teea;

					cout << "\nTHANK YOU. HERE IS YOUR CHANGE:\n\n";
					cout << "Burton: "<<Burton<<"\nSeppi: "<<Seppi<<"\nClement: "<<Clement<<"\nChild: "<<Child<<"\nTee-A: "<<Teea<<"\n\n";
					
					option=1;
					}
					else
					{
						cout <<"ERROR: COIN VALUE IS GREATER THAN MONEY AVAILABLE\n\n";
						option =1;
					}
				}//end of if seppi coin input

				else if ((coin == "Clement") || (coin == "clement"))
				{
					if(money>=8)
					{
					Clement=calcclements(money,0);
						money= money - (8*Clement);
					Child=calcchilds(money,0);
						money= money - (3*Child);
					Teea=calcteeas(money,0);
						money= money - Teea;

					cout << "\nTHANK YOU. HERE IS YOUR CHANGE:\n\n";
					cout << "Burton: "<<Burton<<"\nSeppi: "<<Seppi<<"\nClement: "<<Clement<<"\nChild: "<<Child<<"\nTee-A: "<<Teea<<"\n\n";
					option=1;
					}
					else
					{
						cout <<"ERROR: COIN VALUE IS GREATER THAN MONEY AVAILABLE\n\n";
						option =1;
					}
				}//end of if clement coin input

				else if ((coin == "Child") || (coin == "child"))
				{
					if (money>= 3)
					{
					Child=calcchilds(money,0);
						money= money - (3*Child);
					Teea=calcteeas(money,0);
						money= money - Teea;

					cout << "\nTHANK YOU. HERE IS YOUR CHANGE:\n\n";
					cout << "Burton: "<<Burton<<"\nSeppi: "<<Seppi<<"\nClement: "<<Clement<<"\nChild: "<<Child<<"\nTee-A: "<<Teea<<"\n\n";
					option=1;
					}
					else
					{
						cout <<"ERROR: COIN VALUE IS GREATER THAN MONEY AVAILABLE\n\n";
						option =1;
					}
				}//end of if child coin input

				else if ((coin == "Tee-A") || (coin == "Tee-a") || (coin == "tee-A") || (coin =="tee-a"))
				{
					if (money>=1)
					{
					Teea=calcteeas(money,0);
						money= money - Teea;

					cout << "\nTHANK YOU. HERE IS YOUR CHANGE:\n\n";
					cout << "Burton: "<<Burton<<"\nSeppi: "<<Seppi<<"\nClement: "<<Clement<<"\nChild: "<<Child<<"\nTee-A: "<<Teea<<"\n\n";
					
					option=1;
					}
					else
					{
						cout <<"ERROR: COIN VALUE IS GREATER THAN MONEY AVAILABLE\n\n";
						option =1;
					}
				}//end of if tee-a coin input
				
				else
				{
					cout << "ERROR: NOT A VALID COIN NAME\n\n";
					option = 1;
				}//end of else coin input
				
			}//end of if money>0

			else
			{
				cout << "ERROR: NOT A VALID AMOUNT\n\n";
				option = 1;
			}//end of else money>0


		}//end of option 4 if statement

		else if ((option != 1) && (option != 2) && (option != 3) && (option != 4))
		{
			cout << "ERROR: NOT A VALID INPUT\n\n";
			option= 1;

		}//end of else for menu error
	}//end of menu
	

system ("pause");
return 0;
}//end of main