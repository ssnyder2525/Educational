package client.serverProxy;

import java.util.ArrayList;

import shared.communication.proxy.AcceptTrade;
import shared.communication.proxy.BuildCity;
import shared.communication.proxy.BuildRoad;
import shared.communication.proxy.BuildSettlement;
import shared.communication.proxy.BuyDevCard;
import shared.communication.proxy.ChangeLogLevelRequest;
import shared.communication.proxy.CreateGameRequestParams;
import shared.communication.proxy.Credentials;
import shared.communication.proxy.DiscardedCards;
import shared.communication.proxy.FinishTurn;
import shared.communication.proxy.JoinGameRequestParams;
import shared.communication.proxy.ListOfCommands;
import shared.communication.proxy.LoadGameRequestParams;
import shared.communication.proxy.MaritimeTrade;
import shared.communication.proxy.Monopoly;
import shared.communication.proxy.MonumentMove;
import shared.communication.proxy.OfferTrade;
import shared.communication.proxy.RoadBuilding;
import shared.communication.proxy.RobPlayer;
import shared.communication.proxy.RollNumber;
import shared.communication.proxy.SaveGameRequestParams;
import shared.communication.proxy.SendChat;
import shared.communication.proxy.SoldierMove;
import shared.communication.proxy.YearOfPlenty;

/** FakeProxy class
 * 
 * @author Cody Burt
 *
 */
public class FakeProxy implements ProxyInterface {

	private String defaultGameModel  = "{   \"deck\": {     \"yearOfPlenty\": 2,     \"monopoly\": 2,     \"soldier\": 14,     \"roadBuilding\": 2,     \"monument\": 5   },   \"map\": {     \"hexes\": [       {         \"location\": {           \"x\": 0,           \"y\": -2         }       },       {         \"resource\": \"brick\",         \"location\": {           \"x\": 1,           \"y\": -2         },         \"number\": 4       },       {         \"resource\": \"wood\",         \"location\": {           \"x\": 2,           \"y\": -2         },         \"number\": 11       },       {         \"resource\": \"brick\",         \"location\": {           \"x\": -1,           \"y\": -1         },         \"number\": 8       },       {         \"resource\": \"wood\",         \"location\": {           \"x\": 0,           \"y\": -1         },         \"number\": 3       },       {         \"resource\": \"ore\",         \"location\": {           \"x\": 1,           \"y\": -1         },         \"number\": 9       },       {         \"resource\": \"sheep\",         \"location\": {           \"x\": 2,           \"y\": -1         },         \"number\": 12       },       {         \"resource\": \"ore\",         \"location\": {           \"x\": -2,           \"y\": 0         },         \"number\": 5       },       {         \"resource\": \"sheep\",         \"location\": {           \"x\": -1,           \"y\": 0         },         \"number\": 10       },       {         \"resource\": \"wheat\",         \"location\": {           \"x\": 0,           \"y\": 0         },         \"number\": 11       },       {         \"resource\": \"brick\",         \"location\": {           \"x\": 1,           \"y\": 0         },         \"number\": 5       },       {         \"resource\": \"wheat\",         \"location\": {           \"x\": 2,           \"y\": 0         },         \"number\": 6       },       {         \"resource\": \"wheat\",         \"location\": {           \"x\": -2,           \"y\": 1         },         \"number\": 2       },       {         \"resource\": \"sheep\",         \"location\": {           \"x\": -1,           \"y\": 1         },         \"number\": 9       },       {         \"resource\": \"wood\",         \"location\": {           \"x\": 0,           \"y\": 1         },         \"number\": 4       },       {         \"resource\": \"sheep\",         \"location\": {           \"x\": 1,           \"y\": 1         },         \"number\": 10       },       {         \"resource\": \"wood\",         \"location\": {           \"x\": -2,           \"y\": 2         },         \"number\": 6       },       {         \"resource\": \"ore\",         \"location\": {           \"x\": -1,           \"y\": 2         },         \"number\": 3       },       {         \"resource\": \"wheat\",         \"location\": {           \"x\": 0,           \"y\": 2         },         \"number\": 8       }     ],     \"roads\": [       {         \"owner\": 1,         \"location\": {           \"direction\": \"S\",           \"x\": -1,           \"y\": -1         }       },       {         \"owner\": 3,         \"location\": {           \"direction\": \"SW\",           \"x\": -1,           \"y\": 1         }       },       {         \"owner\": 3,         \"location\": {           \"direction\": \"SW\",           \"x\": 2,           \"y\": -2         }       },       {         \"owner\": 2,         \"location\": {           \"direction\": \"S\",           \"x\": 1,           \"y\": -1         }       },       {         \"owner\": 0,         \"location\": {           \"direction\": \"S\",           \"x\": 0,           \"y\": 1         }       },       {         \"owner\": 2,         \"location\": {           \"direction\": \"S\",           \"x\": 0,           \"y\": 0         }       },       {         \"owner\": 1,         \"location\": {           \"direction\": \"SW\",           \"x\": -2,           \"y\": 1         }       },       {         \"owner\": 0,         \"location\": {           \"direction\": \"SW\",           \"x\": 2,           \"y\": 0         }       }     ],     \"cities\": [],     \"settlements\": [       {         \"owner\": 3,         \"location\": {           \"direction\": \"SW\",           \"x\": -1,           \"y\": 1         }       },       {         \"owner\": 3,         \"location\": {           \"direction\": \"SE\",           \"x\": 1,           \"y\": -2         }       },       {         \"owner\": 2,         \"location\": {           \"direction\": \"SW\",           \"x\": 0,           \"y\": 0         }       },       {         \"owner\": 2,         \"location\": {           \"direction\": \"SW\",           \"x\": 1,           \"y\": -1         }       },       {         \"owner\": 1,         \"location\": {           \"direction\": \"SW\",           \"x\": -2,           \"y\": 1         }       },       {         \"owner\": 0,         \"location\": {           \"direction\": \"SE\",           \"x\": 0,           \"y\": 1         }       },       {         \"owner\": 1,         \"location\": {           \"direction\": \"SW\",           \"x\": -1,           \"y\": -1         }       },       {         \"owner\": 0,         \"location\": {           \"direction\": \"SW\",           \"x\": 2,           \"y\": 0         }       }     ],     \"radius\": 3,     \"ports\": [       {         \"ratio\": 2,         \"resource\": \"ore\",         \"direction\": \"S\",         \"location\": {           \"x\": 1,           \"y\": -3         }       },       {         \"ratio\": 3,         \"direction\": \"SW\",         \"location\": {           \"x\": 3,           \"y\": -3         }       },       {         \"ratio\": 3,         \"direction\": \"NW\",         \"location\": {           \"x\": 2,           \"y\": 1         }       },       {         \"ratio\": 2,         \"resource\": \"brick\",         \"direction\": \"NE\",         \"location\": {           \"x\": -2,           \"y\": 3         }       },       {         \"ratio\": 2,         \"resource\": \"wheat\",         \"direction\": \"S\",         \"location\": {           \"x\": -1,           \"y\": -2         }       },       {         \"ratio\": 2,         \"resource\": \"wood\",         \"direction\": \"NE\",         \"location\": {           \"x\": -3,           \"y\": 2         }       },       {         \"ratio\": 3,         \"direction\": \"SE\",         \"location\": {           \"x\": -3,           \"y\": 0         }       },       {         \"ratio\": 2,         \"resource\": \"sheep\",         \"direction\": \"NW\",         \"location\": {           \"x\": 3,           \"y\": -1         }       },       {         \"ratio\": 3,         \"direction\": \"N\",         \"location\": {           \"x\": 0,           \"y\": 3         }       }     ],     \"robber\": {       \"x\": 0,       \"y\": -2     }   },   \"players\": [     {       \"resources\": {         \"brick\": 0,         \"wood\": 1,         \"sheep\": 1,         \"wheat\": 1,         \"ore\": 0       },       \"oldDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"newDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"roads\": 13,       \"cities\": 4,       \"settlements\": 3,       \"soldiers\": 0,       \"victoryPoints\": 2,       \"monuments\": 0,       \"playedDevCard\": false,       \"discarded\": false,       \"playerID\": 0,       \"playerIndex\": 0,       \"name\": \"Sam\",       \"color\": \"red\"     },     {       \"resources\": {         \"brick\": 1,         \"wood\": 0,         \"sheep\": 1,         \"wheat\": 0,         \"ore\": 1       },       \"oldDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"newDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"roads\": 13,       \"cities\": 4,       \"settlements\": 3,       \"soldiers\": 0,       \"victoryPoints\": 2,       \"monuments\": 0,       \"playedDevCard\": false,       \"discarded\": false,       \"playerID\": 1,       \"playerIndex\": 1,       \"name\": \"Brooke\",       \"color\": \"blue\"     },     {       \"resources\": {         \"brick\": 0,         \"wood\": 1,         \"sheep\": 1,         \"wheat\": 1,         \"ore\": 0       },       \"oldDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"newDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"roads\": 13,       \"cities\": 4,       \"settlements\": 3,       \"soldiers\": 0,       \"victoryPoints\": 2,       \"monuments\": 0,       \"playedDevCard\": false,       \"discarded\": false,       \"playerID\": 10,       \"playerIndex\": 2,       \"name\": \"Pete\",       \"color\": \"red\"     },     {       \"resources\": {         \"brick\": 0,         \"wood\": 1,         \"sheep\": 1,         \"wheat\": 0,         \"ore\": 1       },       \"oldDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"newDevCards\": {         \"yearOfPlenty\": 0,         \"monopoly\": 0,         \"soldier\": 0,         \"roadBuilding\": 0,         \"monument\": 0       },       \"roads\": 13,       \"cities\": 4,       \"settlements\": 3,       \"soldiers\": 0,       \"victoryPoints\": 2,       \"monuments\": 0,       \"playedDevCard\": false,       \"discarded\": false,       \"playerID\": 11,       \"playerIndex\": 3,       \"name\": \"Mark\",       \"color\": \"green\"     }   ],   \"log\": {     \"lines\": [       {         \"source\": \"Sam\",         \"message\": \"Sam built a road\"       },       {         \"source\": \"Sam\",         \"message\": \"Sam built a settlement\"       },       {         \"source\": \"Sam\",         \"message\": \"Sam's turn just ended\"       },       {         \"source\": \"Brooke\",         \"message\": \"Brooke built a road\"       },       {         \"source\": \"Brooke\",         \"message\": \"Brooke built a settlement\"       },       {         \"source\": \"Brooke\",         \"message\": \"Brooke's turn just ended\"       },       {         \"source\": \"Pete\",         \"message\": \"Pete built a road\"       },       {         \"source\": \"Pete\",         \"message\": \"Pete built a settlement\"       },       {         \"source\": \"Pete\",         \"message\": \"Pete's turn just ended\"       },       {         \"source\": \"Mark\",         \"message\": \"Mark built a road\"       },       {         \"source\": \"Mark\",         \"message\": \"Mark built a settlement\"       },       {         \"source\": \"Mark\",         \"message\": \"Mark's turn just ended\"       },       {         \"source\": \"Mark\",         \"message\": \"Mark built a road\"       },       {         \"source\": \"Mark\",         \"message\": \"Mark built a settlement\"       },       {         \"source\": \"Mark\",         \"message\": \"Mark's turn just ended\"       },       {         \"source\": \"Pete\",         \"message\": \"Pete built a road\"       },       {         \"source\": \"Pete\",         \"message\": \"Pete built a settlement\"       },       {         \"source\": \"Pete\",         \"message\": \"Pete's turn just ended\"       },       {         \"source\": \"Brooke\",         \"message\": \"Brooke built a road\"       },       {         \"source\": \"Brooke\",         \"message\": \"Brooke built a settlement\"       },       {         \"source\": \"Brooke\",         \"message\": \"Brooke's turn just ended\"       },       {         \"source\": \"Sam\",         \"message\": \"Sam built a road\"       },       {         \"source\": \"Sam\",         \"message\": \"Sam built a settlement\"       },       {         \"source\": \"Sam\",         \"message\": \"Sam's turn just ended\"       }     ]   },   \"chat\": {     \"lines\": []   },   \"bank\": {     \"brick\": 23,     \"wood\": 21,     \"sheep\": 20,     \"wheat\": 22,     \"ore\": 22   },   \"turnTracker\": {     \"status\": \"Rolling\",     \"currentTurn\": 0,     \"longestRoad\": -1,     \"largestArmy\": -1   },   \"winner\": -1,   \"version\": 0}";
	
	public String getUserCookie() { return "{\"name\":\"Sam\",\"password\":\"sam\",\"playerID\":0}"; }
	/**
	 * This function will call the server API at 
	 * user / login
	 * 
	 * @param Credentials object that contains the username and password
	 * @return JSON String of the format:
	 * name: String,
	 * password: String,
	 * playerID: Integer
	 */ 
	public String loginUser(Credentials credentials){ 
		return "Success"; 
	}
	
	/**
	 * This function will call the server API at
	 * user / register
	 * @param Credentials object that contains the username and password
	 * @return JSON String that indicates success/failure
	 * 
	 */
	public String registerUser(Credentials credentials){ 
		return "Success"; 
	}
	
	/**
	 * This function will call the server API at
	 * games / list
	 * 
	 * @return List of games and the players
	 * 
	 */
	public String getGamesList(){ 
		String defaultGamesList = "[ { \"title\": \"Default Game\", \"id\": 0,  \"players\": [   {     \"color\": \"orange\",     \"name\": \"Sam\",     \"id\": 0   },   {     \"color\": \"blue\",     \"name\": \"Brooke\",     \"id\": 1   },   {     \"color\": \"red\",     \"name\": \"Pete\",     \"id\": 10   },   {     \"color\": \"green\",     \"name\": \"Mark\",     \"id\": 11   } ] }, { \"title\": \"AI Game\", \"id\": 1, \"players\": [   {     \"color\": \"orange\",     \"name\": \"Pete\",     \"id\": 10   },   {     \"color\": \"yellow\",     \"name\": \"Ken\",     \"id\": -2   },   {     \"color\": \"blue\",     \"name\": \"Steve\",     \"id\": -3   },   {     \"color\": \"white\",     \"name\": \"Hannah\",     \"id\": -4   } ] }, { \"title\": \"Empty Game\", \"id\": 2, \"players\": [   {     \"color\": \"orange\",     \"name\": \"Sam\",     \"id\": 0   },   {     \"color\": \"blue\",     \"name\": \"Brooke\",     \"id\": 1   },   {     \"color\": \"red\",     \"name\": \"Pete\",     \"id\": 10   },   {     \"color\": \"green\",     \"name\": \"Mark\",     \"id\": 11   } ] } ]";
		return defaultGamesList;
	}
	
	/**
	 * This function will call the server API at
	 * games / create
	 * @param CreateGameRequestParams object that contains the name of 
	 * the game and settings for randomTiles, randomNumbers, and
	 * randomPorts
	 * @return JSON String that contains the game's title, id, and 
	 * a list of empty players
	 */
	public String createGame(CreateGameRequestParams params) { 
		String defaultCreateGame = "{ \"title\": \"new game title\", \"id\": 3, \"players\": [ {}, {}, {}, {} ] }"; 
		return defaultCreateGame;
	}
	
	/**
	 * This function will call the server API at
	 * games / join
	 * @param JoinGameRequestParams object that contains the id of the
	 * game the player wants to join and the color they want to be
	 * @return JSON String that indicates whether it was a success or
	 * failure
	 */
	public String joinGame(JoinGameRequestParams params) {
		return "Success";
	}
	
	/**
	 * This function will call the server API at
	 * games / save
	 * @param SaveGameRequestParams object that contains the id of the
	 * game the player wants to join and file name they want to save it as
	 * @return JSON String that indicates whether it was a success or
	 * failure
	 */
	public String saveGame(SaveGameRequestParams saveGameRequest) {
		return "Success";
	}
	
	/**
	 * This function will call the server API at
	 * games / load
	 * @param LoadGameRequestParams object that contains the file name 
	 * they want to load from
	 * @return JSON String that indicates whether it was a success or
	 * failure
	 */
	public String loadGame(LoadGameRequestParams loadGameRequest) {
		return "Success";
	}
	
	/**
	 * This function will call the server API at
	 * game / model
	 * @param The version number of the current state
	 * @return JSON String that contains the current game state
	 */
	public String getGameModel(int versionNumber) {
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * game / reset
	 * @return JSON String that contains the current game state
	 */
	public String resetGame(){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * game / commands
	 * @param ListOfCommands object that contains the desired
	 * commands to be executed
	 * @return JSON String that contains the client model after
	 * that list of commands have been executed
	 */
	public String executeGameCommands(ListOfCommands listOfCommands){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * game / commands
	 * @return JSON String that contains list of commands
	 * executed in the game
	 */
	public String getGameCommands() {
		String defaultGameCommands = "[ { \"content\": \"hello... it's me\", \"type\": \"sendChat\", \"playerIndex\": 0 } ]";
		return defaultGameCommands;
	}
	
	/**
	 * This function will call the server API at
	 * moves / sendChat
	 * @param SendChat object that contains the player index
	 * of the message sender and the message content
	 * @return JSON String that contains the client model
	 */
	public String sendChat(SendChat sendChat){
		return defaultGameModel;
	}
	/**
	 * This function will call the server API at
	 * moves / rollNumber
	 * @param RollNumber object that contains the player index
	 * and what number they rolled
	 * @return JSON String that contains the client model
	 */
	public String rollNumber(RollNumber robNumber){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / robPlayer
	 * @param RobPlayer object that contains the index of the
	 * player robbing, and the new location of the robber
	 * @return JSON String that contains the client model
	 */
	public String robPlayer(RobPlayer robPlayer){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / finishTurn
	 * @param FinishTurn object that contains the player index
	 * that's ending their turn
	 * @return JSON String that contains the client model
	 */
	public String finishTurn(FinishTurn finishTurn){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / buyDevCard
	 * @param BuyDevCard object that contains the player index
	 * buying the card
	 * @return JSON String that contains the client model
	 */
	public String buyDevCard(BuyDevCard buyDevCard){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / Year_Of_Plenty
	 * @param YearOfPlenty object that contains the player index
	 * playing the card and the two resources they gain
	 * @return JSON String that contains the client model
	 */
	public String yearOfPlenty(YearOfPlenty yearOfPlenty){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / Road_Building
	 * @param RoadBuilding object that contains the player index
	 * and the two locations they want to build roads
	 * @return JSON String that contains the client model
	 */
	public String roadBuilding(RoadBuilding roadBuilding){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / Soldier
	 * @param SoldierMove object that contains the player index
	 * doing the robbing, the player index of the one they're
	 * robbing, and the new location of the robber
	 * @return JSON String that contains the client model
	 */
	public String moveSoldier(SoldierMove soldierMove){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / Monopoly
	 * @param Monopoly object that contains the player index
	 * and the resource they will monopolize
	 * @return JSON String that contains the client model
	 */
	public String playMonopolyCard(Monopoly monopoly){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / Monument
	 * @param MonumentMove object that contains the player index
	 * playing the monument card
	 * @return JSON String that contains the client model
	 */
	public String playMonumentCard(MonumentMove monumentMove){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / buildRoad
	 * @param BuildRoad object that contains the player index
	 * building the road, the location where they want to
	 * build, and whether or not it's free or not
	 * @return JSON String that contains the client model
	 */
	public String buildRoad(BuildRoad buildRoad){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / buildCity
	 * @param BuildCity object that contains the player index
	 * building the city and the location of the city
	 * @return JSON String that contains the client model
	 */
	public String buildCity(BuildCity buildCity){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / buildSettlement
	 * @param BuildSettlement object that contains the player index
	 * building the settlement, the location, and whether it's free
	 * @return JSON String that contains the client model
	 */
	public String buildSettlement(BuildSettlement buildSettlement){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / offerTrade
	 * @param OfferTrade object that contains the player index
	 * sending the offer, the player index receiving the offer,
	 * and the list resources offered and desired
	 * @return JSON String that contains the client model
	 */
	public String offerTrade(OfferTrade offerTrade){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / acceptTrade
	 * @param AcceptTrade object that contains the player index
	 * responding to the trade and whether they accept or reject it
	 * @return JSON String that contains the client model
	 */
	public String acceptTrade(AcceptTrade acceptTrade){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / maritimeTrade
	 * @param MaritimeTrade object that contains the player index
	 * of the player trading, the ratio they're trading at, the
	 * desired resource and the offered resource
	 * @return JSON String that contains the client model
	 */
	public String maritimeTrade(MaritimeTrade maritimeTrade){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * moves / discardedCards
	 * @param DiscardedCards object that contains the player index
	 * discarding cards and the list of resources they're discarding
	 * @return JSON String that contains the client model
	 */
	public String discardCards(DiscardedCards discardedCards){
		return defaultGameModel;
	}
	
	/**
	 * This function will call the server API at
	 * util / changeLogLevel
	 * @param ChangeLogLevelRequest that contains the server's
	 * new log level
	 * @return JSON String that indicates whether it succeed
	 */
	public String changeLogLevel(ChangeLogLevelRequest logLevel) {
		return "Success";
	}
	
	
	@Override
	public String addAI(String aiType) {
		if (aiType.equals("LARGEST_ARMY")) {
			return "Success";
		}
		return "Failure";
	}
	
	
	@Override
	public ArrayList<String> getListAI() {
		ArrayList<String>ret =  new ArrayList<String>();
		return ret;
	}
	
	
	
	
	
	
	
	
	
}
