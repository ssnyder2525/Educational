package shared.models.cardClasses;

import java.util.HashMap;

import shared.definitions.ResourceType;

public class Bank 
{	
	/**
	 * The container for the resource cards in the bank
	 */
	private ResourceCards resourceCards;

	/**
	 * The Bank keeps track of how many resource cards are without owners.
	 */
	public Bank() {
		resourceCards = new ResourceCards(19,19,19,19,19);
	}
	
	public Bank(HashMap<ResourceType, Integer> resources) {
		resourceCards = new ResourceCards(0,0,0,0,0);
		for (ResourceType type : resources.keySet()) {
			this.resourceCards.addCard(type, resources.get(type));
		}
	}
	
	/**
	 * Subtracts cards from the bank to give to a player
	 * @throws InsufficientCardNumberException 
	 */
	public void takeResourceCards(ResourceType type, int num) throws InsufficientCardNumberException {
		resourceCards.removeCard(type, num);
	}
	
	/**
	 * Adds cards to the bank from a player's hand
	 */
	public void AddResourceCards(ResourceType type, int num) {
		resourceCards.addCard(type, num);
	}
	
	/**
	 * Checks if there is sufficient resources of a specified in this type to remove the specified number.
	 * @return if a card can be removed (true or false)
	 */
	public boolean canRemove(ResourceType type, int num) {
		return resourceCards.canRemove(type, num);
	}
	
	/**
	 * Gets the resource cards available in the bank
	 * @return a ResourceCards object containing all available resource cards
	 */
	public ResourceCards getResources() {
		return resourceCards;
	}
	
	public String serialize() {
    	String json = "bank: {brick: ";
    	json += resourceCards.brickCards + ", wood: ";
    	json += resourceCards.woodCards + ", sheep: ";
    	json += resourceCards.sheepCards + ", wheat: ";
    	json += resourceCards.wheatCards + ", ore: ";
    	json += resourceCards.oreCards;
    	json += "}";
    	return json;
    }

}
