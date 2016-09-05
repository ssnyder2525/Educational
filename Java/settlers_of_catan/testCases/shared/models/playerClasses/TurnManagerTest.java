package shared.models.playerClasses;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import shared.communication.proxy.CreateGameRequestParams;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.RobberLocation;
import shared.models.Game;
import shared.models.cardClasses.InsufficientCardNumberException;
import shared.models.mapClasses.InvalidTypeException;

public class TurnManagerTest {
	
	Game game;
	
	@Before
	public void setUp() {
		CreateGameRequestParams params = new CreateGameRequestParams("newGame", true, true, true);
		game = new Game(params);
	}
	
	@Test
	public void testCanBuyDevCard() {
		Player player = new Player(123, "bob", CatanColor.BLUE, 0);
		try {
			game.getPlayers().addPlayer(player);
		} catch (Exception e) {
			fail("could not add player.");
		}
		game.getTurnManager().setCurrentTurn(0);
		assertTrue(game.getTurnManager().isTurn(0));
		game.getPlayers().getPlayerByIndex(0).addResourceToHand(ResourceType.WHEAT, 5);
		game.getPlayers().getPlayerByIndex(0).addResourceToHand(ResourceType.SHEEP, 5);
		game.getPlayers().getPlayerByIndex(0).addResourceToHand(ResourceType.ORE, 5);
		assertTrue(game.getTurnManager().CanBuyDevCard(0)); // Check if the current player can buy a development card
		
		player = new Player(234, "sal", CatanColor.RED, 1);
		try {
			game.getPlayers().addPlayer(player);
		} catch (Exception e) {
			fail("could not add player.");
		}
		assertFalse(game.getTurnManager().CanBuyDevCard(1)); // Other player's shouldn't be able to buy a dev card
	}
	
	
	@Test
	public void testCanBuyRoad() {
		Player player = new Player(123, "bob", CatanColor.BLUE, 0);
		try {
			game.getPlayers().addPlayer(player);
		} catch (Exception e) {
			fail("could not add player.");
		}
		game.getTurnManager().setCurrentTurn(0);
		game.getPlayers().getPlayerByIndex(0).addResourceToHand(ResourceType.WOOD, 5);
		game.getPlayers().getPlayerByIndex(0).addResourceToHand(ResourceType.BRICK, 5);
		assertTrue(game.getTurnManager().getPlayers().getPlayerByIndex(0).canBuildRoad());
	}
	

	@Test
	public void testCanBuySettlement() {
		Player player = new Player(123, "bob", CatanColor.BLUE, 0);
		try {
			game.getPlayers().addPlayer(player);
		} catch (Exception e) {
			fail("could not add player.");
		}
		game.getTurnManager().setCurrentTurn(0);
		game.getPlayers().getPlayerByIndex(0).addResourceToHand(ResourceType.BRICK, 5);
		assertFalse(game.getTurnManager().getPlayers().getPlayerByIndex(0).canBuildSettlement());
		
		game.getPlayers().getPlayerByIndex(0).addResourceToHand(ResourceType.WOOD, 5);
		game.getPlayers().getPlayerByIndex(0).addResourceToHand(ResourceType.WHEAT, 5);
		game.getPlayers().getPlayerByIndex(0).addResourceToHand(ResourceType.SHEEP, 5);
		assertTrue(game.getTurnManager().getPlayers().getPlayerByIndex(0).canBuildSettlement());
	}
	
	
	@Test
	public void testCanBuyCity() {
		Player player = new Player(123, "bob", CatanColor.BLUE, 0);
		try {
			game.getPlayers().addPlayer(player);
		} catch (Exception e) {
			fail("could not add player.");
		}
		game.getTurnManager().setCurrentTurn(0);
		game.getPlayers().getPlayerByIndex(0).addResourceToHand(ResourceType.WOOD, 5);
		game.getPlayers().getPlayerByIndex(0).addResourceToHand(ResourceType.BRICK, 5);
		assertFalse(game.getTurnManager().getPlayers().getPlayerByIndex(0).canBuildCity());

		game.getPlayers().getPlayerByIndex(0).addResourceToHand(ResourceType.ORE, 5);
		game.getPlayers().getPlayerByIndex(0).addResourceToHand(ResourceType.WHEAT, 5);
		assertTrue(game.getTurnManager().getPlayers().getPlayerByIndex(0).canBuildCity());
	}
	
	
	@Test
	public void testCanRollNumber() {
		Player player = new Player(123, "bob", CatanColor.BLUE, 0);
		try {
			game.getPlayers().addPlayer(player);
		} catch (Exception e) {
			fail("could not add player.");
		}
		game.getTurnManager().setCurrentTurn(0);
		assertTrue(game.getTurnManager().getPlayers().getPlayerByIndex(0).canRollDice());
		game.rollDice(0);
	}
	
	
	@Test
	public void testIsTurn() {
		Player player = new Player(123, "bob", CatanColor.BLUE, 0);
		try {
			game.getPlayers().addPlayer(player);
		} catch (Exception e) {
			fail("could not add player.");
		}
		game.getTurnManager().setCurrentTurn(0);
		assertTrue(game.getTurnManager().isTurn(0));
	}
	
	
	@Test
	public void testCanFinishTurn() {
		Player player = new Player(123, "bob", CatanColor.BLUE, 0);
		try {
			game.getPlayers().addPlayer(player);
		} catch (Exception e) {
			fail("could not add player.");
		}
		game.getTurnManager().setCurrentTurn(0);
	}
	
	
	@Test
	public void testCanSendChat() {
		Player player = new Player(123, "bob", CatanColor.BLUE, 0);
		try {
			game.getPlayers().addPlayer(player);
		} catch (Exception e) {
			fail("could not add player.");
		}
		game.getTurnManager().setCurrentTurn(0);
		assertTrue(game.getTurnManager().CanSendChat());
	}
	
	@Test
	public void testPlayDevCards() throws InsufficientCardNumberException, InvalidTypeException {
		Player player = new Player(123, "Bob", CatanColor.BLUE, 0);
		Player player2 = new Player(123, "Henry", CatanColor.GREEN, 0);
		Player player3 = new Player(123, "Matt", CatanColor.RED, 0);
		Player player4 = new Player(123, "Achmed", CatanColor.PUCE, 0);
		try {
			game.getPlayers().addPlayer(player);
			game.getPlayers().addPlayer(player2);
			game.getPlayers().addPlayer(player3);
			game.getPlayers().addPlayer(player4);
		} catch (Exception e) {
			fail("could not add player.");
		}
		
		// give all players plenty of resource cards
		for (Player p : game.getPlayers().getPlayers()) {
			p.addResourceToHand(ResourceType.ORE, 50);
			p.addResourceToHand(ResourceType.BRICK, 50);
			p.addResourceToHand(ResourceType.WOOD, 50);
			p.addResourceToHand(ResourceType.WHEAT, 50);
			p.addResourceToHand(ResourceType.SHEEP, 50);
		}
			
		game.getTurnManager().setCurrentTurn(0);
		
		//player 1 buys all the dev cards cause he's a jerk.
		for (int i = 0; i < 25; i++) {
			game.getTurnManager().buyDevCard(0);
		}
		
		//finish the turn so that player1's dev cards are usable
		game.getTurnManager().finishTurn(0);
		game.getTurnManager().setCurrentTurn(0);
		
		//play a soldier card
		RobberLocation loc1 = game.getMap().getRobberLocation();
		HexLocation hexLoc = new HexLocation(0,0);
		game.getTurnManager().playSoldierCard(0, hexLoc, 1);
		RobberLocation loc2 = game.getMap().getRobberLocation();
		assertTrue(loc2.getHexLoc().getX() == 0 && loc2.getHexLoc().getY() == 0);
		Player p1 = game.getPlayers().getPlayerByIndex(0);
		Player p2 = game.getPlayers().getPlayerByIndex(1);
		//add the resources used by player 1 for dev cards
		p1.addResourceToHand(ResourceType.ORE, 25);
		p1.addResourceToHand(ResourceType.SHEEP, 25);
		p1.addResourceToHand(ResourceType.WHEAT, 25);
		
		//check that p1 got a resource
		assertTrue(p1.getNumOfResource(ResourceType.BRICK) == 51 || 
				p1.getNumOfResource(ResourceType.ORE) == 51 || p1.getNumOfResource(ResourceType.SHEEP) == 51 || 
				p1.getNumOfResource(ResourceType.WHEAT) == 51 || p1.getNumOfResource(ResourceType.WOOD) == 51);
		
		//check that p2 lost a resource
		assertTrue(p2.getNumOfResource(ResourceType.BRICK) == 49 || 
				p2.getNumOfResource(ResourceType.ORE) == 49 || p2.getNumOfResource(ResourceType.SHEEP) == 49 || 
				p2.getNumOfResource(ResourceType.WHEAT) == 49 || p2.getNumOfResource(ResourceType.WOOD) == 49);
		
		//play a roadBuilding card
		game.getTurnManager().finishTurn(0);
		game.getTurnManager().setCurrentTurn(0);
		EdgeLocation eloc = new EdgeLocation(new HexLocation(1,1), EdgeDirection.NorthEast);
		EdgeLocation eloc2 = new EdgeLocation(new HexLocation(1,1), EdgeDirection.NorthWest);
		game.getTurnManager().playRoadBuildingCard(0, eloc, eloc2);
		
		//play a year of plenty card
		game.getTurnManager().finishTurn(0);
		game.getTurnManager().setCurrentTurn(0);
		int orecards = p1.getNumOfResource(ResourceType.ORE);
		int woodcards = p1.getNumOfResource(ResourceType.WOOD);
		game.getTurnManager().playYearOfPlentyCard(0, ResourceType.ORE, ResourceType.WOOD);
		assertTrue(p1.getNumOfResource(ResourceType.ORE) == orecards + 1);
		assertTrue(p1.getNumOfResource(ResourceType.WOOD) == woodcards + 1);
		
		//play a monument card
		game.getTurnManager().finishTurn(0);
		game.getTurnManager().setCurrentTurn(0);
		int points = p1.getVictoryPoints();
		game.getTurnManager().playMonumentCard(0);
		assertTrue(p1.getVictoryPoints() == points + 1);
		
		//play a monopoly card
		game.getTurnManager().finishTurn(0);
		game.getTurnManager().setCurrentTurn(0);
		int orenum = p1.getNumOfResource(ResourceType.ORE);
		game.getTurnManager().playMonopolyCard(0, ResourceType.ORE);
		//if player2 got ore robbed, p1 will recieve 149 ore cards.
		assertTrue(p1.getNumOfResource(ResourceType.ORE) == orenum + 150 || p1.getNumOfResource(ResourceType.ORE) == orenum + 149);
		
	}
	
}
