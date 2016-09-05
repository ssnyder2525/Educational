package shared.communication.proxy;

public class DiscardedCards
{

	/**
	 * The index of the player discarding cards
	 */
	public int playerIndex;
	
	public int sheep;
	
	public int wood;
	
	public int ore;
	
	public int wheat;
	
	public int brick;

	
	/**
	 * The cards they are discarded
	 */
	public DiscardedCards(int playerIndex, int sheep, int wood, int ore, int wheat, int brick) {
		this.playerIndex = playerIndex;
		this.sheep = sheep;
		this.wood = wood;
		this.ore = ore;
		this.wheat = wheat;
		this.brick = brick;
	}

	public DiscardedCards(){}
	
}
