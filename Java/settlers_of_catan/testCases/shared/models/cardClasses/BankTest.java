package shared.models.cardClasses;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import shared.definitions.ResourceType;

public class BankTest {
	
	Bank bank = new Bank();

	@Test
	//The resourceCard object has been tested, so the only thing to test here is draining available resources from the bank.
	public void addAndRemoveTest() {
		//remove all brick.
		try {
			bank.takeResourceCards(ResourceType.BRICK, 19);
		} catch (InsufficientCardNumberException e) {
			e.printStackTrace();
		}
		
		//try to remove a brick
		boolean failed = false;
		try {
			bank.takeResourceCards(ResourceType.BRICK, 1);
		} catch (InsufficientCardNumberException e) {
			failed = true;
		}
		assertTrue(failed);
		
		bank.AddResourceCards(ResourceType.BRICK, 19);
		assertTrue(bank.getResources().brickCards == 19);
	}

}
