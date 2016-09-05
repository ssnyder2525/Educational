package shared.models;

import java.util.Random;

public class Dice {

/**
 * This class generates two random numbers between one and 6 in representation of a pair of dice.
 */
	public Dice() {}
	
	/**
	 * rolls two dice
	 * @return the sum of two random numbers between 1 and 6
	 */
	public static int rollDice() {
		Random rand = new Random(System.currentTimeMillis());
		int first = rand.nextInt(6) + 1;
		int second = rand.nextInt(6) + 1;
		return first + second;
	}

}
