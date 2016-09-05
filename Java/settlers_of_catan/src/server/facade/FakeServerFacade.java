package server.facade;

import java.util.List;

import server.ServerException;
import server.command.ACommand;
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
import shared.models.Game;

public class FakeServerFacade implements IServerFacade {
	
	private String modelJson = "{  \"deck\": {    \"yearOfPlenty\": 2,    \"monopoly\": 2,    \"soldier\": 14,    \"roadBuilding\": 2,    \"monument\": 5  },  \"map\": {    \"hexes\": [      {        \"location\": {          \"x\": 0,          \"y\": -2        }      },      {        \"resource\": \"brick\",        \"location\": {          \"x\": 1,          \"y\": -2        },        \"number\": 4      },      {        \"resource\": \"wood\",        \"location\": {          \"x\": 2,          \"y\": -2        },        \"number\": 11      },      {        \"resource\": \"brick\",        \"location\": {          \"x\": -1,          \"y\": -1        },        \"number\": 8      },      {        \"resource\": \"wood\",        \"location\": {          \"x\": 0,          \"y\": -1        },        \"number\": 3      },      {        \"resource\": \"ore\",        \"location\": {          \"x\": 1,          \"y\": -1        },        \"number\": 9      },      {        \"resource\": \"sheep\",        \"location\": {          \"x\": 2,          \"y\": -1        },        \"number\": 12      },      {        \"resource\": \"ore\",        \"location\": {          \"x\": -2,          \"y\": 0        },        \"number\": 5      },      {        \"resource\": \"sheep\",        \"location\": {          \"x\": -1,          \"y\": 0        },        \"number\": 10      },      {        \"resource\": \"wheat\",        \"location\": {          \"x\": 0,          \"y\": 0        },        \"number\": 11      },      {        \"resource\": \"brick\",        \"location\": {          \"x\": 1,          \"y\": 0        },        \"number\": 5      },      {        \"resource\": \"wheat\",        \"location\": {          \"x\": 2,          \"y\": 0        },        \"number\": 6      },      {        \"resource\": \"wheat\",        \"location\": {          \"x\": -2,          \"y\": 1        },        \"number\": 2      },      {        \"resource\": \"sheep\",        \"location\": {          \"x\": -1,          \"y\": 1        },        \"number\": 9      },      {        \"resource\": \"wood\",        \"location\": {          \"x\": 0,          \"y\": 1        },        \"number\": 4      },      {        \"resource\": \"sheep\",        \"location\": {          \"x\": 1,          \"y\": 1        },        \"number\": 10      },      {        \"resource\": \"wood\",        \"location\": {          \"x\": -2,          \"y\": 2        },        \"number\": 6      },      {        \"resource\": \"ore\",        \"location\": {          \"x\": -1,          \"y\": 2        },        \"number\": 3      },      {        \"resource\": \"wheat\",        \"location\": {          \"x\": 0,          \"y\": 2        },        \"number\": 8      }    ],    \"roads\": [      {        \"owner\": 1,        \"location\": {          \"direction\": \"S\",          \"x\": -1,          \"y\": -1        }      },      {        \"owner\": 3,        \"location\": {          \"direction\": \"SW\",          \"x\": -1,          \"y\": 1        }      },      {        \"owner\": 3,        \"location\": {          \"direction\": \"SW\",          \"x\": 2,          \"y\": -2        }      },      {        \"owner\": 2,        \"location\": {          \"direction\": \"S\",          \"x\": 1,          \"y\": -1        }      },      {        \"owner\": 0,        \"location\": {          \"direction\": \"S\",          \"x\": 0,          \"y\": 1        }      },      {        \"owner\": 2,        \"location\": {          \"direction\": \"S\",          \"x\": 0,          \"y\": 0        }      },      {        \"owner\": 1,        \"location\": {          \"direction\": \"SW\",          \"x\": -2,          \"y\": 1        }      },      {        \"owner\": 0,        \"location\": {          \"direction\": \"SW\",          \"x\": 2,          \"y\": 0        }      }    ],    \"cities\": [],    \"settlements\": [      {        \"owner\": 3,        \"location\": {          \"direction\": \"SW\",          \"x\": -1,          \"y\": 1        }      },      {        \"owner\": 3,        \"location\": {          \"direction\": \"SE\",          \"x\": 1,          \"y\": -2        }      },      {        \"owner\": 2,        \"location\": {          \"direction\": \"SW\",          \"x\": 0,          \"y\": 0        }      },      {        \"owner\": 2,        \"location\": {          \"direction\": \"SW\",          \"x\": 1,          \"y\": -1        }      },      {        \"owner\": 1,        \"location\": {          \"direction\": \"SW\",          \"x\": -2,          \"y\": 1        }      },      {        \"owner\": 0,        \"location\": {          \"direction\": \"SE\",          \"x\": 0,          \"y\": 1        }      },      {        \"owner\": 1,        \"location\": {          \"direction\": \"SW\",          \"x\": -1,          \"y\": -1        }      },      {        \"owner\": 0,        \"location\": {          \"direction\": \"SW\",          \"x\": 2,          \"y\": 0        }      }    ],    \"radius\": 3,    \"ports\": [      {        \"ratio\": 2,        \"resource\": \"ore\",        \"direction\": \"S\",        \"location\": {          \"x\": 1,          \"y\": -3        }      },      {        \"ratio\": 3,        \"direction\": \"SW\",        \"location\": {          \"x\": 3,          \"y\": -3        }      },      {        \"ratio\": 3,        \"direction\": \"NW\",        \"location\": {          \"x\": 2,          \"y\": 1        }      },      {        \"ratio\": 2,        \"resource\": \"brick\",        \"direction\": \"NE\",        \"location\": {          \"x\": -2,          \"y\": 3        }      },      {        \"ratio\": 2,        \"resource\": \"wheat\",        \"direction\": \"S\",        \"location\": {          \"x\": -1,          \"y\": -2        }      },      {        \"ratio\": 2,        \"resource\": \"wood\",        \"direction\": \"NE\",        \"location\": {          \"x\": -3,          \"y\": 2        }      },      {        \"ratio\": 3,        \"direction\": \"SE\",        \"location\": {          \"x\": -3,          \"y\": 0        }      },      {        \"ratio\": 2,        \"resource\": \"sheep\",        \"direction\": \"NW\",        \"location\": {          \"x\": 3,          \"y\": -1        }      },      {        \"ratio\": 3,        \"direction\": \"N\",        \"location\": {          \"x\": 0,          \"y\": 3        }      }    ],    \"robber\": {      \"x\": 0,      \"y\": -2    }  },  \"players\": [    {      \"resources\": {        \"brick\": 0,        \"wood\": 1,        \"sheep\": 1,        \"wheat\": 1,        \"ore\": 0      },      \"oldDevCards\": {        \"yearOfPlenty\": 0,        \"monopoly\": 0,        \"soldier\": 0,        \"roadBuilding\": 0,        \"monument\": 0      },      \"newDevCards\": {        \"yearOfPlenty\": 0,        \"monopoly\": 0,        \"soldier\": 0,        \"roadBuilding\": 0,        \"monument\": 0      },      \"roads\": 13,      \"cities\": 4,      \"settlements\": 3,      \"soldiers\": 0,      \"victoryPoints\": 2,      \"monuments\": 0,      \"playedDevCard\": false,      \"discarded\": false,      \"playerID\": 0,      \"playerIndex\": 0,      \"name\": \"Sam\",      \"color\": \"red\"    },    {      \"resources\": {        \"brick\": 1,        \"wood\": 0,        \"sheep\": 1,        \"wheat\": 0,        \"ore\": 1      },      \"oldDevCards\": {        \"yearOfPlenty\": 0,        \"monopoly\": 0,        \"soldier\": 0,        \"roadBuilding\": 0,        \"monument\": 0      },      \"newDevCards\": {        \"yearOfPlenty\": 0,        \"monopoly\": 0,        \"soldier\": 0,        \"roadBuilding\": 0,        \"monument\": 0      },      \"roads\": 13,      \"cities\": 4,      \"settlements\": 3,      \"soldiers\": 0,      \"victoryPoints\": 2,      \"monuments\": 0,      \"playedDevCard\": false,      \"discarded\": false,      \"playerID\": 1,      \"playerIndex\": 1,      \"name\": \"Brooke\",      \"color\": \"red\"    },    {      \"resources\": {        \"brick\": 0,        \"wood\": 1,        \"sheep\": 1,        \"wheat\": 1,        \"ore\": 0      },      \"oldDevCards\": {        \"yearOfPlenty\": 0,        \"monopoly\": 0,        \"soldier\": 0,        \"roadBuilding\": 0,        \"monument\": 0      },      \"newDevCards\": {        \"yearOfPlenty\": 0,        \"monopoly\": 0,        \"soldier\": 0,        \"roadBuilding\": 0,        \"monument\": 0      },      \"roads\": 13,      \"cities\": 4,      \"settlements\": 3,      \"soldiers\": 0,      \"victoryPoints\": 2,      \"monuments\": 0,      \"playedDevCard\": false,      \"discarded\": false,      \"playerID\": 10,      \"playerIndex\": 2,      \"name\": \"Pete\",      \"color\": \"red\"    },    {      \"resources\": {        \"brick\": 0,        \"wood\": 1,        \"sheep\": 1,        \"wheat\": 0,        \"ore\": 1      },      \"oldDevCards\": {        \"yearOfPlenty\": 0,        \"monopoly\": 0,        \"soldier\": 0,        \"roadBuilding\": 0,        \"monument\": 0      },      \"newDevCards\": {        \"yearOfPlenty\": 0,        \"monopoly\": 0,        \"soldier\": 0,        \"roadBuilding\": 0,        \"monument\": 0      },      \"roads\": 13,      \"cities\": 4,      \"settlements\": 3,      \"soldiers\": 0,      \"victoryPoints\": 2,      \"monuments\": 0,      \"playedDevCard\": false,      \"discarded\": false,      \"playerID\": 11,      \"playerIndex\": 3,      \"name\": \"Mark\",      \"color\": \"green\"    }  ],  \"log\": {    \"lines\": [      {        \"source\": \"Sam\",        \"message\": \"Sam built a road\"      },      {        \"source\": \"Sam\",        \"message\": \"Sam built a settlement\"      },      {        \"source\": \"Sam\",        \"message\": \"Sam's turn just ended\"      },      {        \"source\": \"Brooke\",        \"message\": \"Brooke built a road\"      },      {        \"source\": \"Brooke\",        \"message\": \"Brooke built a settlement\"      },      {        \"source\": \"Brooke\",        \"message\": \"Brooke's turn just ended\"      },      {        \"source\": \"Pete\",        \"message\": \"Pete built a road\"      },      {        \"source\": \"Pete\",        \"message\": \"Pete built a settlement\"      },      {        \"source\": \"Pete\",        \"message\": \"Pete's turn just ended\"      },      {        \"source\": \"Mark\",        \"message\": \"Mark built a road\"      },      {        \"source\": \"Mark\",        \"message\": \"Mark built a settlement\"      },      {        \"source\": \"Mark\",        \"message\": \"Mark's turn just ended\"      },      {        \"source\": \"Mark\",        \"message\": \"Mark built a road\"      },      {        \"source\": \"Mark\",        \"message\": \"Mark built a settlement\"      },      {        \"source\": \"Mark\",        \"message\": \"Mark's turn just ended\"      },      {        \"source\": \"Pete\",        \"message\": \"Pete built a road\"      },      {        \"source\": \"Pete\",        \"message\": \"Pete built a settlement\"      },      {        \"source\": \"Pete\",        \"message\": \"Pete's turn just ended\"      },      {        \"source\": \"Brooke\",        \"message\": \"Brooke built a road\"      },      {        \"source\": \"Brooke\",        \"message\": \"Brooke built a settlement\"      },      {        \"source\": \"Brooke\",        \"message\": \"Brooke's turn just ended\"      },      {        \"source\": \"Sam\",        \"message\": \"Sam built a road\"      },      {        \"source\": \"Sam\",        \"message\": \"Sam built a settlement\"      },      {        \"source\": \"Sam\",        \"message\": \"Sam's turn just ended\"      }    ]  },  \"chat\": {    \"lines\": []  },  \"bank\": {    \"brick\": 23,    \"wood\": 21,    \"sheep\": 20,    \"wheat\": 22,    \"ore\": 22  },  \"turnTracker\": {    \"status\": \"Rolling\",    \"currentTurn\": 0,    \"longestRoad\": -1,    \"largestArmy\": -1  },  \"winner\": -1,  \"version\": 0}Response Code";

// USER
	@Override
	public String login(Credentials credentials) throws ServerException {
		if (credentials.username.equals("Sam")) {
			return "Success";
		}
		throw new ServerException("Failed to login - bad username or password.");
	}

	@Override
	public String register(Credentials credentials) throws ServerException {
		if (credentials.username.equals("Sam")) {
			throw new ServerException("Failed to register - someone already has that username.");
		}
		return "Success";
		
	}

// GAMES
	@Override
	public String getGamesList() {
		return "[  {    \"title\": \"Default Game\",    \"id\": 0,    \"players\": [      {        \"color\": \"orange\",        \"name\": \"Sam\",        \"id\": 0      },      {        \"color\": \"blue\",        \"name\": \"Brooke\",        \"id\": 1      },      {        \"color\": \"red\",        \"name\": \"Pete\",        \"id\": 10      },      {        \"color\": \"green\",        \"name\": \"Mark\",        \"id\": 11      }    ]  }]";
	}

	@Override
	public String createGame(CreateGameRequestParams params) {
		return "{  \"title\": \"string\",  \"id\": 0,  \"players\": [    {},    {},    {},    {}  ]}";
	}

	@Override
	public String joinGame(JoinGameRequestParams params, Credentials credentials) throws ServerException {
		return "Success";
	}

// GAME
	@Override
	public String addAI(String aiType, int gameID) throws ServerException {
		return "Success";
	}

	@Override
	public String getListAI() {
		return "['LARGEST_ARMY']";
	}

	@Override
	public String getModel(int versionNumber, int gameID) throws ServerException {
		if (gameID == -1) {
			throw new ServerException("The catan.user HTTP cookie is missing.  You must login before calling this method.");
		}
		return modelJson;
	}

	@Override
	public String getModel(int gameID) {
		return modelJson;
	}

// MOVES
	@Override
	public String sendChat(SendChat sendChat, int gameID) throws ServerException {
		if (sendChat.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String rollNumber(RollNumber rollNumber, int gameID) throws ServerException {
		if (rollNumber.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String robPlayer(RobPlayer robPlayer, int gameID) throws ServerException {
		if (robPlayer.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String finishTurn(FinishTurn finishTurn, int gameID) throws ServerException {
		if (finishTurn.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String buyDevCard(BuyDevCard buyDevCard, int gameID) throws ServerException {
		if (buyDevCard.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String yearOfPlenty(YearOfPlenty yearOfPlenty, int gameID) throws ServerException {
		if (yearOfPlenty.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String roadBuilding(RoadBuilding roadBuilding, int gameID) throws ServerException {
		if (roadBuilding.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String moveSoldier(SoldierMove soldierMove, int gameID) throws ServerException {
		if (soldierMove.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String playMonopolyCard(Monopoly monopoly, int gameID) throws ServerException {
		if (monopoly.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String playMonumentCard(MonumentMove monumentMove, int gameID) throws ServerException {
		if (monumentMove.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String buildRoad(BuildRoad buildRoad, int gameID) throws ServerException {
		if (buildRoad.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String buildCity(BuildCity buildCity, int gameID) throws ServerException {
		if (buildCity.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String buildSettlement(BuildSettlement buildSettlement, int gameID) throws ServerException {
		if (buildSettlement.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String offerTrade(OfferTrade offerTrade, int gameID) throws ServerException {
		if (offerTrade.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String acceptTrade(AcceptTrade acceptTrade, int gameID) throws ServerException {
		if (acceptTrade.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String maritimeTrade(MaritimeTrade maritimeTrade, int gameID) throws ServerException {
		if (maritimeTrade.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	@Override
	public String discardCards(DiscardedCards discardedCards, int gameID) throws ServerException {
		if (discardedCards.playerIndex == -1) {
			throw new ServerException("Invalid command.  ['playerIndex' field has an invalid value.]");
		}
		return modelJson;
	}

	
// HELPER
	@Override
	public int getPlayerIDFromCredentials(Credentials credentials) throws ServerException {
		return 1;
	}

	@Override
	public void restoreAllUsers(List<Credentials> credentials) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restoreAllGames(List<Game> games) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runAllCommands(List<ACommand> commands) {
		// TODO Auto-generated method stub
		
	}
	
	public Game getGameByID(int gameID) { return null; }
}
