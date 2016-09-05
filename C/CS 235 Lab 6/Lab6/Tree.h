#pragma once
#include <iostream>
#include <string>
#include <queue>
using namespace std;

template <typename ItemType>

class Tree {

private:
	struct Node
	{         //struct simply makes everything public.
		ItemType item;
		Node* left = NULL;
		Node* right = NULL;
		Node* parent = NULL;
		int height = 0;
	};

public:
	Node* root = NULL;


	int size = 0;

	void clear()
	{
		while (size != 0)
		{
			remove(root->item);
		}

	};



	void updateheight(Node* n)
	{
		int l = getheight(n->left);
		int r = getheight(n->right);
		if (l >= r)
		{
			n->height = l + 1;
		}
		else
		{
			n->height = r + 1;
		}
	}
	//----------------------------------------------------------------------------------------------------------------------------------------
	string haschildren(Node* n)
	{
		if (n == NULL)
		{
			return "0";
		}
		else if ((n->left != NULL) && (n->right == NULL))
			return "left";
		else if ((n->left != NULL) && (n->right != NULL))
			return "both";
		else if ((n->left == NULL) && (n->right != NULL))
			return "right";
		else
			return "childless";
	}

	//----------------------------------------------------------------------------------------------------------------------------------------
	void setH2(Node* n)
	{
		string t = haschildren(root);
		if (t == "both")
		{
			if (root->left->height > root->right->height)// set the roots height
				root->height = root->left->height + 1;
			else
				root->height = root->right->height + 1;
		}
		else if (t == "right")
			root->height = root->right->height + 1;
		else if (t == "left")
			root->height = root->left->height + 1;
	}
	//----------------------------------------------------------------------------------------------------------------------------------------
	void setH(Node* n)
	{
		if (n != NULL)
		{
			updateheight(n);
			while (n != root)
			{
				n = n->parent;
				updateheight(n);
			}
		}
	};
	//----------------------------------------------------------------------------------------------------------------------------------------
	/*int setbalancedheight(Node* n)
	{
		int nsheight = 0;
		int nsheight2 = 0;
		string t = haschildren(n);
		if (t == "both")
		{
			nsheight = setbalancedheight(n->left);
			nsheight2 = setbalancedheight(n->right);
			if (nsheight > nsheight2)
				n->height = ++nsheight;
			else
				n->height = ++nsheight2;
			return n->height;
		}
		else if (t == "left")
		{
			nsheight = setbalancedheight(n->left);
			n->height = ++nsheight;
			return nsheight;
		}
		else if (t == "right")
		{
			nsheight = setbalancedheight(n->right);
			n->height = ++nsheight;
			return nsheight;
		}
		else if (t == "childless")
		{
			n->height = 1;
			return 1;
		}
	}*/
	//----------------------------------------------------------------------------------------------------------------------------------------
	Node* insert(Node* n, ItemType&item)
	{
		if (item < n->item)
		{
			Node* newnode = new Node;
			n->left = newnode;  // set new node as a child
			newnode->item = item;
			newnode->parent = n;     //set new node's parent
			size++;
			return newnode;
		}

		else if (item > n->item)
		{
			Node* newnode = new Node;
			n->right = newnode;  // set new node as a child
			newnode->item = item;
			newnode->parent = n;     //set new node's parent
			size++;
			return newnode;
		}

		else
		{
			return NULL;
		}
		
	};
	//----------------------------------------------------------------------------------------------------------------------------------------
	void add(ItemType& item)
	{
		Node* newitem = add(root, root, item);

		if (newitem != NULL)
		{
			setH(newitem);
			while (newitem != root)
			{
				newitem = newitem->parent;
				Balance(newitem);
			}
		}
	}
	//----------------------------------------------------------------------------------------------------------------------------------------
	Node* add(Node* n, Node* prevnode, ItemType& item)
	{
		Node* newitem=NULL;
		if (root == NULL)
		{
			newitem = new Node;
			root = newitem;
			newitem->item = item;
			newitem->height = 0;
			size++;
			return newitem;
		}

		else if (n == NULL)
		{
			newitem = insert(prevnode, item);
			return newitem;
		}

		else
		{


			if (item < n->item)
			{
				prevnode = n;
				newitem = add(n->left, prevnode, item);
				return newitem;
			}

			else if (item > n->item)
			{
				prevnode = n;
				newitem = add(n->right, prevnode, item);
				return newitem;
			}
			return newitem;
			
		}
		return newitem;
	};
	//----------------------------------------------------------------------------------------------------------------------------------------
	Node* findleftmost(Node* n)
	{
		while (n->left != NULL)
		{
			n = n->left;
		}
		return n;
	};

	//----------------------------------------------------------------------------------------------------------------------------------------

	void finalbalance(Node* p2)
	{
		if (p2 != NULL)
		{
			Balance(p2);
			while (p2 != root)
			{
				p2 = p2->parent;
				Balance(p2);
			}
		}
		else if (root != NULL)
			Balance(root);
	}

	//----------------------------------------------------------------------------------------------------------------------------------------
	string remove(const ItemType& item)
	{
		string item2 = item;
		Node* n = root;
		if (n != NULL)
		{
			while (n->item != item)
			{
				if ((item < n->item) && (n->left != NULL))
					n = n->left;
				else if ((item > n->item) && (n->right != NULL))
					n = n->right;
				else
					return "-1";
			}
			if (n->item == item)
				rembal(n, item);
		}
		return item2;

	};
	//----------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------

	void rembal(Node* n, string item)
	{
		Node* a = NULL;// only initialized for the sake of initializing. Will not be n for long.
		Node* p2 = NULL;
		string t = haschildren(n);
		if (n->right != NULL)
			a = findleftmost(n->right);
		else
			a = n->left;
		if ((size != 1) && (a != NULL))
		{
			if (a->parent != n)
				p2 = a->parent;
			else
				p2 = a;
		}
		else
			p2 = n->parent;
		if (n == root)
		{
			root = a;
		}


		simp1(n, a, t, item);

		simp2(n, a, item, t);


		if (p2 == n)
			p2 = NULL;
		
		updatetheheights(a, p2);
		delete n;
		size--;
		finalbalance(p2);
	};


	void simp2(Node* n, Node* a, string item, string t)
	{
		if (a == NULL)
		{
			if (n == root)
				root = NULL;
		}
		else
		{
			a->parent = n->parent;
			if ((n->parent != NULL) && (t != "childless"))
			{
				if (n->parent->item > item)
					n->parent->left = a;
				if (n->parent->item < item)
					n->parent->right = a;
			}

		}
	}
	//----------------------------------------------------------------------------------------------------------------------------------------
	void updatetheheights(Node* a, Node* p2)
	{
		if (a != NULL)
		{
			updateheight(a);
		}
		if (p2 != NULL)
		{
			updateheight(p2);
			if (a!=NULL)
			{
				updateheight(a);
			
				if (a->height < p2 -> height)
				{
					while (a -> height)
					{
						a = a->parent;
						updateheight(a);
					}
				}
			
				else
				{
					while (p2 != root)
					{
						p2 = p2->parent;
						updateheight(p2);
					}
				}
			}
		
			else
			{
				while (p2 != root)
				{
					p2 = p2->parent;
					updateheight(p2);
				}
			}
		}
		}
	
	//----------------------------------------------------------------------------------------------------------------------------------------
	bool find(Node* n, const ItemType& item)
	{
		if (n != NULL)
		{
			string t = haschildren(n);
			if (n->item == item)
			{
				return true;
			}
			else if ((n->left != NULL) && (item < n->item))
			{
				return find(n->left, item);
			}
			else if ((n->right != NULL) && (item > n->item))
				return find(n->right, item);
			else
			{
				return false;
			}
		}
		return false;
	};

	void simp1(Node* n, Node* a, string t, string item)
	{
		if ((t == "both") || (t == "right"))
		{
			a->left = n->left;
			if (n->left != NULL)
			{
				n->left->parent = a;
			}
			if (n->right != a)
			{
				if (a->right == NULL)                                                              //new
				{
					a->parent->left = NULL;
				}
				else
				{
					a->parent->left = a->right;
					a->right->parent = a->parent;
				}
				a->right = n->right;
				n->right->parent = a;
			}
		}

		else 
		{
			childless(n, a, item, t);
		}
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------------
	void childless(Node* n, Node* a, string item, string t)
	{
		if ((t == "childless") && (a != root))
		{
			if (n->parent->item > item)
				n->parent->left = NULL;
			if (n->parent->item < item)
				n->parent->right = NULL;

		}
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------------
	int getheight(Node* n)
	{
		if (n == NULL)
			return 0;
		else
			return (n->height);

	};
	//----------------------------------------------------------------------------------------------------------------------------------------
	void Balance(Node* n)
	{
		
		int n1 = getheight(n->left);
		int n2 = getheight(n->right);
		if ((n1 - n2) > 1)
			balancetoright(n);
		else if ((n2 - n1) > 1)
		{
			balancetoleft(n);
		}
	};
	//----------------------------------------------------------------------------------------------------------------------------------------
	void balancetoright(Node* n)//is it a single or double rotate?
	{

		if ((getheight(n->left->right)) > (getheight(n->left->left)))//crooked greater than straight? 
		{
			n->left = rotateleft(n->left);  //Dbl rotate R; 
		}

		rotateright(n);
	};
	//----------------------------------------------------------------------------------------------------------------------------------------
	void balancetoleft(Node* n)//is it a single or double rotate?
	{

		if ((getheight(n->right->left)) > (getheight(n->right->right)))//crooked greater than straight?
		{
			n->right = rotateright(n->right);  //Dbl rotate R; 
		}

		rotateleft(n);
	};
	//----------------------------------------------------------------------------------------------------------------------------------------
	Node* rotateright(Node*n)
	{
		Node* prev = n->parent;
		Node* t = n->left;

		if (n == root)
		{
			root = t;
			root->parent = NULL;
		}

		n->left = t->right;
		if (t->right != NULL)
			t->right->parent = n;//setting infinite loop used to be n->left instead of n
		t->right = n;
		n->parent = t;

		if (prev != NULL)//added
		if (t->item > prev->item)
		{
			prev->right = t;
			t->parent = prev;
		}
		else if (t->item < prev->item)
		{
			prev->left = t;
			t->parent = prev;
		}
		updateheight(n);
		while (n != root)
		{
			n = n->parent;
			updateheight(n);
		}
		
		
		return t;
	};
	//----------------------------------------------------------------------------------------------------------------------------------------
	Node* rotateleft(Node*n)
	{
		Node* prev = n->parent;
		Node* t = n->right;

		if (n == root)
		{
			root = t;
			root->parent = NULL;
		}
		n->right = t->left;
		if (t->left != NULL)
			t->left->parent = n;//used to be n->right.... caused infinite loop.
		t->left = n;
		n->parent = t;

		if (prev != NULL)
		if (t->item > prev->item)
		{
			prev->right = t;
			t->parent = prev;
		}
		else if (t->item < prev->item)
		{
			prev->left = t;
			t->parent = prev;
		}
		updateheight(n);
		while (n != root)
		{
			n = n->parent;
			updateheight(n);
		}
		
		
		return t;
	};
	//----------------------------------------------------------------------------------------------------------------------------------------
	void Print(ostream& out)
	{
		if (root != NULL)
		{
			queue <Node*> levels;
			levels.push(root);
			int level = 0;
			int levelsize = 1;
			int nextlevelsize = 1;
			int added = 0;

			while (nextlevelsize > 0)
			{
				Node* temp = levels.front();
				out << "level " << level << ": ";
				levelsize = nextlevelsize;
				nextlevelsize = 0;
				added = 0;
				while (levelsize > 0)//should keep levels separate from each other
				{
					temp = levels.front();
					out << temp->item << "(" << temp->height << ") ";
					added++;
					if ((added % 8 == 0) && (levelsize > 1))
					{
						out << endl;
						out << "level " << level << ": ";
					}
					if (temp->left != NULL)
					{
						levels.push(temp->left);
						nextlevelsize++;

					}
					if (temp->right != NULL)
					{
						levels.push(temp->right);
						nextlevelsize++;
					}
					levels.pop();
					levelsize--;
				}
				out << endl;
				level++;
			}
		}
	};
	~Tree()
	{
	}
};

