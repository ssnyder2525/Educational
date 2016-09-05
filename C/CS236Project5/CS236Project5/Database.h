#pragma once
#include "Relation.h"
#include <iostream>
#include <string>
#include <map>
#include "Predicate.h"
#include "DatalogProgram.h"
#include "Graph.h"
using namespace std;
class Database
{
public:
	Database();
	~Database();
	map<string, Relation> relations;
	map<string, int> variables;
	
	void init(ofstream &out, DatalogProgram &dLP);
	void analyzeFacts(DatalogProgram &dLP);
	void analyzeSchemes(DatalogProgram &dLP);
	void analyzeRules(Graph &graph, DatalogProgram &dLP, ofstream &out, int &totalFacts2, set<Tuple> &resultingTuples);
	void analyzeRules2(Graph &graph, DatalogProgram &dLP, ofstream &out, int &totalFacts, int &totalFacts2, set<Tuple> &resultingTuples, int &passes, bool &cycles);
	void analyzeQueries(unsigned int &i2, DatalogProgram &dLP, unsigned int &i, ofstream &out);
	void listOrdered(Graph &graph, int k, ofstream &out);
	void printBackwardEdges(map<string, vector<string>> &backwardEdges, ofstream &out);
	void analyzeRightSide(DatalogProgram &dLP, ofstream &out, vector<Relation> &relationsToBeJoined, int ruleNum);
	void joinRightSide(vector<Relation> &relationsToBeJoined);
	void findPositionsToGet(vector<int> &positionsToGet, DatalogProgram &dLP, vector<Relation> &relationsToBeJoined, int ruleNum);
	void getResultingTuples(vector<Relation> &relationsToBeJoined, int &sizeBefore, vector<int> &positionsToGet, map<string, Relation>::iterator toChange, set<Tuple> &resultingTuples);
	void addNewScheme(Predicate &scheme);
	void printSchemesAnalyzed(ofstream &out);
	void addNewFact(Predicate &fact);
	void printFactsAnalyzed(ofstream &out);
	Relation analyzeQuery(Predicate &query, ofstream &out);
	void rename(Predicate &query, ofstream &out);
	void printTuples(ofstream &out, Relation &result);
	void printTuple(set<Tuple>::iterator &tuple, ofstream &out, Scheme scheme);
	Relation analyzeRule(Predicate &query, ofstream &out);
	Relation findScheme(Predicate &param);
	void printRulesAnalyzed(ofstream &out, Relation &rule, set<Tuple> results);
	int getTotalFacts();
};

