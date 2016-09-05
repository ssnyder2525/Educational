package shared.serializerJSON;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shared.definitions.ResourceType;
import shared.models.cardClasses.Bank;
import shared.models.cardClasses.CardDeck;
import shared.models.cardClasses.DevCards;
import shared.models.cardClasses.InsufficientCardNumberException;
import shared.models.chatClasses.GameChat;
import shared.models.logClasses.GameLog;
import shared.models.playerClasses.GamePlayers;
import shared.models.playerClasses.Player;


public class DeserializerTest {
	
	Deserializer deserializer = new Deserializer();
	
	JsonObject json;
	
	@Before
	public void setUp() {
		String gameModelString = "{   \"deck\": {     \"yearOfPlenty\": 2,     \"monopoly\": 2,     \"soldier\": 14,     \"roadBuilding\": 2,     \"monument\": 5   },   \"map\": {     \"hexes\": [       {         \"location\": {           \"x\": 0,           \"y\": -2         }       },       {         \"resource\": \"brick\",         \"location\": {           \"x\": 1,           \"y\": -2         },         \"number\": 4       },       {         \"resource\": \"wood\",         \"location\": {           \"x\": 2,           \"y\": -2         },         \"number\": 11       },       {         \"resource\": \"brick\",         \"location\": {           \"x\": -1,           \"y\": -1         },         \"number\": 8       },       {         \"resource\": \"wood\",         \"location\": {           \"x\": 0,           \"y\": -1         },         \"number\": 3       },       {         \"resource\": \"ore\",         \"location\": {           \"x\": 1,           \"y\": -1         },         \"number\": 9       },       {         \"resource\": \"sheep\",         \"location\": {           \"x\": 2,           \"y\": -1         },         \"number\": 12       },       {         \"resource\": \"ore\",         \"location\": {           \"x\": -2,           \"y\": 0         },         \"number\": 5       },       {         \"resource\": \"sheep\",         \"location\": {           \"x\": -1,           \"y\": 0         },         \"number\": 10       },       {         \"resource\": \"wheat\",         \"location\": {           \"x\": 0,           \"y\": 0         },         \"number\": 11       },       {         \"resource\": \"brick\",         \"location\": {           \"x\": 1,           \"y\": 0         },         \"number\": 5       },       {         \"resource\": \"wheat\",         \"location\": {           \"x\": 2,           \"y\": 0         },         \"number\": 6       },       {         \"resource\": \"wheat\",         \"location\": {           \"x\": -2,           \"y\": 1         },         \"number\": 2       },       {         \"resource\": \"sheep\",         \"location\": {           \"x\": -1,           \"y\": 1         },         \"number\": 9       },       {         \"resource\": \"wood\",         \"location\": {           \"x\": 0,           \"y\": 1         },         \"number\": 4       },       {         \"resource\": \"sheep\",         \"location\": {           \"x\": 1,           \"y\": 1         },         \"number\": 10       },       {         \"resource\": \"wood\",         \"location\": {           \"x\": -2,           \"y\": 2         },         \"number\": 6       },       {         \"resource\": \"ore\",         \"location\": {           \"x\": -1,           \"y\": 2         },         \"number\": 3       },       {         \"resource\": \"wheat\",         \"location\": {           \"x\": 0,           \"y\": 2         },         \"number\": 8       }     ],     \"roads\": [       {         \"owner\": 1,         \"location\": {           \"direction\": \"S\",           \"x\": -1,           \"y\": -1         }       },       {         \"owner\": 3,         \"location\": {           \"direction\": \"SW\",           \"x\": -1,           \"y\": 1         }       },       {         \"owner\": 3,         \"location\": {           \"direction\": \"SW\",           \"x\": 2,           \"y\": -2         }       },       {         \"owner\": 2,         \"location\": {           \"direction\": \"S\",           \"x\": 1,           \"y\": -1         }       },       {         \"owner\": 0,         \"location\": {           \"direction\": \"S\",           \"x\": 0,           \"y\": 1         }       },       {         \"owner\": 2,         \"location\": {           \"direction\": \"S\",           \"x\": 0,           \"y\": 0         }       },       {         \"owner\": 1,         \"location\": {           \"direction\": \"SW\",           \"x\": -2,           \"y\": 1         }       },       {         \"owner\": 0,         \"location\": {           \"direction\": \"SW\",           \"x\": 2,           \"y\": 0         }       }     ],     \"cities\": [],     \"settlements\": [       {         \"owner\": 3,         \"location\": {           \"direction\": \"SW\",           \"x\": -1,           \"y\": 1         }       },       {         \"owner\": 3,         \"location\": {           \"direction\": \"SE\",           \"x\": 1,           \"y\": -2         }       },       {         \"owner\": 2,         \"location\": {           \"direction\": \"SW\",           \"x\": 0,           \"y\": 0         }       },       {         \"owner\": 2,         \"location\": {           \"direction\": \"SW\",           \"x\": 1,           \"y\": -1         }       },       {         \"owner\": 1,         \"location\": {           \"direction\": \"SW\",           \"x\": -2,           \"y\": 1         }       },       {         \"owner\": 0,         \"location\": {           \"direction\": \"SE\",           \"x\": 0,           \"y\": 1         }       },       {         \"owner\": 1,         \"location\": {           \"direction\": \"SW\",           \"x\": -1,           \"y\": -1         }       },       {         \"owner\": 0,         \"location\": {           \"direction\": \"SW\",           \"x\": 2,           \"y\": 0         }       }     ],     \"radius\": 3,     \"ports\": [       {         \"ratio\": 2,         \"resource\": \"ore\",         \"direction\": \"S\",         \"location\": {           \"x\": 1,           \"y\": -3         }       },       {         \"ratio\": 3,         \"direction\": \"SW\",         \"location\": {           \"x\": 3,           \"y\": -3         }       },       {         \"ratio\": 3,         \"direction\": \"NW\",         \"location\": {           \"x\": 2,           \"y\": 1         }       },       {         \"ratio\": 2,         \"resource\": \"brick\",         \"direction\": \"NE\",         \"location\": {           \"x\": -2,           \"y\": 3         }       },       {         \"ratio\": 2,         \"resource\": \"wheat\",         \"direction\": \"S\",         \"location\": {           \"x\": -1,           \"y\": -2         }       },       {         \"ratio\": 2,         \"resource\": \"wood\",         \"direction\": \"NE\",         \"location\": {           \"x\": -3,           \"y\": 2         }       },       {         \"ratio\": 3,         \"direction\": \"SE\",         \"location\": {           \"x\": -3,           \"y\": 0         }       },       {         \"ratio\": 2,         \"resource\": \"sheep\",         \"direction\": \"NW\",         \"location\": {           \"x\": 3,           \"y\": -1         }       },       {         \"ratio\": 3,         \"direction\": \"N\",         \"location\": {           \"x\": 0,           \"y\": 3         }       }     ],     \"robber\": {       \"x\": 0,       \"y\": -2     }   },   \"players\": [     {       \"resources\": {         \"brick\": 0,         \"wood\": 1,         \"sheep\": 1,         \"wheat\": 1,         \"ore\": 0       },       \"oldDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"newDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"roads\": 13,       \"cities\": 4,       \"settlements\": 3,       \"soldiers\": 0,       \"victoryPoints\": 2,       \"monuments\": 0,       \"playedDevCard\": false,       \"discarded\": false,       \"playerID\": 0,       \"playerIndex\": 0,       \"name\": \"Sam\",       \"color\": \"red\"     },     {       \"resources\": {         \"brick\": 1,         \"wood\": 0,         \"sheep\": 1,         \"wheat\": 0,         \"ore\": 1       },       \"oldDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"newDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"roads\": 13,       \"cities\": 4,       \"settlements\": 3,       \"soldiers\": 0,       \"victoryPoints\": 2,       \"monuments\": 0,       \"playedDevCard\": false,       \"discarded\": false,       \"playerID\": 1,       \"playerIndex\": 1,       \"name\": \"Brooke\",       \"color\": \"blue\"     },     {       \"resources\": {         \"brick\": 0,         \"wood\": 1,         \"sheep\": 1,         \"wheat\": 1,         \"ore\": 0       },       \"oldDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"newDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"roads\": 13,       \"cities\": 4,       \"settlements\": 3,       \"soldiers\": 0,       \"victoryPoints\": 2,       \"monuments\": 0,       \"playedDevCard\": false,       \"discarded\": false,       \"playerID\": 10,       \"playerIndex\": 2,       \"name\": \"Pete\",       \"color\": \"red\"     },     {       \"resources\": {         \"brick\": 0,         \"wood\": 1,         \"sheep\": 1,         \"wheat\": 0,         \"ore\": 1       },       \"oldDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"newDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"roads\": 13,       \"cities\": 4,       \"settlements\": 3,       \"soldiers\": 0,       \"victoryPoints\": 2,       \"monuments\": 0,       \"playedDevCard\": false,       \"discarded\": false,       \"playerID\": 11,       \"playerIndex\": 3,       \"name\": \"Mark\",       \"color\": \"green\"     }   ],   \"log\": {     \"lines\": [       {         \"source\": \"Sam\",         \"message\": \"Sam built a road\"       },       {         \"source\": \"Sam\",         \"message\": \"Sam built a settlement\"       },       {         \"source\": \"Sam\",         \"message\": \"Sam's turn just ended\"       },       {         \"source\": \"Brooke\",         \"message\": \"Brooke built a road\"       },       {         \"source\": \"Brooke\",         \"message\": \"Brooke built a settlement\"       },       {         \"source\": \"Brooke\",         \"message\": \"Brooke's turn just ended\"       },       {         \"source\": \"Pete\",         \"message\": \"Pete built a road\"       },       {         \"source\": \"Pete\",         \"message\": \"Pete built a settlement\"       },       {         \"source\": \"Pete\",         \"message\": \"Pete's turn just ended\"       },       {         \"source\": \"Mark\",         \"message\": \"Mark built a road\"       },       {         \"source\": \"Mark\",         \"message\": \"Mark built a settlement\"       },       {         \"source\": \"Mark\",         \"message\": \"Mark's turn just ended\"       },       {         \"source\": \"Mark\",         \"message\": \"Mark built a road\"       },       {         \"source\": \"Mark\",         \"message\": \"Mark built a settlement\"       },       {         \"source\": \"Mark\",         \"message\": \"Mark's turn just ended\"       },       {         \"source\": \"Pete\",         \"message\": \"Pete built a road\"       },       {         \"source\": \"Pete\",         \"message\": \"Pete built a settlement\"       },       {         \"source\": \"Pete\",         \"message\": \"Pete's turn just ended\"       },       {         \"source\": \"Brooke\",         \"message\": \"Brooke built a road\"       },       {         \"source\": \"Brooke\",         \"message\": \"Brooke built a settlement\"       },       {         \"source\": \"Brooke\",         \"message\": \"Brooke's turn just ended\"       },       {         \"source\": \"Sam\",         \"message\": \"Sam built a road\"       },       {         \"source\": \"Sam\",         \"message\": \"Sam built a settlement\"       },       {         \"source\": \"Sam\",         \"message\": \"Sam's turn just ended\"       }     ]   },   \"chat\": {     \"lines\": []   },   \"bank\": {     \"brick\": 23,     \"wood\": 21,     \"sheep\": 20,     \"wheat\": 22,     \"ore\": 22   },   \"turnTracker\": {     \"status\": \"Rolling\",     \"currentTurn\": 0,     \"longestRoad\": -1,     \"largestArmy\": -1   },   \"winner\": -1,   \"version\": 0}";
		JsonParser parser = new JsonParser();
		json = parser.parse(gameModelString).getAsJsonObject();
	}
	
	@Test
	public void deckTest() {		
		JsonObject jsonDeck = json.getAsJsonObject("deck");
		CardDeck desDeck = deserializer.deserializeDeck(jsonDeck);

		
		DevCards devCards = desDeck.getDevCards();
		assertTrue(devCards.getMonopolyCards() == 2);
		assertTrue(devCards.getYearOfPlentyCards() == 2);
		assertTrue(devCards.getRoadBuilderCards() == 2);
		assertTrue(devCards.getMonumentCards() == 5);
		assertTrue(devCards.getSoldierCards() == 14);
	}
	
//	@Test
//	public void mapTest() {
//		JsonObject jsonMap = json.getAsJsonObject("map");
//		Map map = deserializer.deserializeMap(jsonMap);
//
//		// The robber is on (0, -2), so should be false
//		assertFalse(map.canPlaceRobber(0, -2));
//		// The robber is on (0, -2), so should be true
//		assertTrue(map.canPlaceRobber(0, 0));
//	}

	@Test
	public void playersTest() {
		JsonArray jsonPlayers = json.getAsJsonArray("players");
		GamePlayers players = deserializer.deserializePlayers(jsonPlayers);
		
		Player player = players.getPlayerByIndex(0);
		// First player's name should be Sam - Checking
		assertTrue(player.getName().equals("Sam"));
	}
	
	@Test
	public void bankTest() {
		JsonObject jsonBank = json.getAsJsonObject("bank");
		Bank bank = deserializer.deserializeBank(jsonBank);
		
		try {
			bank.takeResourceCards(ResourceType.SHEEP, 20);
		} catch (InsufficientCardNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(bank.canRemove(ResourceType.SHEEP, 1));
	}

	@Test
	public void chatTest() {
		JsonObject jsonLog = json.getAsJsonObject("log");
		GameChat desChat = deserializer.deserializeChat(jsonLog);

		// There are 5 messages to be deserialized from the JSON
		assertTrue(desChat.getMessages().size() == 24);
		// Test the content of the messages
		assertTrue(desChat.getMessages().get(0).getSource().equals("Sam"));
		assertTrue(desChat.getMessages().get(0).getMessage().equals("Sam built a road"));
		assertTrue(desChat.getMessages().get(1).getSource().equals("Sam"));
		assertTrue(desChat.getMessages().get(1).getMessage().equals("Sam built a settlement"));
		assertTrue(desChat.getMessages().get(2).getSource().equals("Sam"));
		assertTrue(desChat.getMessages().get(2).getMessage().equals("Sam's turn just ended"));
		assertTrue(desChat.getMessages().get(3).getSource().equals("Brooke"));
		assertTrue(desChat.getMessages().get(3).getMessage().equals("Brooke built a road"));
		assertTrue(desChat.getMessages().get(4).getSource().equals("Brooke"));
		assertTrue(desChat.getMessages().get(4).getMessage().equals("Brooke built a settlement"));
	}

	@Test
	public void logTest() {
		JsonObject jsonLog = json.getAsJsonObject("log");
		GameLog desLog = deserializer.deserializeLog(jsonLog);

		// There are 24 messages to be deserialized from the JSON
		assertTrue(desLog.getMessages().size() == 24);
		// Test the content of the messages
		assertTrue(desLog.getMessages().get(0).getSource().equals("Sam"));
		assertTrue(desLog.getMessages().get(0).getMessage().equals("Sam built a road"));
		assertTrue(desLog.getMessages().get(1).getSource().equals("Sam"));
		assertTrue(desLog.getMessages().get(1).getMessage().equals("Sam built a settlement"));
		assertTrue(desLog.getMessages().get(2).getSource().equals("Sam"));
		assertTrue(desLog.getMessages().get(2).getMessage().equals("Sam's turn just ended"));
		assertTrue(desLog.getMessages().get(3).getSource().equals("Brooke"));
		assertTrue(desLog.getMessages().get(3).getMessage().equals("Brooke built a road"));
		assertTrue(desLog.getMessages().get(4).getSource().equals("Brooke"));
		assertTrue(desLog.getMessages().get(4).getMessage().equals("Brooke built a settlement"));

	}
}
