package shared.communication.proxy;
import shared.definitions.ResourceType;

public class YearOfPlenty
{

	/**
	 * The index of the player playing the card
	 */
	public int playerIndex;
	
	/**
	 * The first resource you want
	 */
	public ResourceType resourceOne;
	
	/**
	 * The second resource you want
	 */
	public ResourceType resourceTwo;
	
	public YearOfPlenty() 
	{}

	public YearOfPlenty(int playerIndex, ResourceType resourceOne, ResourceType resourceTwo) {
		this.playerIndex = playerIndex;
		this.resourceOne = resourceOne;
		this.resourceTwo = resourceTwo;
	}
	
	public YearOfPlenty(int playerIndex, String resourceOne, String resourceTwo) {
		this.playerIndex = playerIndex;
		switch(resourceOne) {
		case "Brick":
			this.resourceOne = ResourceType.BRICK;
			break;
		case "Ore":
			this.resourceOne = ResourceType.ORE;
			break;
		case "Sheep":
			this.resourceOne = ResourceType.SHEEP;
			break;
		case "Wheat":
			this.resourceOne = ResourceType.WHEAT;
			break;
		case "Wood":
			this.resourceOne = ResourceType.WOOD;
			break;
		}
		switch(resourceTwo) {
		case "Brick":
			this.resourceTwo = ResourceType.BRICK;
			break;
		case "Ore":
			this.resourceTwo = ResourceType.ORE;
			break;
		case "Sheep":
			this.resourceTwo = ResourceType.SHEEP;
			break;
		case "Wheat":
			this.resourceTwo = ResourceType.WHEAT;
			break;
		case "Wood":
			this.resourceTwo = ResourceType.WOOD;
			break;
		}
	}
	
	

	
}
