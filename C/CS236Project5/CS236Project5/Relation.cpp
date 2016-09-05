#include "Relation.h"
#include "Predicate.h"
#include <fstream>
#include <algorithm>

using namespace std;

Relation::Relation()
{
}


Relation::~Relation()
{
}
//
Relation Relation::select(int position, string &value)
{
	set<Tuple> replacementTuples;
	set<Tuple>::iterator it = this->tuples.begin();
	for (it; it != this->tuples.end(); it++)
	{
		if (value[0] == '\'')
		{
			if ((*it)[position] == value)
			{
				replacementTuples.insert((*it));
			}
		}

	}
	this->tuples = replacementTuples;
	return *this;
}

Relation Relation::selectVar(int position, int secondPosition)
{
	set<Tuple> replacementTuples;
	set<Tuple>::iterator it = this->tuples.begin();
	for (it; it != this->tuples.end(); it++)
	{
		if ((*it)[position] == (*it)[secondPosition])
		{
			replacementTuples.insert((*it));
		}
	}
	this->tuples = replacementTuples;
	return *this;
}

Relation Relation::project(set<int> &positions)
{
	Relation result;
	result.Name = this->Name;
	//result.scheme = this->scheme;
	Scheme names;
	for (set<int>::iterator i = positions.begin(); i != positions.end(); i++)
	{
		names.Names.push_back(this->scheme.Names[(*i)]);
	}
	result.scheme = names;
	if (positions.size() != 0)
	{
		for (set<Tuple>::iterator i = this->tuples.begin(); i != this->tuples.end(); i++)
		{
			Tuple tuple;
			//out << "  ";
			for (set<int>::iterator setIt = positions.begin(); setIt != positions.end(); setIt++)
			{
				tuple.push_back((*i).at(*setIt));
				//out << this->scheme.Names[*setIt];
				//out << "=" << (*i).at(*setIt) << " ";
				
			}
			//out << "\n";
			result.tuples.insert(tuple);
		}
	}
	return result;
}

Relation Relation::rename(Predicate &query, set<int> &positions)
{
	Relation result;
	result.Name = this->Name;
	Scheme renamed;
	for (unsigned int i = 0; i < query.params.size(); i++)
	{
		if (query.params[i].name != "")
		{
			renamed.Names.push_back(query.params[i].name);
		}
	}
	result.scheme = renamed;
	if (positions.size() != 0)
	{
		for (set<Tuple>::iterator i = this->tuples.begin(); i != this->tuples.end(); i++)
		{
			Tuple tuple;
			int c = 0;
			for (set<int>::iterator setIt = positions.begin(); setIt != positions.end(); setIt++)
			{
				tuple.push_back((*i).at(c));
				c++;
			}
			result.tuples.insert(tuple);
		}
	}
	return result;
}

Relation Relation::join(Relation &r2)
{
//make the scheme s for the result relation
	Scheme s = this->makeScheme(this->scheme, r2.scheme);
	
//make a new empty relation r using scheme s
	Relation r;
	r.scheme = s;

	//for each Tuple in first scheme
	for (set<Tuple>::iterator i=this->tuples.begin(); i != this->tuples.end(); i++)
	{
		Tuple t1 = (*i);
		//for each tuple in second scheme
		for (set<Tuple>::iterator i2 = r2.tuples.begin(); i2 != r2.tuples.end(); i2++)
		{
			Tuple t2 = (*i2);

			//if t1 and t2 can join
			if (this->joinable(t1, t2, this->scheme, r2.scheme))
			{
				//join t1 and t2 to make tuple t
				Tuple t = this->makeTuple(t1, t2, this->scheme, r2.scheme);
				//add tuple t to relation r
				r.tuples.insert(t);
			}

		}
	}
	return r;
}

bool Relation::joinable(Tuple &t1, Tuple &t2, Scheme &s1, Scheme &s2)
{
	//for each n1 in s1(use an index)
	for (unsigned int i = 0; i < s1.Names.size(); i++)
	{
		//for each n2 in s2
		for (unsigned int j = 0; j < s2.Names.size(); j++)
		{
			//if n1 == n2 & v1 != v2
			if ((s1.Names[i] == s2.Names[j] && (t1[i] != t2[j])))
			{
				return false;
			}
		}
	}
	return true;
}

Tuple Relation::makeTuple(Tuple &t1, Tuple &t2, Scheme &s1, Scheme &s2)
{
	//copy t1 to t
	Tuple t = t1;
		//foreach n2 in s2
	for (unsigned int i = 0; i < s2.Names.size(); i++)
	{
		//if n2 not in s1
		if ((count(s1.Names.begin(), s1.Names.end(), s2.Names[i])) == 0)
		{
			//Add v2 to t
			t.push_back(t2.at(i));
		}
	}
	return t;
}

Scheme Relation::makeScheme(Scheme &s1, Scheme &s2)
{
	Scheme s = s1;
	//for each n2 in s2
	for (unsigned int i = 0; i < s2.Names.size(); i++)
	{
		//if n2 not in s1 use the count function.
		if ((count(s.Names.begin(), s.Names.end(), s2.Names[i])) == 0)
		{
			//Add name to s
			s.Names.push_back(s2.Names[i]);
		}
	}
	return s;
}