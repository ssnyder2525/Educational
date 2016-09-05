package shared.models.playerClasses;

import static org.junit.Assert.*;

import org.junit.Test;

import shared.definitions.CatanColor;

public class GamePlayersTest {

	GamePlayers players = new GamePlayers();
	
	@Test
	public void testGamePlayers() {}

	@Test
	public void testAddGetPlayers() {
		//adds four players, checking the counts along the way. We need to know that there are only four players.
		try {
			players.addPlayer(1, "bob", CatanColor.BLUE);
			assertTrue(players.getNumberOfPlayers() == 1);
			
			players.addPlayer(2, "bob", CatanColor.GREEN);
			assertTrue(players.getNumberOfPlayers() == 2);
			
			players.addPlayer(3, "bob", CatanColor.WHITE);
			assertTrue(players.getNumberOfPlayers() == 3);
			
			players.addPlayer(4, "bob", CatanColor.RED);
			assertTrue(players.getNumberOfPlayers() == 4);
		} catch (Exception e) {
			fail("Failed adding a legal number of players");
		}
		
		assertTrue(players.getPlayerByIndex(0).getColor() == CatanColor.BLUE);
		assertTrue(players.getPlayerByIndex(1).getColor() == CatanColor.GREEN);
		assertTrue(players.getPlayerByIndex(2).getColor() == CatanColor.WHITE);
		assertTrue(players.getPlayerByIndex(3).getColor() == CatanColor.RED);
		
		boolean failed = false;
		try {
			players.addPlayer(5, "bob", CatanColor.BLUE);
		} catch (Exception e) {
			failed = true;
		}
		assertTrue(failed);		
	}

	@Test
	public void testFinishTurn() {
		try {
			players.addPlayer(1, "bob", CatanColor.BLUE);
			assertTrue(players.getNumberOfPlayers() == 1);
			
			players.addPlayer(2, "bob", CatanColor.BLUE);
			assertTrue(players.getNumberOfPlayers() == 2);
			
			players.addPlayer(3, "bob", CatanColor.BLUE);
			assertTrue(players.getNumberOfPlayers() == 3);
			
			players.addPlayer(4, "bob", CatanColor.BLUE);
			assertTrue(players.getNumberOfPlayers() == 4);
		} catch (Exception e) {
			fail("Failed adding a legal number of players");
		}
		players.getPlayerByIndex(1).startTurn();
		players.finishTurn(1);
		assertFalse(players.getPlayerByIndex(1).isTurn());
	}
	
	@Test
	public void testCheckForLargestArmy() {
		try {
			players.addPlayer(1, "bob", CatanColor.BLUE);
			players.addPlayer(2, "bob", CatanColor.GREEN);
			players.addPlayer(3, "bob", CatanColor.WHITE);
			players.addPlayer(4, "bob", CatanColor.RED);
		} catch (Exception e) {
			fail("Failed adding a legal number of players");
		}
		assertTrue(players.checkForLargestArmy() == -1);
		players.getPlayerByIndex(3).addToArmy();
		players.getPlayerByIndex(3).addToArmy();
		assertTrue(players.checkForLargestArmy() == -1);
		players.getPlayerByIndex(3).addToArmy();
		assertTrue(players.checkForLargestArmy() == 3);
	}

}
