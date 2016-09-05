package shared.communication.proxy;

public class AcceptTrade
{

	/**
	 * The index of the player responding to the trade
	 */
	public int playerIndex;
	
	/**
	 * True if they accept the trade, false if they reject it
	 */
	public Boolean response;
	
	public AcceptTrade(int playerIndex, Boolean response) {
		this.playerIndex = playerIndex;
		this.response = response;
	}


	
}
