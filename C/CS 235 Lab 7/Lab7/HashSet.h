#pragma once
#include <iostream>
#include <fstream>
#include <string>
#include "List.h"

using namespace std;

template <typename type>

class HashSet {

private:
	List<type>* table;
	int tablesize;
	int size;   //How many items there are
public:
	HashSet()
	{
		table = NULL;
		tablesize=0;
		size=0;
	}
	void clear()
	{
		delete[] table;
		table = NULL;
		size = 0;
		tablesize = 0;
	}


//********************************************************************************************************************************************

	void add(const type& item) {
		if (!find(item))
		{
			//solves duplicate problem.

			if (size == tablesize)//table is full
			{
				rehashbigger();
			}
			unsigned int i = hashcode(item);
			int iter = table[i].size();
			table[i].insert(iter, item);
			size++;
		}
	}

//********************************************************************************************************************************************

	type remove(const type& item) {

		if (find(item))
		{
			//solves duplicate problem.

			unsigned int i = hashcode(item);
			int index = table[i].find(item);
			table[i].remove(index); // problem with duplicates.
			size--;
			if (size <= tablesize / 2)//if table is half full
			{
				rehashsmaller();
			}
			
			return item;
		}
		return type();
	}

//********************************************************************************************************************************************

	bool find(const type& item) {
		if (tablesize > 0)
		{
			unsigned int i = hashcode(item);
			int j = table[i].find(item);
			if (j != -1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
//********************************************************************************************************************************************


	void print(ofstream& output)
	{
		int c = 0;
		int count = 0;
		type p = type();
		for (unsigned int i = 0; i < tablesize; i++)
		{
			c = 0;
			count = 0;
			output << "Hash " << i << ": ";
			int listsize = table[i].size();
			while (c < listsize)
			{
				count++;
				p = table[i].getitem(c);
				output <<p<<" ";
				if ((count % 8 == 0) && (c!= listsize-1))
				{
					output << endl << "Hash " << i << ": ";
				}
				c++;
			}
			output << endl;
		}
	}

//********************************************************************************************************************************************

	unsigned int hashcode(const std::string& s)
	{
		char c;
		unsigned int hashcode=0;
		for (unsigned int i = 0; i<s.size(); i++)
		{
			c = s[i];
			hashcode *= 31;
			hashcode += c;
		}
			hashcode = hashcode%tablesize;
			return hashcode;
	}

	//********************************************************************************************************************************************

	void rehashbigger()
	{

		tablesize = tablesize * 2 + 1;//create bigger array;
		if (tablesize == 1)
		{
			if (table != NULL)
			{
				delete[] table;
			}
			table = new List<type>[1];
		}
		else
		{
			//This is the test
			List<type>* newtable = new List<type>[tablesize];
			rehashitems(newtable);
		}
	}

//********************************************************************************************************************************************

	void rehashsmaller()
	{
		tablesize = tablesize / 2;//create smaller array;
		if (tablesize == 0)
		{
			delete[] table;
			table = NULL;
		}
		else
		{
			//This is the test
			
			List<type>* newtable = new List<type>[tablesize];
			rehashitemssmaller(newtable);
		}
	}

//********************************************************************************************************************************************

	void rehashitems(List<type>*& newtable)
	{
		int index = 0;
		List<type>* arraycopy = table;
		table = newtable;
		size = 0;
		for (unsigned int i = 0; i < tablesize/2; i++)
		{
			index = 0;
			int arraysize = arraycopy[i].size();
			while ( arraysize!= 0)
			{
				type temp = arraycopy[i].getitem(index);
				add(temp);
				arraysize--;
				index++;
			}
		}
		delete[] arraycopy;
	}


	void rehashitemssmaller(List<type>*& newtable)
	{
		List<type>* arraycopy = table;
		table = newtable;
		size = 0;
		for (unsigned int i = 0; i < tablesize * 2+1; i++)
		{
			int arraysize = arraycopy[i].size();
			while (arraysize != 0)
			{
				type temp = arraycopy[i].getitem(0);
				add(temp);
				arraysize--;
			}
		}
		delete[] arraycopy;
	}
	
	~HashSet()
	{
		clear();
	}

};