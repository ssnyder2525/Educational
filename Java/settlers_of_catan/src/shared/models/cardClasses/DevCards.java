package shared.models.cardClasses;

import shared.definitions.DevCardType;

public class DevCards {
	
	/**The number of soldier cards in this group*/
	int soldierCards = 0;
	/**The number of year of plenty cards in this group*/
	int yearOfPlentyCards = 0;
	/**The number of monopoly cards in this group*/
	int monopolyCards = 0;
	/**The number of road builder cards in this group*/
	int roadBuilderCards = 0;
	/**The number of monument cards in this group*/
	int monumentCards = 0;

	/**
	 * A container for holding development cards.
	 */
	public DevCards(int soldierCards, int yearOfPlentyCards, int monopolyCards, int roadBuilderCards, int monumentCards) {
		this.soldierCards = soldierCards;
		this.yearOfPlentyCards = yearOfPlentyCards;
		this.monopolyCards = monopolyCards;
		this.roadBuilderCards = roadBuilderCards;
		this.monumentCards = monumentCards;
	}

	public int getSoldierCards() {
		return soldierCards;
	}

	public int getYearOfPlentyCards() {
		return yearOfPlentyCards;
	}

	public int getMonopolyCards() {
		return monopolyCards;
	}

	public int getRoadBuilderCards() {
		return roadBuilderCards;
	}

	public int getMonumentCards() {
		return monumentCards;
	}
	
	/**
	 * Adds a card of a specific type to this group
	 * @param type the type of development card to add
	 */
	public void addCards(DevCardType type, int num) {
		switch(type){
			case MONOPOLY:
				this.monopolyCards+=num;
				break;
			case MONUMENT:
				this.monumentCards+=num;
				break;
			case ROAD_BUILD:
				this.roadBuilderCards+=num;
				break;
			case SOLDIER:
				this.soldierCards+=num;
				break;
			case YEAR_OF_PLENTY:
				this.yearOfPlentyCards+=num;
				break;
		}
	}
	
	/**
	 * Adds a card of a specific type to this group
	 * @param type the type of development card to add
	 */
	public void addCard(DevCardType type) {
		switch(type){
			case MONOPOLY:
				this.monopolyCards++;
				break;
			case MONUMENT:
				this.monumentCards++;
				break;
			case ROAD_BUILD:
				this.roadBuilderCards++;
				break;
			case SOLDIER:
				this.soldierCards++;
				break;
			case YEAR_OF_PLENTY:
				this.yearOfPlentyCards++;
				break;
		}
	}
	
	/**
	 * Removes a card of a specific type from this group
	 * @param type the type of development card to remove
	 * @throws InsufficientCardNumberException 
	 */
	public void removeCard(DevCardType type) throws InsufficientCardNumberException {
		switch(type){
			case MONOPOLY:
				if(getMonopolyCards() == 0){throw new InsufficientCardNumberException();}
				this.monopolyCards--;
				break;
			case MONUMENT:
				if(getMonumentCards() == 0){throw new InsufficientCardNumberException();}
				this.monumentCards--;
				break;
			case ROAD_BUILD:
				if(getRoadBuilderCards() == 0){throw new InsufficientCardNumberException();}
				this.roadBuilderCards--;
				break;
			case SOLDIER:
				if(getSoldierCards() == 0){throw new InsufficientCardNumberException();}
				this.soldierCards--;
				break;
			case YEAR_OF_PLENTY:
				if(getYearOfPlentyCards() == 0){throw new InsufficientCardNumberException();}
				this.yearOfPlentyCards--;
				break;
		}
	}
	
	/**
	 * Checks if a specified type of card can be removed
	 * @param type the type of development card to remove
	 */
	public boolean canRemoveCard(DevCardType type){
		switch(type){
			case MONOPOLY:
				if(getMonopolyCards() == 0){return false;}
				break;
			case MONUMENT:
				if(getMonumentCards() == 0){return false;}
				break;
			case ROAD_BUILD:
				if(getRoadBuilderCards() == 0){return false;}
				break;
			case SOLDIER:
				if(getSoldierCards() == 0){return false;}
				break;
			case YEAR_OF_PLENTY:
				if(getYearOfPlentyCards() == 0){return false;}
				break;
			}
		return true;
	}
}
