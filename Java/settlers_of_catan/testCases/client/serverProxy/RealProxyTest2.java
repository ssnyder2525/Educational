package client.serverProxy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import shared.communication.proxy.*;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

public class RealProxyTest2 {

	RealProxy realProxy = null;
	
	@Before
	public void setUp() throws Exception {
		this.realProxy = new RealProxy("http://localhost:8081");
		
		CreateGameRequestParams createParams = new CreateGameRequestParams("test", true, true, true);
		this.realProxy.createGame(createParams);
		
		JoinGameRequestParams params = new JoinGameRequestParams();
		params.id = 1;
		
		Credentials credentials = new Credentials();
		try {
			credentials.username = "personOne";
			credentials.password = "personOne";
			assertEquals(this.realProxy.registerUser(credentials), "Success");
			assertEquals(this.realProxy.loginUser(credentials), "Success");
			params.color = "blue";
			this.realProxy.joinGame(params);
			
			credentials.username = "personTwo";
			credentials.password = "personTwo";
			assertEquals(this.realProxy.registerUser(credentials), "Success");
			assertEquals(this.realProxy.loginUser(credentials), "Success");
			params.color = "red";
			this.realProxy.joinGame(params);
			
			credentials.username = "personThree";
			credentials.password = "personThree";
			assertEquals(this.realProxy.registerUser(credentials), "Success");
			assertEquals(this.realProxy.loginUser(credentials), "Success");
			params.color = "green";
			this.realProxy.joinGame(params);
			
			credentials.username = "personFour";
			credentials.password = "personFour";
			assertEquals(this.realProxy.registerUser(credentials), "Success");
			assertEquals(this.realProxy.loginUser(credentials), "Success");
			params.color = "puce";
			this.realProxy.joinGame(params);
			
			//sets up four users
			
		}
		catch (Exception e) {
			//Setup is only needed for the initial setup of the server
			credentials.username = "personOne";
			credentials.password = "personOne";
			assertEquals(this.realProxy.loginUser(credentials), "Success");
			params.color = "blue";
			this.realProxy.joinGame(params);
			//fail("Exception");
		}
		
	}

	
// USER
	@Test
	public void testLoginUser() {
		try {
			String response = realProxy.loginUser(new Credentials("personOne", "personOne"));
			Boolean success = response.equals("Success");
			assertTrue(success);
			
			response = realProxy.loginUser(new Credentials("personOnes", "personOnes"));
			Boolean failure = response.equals("Success");
			assertFalse(failure);

		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testRegisterUser() {
		try {
			String response = realProxy.registerUser(new Credentials("personFive", "personFive"));
			Boolean success = response.equals("Success");
			assertTrue(success);
			
			response = realProxy.loginUser(new Credentials("personFive", "personFive"));
			success = response.equals("Success");
			assertTrue(success);

		} catch (Exception e) {
			fail("shouldn't get an exception, this only works with a fresh server");
		}
	}
	
	
// GAMES
	@Test
	public void testListGames() {
		try {
			String response = realProxy.getGamesList();
			Boolean success = response.length() > 0;
			assertTrue(success);

		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testCreateGame() {
		try {
			String response = realProxy.createGame(new CreateGameRequestParams("testCreate", true, true, true));
			Boolean success = response.length() > 0;
			assertTrue(success);

		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testJoinGame() {
		try {
			String response = realProxy.joinGame(new JoinGameRequestParams(1, "red"));
			Boolean success = response.length() > 0;
			assertTrue(success);

		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	

// GAME
	@Test
	public void testGetGameModel() {
		try {
			
			String response = realProxy.getGameModel(-1);
			Boolean success = response.length() > 0;
			assertTrue(success);

		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	
// MOVES
	@Test
	public void testBuildRoad() {
		try {
			String response = realProxy.buildRoad(new BuildRoad(0, true, 0, 0, "NW"));
			Boolean success = response.length() > 0;
			assertTrue(success);

		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testBuildSettlement() {
		try {
			String response = realProxy.buildSettlement(new BuildSettlement(0, true, 0, 0, "NW"));
			Boolean success = response.length() > 0;
			assertTrue(success);
			
		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testSendChat() {
		try {
			String response = realProxy.sendChat(new SendChat(0, "HELLO"));
			Boolean success = response.length() > 0;
			assertTrue(success);
			
		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testRollNumber() {
		try {
			String response = realProxy.rollNumber(new RollNumber(0, 6));
			Boolean success = response.length() > 0;
			assertTrue(success);
			
		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testRobPlayer() {
		try {
			String response = realProxy.robPlayer(new RobPlayer(0, 1, new HexLocation(1, 1)));
			Boolean success = response.length() > 0;
			assertTrue(success);
			
		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testFinishTurn() {
		try {
			String response = realProxy.finishTurn(new FinishTurn(0));
			Boolean success = response.length() > 0;
			assertTrue(success);
			
		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testBuyDevCard() {
		try {
			String response = realProxy.buyDevCard(new BuyDevCard(0));
			Boolean success = response.length() > 0;
			fail("should get an exception");
			
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testYearOfPlenty() {
		try {
			String response = realProxy.yearOfPlenty(new YearOfPlenty(0, ResourceType.BRICK, ResourceType.SHEEP));
			Boolean success = response.length() > 0;
			assertTrue(success);
			
		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testRoadBuilding() {
		try {
			String response = realProxy.roadBuilding(new RoadBuilding(0, new EdgeLocation(new HexLocation(1,1), EdgeDirection.North), new EdgeLocation(new HexLocation(1,2), EdgeDirection.North)));
			Boolean success = response.length() > 0;
			assertTrue(success);
			
		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testSoldier() {
		try {
			String response = realProxy.moveSoldier(new SoldierMove(0, 1, new HexLocation(1,1)));
			Boolean success = response.length() > 0;
			assertTrue(success);
			
		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testMonopoly() {
		try {
			String response = realProxy.playMonopolyCard(new Monopoly(0, ResourceType.WHEAT));
			Boolean success = response.length() > 0;
			assertTrue(success);
			
		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testMonument() {
		try {
			String response = realProxy.playMonumentCard(new MonumentMove(1));
			Boolean success = response.length() > 0;
			assertTrue(success);
			
		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testOfferTrade() {
		try {
			String response = realProxy.offerTrade(new OfferTrade(0, 1, 1, 1, 1, 1, 0));
			Boolean success = response.length() > 0;
			assertTrue(success);
			
		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testAcceptTrade() {
		try {
			String response = realProxy.acceptTrade(new AcceptTrade(0, true));
			Boolean success = response.length() > 0;
			assertTrue(success);
			
		} catch (Exception e) {
			fail("shouldn't get an exception");
		}
	}
	
	@Test
	public void testMaritimeTrade() {
		try {
			String response = realProxy.maritimeTrade(new MaritimeTrade(ResourceType.BRICK, ResourceType.ORE, 3, 0));
			Boolean success = response.length() > 0;
			fail("should get an exception");
			
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testDiscardCards() {
		try {
			String response = realProxy.discardCards(new DiscardedCards(0, 1, 2, 1, 0, 0));
			Boolean success = response.length() > 0;
			fail("should get an exception");
			
		} catch (Exception e) {
			assertTrue(true);
		}
	}
}
