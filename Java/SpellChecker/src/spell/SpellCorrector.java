package spell;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector 
{
	Trie trie = new Trie();

	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		Scanner sc = new Scanner(new FileReader(dictionaryFileName));
		while(sc.hasNext())
		{
			String word = sc.next();
			if(word.length() > 1)
			trie.add(word);
		}
		sc.close();
	}

	@Override
	public String suggestSimilarWord(String inputWord)	throws NoSimilarWordFoundException 
	{
		if(inputWord.length() == 0)
		{
			throw new NoSimilarWordFoundException();
		}
		Node answer = new Node();
		answer = trie.find(inputWord);
		String suggestion = null;
		if(answer != null)
		{
			return inputWord;
		}
		else
		{
			suggestion = trie.makeSuggestion(inputWord);
		}
		if (suggestion == null)
		{
			throw new NoSimilarWordFoundException();
		}
		return suggestion;
	}

}
