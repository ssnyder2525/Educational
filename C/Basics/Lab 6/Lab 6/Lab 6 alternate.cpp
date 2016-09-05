#include <iostream>
#include <iomanip>
#include <math.h>
#include <fstream>
#include <string>

using namespace std;

double averagecalc(double one, double two, double three, double four)
{
	double ans= (one+two+three+four)/4;
		return ans;
}




int main (){
	

	const int SIZE=20;
	double plate[SIZE][SIZE];
	int row;
	int col;
	for (col=0; col<SIZE; col++)
	{
	for (row=0; row<SIZE; row++)
	{
		if (((col==0) || (col==19)) && ((row!=0) && (row!=19)))
		{
			plate [row][col]=100;
			cout<<plate[row][col]<<",";
		}
		
		else
		{
			plate[row][col]=0;
			if (row==19)
			{
				cout<<plate[row][col];
			}
			else
			{
				cout<<plate[row][col]<<" , ";
			}
			
		}
	}
	cout<<"\n";
	}
	cout<<"\n\n\n";

	


double array1=0;
double array2=0;
double difference=1;
double difference2=0;
double totaldifference=0;
double totaldifference2=1;
int counter=0;

while (totaldifference2>.1)
{
	
	
	for (col=0; col<20; col++)
	{
	for (row=0; row<20; row++)
	{	
		
		array1=plate[row][col];

		if (((col==0)|| (col==19)) && ((row!=0) && (row!=19)))
		{
			plate [row][col]=100;
			//cout<<plate[row][col]<<",";
		}
		
	
		if ((col>0) && (row>0) && (col<19) && (row < 19))
		{
		double average =averagecalc(plate [row][col-1] , plate [row][col+1] , plate [row-1][col] , plate [row+1][col]);


			plate[row][col]=average;
			if (row==19)
			{
				//cout<<setprecision(2)<<fixed<<plate[row][col];
			}
			else
			{
				//cout<<setprecision(2)<<fixed<<plate[row][col]<<",";
			}
		}
		array2=plate[row][col];
		difference2=array2-array1;
		if (difference2>difference)
		{
			difference=difference2;
		}
		else
		{
		
		}
	}
	
	
	//cout<<"\n";
	}
	counter=counter+1;
	totaldifference=difference;
		if (totaldifference<totaldifference2)
		{
			totaldifference2=totaldifference;
		}
		difference=0;
		
	//cout<<"\n\n\n";
}
ofstream excelfile ("Lab6.csv");
	
	for (col=0; col<20; col++)
	{
	for (row=0; row<20; row++)
	{	
		if (row<19)
			{
				cout<<setprecision(2)<<fixed<<plate[row][col]<<",";
		}
		else
		{
			cout<<setprecision(2)<<fixed<<plate[row][col];
		}
	}
		cout<<endl;
	}
	cout<< "Where would you like to write the file?\n";
	string filelocation;
	getline(cin,filelocation);
	
	if (filelocation.substr(0,1) == "\"") 
	{
		filelocation = filelocation.substr(1,filelocation.length()-2)
	}
	ofstream myfile (filelocation);
	if (myfile.is_open());
	{
		for(int row=0;row<20;row++)
		{
			for (int col=0;col<20;col++)
			{
				out<<plate[row][col]<<",";
			}
			out<<endl;
		}
		out.close();
	}
	else if (myfile.is_closed())
	{
		cout<<"Error\n";
	}


//cout<<counter<<"\n";


	system ("pause");
	return 0;
}