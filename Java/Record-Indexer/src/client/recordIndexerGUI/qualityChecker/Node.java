package client.recordIndexerGUI.qualityChecker;

public class Node{
	Node[] nodes;
	int wordCount;
	
	public Node() {
		nodes = new Node[26];
		wordCount = 0;
	}

	public int getValue() 
	{
		return wordCount;
	}
	
	public int add(String word, int placeholder)
	{
		int newNodes = 0;
		if(placeholder == word.length())
		{
			wordCount++;
			return newNodes;
		}
	
		int nextLetter = word.charAt(placeholder) - 97;
		placeholder++;
		if(nodes[nextLetter] == null)
		{
			newNodes++;
			Node newNode = new Node();
			nodes[nextLetter] = newNode;
			newNodes = newNodes + newNode.add(word, placeholder);
			return newNodes;
		}
		else
		{
			newNodes = newNodes+ nodes[nextLetter].add(word, placeholder);
			return newNodes;
		}
	}
	
	public Node find(String word, int placeholder)
	{
		Node answer = null;
		if(placeholder == word.length())
		{
			if (wordCount >0)
			{
				return this;
			}
			else
				return null;
		}
		
		
		int nextLetter = word.charAt(placeholder) - 97;
		if(nodes[nextLetter] != null)
		{
			placeholder++;
			answer = nodes[nextLetter].find(word, placeholder);
		}
		return answer;
	}
	
	public StringBuilder toString(StringBuilder allWords, StringBuilder currentWord)
	{
		if(wordCount > 0)
		{
			allWords.append(currentWord);
			allWords.append("\n");
		}
		for (int i = 0; i < nodes.length; i++)
		{
			if(nodes[i] != null)
			{
				currentWord.append((char) (i+97));
				allWords = nodes[i].toString(allWords, currentWord);
				currentWord.deleteCharAt(currentWord.length()-1);
			}
		}
		return allWords;
	}

}
