package shared.models.cardClasses;

import java.util.HashMap;

import shared.definitions.DevCardType;

public class CardDeck {
	
	/**
	 * This holds the number of each type of card left in the deck.
	 */
	DevCards devCards;
	/**
	 * This holds representations of cards that can be shuffled randomly, then drawn one at a time.
	 */
	DevCardType[] deck = new DevCardType[25];
	
	private int cardsInDeck = 25;

	/**
	 * The container that holds the development cards in the deck.
	 */
	public CardDeck() {
		devCards = new DevCards(14,2,2,2,5);
		//adds the right number of cards to the deck representation.
		int i = 0;
		while(i < 14) {
			deck[i] = DevCardType.SOLDIER;
			i++;
		}
		while(i < 19) {
			deck[i] = DevCardType.MONUMENT;
			i++;
		}
		while(i < 21) {
			deck[i] = DevCardType.ROAD_BUILD;
			i++;
		}
		while(i < 23) {
			deck[i] = DevCardType.MONOPOLY;
			i++;
		}
		while(i < 25) {
			deck[i] = DevCardType.YEAR_OF_PLENTY;
			i++;
		}
		shuffle();
	}

	public CardDeck(HashMap<DevCardType, Integer> cards) {
		devCards = new DevCards(0,0,0,0,0);
		cardsInDeck = 0;
		for(DevCardType type : cards.keySet()) {
			this.devCards.addCards(type, cards.get(type));
		}
		for(DevCardType type : cards.keySet()) {
			for(int i = 0; i < cards.get(type); i++) {
				this.deck[cardsInDeck] = type;
				cardsInDeck++;
			}
		}
	}
	
	/**
	 * Getter for devCards - Only for testing
	 * @return devCards
	 */
	public DevCards getDevCards() {
		return devCards;
	}
	
	/** Gives the next card in the deck. The devCard object will subtract one from the type drawn.
	 * 
	 * @return the type of the card drawn
	 * @throws InsufficientCardNumberException 
	 */
	public DevCardType drawCard() throws InsufficientCardNumberException {
		if(cardsInDeck == 0) {
			return null;
		}
		devCards.removeCard(deck[--cardsInDeck]);
		return deck[cardsInDeck];
	}
	
	 /**
     * Shuffles the deck. This will only happen in the constructor. There is no other time the deck needs to be shuffled.
     */
    public void shuffle() {
        for ( int i = deck.length-1; i > 0; i-- ) {
            int rand = (int)(Math.random()*(i+1));
            DevCardType temp = deck[i];
            deck[i] = deck[rand];
            deck[rand] = temp;
        }
    }
    
    /**
     * Build out a string of JSON that represents the current deck of development cards.
     * @return a string of JSON that represents the current deck of development cards
     */
    public String serialize() {
    	String json = "deck: {yearOfPlenty: ";
    	json += devCards.yearOfPlentyCards + ", monopoly: ";
    	json += devCards.monopolyCards + ", soldier: ";
    	json += devCards.soldierCards + ", roadBuilding: ";
    	json += devCards.roadBuilderCards + ", monument: ";
    	json += devCards.monumentCards;
    	json += "}";
    	return json;
    }
}
