package shared.models.cardClasses;

import static org.junit.Assert.*;
import org.junit.Test;

import shared.definitions.DevCardType;

public class CardDeckTest {
	
	CardDeck deck = new CardDeck();

	@Test
	public void drawCardTest() {
		//This test will be different every time due to the randomness of the deck.
		//There may be a better way to do it, but for now it makes a deck of cards,
		//shuffles it, then removes the top card and makes sure that card is 
		//deducted from the total count of that type of card.
		DevCardType card = null;
		try {
			card = deck.drawCard();
		} catch (InsufficientCardNumberException e) {
			e.printStackTrace();
			fail();
		}
		switch (card) {
		case MONOPOLY:
			assertTrue(deck.devCards.monopolyCards == 1);
			break;
		case MONUMENT:
			assertTrue(deck.devCards.monumentCards == 4);
			break;
		case ROAD_BUILD:
			assertTrue(deck.devCards.roadBuilderCards == 1);
			break;
		case SOLDIER:
			assertTrue(deck.devCards.soldierCards == 13);
			break;
		case YEAR_OF_PLENTY:
			assertTrue(deck.devCards.yearOfPlentyCards == 1);
			break;
		}
	}

}
