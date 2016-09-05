package shared.serializerJSON;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import client.data.GameInfo;
import client.data.PlayerInfo;
import server.ServerException;
import server.command.ACommand;
import server.command.CommandFactory;
import server.facade.IServerFacade;
import shared.communication.proxy.OfferTrade;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.RobberLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.models.Game;
import shared.models.cardClasses.Bank;
import shared.models.cardClasses.CardDeck;
import shared.models.chatClasses.GameChat;
import shared.models.chatClasses.Message;
import shared.models.logClasses.GameLog;
import shared.models.mapClasses.EdgeMap;
import shared.models.mapClasses.HexMap;
import shared.models.mapClasses.InvalidTokenException;
import shared.models.mapClasses.InvalidTypeException;
import shared.models.mapClasses.Map;
import shared.models.mapClasses.Piece;
import shared.models.mapClasses.PlayerMap;
import shared.models.mapClasses.Hex;
import shared.models.mapClasses.VertexMap;
import shared.models.mapClasses.WaterHex;
import shared.models.playerClasses.GamePlayers;
import shared.models.playerClasses.Player;

/** Deserializer
 * 
 * @author Bo Pace
 *
 */
public class Deserializer {
	private static Deserializer instance = null;
	
	public static Deserializer getInstance() {
		if (instance == null) {
			instance = new Deserializer();
		}
		return instance;
	}
	
	// currentTurn
	private int desCurrentTurn = 0;
	private String desCurrentState = "FirstRound";
	private int desLongestRoad = -1;
	private boolean desHasPlayedDevCard = false;
	
	
	public void deserialize(Game game, JsonObject json) { 
		this.deserialize(game, json, false);
	}
	
	/**
	 * Update the game model after deserializing all of the different pieces.
	 * @param game The game model to be updated.
	 * @param json The json to be deserialized.
	 * @param isTest Is this for a test?
	 */
	public void deserialize(Game game, JsonObject json, boolean isTest) {
		// the "des" prefix signifies that the object has been deserialized.
		
		// Pull out the information about "deck" from the JSON
		JsonObject jsonDeck = json.getAsJsonObject("deck");
		CardDeck desDeck = deserializeDeck(jsonDeck);
		
		// Now get the information labeled "map"
		JsonObject jsonMap = json.getAsJsonObject("map");
		Map desMap = deserializeMap(jsonMap);
		
		// Now "players"
		JsonArray jsonPlayers = json.getAsJsonArray("players");
		GamePlayers desPlayers = deserializePlayers(jsonPlayers);
		
		// "log"
		JsonObject jsonLog = json.getAsJsonObject("log");
		GameLog desLog = deserializeLog(jsonLog);
		
		// "chat"
		JsonObject jsonChat = json.getAsJsonObject("chat");
		GameChat desChat = deserializeChat(jsonChat);
		
		// "bank"
		JsonObject jsonBank = json.getAsJsonObject("bank");
		Bank desBank = deserializeBank(jsonBank);
		
		// "tradeOffer"
		OfferTrade desOfferTrade = null;
		if (json.has("tradeOffer")) {
			desOfferTrade = deserializeOfferTrade(json.getAsJsonObject("tradeOffer"));
		}
		
		// "turnTracker"
		JsonObject jsonTurnTracker = json.getAsJsonObject("turnTracker");
		deserializeTurnTracker(jsonTurnTracker);
		
		// winner
		int desWinner = json.getAsJsonPrimitive("winner").getAsInt();

		if (isTest) { // Used with testing the poller
			game.updateForTest(desMap, desBank, desDeck, desPlayers, desLog, desChat, desOfferTrade, 
					desCurrentTurn, desCurrentState, desHasPlayedDevCard, desWinner, desLongestRoad);
		}
		else {
			game.update(desMap, desBank, desDeck, desPlayers, desLog, desChat, desOfferTrade, 
					desCurrentTurn, desCurrentState, desHasPlayedDevCard, desWinner, desLongestRoad);
		}
	}
	
	public void deserializeSavedGame(Game game, JsonObject json) {
		// the "des" prefix signifies that the object has been deserialized.
		
		// Pull out the information about "deck" from the JSON
		JsonObject jsonDeck = json.getAsJsonObject("deck");
		CardDeck desDeck = deserializeDeck(jsonDeck);
		
		// Now get the information labeled "map"
		JsonObject jsonMap = json.getAsJsonObject("map");
		Map desMap = deserializeMap(jsonMap);
		
		// Now "players"
		JsonArray jsonPlayers = json.getAsJsonArray("players");
		GamePlayers desPlayers = deserializePlayers(jsonPlayers);
		
		// "log"
		JsonObject jsonLog = json.getAsJsonObject("log");
		GameLog desLog = deserializeLog(jsonLog);
		
		// "chat"
		JsonObject jsonChat = json.getAsJsonObject("chat");
		GameChat desChat = deserializeChat(jsonChat);
		
		// "bank"
		JsonObject jsonBank = json.getAsJsonObject("bank");
		Bank desBank = deserializeBank(jsonBank);
		
		// "tradeOffer"
		OfferTrade desOfferTrade = null;
		if (json.has("tradeOffer")) {
			desOfferTrade = deserializeOfferTrade(json.getAsJsonObject("tradeOffer"));
		}
		
		// "turnTracker"
		JsonObject jsonTurnTracker = json.getAsJsonObject("turnTracker");
		deserializeTurnTracker(jsonTurnTracker);
		
		// winner
		int desWinner = json.getAsJsonPrimitive("winner").getAsInt();

		
		game.saveGame(desMap, desBank, desDeck, desPlayers, desLog, desChat, desOfferTrade, 
					desCurrentTurn, desCurrentState, desHasPlayedDevCard, desWinner, desLongestRoad);
	}
	
	/**
	 * Deserializes the deck from the game JSON
	 * @param jsonDeck Takes in the deck JSON that has been separated from the GameModel JSON
	 * @return returns a CardDeck object that has been successfully deserialized
	 */
	public CardDeck deserializeDeck(JsonObject jsonDeck) {
		// Year of Plenty
		JsonPrimitive jsonYearOfPlenty = jsonDeck.getAsJsonPrimitive("yearOfPlenty");
		int yearOfPlentyCount = jsonYearOfPlenty.getAsInt();
		// Monopoly
		JsonPrimitive jsonMonopoly = jsonDeck.getAsJsonPrimitive("monopoly");
		int monopolyCount = jsonMonopoly.getAsInt();
		// Soldier
		JsonPrimitive jsonSoldier = jsonDeck.getAsJsonPrimitive("soldier");
		int soldierCount = jsonSoldier.getAsInt();
		// RoadBuilding
		JsonPrimitive jsonRoadBuilding = jsonDeck.getAsJsonPrimitive("roadBuilding");
		int roadBuildingCount = jsonRoadBuilding.getAsInt();
		// Monument
		JsonPrimitive jsonMonument = jsonDeck.getAsJsonPrimitive("monument");
		int monumentCount = jsonMonument.getAsInt();
		
		HashMap<DevCardType, Integer> cards = new HashMap<DevCardType, Integer>();
		cards.put(DevCardType.YEAR_OF_PLENTY, yearOfPlentyCount);
		cards.put(DevCardType.MONOPOLY, monopolyCount);
		cards.put(DevCardType.SOLDIER, soldierCount);
		cards.put(DevCardType.ROAD_BUILD, roadBuildingCount);
		cards.put(DevCardType.MONUMENT, monumentCount);

		return new CardDeck(cards);
	}
	
	/**
	 * Deserializes the map from the game JSON
	 * @param jsonMap Takes in the map JSON that has been separated from the GameModel JSON
	 * @return returns a Map object that has been successfully deserialized
	 */
	public Map deserializeMap(JsonObject jsonMap) {
		HexMap hexes = new HexMap();
		VertexMap vertices = new VertexMap();
		EdgeMap edges = new EdgeMap();
		//PortMap ports = new PortMap();
		RobberLocation robberLocation = null;
		PlayerMap playerMap = new PlayerMap();
		try {
			
			// Build the hexes for the map
			// HexMap hexes
			JsonArray jsonHexes = jsonMap.getAsJsonArray("hexes");
			for (JsonElement hex : jsonHexes) {
				
				Hex newHex;
				if (hex.getAsJsonObject().has("resource")) {
					int tokenNumber = hex.getAsJsonObject().getAsJsonPrimitive("number").getAsInt();
					switch(hex.getAsJsonObject().getAsJsonPrimitive("resource").getAsString()) {
					case "wood":
						newHex = new Hex(HexType.WOOD, tokenNumber);
						break;
					case "brick":
						newHex = new Hex(HexType.BRICK, tokenNumber);
						break;
					case "sheep":
						newHex = new Hex(HexType.SHEEP, tokenNumber);
						break;
					case "wheat":
						newHex = new Hex(HexType.WHEAT, tokenNumber);
						break;
					case "ore":
						newHex = new Hex(HexType.ORE, tokenNumber);
						break;
					default:
						newHex = new Hex(HexType.DESERT, -1);
						break;
					}
				} else { // desert
					newHex = new Hex(HexType.DESERT, -1);
				}
				JsonObject hexJsonLoc = hex.getAsJsonObject().getAsJsonObject("location");
				int locX = hexJsonLoc.getAsJsonPrimitive("x").getAsInt();
				int locY = hexJsonLoc.getAsJsonPrimitive("y").getAsInt();
				HexLocation hexLoc = new HexLocation(locX, locY);
				
				hexes.setHex(hexLoc, newHex);
			}
			
			// Build out the roads from the JSON
			JsonArray jsonRoads = jsonMap.getAsJsonArray("roads");
			for (JsonElement road : jsonRoads) {
				int ownerIndex = road.getAsJsonObject().getAsJsonPrimitive("owner").getAsInt();
				Piece newRoad = new Piece(PieceType.ROAD, ownerIndex);
				JsonObject jsonRoadLoc = road.getAsJsonObject().getAsJsonObject("location");
				int locX = jsonRoadLoc.getAsJsonPrimitive("x").getAsInt();
				int locY = jsonRoadLoc.getAsJsonPrimitive("y").getAsInt();
				HexLocation hexLoc = new HexLocation(locX, locY);
				EdgeLocation newEdgeLoc = null;
				switch(jsonRoadLoc.getAsJsonPrimitive("direction").getAsString()) {
				case "NW":
					newEdgeLoc = new EdgeLocation(hexLoc, EdgeDirection.NorthWest);
					break;
				case "N":
					newEdgeLoc = new EdgeLocation(hexLoc, EdgeDirection.North);
					break;
				case "NE":
					newEdgeLoc = new EdgeLocation(hexLoc, EdgeDirection.NorthEast);
					break;
				case "SE":
					newEdgeLoc = new EdgeLocation(hexLoc, EdgeDirection.SouthEast);
					break;
				case "S":
					newEdgeLoc = new EdgeLocation(hexLoc, EdgeDirection.South);
					break;
				case "SW":
					newEdgeLoc = new EdgeLocation(hexLoc, EdgeDirection.SouthWest);
					break;
				}
				edges.setEdge(newEdgeLoc, newRoad);
				playerMap.addRoad(newEdgeLoc, ownerIndex);
			}

			// Build out the cities from the JSON
			JsonArray jsonCities = jsonMap.getAsJsonArray("cities");
			for (JsonElement city : jsonCities) {
				int ownerIndex = city.getAsJsonObject().getAsJsonPrimitive("owner").getAsInt();
				Piece newCity = new Piece(PieceType.CITY, ownerIndex);
				
				JsonObject jsonCityLoc = city.getAsJsonObject().getAsJsonObject("location");
				int locX = jsonCityLoc.getAsJsonPrimitive("x").getAsInt();
				int locY = jsonCityLoc.getAsJsonPrimitive("y").getAsInt();
				HexLocation hexLoc = new HexLocation(locX, locY);
				
				VertexLocation newVertexLoc = null;
				switch(jsonCityLoc.getAsJsonPrimitive("direction").getAsString()) {
				case "W":
					newVertexLoc = new VertexLocation(hexLoc, VertexDirection.West);
					break;
				case "NW":
					newVertexLoc = new VertexLocation(hexLoc, VertexDirection.NorthWest);
					break;
				case "NE":
					newVertexLoc = new VertexLocation(hexLoc, VertexDirection.NorthEast);
					break;
				case "E":
					newVertexLoc = new VertexLocation(hexLoc, VertexDirection.East);
					break;
				case "SE":
					newVertexLoc = new VertexLocation(hexLoc, VertexDirection.SouthEast);
					break;
				case "SW":
					newVertexLoc = new VertexLocation(hexLoc, VertexDirection.SouthWest);
					break;
				}
				vertices.setVertex(newVertexLoc, newCity);
				playerMap.addCity(newVertexLoc, ownerIndex);
			}
			
			// Build out the settlements from the JSON
			JsonArray jsonSettlements = jsonMap.getAsJsonArray("settlements");
			for (JsonElement settlement : jsonSettlements) {
				int ownerIndex = settlement.getAsJsonObject().getAsJsonPrimitive("owner").getAsInt();
				Piece newSettlement = new Piece(PieceType.SETTLEMENT, ownerIndex);
				
				JsonObject jsonSettlementLoc = settlement.getAsJsonObject().getAsJsonObject("location");
				int locX = jsonSettlementLoc.getAsJsonPrimitive("x").getAsInt();
				int locY = jsonSettlementLoc.getAsJsonPrimitive("y").getAsInt();
				HexLocation hexLoc = new HexLocation(locX, locY);
				
				VertexLocation newVertexLoc = null;
				switch(jsonSettlementLoc.getAsJsonPrimitive("direction").getAsString()) {
				case "W":
					newVertexLoc = new VertexLocation(hexLoc, VertexDirection.West);
					break;
				case "NW":
					newVertexLoc = new VertexLocation(hexLoc, VertexDirection.NorthWest);
					break;
				case "NE":
					newVertexLoc = new VertexLocation(hexLoc, VertexDirection.NorthEast);
					break;
				case "E":
					newVertexLoc = new VertexLocation(hexLoc, VertexDirection.East);
					break;
				case "SE":
					newVertexLoc = new VertexLocation(hexLoc, VertexDirection.SouthEast);
					break;
				case "SW":
					newVertexLoc = new VertexLocation(hexLoc, VertexDirection.SouthWest);
					break;
				}
				vertices.setVertex(newVertexLoc, newSettlement);
				playerMap.addSettlement(newVertexLoc, ownerIndex);
			}
			
			// Build out the ports from the JSON
			JsonArray jsonPorts = jsonMap.getAsJsonArray("ports");
			for (JsonElement port : jsonPorts) {
				int ratio = port.getAsJsonObject().getAsJsonPrimitive("ratio").getAsInt();
				PortType portType = null;
				if (ratio == 3) {
					portType = PortType.THREE;
				} else {
					switch(port.getAsJsonObject().getAsJsonPrimitive("resource").getAsString()) {
					case "wood":
						portType = PortType.WOOD;
						break;
					case "brick":
						portType = PortType.BRICK;
						break;
					case "sheep":
						portType = PortType.SHEEP;
						break;
					case "wheat":
						portType = PortType.WHEAT;
						break;
					case "ore":
						portType = PortType.ORE;
						break;
					}
				}
				EdgeDirection portDir = null;
				switch(port.getAsJsonObject().getAsJsonPrimitive("direction").getAsString()) {
				case "NW":
					portDir = EdgeDirection.NorthWest;
					break;
				case "N":
					portDir = EdgeDirection.North;
					break;
				case "NE":
					portDir = EdgeDirection.NorthEast;
					break;
				case "SE":
					portDir = EdgeDirection.SouthEast;
					break;
				case "S":
					portDir = EdgeDirection.South;
					break;
				case "SW":
					portDir = EdgeDirection.SouthWest;
					break;
				}
				
				JsonObject jsonPortLoc = port.getAsJsonObject().getAsJsonObject("location");
				int locX = jsonPortLoc.getAsJsonPrimitive("x").getAsInt();
				int locY = jsonPortLoc.getAsJsonPrimitive("y").getAsInt();
				HexLocation waterHexLoc = new HexLocation(locX, locY);
				
				WaterHex newWaterHex = new WaterHex(portType, portDir);
				hexes.setHex(waterHexLoc, newWaterHex);
			}
			
			// Place dat robber
			JsonObject jsonRobberLoc = jsonMap.getAsJsonObject("robber");
			int robberLocX = jsonRobberLoc.getAsJsonPrimitive("x").getAsInt();
			int robberLocY = jsonRobberLoc.getAsJsonPrimitive("y").getAsInt();
			HexLocation robberHex = new HexLocation(robberLocX, robberLocY);
			robberLocation = new RobberLocation(robberHex);
			
		} catch (InvalidTokenException e) {
		
		} catch(InvalidTypeException e) {
			
		}
		
		return new Map(hexes, vertices, edges, robberLocation, playerMap);
	}
	
	/**
	 * Deserializes the players from the game JSON
	 * @param jsonPlayers Takes in the player JSON that has been separated from the GameModel JSON
	 * @return returns a GamePlayers object that has been successfully deserialized
	 */
	public GamePlayers deserializePlayers(JsonArray jsonPlayers) {
		GamePlayers players = new GamePlayers();
		
		for (JsonElement player : jsonPlayers) {
			if (!player.isJsonNull()) {
				// Resources
				HashMap<ResourceType, Integer> playerResources = new HashMap<ResourceType, Integer>();
				JsonObject playerObj = player.getAsJsonObject();
				JsonObject resources = playerObj.getAsJsonObject("resources");
				int brickCount = resources.getAsJsonPrimitive("brick").getAsInt();
				playerResources.put(ResourceType.BRICK, brickCount);
				int woodCount = resources.getAsJsonPrimitive("wood").getAsInt();
				playerResources.put(ResourceType.WOOD, woodCount);
				int sheepCount = resources.getAsJsonPrimitive("sheep").getAsInt();
				playerResources.put(ResourceType.SHEEP, sheepCount);
				int wheatCount = resources.getAsJsonPrimitive("wheat").getAsInt();
				playerResources.put(ResourceType.WHEAT, wheatCount);
				int oreCount = resources.getAsJsonPrimitive("ore").getAsInt();
				playerResources.put(ResourceType.ORE, oreCount);
				
				// Old Dev Cards
				HashMap<DevCardType, Integer> playerOldDevCards = new HashMap<DevCardType, Integer>();
				JsonObject oldDevCards = playerObj.getAsJsonObject("oldDevCards");
				int oldYearOfPlentyCount = oldDevCards.getAsJsonPrimitive("yearOfPlenty").getAsInt();
				playerOldDevCards.put(DevCardType.YEAR_OF_PLENTY, oldYearOfPlentyCount);
				int oldMonopolyCount = oldDevCards.getAsJsonPrimitive("monopoly").getAsInt();
				playerOldDevCards.put(DevCardType.MONOPOLY, oldMonopolyCount);
				int oldSoldierCount = oldDevCards.getAsJsonPrimitive("soldier").getAsInt();
				playerOldDevCards.put(DevCardType.SOLDIER, oldSoldierCount);
				int oldRoadBuildingCount = oldDevCards.getAsJsonPrimitive("roadBuilding").getAsInt();
				playerOldDevCards.put(DevCardType.ROAD_BUILD, oldRoadBuildingCount);
				int oldMonumentCount = oldDevCards.getAsJsonPrimitive("monument").getAsInt();
				playerOldDevCards.put(DevCardType.MONUMENT, oldMonumentCount);
				
				// New Dev Cards
				HashMap<DevCardType, Integer> playerNewDevCards = new HashMap<DevCardType, Integer>();
				JsonObject newDevCards = playerObj.getAsJsonObject("newDevCards");
				int newYearOfPlentyCount = newDevCards.getAsJsonPrimitive("yearOfPlenty").getAsInt();
				playerNewDevCards.put(DevCardType.YEAR_OF_PLENTY, newYearOfPlentyCount);
				int newMonopolyCount = newDevCards.getAsJsonPrimitive("monopoly").getAsInt();
				playerNewDevCards.put(DevCardType.MONOPOLY, newMonopolyCount);
				int newSoldierCount = newDevCards.getAsJsonPrimitive("soldier").getAsInt();
				playerNewDevCards.put(DevCardType.SOLDIER, newSoldierCount);
				int newRoadBuildingCount = newDevCards.getAsJsonPrimitive("roadBuilding").getAsInt();
				playerNewDevCards.put(DevCardType.ROAD_BUILD, newRoadBuildingCount);
				int newMonumentCount = newDevCards.getAsJsonPrimitive("monument").getAsInt();
				playerNewDevCards.put(DevCardType.MONUMENT, newMonumentCount);
				
				int playerRoads = playerObj.getAsJsonPrimitive("roads").getAsInt();
				int playerCities = playerObj.getAsJsonPrimitive("cities").getAsInt();
				int playerSettlements = playerObj.getAsJsonPrimitive("settlements").getAsInt();
				int playerSoldiers = playerObj.getAsJsonPrimitive("soldiers").getAsInt();
				int playerVictoryPoints = playerObj.getAsJsonPrimitive("victoryPoints").getAsInt();
				int playerMonuments = playerObj.getAsJsonPrimitive("monuments").getAsInt();
				int playerID = playerObj.getAsJsonPrimitive("playerID").getAsInt();
				int playerIndex = playerObj.getAsJsonPrimitive("playerIndex").getAsInt();
				String playerName = playerObj.getAsJsonPrimitive("name").getAsString();
				CatanColor playerColor = null;
				
				switch(playerObj.getAsJsonPrimitive("color").getAsString()) {
				case "red":
					playerColor = CatanColor.RED;
					break;
				case "orange":
					playerColor = CatanColor.ORANGE;
					break;
				case "yellow":
					playerColor = CatanColor.YELLOW;
					break;
				case "blue":
					playerColor = CatanColor.BLUE;
					break;
				case "green":
					playerColor = CatanColor.GREEN;
					break;
				case "purple":
					playerColor = CatanColor.PURPLE;
					break;
				case "puce":
					playerColor = CatanColor.PUCE;
					break;
				case "white":
					playerColor = CatanColor.WHITE;
					break;
				case "brown":
					playerColor = CatanColor.BROWN;
					break;
				}
				
				try {
					players.addPlayer(new Player(playerResources, playerOldDevCards, playerNewDevCards,
							playerRoads, playerCities, playerSettlements, playerSoldiers, playerVictoryPoints,
							playerMonuments, playerID, playerName, playerColor, playerIndex, false, false));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return players;
	}
	
	/**
	 * Deserializes the log from the game JSON
	 * @param jsonLog Takes in the log JSON that has been separated from the GameModel JSON
	 * @return returns a GameLog object that has been successfully deserialized
	 */
	public GameLog deserializeLog(JsonObject jsonLog) {
		ArrayList<Message> messages = new ArrayList<Message>();
		JsonArray jsonLines = jsonLog.getAsJsonArray("lines");
		for (JsonElement line : jsonLines) {
			String source = line.getAsJsonObject().getAsJsonPrimitive("source").getAsString();
			String message = line.getAsJsonObject().getAsJsonPrimitive("message").getAsString();
			messages.add(new Message(source, message));
		}
		return new GameLog(messages);
	}
	
	/**
	 * Deserializes the chat from the game JSON
	 * @param jsonChat Takes in the chat JSON that has been separated from the GameModel JSON
	 * @return returns a GameChat object that has been successfully deserialized
	 */
	public GameChat deserializeChat(JsonObject jsonChat) {
		ArrayList<Message> messages = new ArrayList<Message>();
		JsonArray jsonLines = jsonChat.getAsJsonArray("lines");
		for (JsonElement line : jsonLines) {
			String source = line.getAsJsonObject().getAsJsonPrimitive("source").getAsString();
			String message = line.getAsJsonObject().getAsJsonPrimitive("message").getAsString();
			messages.add(new Message(source, message));
		}
		return new GameChat(messages);
	}
	
	/**
	 * Deserializes the bank from the game JSON
	 * @param jsonBank Takes in the bank JSON that has been separated from the GameModel JSON
	 * @return returns a Bank object that has been successfully deserialized
	 */
	public Bank deserializeBank(JsonObject jsonBank) {
		
		HashMap<ResourceType, Integer> bankResources = new HashMap<ResourceType, Integer>();
		int brickCount = jsonBank.getAsJsonPrimitive("brick").getAsInt();
		bankResources.put(ResourceType.BRICK, brickCount);
		int woodCount = jsonBank.getAsJsonPrimitive("wood").getAsInt();
		bankResources.put(ResourceType.WOOD, woodCount);
		int sheepCount = jsonBank.getAsJsonPrimitive("sheep").getAsInt();
		bankResources.put(ResourceType.SHEEP, sheepCount);
		int wheatCount = jsonBank.getAsJsonPrimitive("wheat").getAsInt();
		bankResources.put(ResourceType.WHEAT, wheatCount);
		int oreCount = jsonBank.getAsJsonPrimitive("ore").getAsInt();
		bankResources.put(ResourceType.ORE, oreCount);
		
		return new Bank(bankResources);
	}
	
	public OfferTrade deserializeOfferTrade(JsonObject jsonOfferTrade) {
		int sender = jsonOfferTrade.getAsJsonPrimitive("sender").getAsInt();
		int receiver = jsonOfferTrade.getAsJsonPrimitive("receiver").getAsInt();
		JsonObject offer = jsonOfferTrade.getAsJsonObject("offer");
		int brick = offer.getAsJsonPrimitive("brick").getAsInt();
		int sheep = offer.getAsJsonPrimitive("sheep").getAsInt();
		int ore = offer.getAsJsonPrimitive("ore").getAsInt();
		int wheat = offer.getAsJsonPrimitive("wheat").getAsInt();
		int wood = offer.getAsJsonPrimitive("wood").getAsInt();
		return new OfferTrade(sender, receiver, brick, sheep, ore, wheat, wood);
	}
	
	/**
	 * Gets the currentTurn number from the TurnTracker portion of the JSON
	 * @param jsonTurnTracker Takes in the turnTracker JSON that has been separated from the GameModel JSON
	 */
	public void deserializeTurnTracker(JsonObject jsonTurnTracker) {
		// All we pull from turnTracker at the moment is who's turn it is
		desCurrentTurn = jsonTurnTracker.getAsJsonPrimitive("currentTurn").getAsInt();
		desCurrentState = jsonTurnTracker.getAsJsonPrimitive("status").getAsString();
		desHasPlayedDevCard = jsonTurnTracker.getAsJsonPrimitive("hasPlayedDevCard").getAsBoolean();
		try {
			desLongestRoad = jsonTurnTracker.getAsJsonPrimitive("longestRoad").getAsInt();
		} catch (Exception e) {
			desLongestRoad = -1; // This is an optional value, the default value is -1
		}
	}
	
	/**
	 * Parses a list of games and returns game objects in a list that are usable by the client. The Join Game controller uses this.
	 * @param gamesListString
	 * @return A list of GameInfo objects
	 */
	public GameInfo[] deserializeGamesList(String gamesListString) {
		JsonParser parser = new JsonParser();
		//add a name to the array so I can make it a json array
		gamesListString = "{\"games\":".concat(gamesListString) + "}";
		JsonObject gameModelJson = parser.parse(gamesListString).getAsJsonObject();
		JsonArray games = gameModelJson.getAsJsonArray("games");
		GameInfo[] gameList = new GameInfo[games.size()];
		//for each json game
		for(int i = 0; i < games.size(); i++) {
			//build a json object
			JsonObject jsonGame = games.get(i).getAsJsonObject();
			GameInfo game = new GameInfo();
			game.setTitle(jsonGame.get("title").getAsString());
			game.setId(jsonGame.get("id").getAsInt());
			//split players into a json array
			JsonArray players = jsonGame.getAsJsonArray("players");
			//for each player
			for(int j = 0; j < players.size(); j++) {
				//build a json player and add it to a list for the game info object
				JsonObject jsonPlayer = players.get(j).getAsJsonObject();
				PlayerInfo player = new PlayerInfo();
				//The player array might not have four players so skip any empty objects
				if(!jsonPlayer.has("id")) {
					continue;
				}
				
				player.setId(jsonPlayer.get("id").getAsInt());
				player.setName(jsonPlayer.get("name").getAsString());
				player.setColor(CatanColor.valueOf(jsonPlayer.get("color").getAsString().toUpperCase()));
				player.setPlayerIndex(j);
				game.addPlayer(player);
			}
			gameList[i] = game;
		}		
		return gameList;
	}
	
	public ACommand deserializeCommand(String json, IServerFacade facade) throws ServerException {
		JsonParser parser = new JsonParser();
		JsonObject jsonCommand = parser.parse(json).getAsJsonObject();
		
		String[] commandType = {
			jsonCommand.get("type").getAsJsonObject().get("path1").getAsString(),
			jsonCommand.get("type").getAsJsonObject().get("path2").getAsString()
		};
		
		//Gson gson = new GsonBuilder().create();
		//java.lang.reflect.Type typeOfHashMap = new TypeToken<HashMap<String, String>>() { }.getType();
		//HashMap<String, String> cookies = gson.fromJson(json, typeOfHashMap);
		
		HashMap<String, String> cookies = new HashMap<String, String>();
		if (jsonCommand.has("cookies")) {
			if(jsonCommand.get("cookies").getAsJsonObject().has("catan.user")) {
				cookies.put("catan.user", jsonCommand.get("cookies").getAsJsonObject().get("catan.user").getAsString());
			}
			if(jsonCommand.get("cookies").getAsJsonObject().has("catan.game")) {
				cookies.put("catan.game", jsonCommand.get("cookies").getAsJsonObject().get("catan.game").getAsString());
			}
		}
		
		ACommand command = CommandFactory.getInstance().buildCommand(
			commandType,
			jsonCommand.get("body").getAsString(),
			cookies,
			facade,
			jsonCommand.get("httpMethod").getAsString()
		);
		return command;
	}
}