#include "Graph.h"
#include <vector>
#include "Predicate.h"
#include "Rule.h"

using namespace std;

Graph::Graph()
{
}


Graph::~Graph()
{
}

void Graph::buildGraph(vector<Predicate> &queries, vector<Rule> &rules)
{
	for (unsigned int i = 0; i < queries.size(); i++)
	{
		for (unsigned int j = 0; j < rules.size(); j++)
		{
			checkRuleHead(queries[i], rules[j].predHead, i, j);
		}
		checkForEmptyRules(i, rules);
	}

	for (unsigned int i = 0; i < rules.size(); i++)
	{
		for (unsigned int k = 0; k < rules[i].params.size(); k++)
		{
				checkRuleBody(rules[i].params[k], rules, k, i);
		}
	}
}

void Graph::checkForEmptyRules(int i, vector<Rule> &rules)
{
	if (rules.size() == 0)
	{
		string is = to_string(i + 1);
		string from = "Q" + is;
		Node newNode;
		Nodes[from] = newNode;
	}
}

void Graph::checkRuleHead(Predicate &query, Predicate &ruleHead, int i, int j)
{
	string is = to_string(i + 1);
	string js = to_string(j + 1);
	string from = "Q" + is;
	string to = "R" + js;
	if (query.name == ruleHead.name)
	{
		if (Nodes.find(from) == Nodes.end())
		{
			Node newNode;
			newNode.names.insert(to);
			Nodes[from] = newNode;
		}

		else
		{
			Nodes[from].names.insert(to);
		}
	}
	else
	{
		Node newNode;
		if (Nodes.find(from) == Nodes.end())
		{
			Node newNode;
			Nodes[from] = newNode;
		}
	}
}

void Graph::checkRuleBody(Predicate &rule, vector<Rule> &rules, int i, int j)
{
	for (unsigned int k = 0; k < rules.size(); k++)
	{
		string is = to_string(j + 1);
		string ks = to_string(k + 1);
		string from = "R" + is;
		string to = "R" + ks;
		if (rule.name == rules[k].predHead.name)
		{
			if (Nodes.find(from) == Nodes.end())
			{
				Node newNode;
				newNode.names.insert(to);
				Nodes[from] = newNode;
			}

			else
			{
				Nodes[from].names.insert(to);
			}
		}
		else
		{
			Node newNode;
			if (Nodes.find(from) == Nodes.end())
			{
				Node newNode;
				Nodes[from] = newNode;
			}
		}
	}
}

void Graph::printGraph(ofstream &out)
{
	for (map<string, Node>::iterator setI = this->Nodes.begin(); setI != this->Nodes.end(); setI++)
	{
		out << "  "<<(*setI).first<<":";
		for (set<string>::iterator setIt = (*setI).second.names.begin(); setIt != (*setI).second.names.end(); setIt++)
		{
			out << " " << (*setIt);
		}
		out << '\n';
	}
}

void Graph::printQuery(Predicate &query, ofstream &out)
{
	out << query.name << "(";
	int i2 = 0;
	while (i2 < query.params.size())
	{
		out << query.params[i2].name << query.params[i2].value;
		if (i2 != query.params.size() - 1)
		{
			out << ",";
		}
		i2++;
	}
	out << ")?";
}

int Graph::depthFirstSearch(Node &node, int dfsValue, string &title)
{
	node.visited = true;
	set<string>::iterator i = node.names.begin();
	for (i; i != node.names.end(); i++)
	{
		map<string, Node>::iterator i2 = this->Nodes.find(*i);
		if ((*i2).second.visited == false)
		{
			string nextTitle = (*i2).first;
			dfsValue = depthFirstSearch((*i2).second, dfsValue, nextTitle);
			postOrdered.push_back((*i2).first);
		}
		else
		{
			if ((*i2).second.visited > 0)
			{
				backwardEdges[title].push_back((*i2).first);
			}
		}
	}
	node.dfsValue = dfsValue;
	dfsValue++;
	path.insert(title);
	return dfsValue;
}


bool Graph::checkBackwardEdges(Node &queryNode)
{
	bool ret = false;
	map<string, vector<string>>::iterator i = this->backwardEdges.begin();
	for (i; i != this->backwardEdges.end(); i++)
	{
		map<string, Node>::iterator i2 = this->Nodes.find((*i).first);
		vector<string> copy;
		for (unsigned int j = 0; j < (*i).second.size(); j++)
		{
			map<string, Node>::iterator i3 = this->Nodes.find((*i).second[j]);
			if (((*i2).second.dfsValue < (*i3).second.dfsValue) || ((*i2).first == (*i3).first))
			{
				ret = true;
				copy.push_back((*i).second[j]);
			}
		}
		if ((*i).second.size() != copy.size())
		{
			(*i).second = copy;
		}
	}
	return ret;
}

void Graph::resetNodes()
{
	map<string, Node>::iterator i = this->Nodes.begin();
	for (i; i != this->Nodes.end(); i++)
	{
		(*i).second.visited = false;
	}
}