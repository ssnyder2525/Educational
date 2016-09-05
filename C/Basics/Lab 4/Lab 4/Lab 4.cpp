/* 

1.I tested -9 as the slot number in both single token and multi-token scenerios. I was reprompted for the slot number until it was withing 0 and 8.

2.I tested the quit menu in every situaion. The program terminated.

3. I entered -1 as the number of tokens to put into a slot. I was asked to reenter the number until it was positive.

*/

#include <iostream>
#include <string>
#include <time.h>
using namespace std;

int main()
{
	srand(time(0));
	double money;
	money=0;



	int choice=1;


	while(choice == 1)
	{

	cout<<"What would you like to do?\n\n1) Insert one token\n2)Insert multiple tokens\n3)Quit\n";
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


					cout<<slot<<", ";
				}
				else
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


					cout<<slot;

				}
			}

			cout<<"]\n\n";



			//Rewards

			if (slot ==0)
			{
				cout<<"You have won $100\n\n";
				money=money+100;
				//cout<<"You have $"<<money<<"\n\n\n\n";
				slot=-1;
			}

			if (slot ==1)
			{
				cout<<"You have won $500\n\n";
				money=money+500;
				//cout<<"You have $"<<money<<"\n\n\n\n";
				slot=-1;
			}

			if (slot ==2)
			{
				cout<<"You have won $1,000\n\n";
				money=money+1000;
				//cout<<"You have $"<<money<<"\n\n\n\n";
				slot=-1;
			}

			if (slot ==3)
			{
				cout<<"You have won $0\n\n";
				//cout<<"You have $"<<money<<"\n\n\n\n";
				slot=-1;
			}

			if (slot ==4)
			{
				cout<<"You have won $10,000\n\n";
				money=money+10000;
				//cout<<"You have $"<<money<<"\n\n\n\n";
				slot=-1;
			}

			if (slot ==5)
			{
				cout<<"You have won $0\n\n";

				//cout<<"You have $"<<money<<"\n\n\n\n";
				slot=-1;
			}

			if (slot ==6)
			{
				cout<<"You have won $1,000\n\n";
				money=money+1000;
				//cout<<"You have $"<<money<<"\n\n\n\n";
				slot=-1;
			}

			if (slot ==7)
			{
				cout<<"You have won $500\n\n";
				money=money+500;
				//cout<<"You have $"<<money<<"\n\n\n\n";
				slot=-1;
			}

			if (slot ==8)
			{
				cout<<"You have won $100\n\n";
				money=money+100;
				//cout<<"You have $"<<money<<"\n\n\n\n";
				slot=-1;
				
			}
			
		
		}
		
		

	}





	if (choice == 2)
	{

	

		double money2=0;
		double slot;
		double tokens;
//tokens
		
		
		cout<<"how many tokens would you like to use?";
		cin>>tokens;

		
		double origtokens;
		origtokens=tokens;
		
						
		
		if (tokens>0)
		{
		
			cout<< "Which slot would you like to put your token(s) in? (0-8)\n";
			cin>>slot;
			int oslot=slot;
		
	
				 if ((slot == 0) || (slot == 1) || (slot == 2) || (slot == 3) || (slot == 4) || (slot == 5) || (slot == 6) || (slot == 7) || (slot == 8))
				{
					
					int tim=1;
					for(tim;tim<=12;tim++)
					{
						int R = rand() % 2+1;

						if(tim>=11)
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
							

						
						}
						else
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

		
							
							
						}}
					

					
					


					//Rewards

				if (tim == 13)
				{

					if (slot ==0)
					{
						
						money=money+100;
						money2=money2+100;
					}

					if (slot ==1)
					{
						
						money=money+500;
						money2=money2+500;
					}

					if (slot ==2)
					{
						
						money=money+1000;
						money2=money2+1000;
					}

					if (slot ==3)
					{
						
					}

					if (slot ==4)
					{
						
						money=money+10000;
						money2=money2+10000;
					}

					if (slot ==5)
					{
						
						
					}

					if (slot ==6)
					{
						
						money=money+1000;
						money2=money2+1000;
					}

					if (slot ==7)
					{
						
						money=money+500;
						money2=money2+500;
					}

					if (slot ==8)
					{
						
						money=money+100;
						money2=money2+100;
					}			
		
			tokens=tokens-1;
				if (tokens==0)	
				{
					cout<<"You have won $"<<money2<<"\n\n";
					cout<<"Average money per token this time: $"<<money2/origtokens;
					cout<<"\n\n\n";
			choice=1;
				}
				
		
	}

	else if ((slot != 0) && (slot != 1) && (slot != 2) && (slot != 3) && (slot != 4) && (slot != 5) && (slot != 6) && (slot != 7) && (slot != 8))
				
						{
							cout<<"Error. Please try again\n\n";
							
							choice=1;
			
						}


}
		else if ((tokens<0))
				
						{
							cout<<"Error. Please try again\n\n";
							
							choice=1;
			
						}
						

				}	
		
		}

		





		while ((choice !=1) && (choice !=2) && (choice !=3))
		{
			cout<< "I don't understand\n";
			choice=1;
		}

		while (choice == 3)
		{
			break;
			
		}
		

		}
			
		
	

	system("pause");
	return 0;
}