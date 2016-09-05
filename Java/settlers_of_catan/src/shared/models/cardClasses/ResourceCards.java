package shared.models.cardClasses;

import shared.definitions.ResourceType;

public class ResourceCards {

	/**The number of wood cards in this group*/
	int woodCards = 0;
	/**The number of brick cards in this group*/
	int brickCards = 0;
	/**The number of sheep cards in this group*/
	int sheepCards = 0;
	/**The number of wheat cards in this group*/
	int wheatCards = 0;
	/**The number of ore cards in this group*/
	int oreCards = 0;
	
	/**
	 * A container for holding development cards.
	 */
	public ResourceCards(int woodCards, int brickCards, int sheepCards, int wheatCards, int oreCards) {
		this.woodCards = woodCards;
		this.brickCards = brickCards;
		this.sheepCards = sheepCards;
		this.wheatCards = wheatCards;
		this.oreCards = oreCards;
	}
	
	public int getCards(ResourceType resource) {
		switch(resource) {
		case BRICK:
			return brickCards;
		case ORE:
			return oreCards;
		case SHEEP:
			return sheepCards;
		case WHEAT:
			return wheatCards;
		case WOOD:
			return woodCards;
		}
		return -1;
	}

	public int getWoodCards() {
		return woodCards;
	}

	public int getBrickCards() {
		return brickCards;
	}

	public int getSheepCards() {
		return sheepCards;
	}

	public int getWheatCards() {
		return wheatCards;
	}

	public int getOreCards() {
		return oreCards;
	}
	
	/**
	 * Adds a card of a specific type to this group
	 * @param type the type of resource card to add
	 */
	public void addCard(ResourceType type, int num) {
		switch(type){
			case BRICK:
				this.brickCards += num;
				break;
			case ORE:
				this.oreCards += num;
				break;
			case SHEEP:
				this.sheepCards += num;
				break;
			case WHEAT:
				this.wheatCards += num;
				break;
			case WOOD:
				this.woodCards += num;
				break;
		}
	}
	
	public void setZeroCards(ResourceType resource) {
		switch(resource){
		case BRICK:
			this.brickCards = 0;
			break;
		case ORE:
			this.oreCards = 0;
			break;
		case SHEEP:
			this.sheepCards = 0;
			break;
		case WHEAT:
			this.wheatCards = 0;
			break;
		case WOOD:
			this.woodCards  = 0;
			break;
		}
	}
	
	/**
	 * Removes a card of a specific type from this group
	 * @param type the type of resource card to remove
	 * @exception InsufficientCardNumberException
	 */
	public void removeCard(ResourceType type, int num) throws InsufficientCardNumberException 
	{
		switch(type){
			case BRICK:
				if(getBrickCards() < num){throw new InsufficientCardNumberException();}
				this.brickCards -= num;
				break;
			case ORE:
				if(getOreCards() < num){throw new InsufficientCardNumberException();}
				this.oreCards -= num;
				break;
			case SHEEP:
				if(getSheepCards() < num){throw new InsufficientCardNumberException();}
				this.sheepCards -= num;
				break;
			case WHEAT:
				if(getWheatCards() < num){throw new InsufficientCardNumberException();}
				this.wheatCards -= num;
				break;
			case WOOD:
				if(getWoodCards() < num){throw new InsufficientCardNumberException();}
				this.woodCards -= num;
				break;
		}
	}
	
	/**Checks if it is possible to remove a number of a type of resource*/
	public boolean canRemove(ResourceType type, int num) {
		switch(type){
		case BRICK:
			if(getBrickCards() < num){return false;}
			break;
		case ORE:
			if(getOreCards() < num){return false;}
			break;
		case SHEEP:
			if(getSheepCards() < num){return false;}
			break;
		case WHEAT:
			if(getWheatCards() < num){return false;}
			break;
		case WOOD:
			if(getWoodCards() < num){return false;}
			break;
		}
		return true;
	}
	
	/**Checks if the container is empty*/
	public boolean isEmpty() {
		if (brickCards != 0 || oreCards != 0 || sheepCards != 0 || wheatCards != 0 || woodCards != 0) {
			return false;
		}
		return true;
	}

	public int getTotal() {
		return getWoodCards() + getBrickCards() + getSheepCards() 
							  + getWheatCards() + getOreCards();
	}

}
