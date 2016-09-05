package client.recordIndexerGUI.qualityChecker;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector
{
	Trie trie = new Trie();

	public void useDictionary(URL url) throws IOException {
		Scanner sc = new Scanner(url.openStream());
		String words = sc.next();
		String[] wordList = words.split(",");
		for(String word : wordList)
		{
			if(word.length() >= 1)
			{
				trie.add(word);
				System.out.println(word);
			}
		}
		sc.close();
	}

	public Set<String> suggestSimilarWords(String inputWord)
	{
		Set<String> suggestions = new TreeSet<String>();
		
		suggestions = trie.makeSuggestion(inputWord);
	
		if (suggestions == null)
		{
			return null;
		}
		return suggestions;
	}
	
	public boolean isMispelled(String inputWord)
	{
		if(inputWord.length() == 0)
		{
			return false;
		}
		Node answer = new Node();
		answer = trie.find(inputWord);
		if(answer != null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

}
