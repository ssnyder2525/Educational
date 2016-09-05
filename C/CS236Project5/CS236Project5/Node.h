#pragma once
#include <set>
#include <string>

using namespace std;
class Node
{
public:
	Node();
	~Node();
	set<string> names;
	bool visited = false;
	int dfsValue;
	int distanceFromQuery = 0;
};

