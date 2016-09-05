#include "Database.h"
#include <fstream>
#include <map>
#include <string>
#include "DatalogProgram.h"
#include "Graph.h"

using namespace std;


Database::Database()
{
}


Database::~Database()
{
}

void Database::init(ofstream &out, DatalogProgram &dLP)
{
	//Analyze schemes
	analyzeSchemes(dLP);
	printSchemesAnalyzed(out);

	//Analyze facts
	analyzeFacts(dLP);
	out << "Fact Evaluation\n\n";
	printFactsAnalyzed(out);

	//build the graph
	out << "Dependency Graph\n";
	Graph graph;
	graph.buildGraph(dLP.Queries, dLP.Rules);
	graph.printGraph(out);
	out << "\n";

	//analyze the queries

	bool cycles = false;
	out << "Query Evaluation\n\n";
	for (unsigned int i = 0; i < dLP.Queries.size(); i++)
	{
		graph.printQuery(dLP.Queries[i], out);
		out << "\n\n";
		string search = "Q" + to_string(i + 1);
		map<string, Node>::iterator mapIt = graph.Nodes.find(search);
		if (mapIt != graph.Nodes.end())
		{
			string title = (*mapIt).first;
			int result = graph.depthFirstSearch((*mapIt).second, 1, title);
			out << "Postorder Numbers\n";
			int pathSize = graph.path.size();
			for (set<string>::iterator g = graph.path.begin(); g != graph.path.end(); g++)
			{
				string search = (*g);
				map<string, Node>::iterator mapIt2 = graph.Nodes.find(search);
				out << "  " << (*mapIt2).first << ": " << (*mapIt2).second.dfsValue << "\n";
			}
		}
		out << "\n";
		//List rules in Posterior Order
		out << "Rule Evaluation Order\n";
		int k = 0;
		listOrdered(graph, k, out);
		out << "\n";

		//find backward edges
		cycles = graph.checkBackwardEdges((*mapIt).second);
		out << "Backward Edges\n";
		if (graph.backwardEdges.size() != 0)
		{
			printBackwardEdges(graph.backwardEdges, out);
		}
		out << "\n";
		graph.backwardEdges.clear();

		//Analyze rules
		out << "Rule Evaluation\n";

		//Eval Rules
		int totalFacts = 0;
		int totalFacts2 = 1;
		int passes = 0;
		set<Tuple> resultingTuples;
		//while changes take place
		if (graph.postOrdered.size() > 0)
		{
			analyzeRules2(graph,dLP,out,totalFacts,totalFacts2,resultingTuples,passes,cycles);
		}
		out << "\n";
		//this->printFactsAnalyzed(out);
		graph.postOrdered.clear();

		//Analyze query
			out << dLP.Queries[i].name << "(";
			unsigned int i2 = 0;
			analyzeQueries(i2, dLP, i, out);
			out << ")?";
			analyzeQuery(dLP.Queries[i], out);
			out << "\n";

			graph.resetNodes();
			graph.path.clear();
	}
}

void Database::listOrdered(Graph &graph, int k, ofstream &out)
{
	while (k != graph.postOrdered.size())
	{
		out << "  " << graph.postOrdered[k] << "\n";
		k++;
	}
}

void Database::analyzeSchemes(DatalogProgram &dLP)
{
	for (unsigned int i = 0; i < dLP.Schemes.size(); i++)
	{
		addNewScheme(dLP.Schemes[i]);
	}
}

void Database::analyzeFacts(DatalogProgram &dLP)
{
	for (unsigned int i = 0; i < dLP.Facts.size(); i++)
	{
		addNewFact(dLP.Facts[i]);
	}
}


void Database::analyzeRules(Graph &graph, DatalogProgram &dLP, ofstream &out, int &totalFacts2, set<Tuple> &resultingTuples)
{
	for (unsigned int n = 0; n < graph.postOrdered.size(); n++)
	{
		string ruleNumSt = graph.postOrdered.at(n);
		int numOfChars = ruleNumSt.size();
		ruleNumSt = ruleNumSt.substr(1,numOfChars);
		//gets the rule number in the rule vector
		int ruleNum = atoi(ruleNumSt.c_str()) - 1;
		dLP.printRule(out, dLP.Rules[ruleNum]);
		out << "\n";
		//DB.size()
		Relation ruleHead = this->findScheme(dLP.Rules[ruleNum].predHead);
		map<string, Relation>::iterator toChange = this->relations.find(ruleHead.Name);
		int sizeBefore = (*toChange).second.tuples.size();
		vector<Relation> relationsToBeJoined;
		//for every scheme on the right side.
		analyzeRightSide(dLP, out, relationsToBeJoined, ruleNum);

		//Join all the lists of Tuples.
		joinRightSide(relationsToBeJoined);


		//for every param of the new scheme
		//to store the positions of each parameter of the head scheme
		vector<int> positionsToGet;
		findPositionsToGet(positionsToGet, dLP, relationsToBeJoined, ruleNum);


		getResultingTuples(relationsToBeJoined, sizeBefore, positionsToGet, toChange, resultingTuples);

		//DB.size()
		totalFacts2 = getTotalFacts();
		printRulesAnalyzed(out, (*toChange).second, resultingTuples);
		resultingTuples.clear();
	}
}

void Database::analyzeRules2(Graph &graph, DatalogProgram &dLP, ofstream &out, int &totalFacts, int &totalFacts2, set<Tuple> &resultingTuples, int &passes, bool &cycles)
{
	while (totalFacts != totalFacts2)
	{
		totalFacts = getTotalFacts();
		//for each rule
		analyzeRules(graph, dLP, out, totalFacts2, resultingTuples);

		passes++;
		if (cycles == false)
		{
			totalFacts = totalFacts2;
		}
	}
}

void Database::analyzeQueries(unsigned int &i2, DatalogProgram &dLP, unsigned int &i, ofstream &out)
{
	while (i2 < dLP.Queries[i].params.size())
	{
		out << dLP.Queries[i].params[i2].name << dLP.Queries[i].params[i2].value;
		if (i2 != dLP.Queries[i].params.size() - 1)
		{
			out << ",";
		}
		i2++;
	}
}

void Database::printBackwardEdges(map<string, vector<string>> &backwardEdges, ofstream &out)
{
	for (map<string, vector<string>>::iterator i = backwardEdges.begin(); i != backwardEdges.end(); i++)
	{
		if ((*i).second.size() != 0)
		{
			if ((*i).first[0] != 'Q')
			{
				out << "  " << (*i).first << ": ";
				int l = 0;
				while (l != (*i).second.size())
				{
					if ((*i).second[l] != "")
					{
						out << (*i).second[l] << " ";
					}
					l++;
				}
				out << "\n";
			}
		}
	}
}

void Database::analyzeRightSide(DatalogProgram &dLP, ofstream &out, vector<Relation> &relationsToBeJoined, int ruleNum)
{
	for (unsigned int j = 0; j < dLP.Rules[ruleNum].params.size(); j++)
	{
		Relation newRelation = analyzeRule(dLP.Rules[ruleNum].params[j], out);
		relationsToBeJoined.push_back(newRelation);
	}
}

void Database::joinRightSide(vector<Relation> &relationsToBeJoined)
{
	while (relationsToBeJoined.size() > 1)
	{
		Relation result = relationsToBeJoined[0].join(relationsToBeJoined[1]);
		relationsToBeJoined[0] = result;
		relationsToBeJoined.erase(relationsToBeJoined.begin() + 1);
	}
}

void Database::findPositionsToGet(vector<int> &positionsToGet, DatalogProgram &dLP, vector<Relation> &relationsToBeJoined, int ruleNum)
{
	for (unsigned int k = 0; k < dLP.Rules[ruleNum].predHead.params.size(); k++)
	{
		for (unsigned int j = 0; j < relationsToBeJoined[0].scheme.Names.size(); j++)
		{
			if (dLP.Rules[ruleNum].predHead.params[k].name == relationsToBeJoined[0].scheme.Names[j])
			{
				positionsToGet.push_back(j);
			}
		}
	}
}

void Database::getResultingTuples(vector<Relation> &relationsToBeJoined, int &sizeBefore, vector<int> &positionsToGet, map<string, Relation>::iterator toChange, set<Tuple> &resultingTuples)
{
	for (set<Tuple>::iterator sIt = relationsToBeJoined[0].tuples.begin(); sIt != relationsToBeJoined[0].tuples.end(); sIt++)
	{
		Tuple t;
		sizeBefore = (*toChange).second.tuples.size();
		for (unsigned int p = 0; p < positionsToGet.size(); p++)
		{
			t.push_back((*sIt).at(positionsToGet[p]));
		}
		(*toChange).second.tuples.insert(t);
		int sizeAfter = (*toChange).second.tuples.size();
		if (sizeBefore != sizeAfter)
		{
			resultingTuples.insert(t);
		}
	}
}

void Database::addNewScheme(Predicate &scheme)
{
	map<string, Relation>::iterator it = this->relations.find(scheme.name);
	if (it == this->relations.end())
	{
		Relation newRelation;
		newRelation.Name = scheme.name;
		this->relations[scheme.name] = newRelation;
	}
	it = this->relations.find(scheme.name);
	Scheme newScheme;
	for (unsigned int i = 0; i < scheme.params.size(); i++)
	{
		if (scheme.params[i].value == "")
		{
			newScheme.Names.push_back(scheme.params[i].name);
		}
		else
		{
			newScheme.Names.push_back(scheme.params[i].value);
		}
	}
	(*it).second.scheme = newScheme;
	this->relations.insert(pair<string, Relation>(scheme.name, (*it).second));
}

void Database::printSchemesAnalyzed(ofstream &out)
{
	out << "Scheme Evaluation\n\n";
}

void Database::addNewFact(Predicate &fact)
{
	map<string, Relation>::iterator it = this->relations.find(fact.name);
	if (it == this->relations.end())
	{
		Relation newRelation;
		newRelation.Name = fact.name;
		this->relations[fact.name] = newRelation;
	}
	int paramCounter = 0;
	Tuple newFact;
	for (unsigned int i = 0; i < fact.params.size(); i++)
	{
		if (fact.params[i].value == "")
		{
			newFact.push_back(fact.params[i].name);
			(*it).second.Name = (*it).second.scheme.Names[paramCounter];
			paramCounter++;
		}
		else
		{
			newFact.push_back(fact.params[i].value);
			(*it).second.Name = fact.name;
			paramCounter++;
		}
	}
	(*it).second.tuples.insert(newFact);
}

void Database::printFactsAnalyzed(ofstream &out)
{
	
	map<string, Relation>::iterator it = this->relations.begin();
	set<Tuple>::iterator sIt = (*it).second.tuples.begin();
	//for every relation
	for (it; it != this->relations.end(); it++)
	{
		sIt = (*it).second.tuples.begin();
		//print the name of the scheme
		out << (*it).first;
		//numer of parameters in the scheme
		int numberOfParams = (*it).second.scheme.Names.size();
		//for every tuple in the relation
		for (unsigned int i = 0; i < (*it).second.tuples.size(); i++)
		{
			int counter = 0;
			out << "\n  ";
			//count off the parameters;
			while (counter != numberOfParams)
			{
				//print the scheme name, then the fact value
				out << (*it).second.scheme.Names[counter] << "=";
				out << (*sIt).begin()[counter] << " ";
				counter++;
			}
			sIt++;
		}
		out << "\n\n";
	}
}

Relation Database::analyzeQuery(Predicate &query, ofstream &out)
{
	map<string, Relation>::iterator it = this->relations.find(query.name);
	Relation result= (*it).second;
	set<int> positionOfVariables;
	variables.clear();
	if (it == this->relations.end())
	{
		//say it was not found
	}
	else
	{
		map<string, Relation>::iterator it = this->relations.find(query.name);


		string valueToCheck;
		string nameToCheck;
		int positionToCheck = 0;
		int forVarStorage = 0;
		set<string> variablesUsed;

			for (unsigned int i = 0; i < query.params.size(); i++)
			{
				if (query.params[i].value[0] == '\'')
				{
					result = result.select(i, query.params[i].value);
				}
				else
				{
					if (variables.find(query.params[i].name) == variables.end())
					{
						positionOfVariables.insert(i);
						//to keep track of where the variables are.
						variables[query.params[i].name] = i;
					}
					else
					{
						result = result.selectVar(i, variables[query.params[i].name]);
					}
				}
			}
	}
	if (result.tuples.size() > 0)
	{
		out << " Yes(" << result.tuples.size() << ")";
		out << "\nselect\n";
		Scheme pattern = result.scheme;
		for (set<Tuple>::iterator sIt = result.tuples.begin(); sIt != result.tuples.end(); sIt++)
		{
			if (sIt != result.tuples.end())
			{
				out << "  ";
			}
			printTuple(sIt, out, pattern);
			out << "\n";
		}
		out << "project\n";
		result.scheme.Names = result.scheme.Names;
		result = result.project(positionOfVariables);
		printTuples(out, result);
		/*for (set<Tuple>::iterator i = result.tuples.begin(); i != result.tuples.end(); i++)
		{
			out << "  ";
			this->printTuple(i, out, result.scheme);
			out << '\n';
		}*/
		out << "rename\n";
		result = result.rename(query, positionOfVariables);
		printTuples(out, result);
		/*for (set<Tuple>::iterator i = result.tuples.begin(); i != result.tuples.end(); i++)
		{
			out << "  ";
			this->printTuple(i, out, result.scheme);
			out << '\n';
		}*/
	}
	else
	{
		out << " No\n";
	}
	return result;
}

void Database::printTuples(ofstream &out, Relation &result)
{
	for (set<Tuple>::iterator i = result.tuples.begin(); i != result.tuples.end(); i++)
	{
		out << "  ";
		this->printTuple(i, out, result.scheme);
		out << '\n';
	}
}

void Database::printTuple(set<Tuple>::iterator &tuple, ofstream &out, Scheme scheme)
{
	for (unsigned int i = 0; i < (*tuple).size(); i++)
	{
		if (i != 0)
		{
			out << " ";
		}
		out<< scheme.Names[i] << "=";
		out << (*tuple)[i];
	}
}

Relation Database::analyzeRule(Predicate &query, ofstream &out)
{
	map<string, Relation>::iterator it = this->relations.find(query.name);
	Relation result = (*it).second;
	set<int> positionOfVariables;
	variables.clear();
	if (it == this->relations.end())
	{
		//say it was not found
	}
	else
	{
		map<string, Relation>::iterator it = this->relations.find(query.name);


		string valueToCheck;
		string nameToCheck;
		int positionToCheck = 0;
		int forVarStorage = 0;
		set<string> variablesUsed;

		//for (set<Tuple>::iterator setIt = result.tuples.begin(); setIt != result.tuples.end(); setIt++)
		{
			for (unsigned int i = 0; i < query.params.size(); i++)
			{
				if (query.params[i].value[0] == '\'')
				{
					result = result.select(i, query.params[i].value);
				}
				else
				{
					if (variables.find(query.params[i].name) == variables.end())
					{
						positionOfVariables.insert(i);
						//to keep track of where the variables are.
						variables[query.params[i].name] = i;
					}
					else
					{
						result = result.selectVar(i, variables[query.params[i].name]);
					}
				}
			}
		}
	}
	if (result.tuples.size() > 0)
	{
		Scheme pattern = result.scheme;
		result.scheme.Names = result.scheme.Names;
		result = result.project(positionOfVariables);
		result = result.rename(query, positionOfVariables);
	}
	return result;
}

Relation Database::findScheme(Predicate &param)
{
	map<string, Relation>::iterator it = this->relations.find(param.name);
	if (it != this->relations.end())
	{
		return (*it).second;
	}
}

void Database::printRulesAnalyzed(ofstream &out, Relation &rule,set<Tuple> results)
{
	if (results.size() > 0)
	{
		set<Tuple>::iterator resultsIt = results.begin();
		//for every tuple in the relation
		for (resultsIt; resultsIt != results.end(); resultsIt++)
		{
			//numer of parameters in the scheme
			int numberOfParams = (*resultsIt).size();
			out << "  ";
			int counter = 0;
			//count off the parameters;
			while (counter != numberOfParams)
			{
				//print the scheme name, then the fact value
				out << rule.scheme.Names[counter] << "=";
				out << (*resultsIt)[counter] << " ";
				counter++;
			}
			out << "\n";
		}
	}
}

int Database::getTotalFacts()
{
	int total = 0;
	for (map<string, Relation>::iterator i = this->relations.begin(); i != this->relations.end(); i++)
	{
		total = total + (*i).second.tuples.size();
	}
	return total;
}