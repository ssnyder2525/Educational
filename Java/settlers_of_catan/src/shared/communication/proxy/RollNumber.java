package shared.communication.proxy;

import java.util.Random;

public class RollNumber
{

	/**
	 * The index of the player rolling the dice
	 */
	public int playerIndex;
	
	/**
	 * The number they rolled, between 2-12
	 */
	public int roll;
	
	public RollNumber(int playerIndex) {
		Random rand = new Random();
		int roll1 = rand.nextInt(6) + 1;
		int roll2 = rand.nextInt(6) + 1;
		this.roll = roll1 + roll2;
		this.playerIndex = playerIndex;
	}
	
	public RollNumber(int playerIndex, int number) {
		this.playerIndex = playerIndex;
		this.roll = number;
	}

	public int getRoll() {
		return roll;
	}
	
}