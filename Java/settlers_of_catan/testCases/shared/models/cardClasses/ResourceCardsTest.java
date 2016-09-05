package shared.models.cardClasses;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import shared.definitions.ResourceType;

public class ResourceCardsTest {
	
	ResourceCards resourceCards = new ResourceCards(0, 0, 0, 0, 0);
	
	@Test
	public void addTest() {
		//This tests simply tests adding cards from the resource card group.
		resourceCards.addCard(ResourceType.BRICK, 1);
		resourceCards.addCard(ResourceType.ORE, 2);
		resourceCards.addCard(ResourceType.SHEEP, 3);
		resourceCards.addCard(ResourceType.WHEAT, 4);
		resourceCards.addCard(ResourceType.WOOD, 5);
		
		assertTrue(resourceCards.getBrickCards() == 1);
		assertTrue(resourceCards.getOreCards() == 2);
		assertTrue(resourceCards.getSheepCards() == 3);
		assertTrue(resourceCards.getWheatCards() == 4);
		assertTrue(resourceCards.getWoodCards() == 5);
	}
	
	@Test
	public void removeTest() {
		//This tests simply tests subtracting cards from the resource card group.
		resourceCards.addCard(ResourceType.BRICK, 1);
		resourceCards.addCard(ResourceType.ORE, 2);
		resourceCards.addCard(ResourceType.SHEEP, 3);
		resourceCards.addCard(ResourceType.WHEAT, 4);
		resourceCards.addCard(ResourceType.WOOD, 5);
		
		try {
			resourceCards.removeCard(ResourceType.BRICK, 1);
			resourceCards.removeCard(ResourceType.ORE, 2);
			resourceCards.removeCard(ResourceType.SHEEP, 3);
			resourceCards.removeCard(ResourceType.WHEAT, 4);
			resourceCards.removeCard(ResourceType.WOOD, 5);
		} catch (InsufficientCardNumberException e) {
			fail();
			e.printStackTrace();
		}
		
		assertTrue(resourceCards.getBrickCards() == 0);
		assertTrue(resourceCards.getOreCards() == 0);
		assertTrue(resourceCards.getSheepCards() == 0);
		assertTrue(resourceCards.getWheatCards() == 0);
		assertTrue(resourceCards.getWoodCards() == 0);
		
		boolean failed = false;
		try {
			resourceCards.removeCard(ResourceType.BRICK, 1);
		} catch (InsufficientCardNumberException e) {
			failed = true;
		}
		assertTrue(failed);
	}

}
