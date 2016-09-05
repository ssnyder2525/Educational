package client.serverProxy;

import java.util.ArrayList;

import shared.communication.proxy.*;

/** ProxyInterface interface
 * 
 * @author Cody Burt
 *
 */
public interface ProxyInterface {

	public String getUserCookie();
	/**
	 * This function will call the server API at 
	 * user / login
	 * 
	 * @param Credentials object that contains the username and password
	 * @return JSON String of the format:
	 * name: String,
	 * password: String,
	 * playerID: Integer
	 * @throws Exception
	 */ 
	public String loginUser(Credentials credentials) throws Exception;
	
	/**
	 * This function will call the server API at
	 * user / register
	 * @param Credentials object that contains the username and password
	 * @return JSON String that indicates success/failure
	 * @throws Exception
	 */
	public String registerUser(Credentials credentials) throws Exception;
	
	/**
	 * This function will call the server API at
	 * games / list
	 * 
	 * @return JSON String that indicates success/failure
	 * @throws Exception
	 */
	public String getGamesList() throws Exception;
	
	/**
	 * This function will call the server API at
	 * games / create
	 * @param CreateGameRequestParams object that contains the name of 
	 * the game and settings for randomTiles, randomNumbers, and
	 * randomPorts
	 * @return JSON String that contains the game's title, id, and 
	 * a list of empty players
	 * @throws Exception
	 */
	public String createGame(CreateGameRequestParams params) throws Exception;
	
	/**
	 * This function will call the server API at
	 * games / join
	 * @param JoinGameRequestParams object that contains the id of the
	 * game the player wants to join and the color they want to be
	 * @return JSON String that indicates whether it was a success or
	 * failure
	 * @throws Exception
	 */
	public String joinGame(JoinGameRequestParams params) throws Exception;
	
	/**
	 * This function will call the server API at
	 * games / save
	 * @param SaveGameRequestParams object that contains the id of the
	 * game the player wants to join and file name they want to save it as
	 * @return JSON String that indicates whether it was a success or
	 * failure
	 * @throws Exception
	 */
	public String saveGame(SaveGameRequestParams saveGameRequest);
	
	/**
	 * This function will call the server API at
	 * games / load
	 * @param LoadGameRequestParams object that contains the file name 
	 * they want to load from
	 * @return JSON String that indicates whether it was a success or
	 * failure
	 * @throws Exception
	 */
	public String loadGame(LoadGameRequestParams loadGameRequest);
	
	/**
	 * This function will call the server API at
	 * game / model
	 * @param The version number of the current state
	 * @return JSON String that contains the current game state
	 * @throws Exception
	 */
	public String getGameModel(int versionNumber);
	
	/**
	 * This function will call the server API at
	 * game / reset
	 * @return JSON String that contains the current game state
	 * @throws Exception
	 */
	public String resetGame() throws Exception;
	
	/**
	 * This function will call the server API at
	 * game / commands
	 * @param ListOfCommands object that contains the desired
	 * commands to be executed
	 * @return JSON String that contains the client model after
	 * that list of commands have been executed
	 * @throws Exception
	 */
	public String executeGameCommands(ListOfCommands listOfCommands);
	
	/**
	 * This function will call the server API at
	 * game / commands
	 * @return JSON String that contains list of commands
	 * executed in the game
	 * @throws Exception
	 */
	public String getGameCommands();
	
	/**
	 * This function will call the server API at
	 * moves / sendChat
	 * @param SendChat object that contains the player index
	 * of the message sender and the message content
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String sendChat(SendChat sendChat) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / rollNumber
	 * @param RollNumber object that contains the player index
	 * and what number they rolled
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String rollNumber(RollNumber rollNumber) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / robPlayer
	 * @param RobPlayer object that contains the index of the
	 * player robbing, and the new location of the robber
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String robPlayer(RobPlayer robPlayer) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / finishTurn
	 * @param FinishTurn object that contains the player index
	 * that's ending their turn
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String finishTurn(FinishTurn finishTurn) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / buyDevCard
	 * @param BuyDevCard object that contains the player index
	 * buying the card
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String buyDevCard(BuyDevCard buyDevCard) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / Year_Of_Plenty
	 * @param YearOfPlenty object that contains the player index
	 * playing the card and the two resources they gain
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String yearOfPlenty(YearOfPlenty yearOfPlenty) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / Road_Building
	 * @param RoadBuilding object that contains the player index
	 * and the two locations they want to build roads
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String roadBuilding(RoadBuilding roadBuilding) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / Soldier
	 * @param SoldierMove object that contains the player index
	 * doing the robbing, the player index of the one they're
	 * robbing, and the new location of the robber
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String moveSoldier(SoldierMove soldierMove) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / Monopoly
	 * @param Monopoly object that contains the player index
	 * and the resource they will monopolize
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String playMonopolyCard(Monopoly monopoly) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / Monument
	 * @param MonumentMove object that contains the player index
	 * playing the monument card
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String playMonumentCard(MonumentMove monumentMove) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / buildRoad
	 * @param BuildRoad object that contains the player index
	 * building the road, the location where they want to
	 * build, and whether or not it's free or not
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String buildRoad(BuildRoad buildRoad) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / buildCity
	 * @param BuildCity object that contains the player index
	 * building the city and the location of the city
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String buildCity(BuildCity buildCity) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / buildSettlement
	 * @param BuildSettlement object that contains the player index
	 * building the settlement, the location, and whether it's free
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String buildSettlement(BuildSettlement buildSettlement) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / offerTrade
	 * @param OfferTrade object that contains the player index
	 * sending the offer, the player index receiving the offer,
	 * and the list resources offered and desired
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String offerTrade(OfferTrade offerTrade) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / acceptTrade
	 * @param AcceptTrade object that contains the player index
	 * responding to the trade and whether they accept or reject it
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String acceptTrade(AcceptTrade acceptTrade) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / maritimeTrade
	 * @param MaritimeTrade object that contains the player index
	 * of the player trading, the ratio they're trading at, the
	 * desired resource and the offered resource
	 * @return JSON String that contains the client model
	 * 
	 * @throws Exception
	 */
	public String maritimeTrade(MaritimeTrade maritimeTrade) throws Exception;
	
	/**
	 * This function will call the server API at
	 * moves / discardedCards
	 * @param DiscardedCards object that contains the player index
	 * discarding cards and the list of resources they're discarding
	 * @return JSON String that contains the client model
	 * @throws Exception
	 */
	public String discardCards(DiscardedCards discardedCards) throws Exception;
	
	/**
	 * This function will call the server API at
	 * util / changeLogLevel
	 * @param ChangeLogLevelRequest that contains the server's
	 * new log level
	 * @return JSON String that indicates whether it succeded
	 * @throws Exception
	 */
	public String changeLogLevel(ChangeLogLevelRequest logLevel);
	
	
	/**
	 * Adds an AI to the game the client has joined
	 * 
	 * @param aiType
	 * @return
	 */
	public String addAI(String aiType);
	
	/**
	 * Gets the list of available AI's from the server
	 * 
	 * @return
	 */
	public ArrayList<String> getListAI();
	
	
	
	
	
	
	
	
}
