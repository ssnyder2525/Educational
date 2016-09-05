#include "Arena.h"
#include <sstream>
#include "Archer.h"
#include "Robot.h"
#include "Cleric.h"

Arena::Arena(void)
{

}

Arena::~Arena(void)
{

}

bool Arena::addFighter(string info)
{
	//stringstream s;
	int spaces=0;
	string name1;
	string type1;
	int mph1;
	int strength1;
	int speed1;
	int magic1;

	
	for (unsigned int i=0; i<info.size();i++)
	{
		if (info[i]==' ')
		{
			spaces++;
		}
		if (info[info.size()-1]==' ')
		{
			spaces--;                                               //(if (info.fail())	if (!info.eof)
		}
	}
	if (spaces == 5)
	{  
		stringstream strm(info);
		strm>>name1>> type1>>mph1>> strength1>> speed1>> magic1;
		
		if ((mph1>0) && (strength1 >0) && (speed1> 0) && (magic1>0))
		{  
			for (unsigned int i =0; i<allfighters.size();i++)
			{ 
				if (name1 == allfighters[i]->getName())
				{
					return false;
				}
			}
			if (type1=="A")
			{  
				FighterInterface* newfighter = new Archer(name1,type1,mph1,strength1,speed1,magic1);
				
				allfighters.push_back(newfighter);
				
				return true;
				
			}
			else if (type1 == "R")
			{   
				FighterInterface* newfighter  = new Robot (name1,type1,mph1,strength1,speed1,magic1);
				
				allfighters.push_back(newfighter);
				
				return true;
			}
			else if (type1 == "C")
			{  
				FighterInterface* newfighter= new Cleric (name1,type1,mph1,strength1,speed1,magic1);
				
				allfighters.push_back(newfighter);
				
				return true;
			}
			else{return false;}
		}
		else
		{
			return false;
		}
	}
	else
	{
		return false;
	}

}

bool Arena::removeFighter(string name)
{
	
	for (unsigned int i=0; i<allfighters.size();i++)
	{
		if (name == allfighters[i]->getName())
		{
			allfighters.erase(allfighters.begin()+i);
			return true;
		}
		
	}
	return false;
}

FighterInterface* Arena::getFighter(string name)
{
	for (unsigned int i=0; i<allfighters.size();i++)
	{
		if (name == allfighters[i]->getName())
		{
			return allfighters[i];
		}
	}
	return NULL;
}
int Arena::getSize()
{
	int number=allfighters.size();
	return number;
}