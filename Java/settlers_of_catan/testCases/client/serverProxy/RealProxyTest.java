package client.serverProxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shared.communication.proxy.AcceptTrade;
import shared.communication.proxy.BuildCity;
import shared.communication.proxy.BuildRoad;
import shared.communication.proxy.BuildSettlement;
import shared.communication.proxy.BuyDevCard;
import shared.communication.proxy.CreateGameRequestParams;
import shared.communication.proxy.Credentials;
import shared.communication.proxy.DiscardedCards;
import shared.communication.proxy.FinishTurn;
import shared.communication.proxy.JoinGameRequestParams;
import shared.communication.proxy.MaritimeTrade;
import shared.communication.proxy.Monopoly;
import shared.communication.proxy.MonumentMove;
import shared.communication.proxy.OfferTrade;
import shared.communication.proxy.RoadBuilding;
import shared.communication.proxy.RobPlayer;
import shared.communication.proxy.RollNumber;
import shared.communication.proxy.SendChat;
import shared.communication.proxy.SoldierMove;
import shared.communication.proxy.YearOfPlenty;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class RealProxyTest {

	RealProxy realProxy = null;
	
	@Before // before
	public void setUp() throws Exception {
		this.realProxy = new RealProxy("http://localhost:8081");
		
		Credentials credentials = new Credentials();
		credentials.username = "Sam";
		credentials.password = "sam";
		try {
			assertEquals(this.realProxy.loginUser(credentials), "Success");
		}
		catch (Exception e) {
			fail("Exception");
		}
		
		JoinGameRequestParams params = new JoinGameRequestParams();
		params.id = 1;
		params.color = "blue";
		
		try {
			this.realProxy.joinGame(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testLoginUser() {
		Credentials credentials = new Credentials();
		credentials.username = "Sam";
		credentials.password = "sam";
		try {
			assertEquals(this.realProxy.loginUser(credentials), "Success");
		}
		catch (Exception e) {
			fail("Exception");
		}
	}

	@Test
	public void testRegisterUser() {
		Credentials credentials = new Credentials();
		credentials.username = "Cody" + System.currentTimeMillis();
		credentials.password = "cody";
		try {
			assertEquals(this.realProxy.registerUser(credentials), "Success");
		}
		catch (Exception e) {
			fail("Exception");
		}
	}

	@Test
	public void testGetGamesList() {
		try {
			String result = realProxy.getGamesList();
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			fail("Exception");
		}
		
	}

	@Test
	public void testCreateGame() {
		CreateGameRequestParams params = new CreateGameRequestParams();
		params.name = "test name";
		params.randomNumbers = false;
		params.randomPorts = false;
		params.randomTiles = false;
		
		try {
			String json = this.realProxy.createGame(params);
			assertTrue(json.length() > 0);
		}
		catch (Exception e) {
			fail("Exception");
		}
	}

	@Test
	public void testJoinGame() {
		
	}

	@Test
	public void testGetGameModel() {
		try {
			String result = realProxy.getGameModel(0);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		} catch (Exception e) {
		}
	}

	@Test
	public void testSendChat() {
		SendChat sendChat = new SendChat();
		sendChat.content = "new chat message";
		try {
			String result = this.realProxy.sendChat(sendChat);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		} catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testRollNumber() {
		RollNumber rollNumber = new RollNumber(1);
		rollNumber.roll = 10;
		rollNumber.playerIndex = 1;
		try {
			String result = realProxy.rollNumber(rollNumber);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testRobPlayer() {
		RobPlayer robPlayer = new RobPlayer();
		robPlayer.playerIndex = 1;
		robPlayer.victimIndex = 2;
		robPlayer.newLocation = new HexLocation(0,0);
		
		try {
			String result = realProxy.robPlayer(robPlayer);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testFinishTurn() {
		FinishTurn finishTurn = new FinishTurn();
		finishTurn.playerIndex = 1;
		try {
			String result = realProxy.finishTurn(finishTurn);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testBuyDevCard() {
		BuyDevCard buyDevCard = new BuyDevCard();
		buyDevCard.playerIndex = 1;
		
		try {
			String result = realProxy.buyDevCard(buyDevCard);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testYearOfPlenty() {
		YearOfPlenty yearOfPlenty = new YearOfPlenty();
		yearOfPlenty.resourceOne = ResourceType.BRICK;
		yearOfPlenty.resourceTwo = ResourceType.WOOD;
		yearOfPlenty.playerIndex = 0;
		
		try {
			String result = realProxy.yearOfPlenty(yearOfPlenty);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testRoadBuilding() {
		RoadBuilding roadBuilding = new RoadBuilding();
		roadBuilding.firstSpot = new EdgeLocation(new HexLocation(2,0), EdgeDirection.NorthWest);
		roadBuilding.secondSpot = new EdgeLocation(new HexLocation(2,0), EdgeDirection.North);
		roadBuilding.playerIndex = 2;
		
		try {
			String result = realProxy.roadBuilding(roadBuilding);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testMoveSoldier() {
		SoldierMove soldierMove = new SoldierMove();
		soldierMove.newLocation = new HexLocation(0,0);
		soldierMove.playerIndex = 1;
		soldierMove.victimIndex = 2;
		
		
		try {
			String result = realProxy.moveSoldier(soldierMove);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
		
	}

	@Test
	public void testPlayMonopolyCard() {
		Monopoly monopoly = new Monopoly();
		monopoly.resource = ResourceType.SHEEP;
		monopoly.playerIndex = 2;
		
		try {
			String result = realProxy.playMonopolyCard(monopoly);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testPlayMonumentCard() {
		MonumentMove monumentMove = new MonumentMove();
		monumentMove.playerIndex = 1;
		
		try {
			String result = realProxy.playMonumentCard(monumentMove);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
		
	}

	@Test
	public void testBuildRoad() {
		BuildRoad buildRoad = new BuildRoad();
		buildRoad.free = true;
		buildRoad.playerIndex = 0;
		buildRoad.roadLocation = new EdgeLocation(new HexLocation(2,0), EdgeDirection.NorthWest);
		
		try {
			String result = realProxy.buildRoad(buildRoad);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testBuildCity() {
		BuildCity buildCity = new BuildCity();
		buildCity.playerIndex = 0;
		buildCity.vertexLocation = new VertexLocation(new HexLocation(0,1), VertexDirection.SouthEast);
		
		try {
			String result = realProxy.buildCity(buildCity);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testBuildSettlement() {
		BuildSettlement buildSettlement = new BuildSettlement();
		buildSettlement.free = true;
		buildSettlement.playerIndex = 0;
		buildSettlement.vertexLocation = new VertexLocation(new HexLocation(0,2), VertexDirection.NorthWest);
		
		try {
			String result = realProxy.buildSettlement(buildSettlement);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testOfferTrade() {
		OfferTrade offerTrade = new OfferTrade();
		offerTrade.receiverIndex = 0;
		offerTrade.playerIndex = 1;
		
		offerTrade.brick = 0;
		offerTrade.ore = 0;
		offerTrade.wheat = 1;
		offerTrade.wood = -1;
		offerTrade.sheep = 0;
		
		try {
			String result = realProxy.offerTrade(offerTrade);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testAcceptTrade() {
		AcceptTrade acceptTrade = new AcceptTrade(1, false);
		acceptTrade.response = false;
		acceptTrade.playerIndex = 1;
		
		try {
			String result = realProxy.acceptTrade(acceptTrade);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testMaritimeTrade() {
		MaritimeTrade maritimeTrade = new MaritimeTrade(ResourceType.SHEEP, ResourceType.BRICK, 4, 0);
		
		try {			
			String result = realProxy.maritimeTrade(maritimeTrade);
			Assert.assertTrue(result.length() > 0);
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testDiscardCards() {
		DiscardedCards discardedCards = new DiscardedCards();
		discardedCards.playerIndex = 1;
		
		discardedCards.brick = 0;
		discardedCards.ore = 0;
		discardedCards.wheat = 1;
		discardedCards.wood = -1;
		discardedCards.sheep = 0;
		
		try {
			String result = realProxy.discardCards(discardedCards);
			Assert.assertTrue(result != "");
			Assert.assertTrue(result != "400 Error");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("error");
		}
	}

	@Test
	public void testGetListAI() {
		try {
			ArrayList<String> result = realProxy.getListAI();
			assertTrue(result.get(0).equals("LARGEST_ARMY"));
		} catch (Exception e) {
		}
	}

}
