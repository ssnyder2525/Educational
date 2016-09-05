package client.recordIndexerGUI.qualityChecker;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Trie
{
	Node[] nodes;
	int wordCount;
	int nodeCount;
	
	public Trie() {
		nodes = new Node[26];
		wordCount = 0;
		nodeCount = 1;
	}

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

	public Node find(String word) {
		if(word != null)
		{
			try
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
			catch(Exception e)
			{
				return null;
			}
		}
		return null;
	}

	public int getWordCount() {
		return wordCount;
	}

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
	
	public Set<String> makeSuggestion(String word)
	{
		Set<String> keepers = new TreeSet<String>();
		
		ArrayList<String> deletion = new ArrayList<String>();
		ArrayList<String> transposition = new ArrayList<String>();
		ArrayList<String> insertion = new ArrayList<String>();
		ArrayList<String> alteration = new ArrayList<String>();
		
		deletion = getDeletion(word);
		transposition = getTransposition(word);
		insertion = getInsertion(word);
		alteration = getAlteration(word);
		keepers.addAll(choose(deletion));
		keepers.addAll(choose(transposition));
		keepers.addAll(choose(insertion));
		keepers.addAll(choose(alteration));
		
		ArrayList<String> deletion2 = getDouble(deletion);
		ArrayList<String> transposition2 = getDouble(transposition);
		ArrayList<String> insertion2 = getDouble(insertion);
		ArrayList<String> alteration2 = getDouble(alteration);
		
		keepers.addAll(choose(deletion2));
		keepers.addAll(choose(transposition2));
		keepers.addAll(choose(insertion2));
		keepers.addAll(choose(alteration2));
		
		return keepers;
	}
	
	public ArrayList<String> getDeletion(String word)
	{
		ArrayList<String> answer = new ArrayList<String>();
		if(word.length() > 1)
		{
			for(int i = 0; i < word.length(); i++)
			{
				StringBuilder newWord = new StringBuilder();
				newWord.append(word);
				newWord.deleteCharAt(i);
				if(newWord.length()>0)
				answer.add(newWord.toString());
			}
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
	
	public ArrayList<String> choose(ArrayList<String> list)
	{
		ArrayList<String> keepers = new ArrayList<String>();
		if(list.isEmpty())
		{
			return keepers;
		}
		for(int i = 0; i < list.size(); i++)
		{
			Node newNode = find(list.get(i));
			if (newNode != null)
			{
				keepers.add(list.get(i));
			}
		}
		return keepers;
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
