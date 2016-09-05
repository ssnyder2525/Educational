/*  TEST CASES

1.
	1) Insert one token
	2) Insert multiple tokens
	3) Quit
	4) Drop into all slots
	1
	Which slot would you like to put your token in? (0-8)
	-4
	Please only whole numbers between 0 and 8

2.
	1) Insert one token
	2) Insert multiple tokens
	3) Quit
	4) Drop into all slots
	2

	How many tokens do you want to drop?
	10000
	Which slot would you like to put your token in? (0-8)
	5
	[You won $2.30279e+007
	Average money per token: 2302.79


3.1) 
	Insert one token
	2) Insert multiple tokens
	3) Quit
	4) Drop into all slots
	4

	How many tokens do you want to drop into each slot?
	10000
	You won $7.7766e+006 in slot 0 Average: 777.66
	You won 1.04176e+007 in slot 1 Average: 1041.76
	You won 1.64888e+007 in slot 2 Average: 1648.88
	You won 1.26327e+007 in slot 3 Average: 2263.27
	You won 2.55924e+007 in slot 4 Average: 2559.24
	You won 2.29634e+007 in slot 5 Average: 2296.34
	You won 1.59015e+007 in slot 6 Average: 1590.15
	You won 9.88e+006 in slot 7 Average: 988
	You won 7.7172e+006 in slot 8 Average: 771.72
	You have won a total of 139370200
	Average winnings per token: $1548

*/

#include <iostream>
#include <string>
#include <time.h>
using namespace std;

double rewards1(double slot,double money)
{

				if (slot ==0)
			{				
				money=money+100;		
			}
				if (slot ==1)
			{				
				money=money+500;
			}
				if (slot ==2)
			{				
				money=money+1000;
			}
				if (slot ==3)
			{							
			}
				if (slot ==4)
			{				
				money=money+10000;
			}
				if (slot ==5)
			{							
			}
				if (slot ==6)
			{				
				money=money+1000;
			}
				if (slot ==7)
			{				
				money=money+500;	
			}
				if (slot ==8)
			{				
				money=money+100;
			}
return money;
			
}


double findslot(double slot, double tim, double R)
{
		if (R ==1)
					{
						slot=slot-(.5);

							if(slot<0)
						{
							slot=.5;
						}

					}


						if (R == 2)
					{
						slot=slot+(.5);

							if(slot>8)
						{
							slot=7.5;
						}
					}
						return slot;
						return tim;
						return R;
}



int main()
{
	srand(time(0));
	double money;
	money=0;
	double money2=0;
	
int choice=1;
	while(choice == 1)
	{

	cout<<"What would you like to do?\n\n1) Insert one token\n2) Insert multiple tokens\n3) Quit\n4) Drop into all slots\n";
	cin>>choice;
	cout<<"\n\n";
		if (choice == 1)
	{
		double slot;
		cout<< "Which slot would you like to put your token in? (0-8)\n";
		cin>>slot;
			if ((slot != 0) && (slot != 1) && (slot != 2) && (slot != 3) && (slot != 4) && (slot != 5) && (slot != 6) && (slot != 7) && (slot != 8))
				
		{
			cout << "Please only whole numbers between 0 and 8\n\n";
			
			
		}

			while ((slot == 0) || (slot == 1) || (slot == 2) || (slot == 3) || (slot == 4) || (slot == 5) || (slot == 6) || (slot == 7) || (slot == 8))
		{
			int tim;
			int R;
			cout<<"[";


				for(tim =1;tim<=12;tim++)
			{
				
				R = rand() % 2+1;

					if(tim<=11)
				{
					slot=findslot(slot,tim,R);
					cout<<slot<<", ";
				}
					else
				{	
					slot=findslot(slot,tim,R);
					cout<<slot;
				}
				
			}

			cout<<"]\n\n";

			money=rewards1(slot,money);
			cout<<"You have won $"<<money<<"\n\n\n\n";
			money=0;
			slot=9;
		
		
		}
		

	}





	if (choice == 2)
	{
		int tokens;
		cout<<"How many tokens do you want to drop?\n";
		cin>> tokens;
		
	if (tokens>0)
	{
			
		
		double slot;
		cout<< "Which slot would you like to put your token in? (0-8)\n";
		cin>>slot;
		double slot2=slot;


		if ((slot != 0) && (slot != 1) && (slot != 2) && (slot != 3) && (slot != 4) && (slot != 5) && (slot != 6) && (slot != 7) && (slot != 8))
				
		{
			cout << "Please only whole numbers between 0 and 8\n\n";
			choice=1;
			
			
		}



		if ((slot == 0) || (slot == 1) || (slot == 2) || (slot == 3) || (slot == 4) || (slot == 5) || (slot == 6) || (slot == 7) || (slot == 8))
		{
			int tim;
			int R;
			cout<<"[";
		
		for(int i=1;i<=tokens;i++)
		{
			for(tim =1;tim<=12;tim++)
			{
				
				R = rand() % 2+1;

				if(tim<=12)
				{
					slot=findslot(slot,tim,R);				
				}
				}
			

			money=rewards1(slot, money);
			slot=slot2;
		
		}
		cout<<"You won $"<<money<<"\n";
		cout<<"Average money per token: "<<money/tokens<<"\n\n\n\n";
		money=0;
		choice=1;
	
		}
}

	if (tokens<=0)
	{
		cout<<"Please enter another number.\n";
		choice=1;
	}

}	

		while ((choice !=1) && (choice !=2) && (choice !=3)&&(choice!=4))
		{
			cout<< "I don't understand\n";
			choice=1;
		}

		while (choice == 3)
		{
			break;
			
		}
		
		while (choice == 4)
		{
			double slot;
			double R=0;
			double slot2=0;
			int total=0;
			int tokens;
			int tim;
			int i;
			cout<<"How many tokens do you want to drop into each slot?\n";
			cin>>tokens;
		if (tokens>0)
		{
			for (slot2=0;slot2<=8;slot2++)
			{
				slot=slot2;
				for(i=1;i<=tokens;i++)
				{
					

					for (tim=1;tim<=12;tim++)
					{
				R = rand() % 2+1;
					
					slot=findslot(slot,tim,R);
					

					}
					money=rewards1(slot,money);
					slot=slot2;
					
				}
				cout <<"You won $ "<< money << " in slot " << slot2;
				cout << " Average: " << money/tokens<<"\n";
					total = total + money;
					money = 0;
		}
			
			cout<<"You have won  a total of $"<<total<<"\n";
			cout<<"Average winnings per token: $"<<total/(tokens*9)<<"\n\n\n\n";
			money=0;
			choice=1;

		
		}
		
		else 
		{
			cout<<"Positive numbers only, please\n\n\n";
			choice =1;

		}		
	
		}
		}
	system("pause");
	return 0;
}