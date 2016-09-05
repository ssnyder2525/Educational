#pragma once
using namespace std;

template <typename type>

class List
{
public:
	struct node
	{
		type item;
		node* next;
		node* prev;
	};



//private:
	int count;
	node* head;
	node* tail;

//public:
	List() 

	{
		count = 0;
		head = NULL;
		tail = NULL;
	}

	int size()
	{
		return count;
	}

	type getitem(int index)
	{
		int i = 0;
		int count2 = count - 1;
		node* locator=NULL;
		if ((index < (count2 / 2)) && (index>0))
		{
			locator = tail;
			while (i < index)
			{
				locator = locator->next;
				i++;
			}
			type item = locator->item;
			return item;
		}
		else if ((index >= (count2 / 2)) && (index <= count2) && (index != 0))
		{
			locator = head;
			i = count2;
			while (i > index)
			{
				locator = locator->prev;
				i--;
			}
			type item = locator->item;
			return item;
		}
		else
		{
			locator = getitem2(index, locator);
			type item = locator->item;
			return item;
		}
	}

node* getitem2(int index, node* locator)
	{
		if (index == 0)
		{
			locator = tail;
			return locator;
		}
		else
		{
			locator = NULL;
			return locator;
		}
	}

node* getnode2(int index,node* locator)
	{
	  if (index == 0)
		{
			locator = tail;
			return locator;
		}
		else
		{
			locator = NULL;
			return locator;
		}
	}

node* getnode(int index)
	{
		int i = 0;
		int count2 = count - 1;
		node* locator=NULL;
		if ((index < (count2 / 2)) && (index>0))
		{
			locator = tail;
			while (i < index)
			{
				locator = locator->next;
				i++;
			}
			return locator;
		}
		else if ((index >= (count2 / 2)) && (index<=count2)&&(index!= 0))
		{
			locator = head;
			i = count2;
			while (i > index)
			{
				locator = locator->prev;
				i--;
			}
			return locator;
		}
		else 
		  {
		    locator = getnode2(index, locator);
		    return locator;
		  }
	}

void print(std::ofstream& out)
	{
		if (count > 0)
		{
			int i = 0;
			node* tempnode = tail;
			while (i < count)
			{
				out <<"node "<<i<<": "<< tempnode->item << "\n";
				tempnode = tempnode->next;
				i++;
			}
		}
	};

void clear()
	{
		while (count > 0)
		{
			remove(0);
		}
	};

 void insert2(int index, const type & item,node* nextnode, node* newnode)
 {
 if ((nextnode->prev != NULL) && (nextnode != NULL))
		{
			newnode->prev = nextnode->prev;
			nextnode->prev->next = newnode;
			nextnode->prev = newnode;
			newnode->next = nextnode;

		}
 }

void insert(int index, const type & item)
{
	node* newnode = new node;
	newnode->item = item;
	node* nextnode = getnode(index);
	if ((index >= 0) && (index <= count))
	{
		if ((head == NULL) && (tail == NULL))
		{
			head = newnode;
			tail = newnode;
			newnode->next = NULL;
			newnode->prev = NULL;
		}

		else if (index == 0)
		{
			newnode->prev = NULL;
			nextnode->prev = newnode;
			tail = newnode;
			newnode->next = nextnode;
		}

		else if (index == count)
		{
			newnode->prev = head;
			newnode->next = NULL;
			head->next = newnode;
			head = newnode;
		}

		else
		  {
		    insert2(index, item, nextnode, newnode);
		  }
		count++;
	}
};

int find(const type& item) const
	{
		int i = 0;
		node* tempnode = tail;
		while (i < count)
		{
			if (tempnode->item == item)
			{
			 
				return i;
			}
			else
			{
				if (i!=count-1)
				tempnode = tempnode->next;
			}
			i++;
		}
        
			return -1;
	};

type finishremove(node* doomed, node* before, node* after)
	{
	
	  if ((after != NULL) && (before == NULL))
		{
			tail = after;
			after->prev = NULL;
		}

		else if ((after == NULL) && (before != NULL))
		{
			head = before;
			before->next = NULL;
		}
		else if ((after != NULL) && (before != NULL))
		{
			after->prev = before;
			before->next = after;
		}

		count--;
		type item = doomed->item;
		delete doomed; 

		return item;
	};

type remove(int itemnumber)
	{
		node* doomed = getnode(itemnumber);
		
	if (doomed != NULL)
	   {
		node* after = doomed->next;

		node* before = doomed->prev;
		
		if ((after == NULL) && (before == NULL))
		{
			tail = NULL;
			head = NULL;
		}

	   
		 type item = finishremove(doomed, before, after);
		 return item;
	   }
		else
		  {
		    return NULL;
		  }
	};

~List()
	{
		while (count > 0)
		{
			remove(0);
		}
	}
};
















