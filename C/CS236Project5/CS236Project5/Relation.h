#pragma once
#include "Tuple.h"
#include "Scheme.h"
#include <set>
#include <map>
#include "Predicate.h"
using namespace std;
class Relation
{
public:
	Relation();
	~Relation();
	string Name;
	Scheme scheme;
	set<Tuple> tuples;
	
	Relation select(int position, string &value);
	Relation selectVar(int position, int secondPosition);
	Relation project(set<int> &positions);
	Relation rename(Predicate &query, set<int> &positions);
	Relation join(Relation &r2);
	bool joinable(Tuple &t1, Tuple &t2, Scheme &s1, Scheme &s2);
	Tuple makeTuple(Tuple &t1, Tuple &t2, Scheme &s1, Scheme &s2);
	Scheme makeScheme(Scheme &s1, Scheme &s2);
};

