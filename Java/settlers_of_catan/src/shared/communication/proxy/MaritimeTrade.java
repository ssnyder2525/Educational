package shared.communication.proxy;
import shared.definitions.ResourceType;;

public class MaritimeTrade
{

	/**
	 * The index of the player doing the trade
	 */
	public int playerIndex;
	
	/**
	 * The ratio they're trading at
	 */
	public int ratio;
	
	/**
	 * The ratio they're trading at
	 */
	public ResourceType givingUp;
	
	/**
	 * The ratio they're trading at
	 */
	public ResourceType getting;
	
	public MaritimeTrade(ResourceType givingUp, ResourceType getting, int ratio, int playerIndex)
	{
		this.playerIndex = playerIndex;
		this.ratio = ratio;
		this.getting = getting;
		this.givingUp = givingUp;
	}

}
