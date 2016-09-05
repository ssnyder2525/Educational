package hangman;

import hangman.IEvilHangmanGame.GuessAlreadyMadeException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner inputDevice = new Scanner(System.in);
		Game game = new Game();
		File file = new File(args[0]);
		game.startGame(file, Integer.parseInt(args[1]));
		int guesses = Integer.parseInt(args[2]);
		ArrayList<String> guessLog = new ArrayList<String>();
		ArrayList<String> guessesList = new ArrayList<String>();
		while(guesses > 0)
		{
			System.out.println("You have " + guesses + " guesses left");
			Collections.sort(guessesList);
			System.out.println("Used letters:");
			System.out.println(guessesList);
			game.printWord();
			System.out.println("Enter guess:");
			String guess = inputDevice.nextLine();
			
			if (guess.length() == 1)
			{
				Character c = guess.charAt(0);
				c = Character.toLowerCase(c);
				if(Character.isLetter(c))
				{
					if(!guessLog.contains(c.toString()))
					{
						guesses--;
						try {
							game.makeGuess(c);
							guessesList.add(c.toString());
							guessLog.add(c.toString());
						} catch (GuessAlreadyMadeException e) {
							e.printStackTrace();
						}
						if(game.notComplete == false)
						{
							System.out.println("You win!");
							break;
						}
					}
					else
					{
						System.out.println("You already guessed that\n");
					}
				}
				else
				{
					System.out.println("Invalid Input\n");
				}
			}
			else
			{
				System.out.println("Invalid Input\n");
			}
		}
		Random r = new Random();
		r.setSeed(game.wordBank.size());
		int e = r.nextInt(game.wordBank.size());
		String[] u = game.wordBank.toArray(new String[game.wordBank.size()]);
		System.out.println("The word was " + u[e]);
		inputDevice.close();
	}
}

