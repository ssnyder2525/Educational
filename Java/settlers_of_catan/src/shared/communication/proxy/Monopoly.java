package shared.communication.proxy;
import shared.definitions.ResourceType;

public class Monopoly
{

	/**
	 * The index of the player playing that card
	 */
	public int playerIndex;
	
	/**
	 * The resource they want to monopolize
	 */
	public ResourceType resource;
	
	public Monopoly() 
	{}

	public Monopoly(int playerIndex, ResourceType resource) {
		this.playerIndex = playerIndex;
		this.resource = resource;
	}
	
	public Monopoly(int playerIndex, String resource) {
		this.playerIndex = playerIndex;
		switch(resource) {
		case "Brick":
			this.resource = ResourceType.BRICK;
			break;
		case "Ore":
			this.resource = ResourceType.ORE;
			break;
		case "Sheep":
			this.resource = ResourceType.SHEEP;
			break;
		case "Wheat":
			this.resource = ResourceType.WHEAT;
			break;
		case "Wood":
			this.resource = ResourceType.WOOD;
			break;
		}
	}

	
	
}
