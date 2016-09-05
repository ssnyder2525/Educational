
package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Game implements IEvilHangmanGame {
	
	public Set<String> wordBank = new TreeSet<String>();
	public int numOfCharacters = 0;
	public Character[] answer;
	public Boolean notComplete = true;
	@Override
	public void startGame(File dictionary, int wordLength) {
		numOfCharacters = wordLength;
		answer = new Character[numOfCharacters];
		if(wordLength < 2)
		{
			return;
		}
		try 
		{
			Scanner sc = new Scanner(new FileReader(dictionary));
			while (sc.hasNext())
			{
				String currentWord = sc.next();
				if(currentWord.length() == wordLength)
				{
					wordBank.add(currentWord);
				}
			}
			sc.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}

	}
	/**
	 * Make a guess in the current game.
	 * 
	 * @param guess The character being guessed
	 * @return The set of strings that satisfy all the guesses made so far
	 * in the game, including the guess made in this call. The game could claim
	 * that any of these words had been the secret word for the whole game. 
	 * 
	 * @throws GuessAlreadyMadeException If the character <code>guess</code> 
	 * has already been guessed in this game.
	 */
	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
		guess = Character.toLowerCase(guess);
		Set<String> result = new TreeSet<String>();
		ArrayList<String> chosenPatterns = new ArrayList<String>();
		Map<String, Group> container = getGroups(guess, chosenPatterns);		
		if(!chosenPatterns.isEmpty())
		{
			
			if(chosenPatterns.size() == 1)
			{
				result = container.get(chosenPatterns.get(0)).contents;
				wordBank = new TreeSet<String>(result);
				fillInAnswers(chosenPatterns.get(0), guess);
			}
			else
			{
				chosenPatterns = findLowestOccurances(chosenPatterns, container);				
				if(chosenPatterns.size() == 1)
				{
					result = container.get(chosenPatterns.get(0)).contents;
					wordBank = new TreeSet<String>(result);
					fillInAnswers(chosenPatterns.get(0), guess);
				}
				else
				{
					chosenPatterns = getRightMost(chosenPatterns);
					result = container.get(chosenPatterns.get(0)).contents;
					wordBank = new TreeSet<String>(result);
					fillInAnswers(chosenPatterns.get(0), guess);
				}
			}
		}
		return wordBank;
	}
	
	public void printWord()
	{
		notComplete = false;
		StringBuilder word = new StringBuilder();
		for(int i = 0; i < answer.length; i++)
		{
			if(answer[i] == null)
			{
				word.append("-");
				notComplete = true;
			}
			else
			{
				word.append(answer[i]);
			}
		}
		System.out.println("Word:" + word);
	}

	public Map<String, Group> getGroups(char guess, ArrayList<String> chosenPatterns)
	{
		Map<String, Group> container = new TreeMap<String, Group>();
		int largestGroupSize = 0;		
		Iterator<String> iter = wordBank.iterator();
		//for each word in the Bank
		while(iter.hasNext())
		{
			int numberOfMatches = 0;
			StringBuilder buildPattern = new StringBuilder();
			String currentWord = iter.next();
			//for each letter
			for(int i = 0; i < currentWord.length(); i++)
			{
				if(currentWord.charAt(i) == guess)
				{
					numberOfMatches++;
					buildPattern.append('1');
				}
				else
				{
					buildPattern.append('0');
				}
			}
			String pattern = buildPattern.toString();
			if(container.containsKey(pattern))
			{
				container.get(pattern).contents.add(currentWord);
				container.get(pattern).numberOfWords++;
				if(container.get(pattern).numberOfWords > largestGroupSize)
				{
					largestGroupSize = container.get(pattern).numberOfWords;
					chosenPatterns.clear();
					chosenPatterns.add(pattern);
				}
				if(container.get(pattern).numberOfWords == largestGroupSize && !chosenPatterns.contains(pattern))
				{
					chosenPatterns.add(pattern);
				}
			}
			else
			{
				Group newGroup = new Group();
				newGroup.contents.add(currentWord);
				container.put(pattern, newGroup);
				container.get(pattern).numberOfWords++;
				container.get(pattern).occurances = numberOfMatches;
				if(container.get(pattern).numberOfWords > largestGroupSize)
				{
					largestGroupSize = container.get(pattern).numberOfWords;
					chosenPatterns.clear();
					chosenPatterns.add(pattern);
				}
				if(container.get(pattern).numberOfWords == largestGroupSize && !chosenPatterns.contains(pattern))
				{
					chosenPatterns.add(pattern);
				}
			}
		}
		return container;
	}
	
	public void fillInAnswers(String pattern, char guess)
	{
		int c = 0;
		while (c < answer.length)
		{
			if (pattern.charAt(c) == '1')
			{
				answer[c] = guess;
			}
			c++;
		}
	}
	
	public ArrayList<String> findLowestOccurances(ArrayList<String> chosenPatterns, Map<String, Group> container)
	{
		ArrayList<String> newList = new ArrayList<String>();
		int leastOccurances = container.get(chosenPatterns.get(0)).occurances;
		newList.add(chosenPatterns.get(0));
		for(int i = 1; i < chosenPatterns.size(); i++)
		{
			if(container.get(chosenPatterns.get(i)).occurances < leastOccurances)
			{
				newList.clear();
				newList.add(chosenPatterns.get(i));
				leastOccurances = container.get(chosenPatterns.get(i)).occurances;
			}
			if(container.get(chosenPatterns.get(i)).occurances == leastOccurances)
			{
				newList.add(chosenPatterns.get(i));
			}
		}
		return newList;
	}
	
	public ArrayList<String> getRightMost(ArrayList<String> chosenPatterns)
	{
		ArrayList<String> newList = new ArrayList<String>();
		Collections.sort(chosenPatterns);
		newList.add(chosenPatterns.get(0));
		return newList;
	}
}