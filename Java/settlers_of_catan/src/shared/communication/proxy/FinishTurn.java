package shared.communication.proxy;

public class FinishTurn
{

	/**
	 * The index of the player finishing their turn
	 */
	public int playerIndex;
	
	
	public FinishTurn() 
	{}
	
	public FinishTurn(int playerIndex) {
		this.playerIndex = playerIndex;
	}


	
}
