package shared.models.cardClasses;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import shared.definitions.DevCardType;

public class DevCardsTest {
	
	DevCards devCards = new DevCards(14, 2, 2, 2, 5);

	@Test
	public void ConstructorTest() {
		//This checks the total counts on all the types of development cards after the group of dev cards is first created.
		assertTrue(devCards.getSoldierCards() == 14);
		assertTrue(devCards.getMonopolyCards() == 2);
		assertTrue(devCards.getRoadBuilderCards() == 2);
		assertTrue(devCards.getYearOfPlentyCards() == 2);
		assertTrue(devCards.getMonumentCards() == 5);
	}
	
	@Test
	public void addCardTests() {
		//This adds a card to the group and makes sure that that type is incremented.
		devCards.addCard(DevCardType.SOLDIER);
		devCards.addCard(DevCardType.MONOPOLY);
		devCards.addCard(DevCardType.MONUMENT);
		devCards.addCard(DevCardType.ROAD_BUILD);
		devCards.addCard(DevCardType.YEAR_OF_PLENTY);
		assertTrue(devCards.getSoldierCards() == 15);
		assertTrue(devCards.getMonopolyCards() == 3);
		assertTrue(devCards.getRoadBuilderCards() == 3);
		assertTrue(devCards.getYearOfPlentyCards() == 3);
		assertTrue(devCards.getMonumentCards() == 6);
	}
	
	@Test
	public void removeCardTests() {
		//This removes a card from the group and makes sure that that type is decremented.
		try {
			devCards.removeCard(DevCardType.SOLDIER);
			devCards.removeCard(DevCardType.MONOPOLY);
			devCards.removeCard(DevCardType.MONUMENT);
			devCards.removeCard(DevCardType.ROAD_BUILD);
			devCards.removeCard(DevCardType.YEAR_OF_PLENTY);
		} catch (InsufficientCardNumberException e) {
			fail();
			e.printStackTrace();
		}
		assertTrue(devCards.getSoldierCards() == 13);
		assertTrue(devCards.getMonopolyCards() == 1);
		assertTrue(devCards.getRoadBuilderCards() == 1);
		assertTrue(devCards.getYearOfPlentyCards() == 1);
		assertTrue(devCards.getMonumentCards() == 4);
		
		boolean failed = false;
		try {
			devCards.removeCard(DevCardType.MONOPOLY);
			devCards.removeCard(DevCardType.MONOPOLY);
		} catch (InsufficientCardNumberException e) {
			failed = true;
		}
		assertTrue(failed);
	}

}
