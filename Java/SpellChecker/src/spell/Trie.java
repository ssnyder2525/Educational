package spell;

import java.util.ArrayList;

public class Trie implements ITrie 
{
	Node[] nodes;
	int wordCount;
	int nodeCount;
	
	public Trie() {
		nodes = new Node[26];
		wordCount = 0;
		nodeCount = 1;
	}

	@Override
	public void add(String word) 
	{
		word = word.toLowerCase();
		wordCount++;
		int firstLetter = word.charAt(0) - 97;
		if(nodes[firstLetter] == null)
		{
			nodeCount++;
			Node newNode = new Node();
			nodes[firstLetter] = newNode;
			int newNodes = newNode.add(word, 1);
			nodeCount = nodeCount + newNodes;
		}
		else
		{
			int newNodes = nodes[firstLetter].add(word, 1);
			nodeCount = nodeCount + newNodes;
		}

	}

	@Override
	public Node find(String word) {
		if(word != null)
		{
			word = word.toLowerCase();
			Node answer = null;
			int firstLetter = word.charAt(0) - 97;
			if(nodes[firstLetter] != null)
			{
				answer = nodes[firstLetter].find(word, 1);
			}
			return answer;
		}
		return null;
	}

	@Override
	public int getWordCount() {
		return wordCount;
	}

	@Override
	public int getNodeCount() {
		return nodeCount;
	}
	
	@Override
	public int hashCode()
	{
		return wordCount + (nodeCount *37);
	}
	
	@Override
	public String toString()
	{
		StringBuilder allWords = new StringBuilder();
		StringBuilder currentWord = new StringBuilder();
		for (int i = 0; i < nodes.length; i++)
		{
			if (nodes[i] != null)
			{
				currentWord.append((char) (i+97));
				allWords = nodes[i].toString(allWords, currentWord);
				currentWord.deleteCharAt(0);
			}
		}
		return allWords.toString();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null)
		{
			return false;
		}
		if(o == this)
		{
			return true;
		}
		if(o.getClass() == this.getClass())
		{
			Trie ot = (Trie) o;
			for (int i = 0; i < nodes.length; i++)
			{
				boolean t = arrayEquals(nodes[i], ot.nodes[i]);
				if (t == false)
					return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean arrayEquals(Node original, Node other)
	{
		boolean answer = false;
		if (original == null && other != null)
		{
			return false;
		}
		if(original != null && other == null)
		{
			return false;
		}
		if(original == null && other == null)
		{
			return true;
		}
		if(original.wordCount != other.wordCount)
		{
			answer = false;
		}
		else
		{
			for (int i = 0; i < original.nodes.length; i++)
			{
				answer = arrayEquals(original.nodes[i], other.nodes[i]);
				if (answer == false)
				{
					return false;
				}
			}
		}
		return answer;
	}
	
	public String makeSuggestion(String word)
	{
		String answer = null;
		ArrayList<String> deletion = new ArrayList<String>();
		ArrayList<String> transposition = new ArrayList<String>();
		ArrayList<String> insertion = new ArrayList<String>();
		ArrayList<String> alteration = new ArrayList<String>();
		ArrayList<String> choice = new ArrayList<String>();
		deletion = getDeletion(word);
		transposition = getTransposition(word);
		insertion = getInsertion(word);
		alteration = getAlteration(word);
		choice.add(choose(deletion));
		choice.add(choose(transposition));
		choice.add(choose(insertion));
		choice.add(choose(alteration));
		answer = choose(choice);
		if(answer == null)
		{
			ArrayList<String> deletion2 = getDouble(deletion);
			ArrayList<String> transposition2 = getDouble(transposition);
			ArrayList<String> insertion2 = getDouble(insertion);
			ArrayList<String> alteration2 = getDouble(alteration);
			choice.clear();
			choice.add(choose(deletion2));
			choice.add(choose(transposition2));
			choice.add(choose(insertion2));
			choice.add(choose(alteration2));
			answer = choose(choice);
		}
		return answer;
	}
	
	public ArrayList<String> getDeletion(String word)
	{
		ArrayList<String> answer = new ArrayList<String>();
		for(int i = 0; i < word.length(); i++)
		{
			StringBuilder newWord = new StringBuilder();
			newWord.append(word);
			newWord.deleteCharAt(i);
			if(newWord.length()>0)
			answer.add(newWord.toString());
		}
		return answer;
	}
	
	public ArrayList<String> getTransposition(String word)
	{
		ArrayList<String> answer = new ArrayList<String>();
		for(int i = 1; i < word.length(); i++)
		{
			StringBuilder newWord = new StringBuilder();
			newWord.append(word);
			char second = word.charAt(i);
			char first = word.charAt(i-1);
			newWord.setCharAt(i, first);
			newWord.setCharAt(i-1, second);
			answer.add(newWord.toString());
		}
		return answer;
	}
	
	public ArrayList<String> getInsertion(String word)
	{
		ArrayList<String> answer = new ArrayList<String>();
		for(int i = 0; i < word.length()+1; i++)
		{
			StringBuilder newWord = new StringBuilder();
			newWord.append(word);
			for(int j = 0; j < 26; j++)
			{
				newWord.insert(i, (char)(j+97));
				answer.add(newWord.toString());
				newWord.deleteCharAt(i);
			}
		}
		return answer;
	}
	
	public ArrayList<String> getAlteration(String word)
	{
		ArrayList<String> answer = new ArrayList<String>();
		for(int i = 0; i < word.length(); i++)
		{
			StringBuilder newWord = new StringBuilder();
			newWord.append(word);
			for(int j = 0; j < 26; j++)
			{
				newWord.setCharAt(i,(char)(j+97));
				answer.add(newWord.toString());
			}
		}
		return answer;
	}
	
	public String choose(ArrayList<String> list)
	{
		if(list.isEmpty())
		{
			return null;
		}
		int maxCount = -1;
		String answer = null;
		for(int i = 0; i < list.size(); i++)
		{
			Node newNode = find(list.get(i));
			if (newNode != null)
			{
				if(maxCount == -1)
				{
					answer = list.get(i);
					maxCount = newNode.wordCount;
				}
				if(newNode.wordCount > maxCount)
				{
					answer = list.get(i);
					maxCount = newNode.wordCount;
				}
			}
		}
		return answer;
	}
	
	public ArrayList<String> getDouble(ArrayList<String> list)
	{
		ArrayList<String> newList = new ArrayList<String>();
		for(int i = 0; i < list.size(); i++)
		{
			newList.addAll(getDeletion(list.get(i)));
			newList.addAll(getTransposition(list.get(i)));
			newList.addAll(getInsertion(list.get(i)));
			newList.addAll(getAlteration(list.get(i)));
		}
		return newList;
	}

}
