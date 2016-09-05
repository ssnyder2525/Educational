#pragma once
#include <map>
#include <string>
#include <iostream>
#include "Node.h"
#include "Predicate.h"
#include "Rule.h"
#include <fstream>
#include <stack>

using namespace std;
class Graph
{
public:
	Graph();
	~Graph();
	map<string, Node> Nodes;
	vector<string> postOrdered;
	map<string, vector<string>> backwardEdges;
	set<string> path;

	void buildGraph(vector<Predicate> &queries, vector<Rule> &rules);
	void checkForEmptyRules(int i, vector<Rule> &rules);
	void checkRuleHead(Predicate &query, Predicate &ruleHead, int i, int j);
	void checkRuleBody(Predicate &rule, vector<Rule> &rules, int i, int j);
	void printGraph(ofstream &out);
	void printQuery(Predicate &query, ofstream &out);
	int depthFirstSearch(Node &node, int dfsValue, string &title);
	bool checkBackwardEdges(Node &queryNode);
	void resetNodes();
};

