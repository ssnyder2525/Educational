package shared.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.clientFacade.ClientFacade;
import shared.communication.proxy.CreateGameRequestParams;
import shared.communication.proxy.OfferTrade;
import shared.communication.proxy.RollNumber;
import shared.definitions.DevCardType;
import shared.definitions.GameState;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.RobberLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.models.cardClasses.Bank;
import shared.models.cardClasses.CardDeck;
import shared.models.cardClasses.InsufficientCardNumberException;
import shared.models.chatClasses.GameChat;
import shared.models.logClasses.GameLog;
import shared.models.mapClasses.Hex;
import shared.models.mapClasses.HexMap;
import shared.models.mapClasses.InvalidTokenException;
import shared.models.mapClasses.InvalidTypeException;
import shared.models.mapClasses.Map;
import shared.models.mapClasses.Piece;
import shared.models.mapClasses.VertexMap;
import shared.models.playerClasses.GamePlayers;
import shared.models.playerClasses.Player;
import shared.models.playerClasses.TurnManager;

/**
 * 
 * @author Stephen Snyder
 *
 */
public class Game extends Observable
{	
	/**The current state of this game*/
	GameState gameState;
	
	/**The map contains all information having to do with the board.*/
	Map map;
	
	/**The bank contains all resource cards that do not belong to a player.*/
	Bank bank;
	
	/**The cardDeck contains all the development cards not belonging to a player.*/
	CardDeck cardDeck;
	
	/**The game players object holds four player objects that represent four clients that will connect
	 * to the server.
	 */
	GamePlayers players;
	
	/**Stores the log for the game*/
	GameLog log;
	
	/**The game chat object stores and retrieves the history of the chat log between players.*/
	GameChat chat;
	
	OfferTrade offerTrade = null;
	
	/**The turn tracker manages trades between players*/
	TurnManager turnManager;
	
	/**The index of the winner of the game*/
	int winner = -1;
	
	/**The title of this game*/
	String title;
	
	/**Each game has a version ID so the server knows which JSON to return.*/
	int versionID;
	
	/** The id of this game */
	int id;
	
	int currPlayer = -1;  // Index of player (client)
	
	static int nextID = 1;
	
	
	public int getNextID() {
		int id = nextID;
		nextID ++;
		return id;
	}
	
	
// CONSTRUCTORS
	public Game()
	{
		this.gameState = GameState.LOGIN;
		this.map = new Map();
		this.bank = new Bank();
		this.cardDeck = new CardDeck();
		this.players = new GamePlayers();
		this.log = new GameLog();
		this.chat = new GameChat();
		this.turnManager = new TurnManager(map, bank, cardDeck, players, log, chat, -1);
	}
	
	
	public Game(CreateGameRequestParams params)
	{
		this.gameState = GameState.LOGIN;
		try {
			this.map = new Map(
				params.randomTiles,
				params.randomNumbers,
				params.randomPorts
			);
		} catch (InvalidTokenException e) { // TODO: something more graceful than this
			e.printStackTrace();
		} catch (InvalidTypeException e) {
			e.printStackTrace();
		}
		this.bank = new Bank();
		this.cardDeck = new CardDeck();
		this.players = new GamePlayers();
		this.log = new GameLog();
		this.chat = new GameChat();
		this.turnManager = new TurnManager(map, bank, cardDeck, players, log, chat, -1);
		this.title = params.name;
	}
	
	
	public Game(Map map, Bank bank, CardDeck cardDeck, GamePlayers players, GameLog log, GameChat chat, 
			int currentTurn, boolean hasPlayedDevCard, int winner, int longestRoad) 
	{
		this.map = map;
		this.bank = bank;
		this.cardDeck = cardDeck;
		this.players = players;
		this.log = log;
		this.chat = chat;
		this.turnManager = new TurnManager(map, bank, cardDeck, players, log, chat, longestRoad);
		this.turnManager.setCurrentTurn(currentTurn);
		this.turnManager.setHasPlayedDevCard(hasPlayedDevCard);
	}
	
	
	
// GETTERS
	public GameState getGameState()
	{
		return this.gameState;
	}
	
	public Hex getHex(HexLocation loc) {
		return this.map.getHex(loc);
	}
	
	
	public Piece getEdge(EdgeLocation loc) {
		return this.map.getEdge(loc);
	}
	
	
	public Piece getVertex(VertexLocation loc) {
		return this.map.getVertex(loc);
	}
	
	
	public RobberLocation getRobberLocation() {
		return this.map.getRobberLocation();
	}
	
	public int getMaritimeTradeRatio(int playerIndex, ResourceType type) {
		return turnManager.getMaritimeTradeRatio(playerIndex, type);
	}
	
	public Bank getBank() {
		return bank;
	}
	
	public CardDeck getDeck() {
		return cardDeck;
	}
	
	public GamePlayers getPlayers() {
		return players;
	}
	
	public int getWinner() {
		return this.winner;
	}
	
	public TurnManager getTurnManager() {
		return turnManager;
	}
	
	public GameChat getGameChat() {
		return chat;
	}
	
	public GameLog getGameLog() {
		return log;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Map getMap() {
		return this.map;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the version ID so the poller and proxy can request the correct model JSON.
	 */
	public int getVersionID() {
		return versionID;
	}
	
	public void incrementVersionID() {
		versionID++;
	}
	
	
	public int getLongestRoad() {
		return this.turnManager.getLongestRoad();
	}
	
//SETTERS
	public void setGameState(GameState state) {
		this.gameState = state;
	}
	
	public void setGameChat(GameChat chat) {
		this.chat = chat;
	}
	
	public void setGameLog(GameLog log) {
		this.log = log;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	


// OBSERVER
	@Override
	public void addObserver(Observer o)
	{
		super.addObserver(o);
	}
	
	@Override
	public void deleteObserver(Observer o) {
		super.deleteObserver(o);
	}

	

// Public METHODS
	/*
	 * method to move the robber.
	 */
	public ArrayList<Piece> placeRobber (HexLocation hexLoc)
	{
		return this.map.placeRobber(hexLoc);
	}
	
	
	/**
	 * Update the game from a collection of parameters deserialized from a json object.
	 * @param map
	 * @param bank
	 * @param cardDeck
	 * @param players
	 * @param log
	 * @param chat
	 * @param offerTrade
	 * @param currentTurn
	 * @param currentState
	 * @param hasPlayedDevCard
	 * @param winner
	 * @param longestRoad
	 */
	public void update(Map map, Bank bank, CardDeck cardDeck, GamePlayers players, GameLog log, GameChat chat, 
			OfferTrade offerTrade, int currentTurn, String currentState, boolean hasPlayedDevCard, int winner, int longestRoad) {
		this.map = map;
		this.bank = bank;
		this.cardDeck = cardDeck;
		this.players = players;
		this.currPlayer = ClientFacade.getInstance().getUserData().getPlayerIndex();
		this.log = log;
		this.chat = chat;
		this.offerTrade = offerTrade;
		this.turnManager = new TurnManager(map, bank, cardDeck, players, log, chat, longestRoad);
		this.turnManager.setCurrentTurn(currentTurn);
		this.turnManager.setHasPlayedDevCard(hasPlayedDevCard);
		updateState(currentState);
		this.setChanged();
		this.notifyObservers();
	}
	
	public void saveGame(Map map, Bank bank, CardDeck cardDeck, GamePlayers players, GameLog log, GameChat chat, 
			OfferTrade offerTrade, int currentTurn, String currentState, boolean hasPlayedDevCard, int winner, int longestRoad) {
		this.map = map;
		this.bank = bank;
		this.cardDeck = cardDeck;
		this.players = players;
		this.currPlayer = 0;
		this.log = log;
		this.chat = chat;
		this.offerTrade = offerTrade;
		this.turnManager = new TurnManager(map, bank, cardDeck, players, log, chat, longestRoad);
		if (players.getNumberOfPlayers() == 4) {
			this.turnManager.setCurrentTurn(currentTurn);
			this.turnManager.setHasPlayedDevCard(hasPlayedDevCard);
		}
		updateState(currentState);
		this.setChanged();
		this.notifyObservers();
	}
	
	

	/**
	 * Use for testing the Server Poller. Removes the need for the ClientFacade
	 * 
	 * @param map
	 * @param bank
	 * @param cardDeck
	 * @param players
	 * @param log
	 * @param chat
	 * @param currentTurn
	 * @param currentState
	 * @param hasPlayedDevCard
	 * @param winner
	 */
	public void updateForTest(Map map, Bank bank, CardDeck cardDeck, GamePlayers players, GameLog log, GameChat chat, 
			OfferTrade offerTrade, int currentTurn, String currentState, boolean hasPlayedDevCard, int winner, int longestRoad) {
		this.map = map;
		this.bank = bank;
		this.cardDeck = cardDeck;
		this.players = players;
		this.log = log;
		this.chat = chat;
		this.offerTrade = offerTrade;
		this.turnManager = new TurnManager(map, bank, cardDeck, players, log, chat, longestRoad);
		this.turnManager.setCurrentTurn(currentTurn);
		this.turnManager.setHasPlayedDevCard(hasPlayedDevCard);
		this.setChanged();
		this.notifyObservers();
	}
	
	
	// This is not done!!! -- Implement all the other possible states
	/**
	 * Intelligently calculates the current state (based on the new model)
	 * 
	 * @param currentState
	 */
	public void updateState(String currentState) {
		
		switch (currentState) 
		{ 	// TODO: REMAINING -> OUTDATED, TRADEOFFER, TRADEACCEPT // TODO: not sure how these work // TODO: Also I need a way to get the currPlayer (the client's player index)
			case "Rolling":
				this.gameState = this.isTurn(this.currPlayer) ? GameState.ROLLING : GameState.NOTMYTURN;
				break;
			case "Robbing":
				this.gameState = this.isTurn(this.currPlayer) ? GameState.ROBBER : GameState.NOTMYTURN;
				break;
			case "Playing":
				if (this.winner != -1)
				{
					this.gameState = GameState.ENDOFGAME;
				}
				else
				{
					if (this.isTurn(currPlayer))
					{
						this.gameState = GameState.MYTURN;
					}
					else
					{
						this.gameState = GameState.NOTMYTURN;
					}
				}
				break;
			case "Discarding":
				this.gameState = GameState.DISCARD;
				break;
			case "FirstRound":
				this.gameState = this.isTurn(currPlayer) ? GameState.SETUP1 : GameState.NOTMYTURN;
				break;
			case "SecondRound":
				this.gameState = this.isTurn(currPlayer) ? GameState.SETUP2 : GameState.NOTMYTURN;
				break;
			default:
				break;
		}
	}
	
	/**
	 * returns if the specified player index contains the player whose turn it is.
	 * @param playerIndex
	 * @return
	 */
	public boolean isTurn(int playerIndex) {
		return turnManager.isTurn(playerIndex);
	}
	
	
	/**
	 * If the specified player can roll the dice, do so
	 * @exception invalidPlayerID if the player id does not match an existing player.
	 * @return The number rolled or 0 if the player does not have permission to roll the dice.
	 */
	public int rollDice(int playerID) {
		return turnManager.rollDice();
	}

	
	/**
	 * Trades a player's resources for a new road on the map. It must connect with another of the player's
	 * roads, settlements, or cities.
	 * @throws InsufficientCardNumberException 
	 * @throws InvalidTypeException 
	 * @exception invalidPlayerID if the player id does not match an existing player.
	 */
	public void buildRoad(int playerIndex, EdgeLocation loc, boolean free) throws InsufficientCardNumberException, InvalidTypeException {
		turnManager.buildRoad(playerIndex, loc, free);
	}
	
	
	/**
	 * Trades a player's resources for a new settlement on the map. The player must have a road leading to the spot wanted.
	 * The selected place to build must also be at least two building spots away from any other settlement.
	 * @throws InsufficientCardNumberException 
	 * @throws InvalidTypeException 
	 * @exception invalidPlayerID if the player id does not match an existing player.
	 */
	public void buildSettlement(int currPlayer, VertexLocation loc, boolean free) throws InsufficientCardNumberException, InvalidTypeException {
		loc = loc.getNormalizedLocation();
		turnManager.buildSettlement(currPlayer, loc, free);
		if (this.gameState == GameState.SETUP2) {
			HexLocation hexLoc = loc.getHexLoc();
			try {
				this.addResourceTypeFromHexType(this.map.getHexType(hexLoc), currPlayer);
			} catch (IndexOutOfBoundsException e) { }
			try { 
				this.addResourceTypeFromHexType(this.map.getHexType(hexLoc.getNeighborLoc(EdgeDirection.North)), currPlayer);
			} catch (IndexOutOfBoundsException e ) { }
			switch (loc.getDir()) {
				case NorthEast:
					try {
						this.addResourceTypeFromHexType(this.map.getHexType(hexLoc.getNeighborLoc(EdgeDirection.NorthEast)), currPlayer);
					} catch (IndexOutOfBoundsException e) { }
					break;
				case NorthWest:
					try {
						this.addResourceTypeFromHexType(this.map.getHexType(hexLoc.getNeighborLoc(EdgeDirection.NorthWest)), currPlayer);
					} catch (IndexOutOfBoundsException e) { }
					break;
				default:
					throw new InvalidTypeException("This should never happen");
			}
		}
	}
	
	
	private void addResourceTypeFromHexType(HexType hexType, int currPlayer) {
		switch (hexType) {
		case BRICK:
			this.players.getPlayerByIndex(currPlayer).addResourceToHand(ResourceType.BRICK, 1);
			break;
		case ORE:
			this.players.getPlayerByIndex(currPlayer).addResourceToHand(ResourceType.ORE, 1);
			break;
		case SHEEP:
			this.players.getPlayerByIndex(currPlayer).addResourceToHand(ResourceType.SHEEP, 1);
			break;
		case WHEAT:
			this.players.getPlayerByIndex(currPlayer).addResourceToHand(ResourceType.WHEAT, 1);
			break;
		case WOOD:
			this.players.getPlayerByIndex(currPlayer).addResourceToHand(ResourceType.WOOD, 1);
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * Trades a player's resources for a new city on the map. The player must build it in place of an existing settlement.
	 * @throws InsufficientCardNumberException 
	 * @throws InvalidTypeException 
	 * @exception invalidPlayerID if the player id does not match an existing player.
	 */
	public void buildCity(int currPlayer, VertexLocation loc) throws InsufficientCardNumberException, InvalidTypeException {
		turnManager.buildCity(currPlayer, loc);
	}
	
	
	/**
	 * Trades a player's resources for a development card
	 * @throws InsufficientCardNumberException 
	 * @exception invalidPlayerID if the player id does not match an existing player.
	 */
	public void buyDevelopmentCard(int currPlayer) throws InsufficientCardNumberException {
		turnManager.buyDevCard(currPlayer);
	}
	
	
	//play dev card methods
	public void playSoldierCard(int currPlayer, HexLocation hexLoc, int victimIndex) {
		turnManager.playSoldierCard(currPlayer, hexLoc, victimIndex);
	}
	
	public void playYearOfPlentyCard(int currPlayer, ResourceType type1, ResourceType type2) {
		turnManager.playYearOfPlentyCard(currPlayer, type1, type2);
	}
	
	public void playRoadBuildingCard(int currPlayer, EdgeLocation loc, EdgeLocation loc2) throws InvalidTypeException {
		turnManager.playRoadBuildingCard(currPlayer, loc, loc2);
	}
	
	public void playMonumentCard(int currPlayer) {
		turnManager.playMonumentCard(currPlayer);
	}
	
	public void playMonopolyCard(int currPlayer, ResourceType type) {
		turnManager.playMonopolyCard(currPlayer, type);
	}
	
	public void robPlayer(int robber, int robbed) {
		turnManager.robPlayer(robber, robbed);
	}
	
	public void discard(int playerIndex, ResourceType[] cards) throws InsufficientCardNumberException {
		turnManager.discard(playerIndex, cards);
	}
	
	
	/**
	 * Allows a player to trade int resources with the bank. If the player has built on a port, benefits may apply.
	 * @throws InsufficientCardNumberException 
	 */
	public void tradeResourcesWithBank(int playerID, int numberToTrade, ResourceType tradeIn, ResourceType tradeOut) throws InsufficientCardNumberException {
		switch(numberToTrade){
		case 4:
			turnManager.getTradeManager().tradeFour(playerID, tradeIn, tradeOut);
			bank.AddResourceCards(tradeIn, 4);
			bank.takeResourceCards(tradeOut, 1);
			break;
		case 3:
			turnManager.getTradeManager().tradeThreeWithPort(playerID, tradeIn, tradeOut);
			bank.AddResourceCards(tradeIn, 3);
			bank.takeResourceCards(tradeOut, 1);
			break;
		case 2:
			turnManager.getTradeManager().tradeTwoWithPort(playerID, tradeIn, tradeOut);
			bank.AddResourceCards(tradeIn, 2);
			bank.takeResourceCards(tradeOut, 1);
			break;
		}
	}
	
	
	/**
	 * Allows a player to offer an exchange of resources to one or more other players
	 * @throws InsufficientCardNumberException 
	 * @exception invalidPlayerID if the player id does not match an existing player.
	 */
	public void offerATrade(int playerID, List<Integer> playersToOfferTo, HashMap<ResourceType, Integer> out, HashMap<ResourceType, Integer> in) throws InsufficientCardNumberException{
		
	}
	
	/**
	 * returns a trade offer that has been made and is currently open.
	 * @return
	 */
	public OfferTrade getOfferTrade() {
		return offerTrade;
	}
	

//***********************************************************************************************************************************
//														Can Functions
//***********************************************************************************************************************************
	

	public boolean CanDiscardCards(ResourceType type, int num, int currPlayer) {
		return turnManager.CanDiscardCards(type, num, currPlayer);
	}
	
	
	public boolean CanRollNumber(int currPlayer) {
		return turnManager.CanRollNumber(currPlayer);
	}
	
	
	public boolean CanBuildRoad(EdgeLocation edgeLoc, int currPlayer, boolean isFree, boolean isSetup) {
		if ((this.gameState == GameState.SETUP1 && this.turnManager.getPlayers().getPlayerByIndex(currPlayer).getRoads() != 15) ||
				(this.gameState == GameState.SETUP2 && this.turnManager.getPlayers().getPlayerByIndex(currPlayer).getRoads() != 14)) {
			return false;
		}
		return turnManager.CanBuildRoad(edgeLoc, currPlayer, isFree, isSetup);
	}
	
	public boolean CanBuildSettlement(VertexLocation vertLoc, int currPlayer, boolean isFree, boolean isSetup) {
		return turnManager.CanBuildSettlement(vertLoc, currPlayer, isFree, isSetup);
	}
	
	
	public boolean CanBuildCity(VertexLocation vertLoc, int currPlayer) {
		return turnManager.CanBuildCity(vertLoc, currPlayer);
	}
	
	
	public boolean CanOfferTrade(int traderIndex, int tradeeIndex, HashMap<ResourceType, Integer> out, HashMap<ResourceType, Integer> in) {
		return turnManager.CanOfferTrade(traderIndex, tradeeIndex, out, in);
	}
	
	
	public boolean CanMaritimeTrade(int currPlayer, ResourceType type) {
		return turnManager.CanMaritimeTrade(currPlayer, type);
	}
	
	
	public boolean CanFinishTurn() {
		return turnManager.CanFinishTurn();
	}
	
	
	public boolean CanBuyDevCard(int currPlayer) {
		return turnManager.CanBuyDevCard(currPlayer);
	}
	
	
	public boolean CanPlayDevCard(DevCardType card, int currPlayer) {
		return turnManager.CanPlayDevCard(card, currPlayer);
	}
	
	
	public boolean CanPlaceRobber(HexLocation hexLoc, int currPlayer) {
		return turnManager.CanPlaceRobber(hexLoc, currPlayer);
	}
	
	
	public boolean CanSendChat() {
		return turnManager.CanSendChat();
	}
	
	
	public boolean CanAcceptTrade(int traderIndex, int tradeeIndex, HashMap<ResourceType, Integer> out, HashMap<ResourceType, Integer> in) {
		return turnManager.CanAcceptTrade(traderIndex, tradeeIndex, out, in);
	}

	public Boolean settlementTouchesPlayerRoad(VertexLocation loc, int ownerID) {
		return this.map.settlementTouchesPlayerRoad(loc, ownerID);
	}

	/**
	 * This method will take in a roll and will assign resources
	 * @param rollNumber
	 */
	public void processRoll(RollNumber rollNumber) {
		int roll = rollNumber.roll;
		if (roll == 7) {
			boolean discarding = false;
			for (Player p : players.getPlayers()){
				if (p.getTotalResources() > 7) {
					discarding = true;
					p.setDiscarding(true);
				}
			}
			if (discarding) {
				this.gameState = GameState.DISCARD;
				return;
			}
			this.gameState = GameState.ROBBER;
		} else {
			// Find all hexes with the token matching the number rolled
			List<HexLocation> hexes = new ArrayList<HexLocation>();
			HexMap hMap = map.getHexMap();
			for (int x = 0; x <= 2; ++x) {
				
				int maxY = 2 - x;			
				for (int y = -2; y <= maxY; ++y) {
					HexLocation loc = new HexLocation(x, y);
					Hex h = hMap.getHex(loc); 
					if (h.getToken() == roll && !map.getRobberLocation().getHexLoc().equals(loc)) {
						hexes.add(loc);
					}
				}
				if (x != 0) {
					int minY = x - 2;
					for (int y = minY; y <= 2; ++y) {
						HexLocation loc = new HexLocation(-x, y);
						Hex h = hMap.getHex(loc); 
						if (h.getToken() == roll && !map.getRobberLocation().getHexLoc().equals(loc)) {
							hexes.add(loc);
						}
					}
				}
			}
			// For each hex, find if there are any cities or settlements on it
			// TODO: If there are not enough of a certain resource to give to every players who should get one,
			// then no one gets that resource
			VertexMap vMap = map.getVertexMap();
			for (int i = 0; i < hexes.size(); i++) {
				for (VertexDirection dir : VertexDirection.values()) {
					try {
						PieceType p = vMap.getPiece(new VertexLocation(hexes.get(i), dir)).getType();
						if (p.equals(PieceType.SETTLEMENT)) {
							int pIndex = vMap.getPiece(new VertexLocation(hexes.get(i), dir)).getOwner();
							try {
								ResourceType r = ResourceType.valueOf(hMap.getHex(hexes.get(i)).getHexType().toString());
								if(bank.canRemove(r, 1)) {
									players.getPlayerByIndex(pIndex).addResourceToHand(r, 1);
									bank.takeResourceCards(r, 1);
								}
							} catch (Exception e) {
								
							}
						} else if (p.equals(PieceType.CITY)) {
							int pIndex = vMap.getPiece(new VertexLocation(hexes.get(i), dir)).getOwner();
							try {
								ResourceType r = ResourceType.valueOf(hMap.getHex(hexes.get(i)).getHexType().toString());
								if(bank.canRemove(r, 2)) {
									players.getPlayerByIndex(pIndex).addResourceToHand(r, 2);
									bank.takeResourceCards(r, 2);
								}
							} catch (Exception e) {
								
							}
						}
					} catch (IndexOutOfBoundsException e) {
					} 
				}
			}
			this.setGameState(GameState.MYTURN);
		}
	}


	public int getLargestArmy() {
		return this.getTurnManager().getLargestArmy();
	}
}
