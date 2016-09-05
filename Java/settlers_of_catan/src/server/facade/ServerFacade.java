package server.facade;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import server.ServerException;
import server.command.ACommand;
import server.managers.GameManager;
import server.managers.UserManager;
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
import shared.definitions.CatanColor;
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.locations.RobberLocation;
import shared.models.Game;
import shared.models.cardClasses.InsufficientCardNumberException;
import shared.models.chatClasses.GameChat;
import shared.models.chatClasses.Message;
import shared.models.mapClasses.InvalidTypeException;
import shared.models.playerClasses.GamePlayers;
import shared.models.playerClasses.Player;
import shared.serializerJSON.Serializer;

public class ServerFacade implements IServerFacade {
	
	UserManager userManager;
	GameManager gameManager;
	
	public ServerFacade() {
		userManager = new UserManager();
		gameManager = new GameManager();
	}
	
	
	
// USER
	@Override
	public String login(Credentials credentials) throws ServerException {
		userManager.login(credentials);
		return "Success";
	}
	

	@Override
	public String register(Credentials credentials) throws ServerException {
		userManager.register(credentials);
		return "Success";
	}
	
	

//GAMES
	@Override
	public String getGamesList() {
		
		List<Game> games = gameManager.getGames();
		JsonArray gameListJson = new JsonArray();
		
		
		for (Game g: games) {
			JsonObject gameJson = new JsonObject();
			
			gameJson.addProperty("title", g.getTitle());
			gameJson.addProperty("id", g.getId());
			
			JsonArray playerListJson = new JsonArray();
			GamePlayers players = g.getPlayers();
			
			for (Player p: players.getPlayers()) {
				JsonObject playerJson = new JsonObject();
				playerJson.addProperty("color", p.getColor().name());
				playerJson.addProperty("name", p.getName());
				playerJson.addProperty("id", p.getID());
				playerListJson.add(playerJson);
			}
			gameJson.add("players", playerListJson);
			
			gameListJson.add(gameJson);
		}
		
		return gameListJson.toString();
	}
	

	@Override
	public String createGame(CreateGameRequestParams params) {
		
		Game g = new Game(params); //Change this to the newly created game
		this.gameManager.addGame(g);
		
		JsonObject gameJson = new JsonObject();
		
		gameJson.addProperty("title", g.getTitle());
		gameJson.addProperty("id", g.getId());
		
		JsonArray playerListJson = new JsonArray();
		int numberOfEmptyPlayers = 4;
		for (int i = 0; i < numberOfEmptyPlayers; i++) {
			playerListJson.add(new JsonObject());
			
		}
		gameJson.add("players", playerListJson);
		
		return gameJson.toString();
	}
	

	@Override
	public String joinGame(JoinGameRequestParams params, Credentials credentials) throws ServerException {
		
		Game game = this.gameManager.getGameByID(params.id);
		if (game.getPlayers().getPlayers().size() == 4 && game.getPlayers().getPlayerByID(credentials.playerID) == null) {
			throw new ServerException("This game already has 4 players");
		}
		if (game.getPlayers().getPlayerByID(credentials.playerID) == null) {
			Player newPlayer = new Player(credentials.playerID, credentials.username, CatanColor.valueOf(params.color.toUpperCase()), game.getPlayers().getPlayers().size());
			try {
				game.getPlayers().addPlayer(newPlayer);
			} catch (Exception e) {
				throw new ServerException("Error adding player");
			}
		}
		else {
			game.getPlayers().getPlayerByID(credentials.playerID).setColor(CatanColor.valueOf(params.color.toUpperCase()));
		}
		
		if (game.getPlayers().getNumberOfPlayers() == 4 && game.getGameState() == GameState.LOGIN) { 
			game.setGameState(GameState.SETUP1);
		}
		game.incrementVersionID();
		return "Success";
	}
	

	
// GAME
	@Override
	public String getModel(int versionNumber, int gameID) {
		Game game = this.gameManager.getGameByID(gameID);
		if (game.getVersionID() == versionNumber) {
			return "\"true\"";
		} else {
			return Serializer.getInstance().serialize(game);
		}
	}
	
	
	@Override
	public String getModel(int gameID) {
		Game game = this.gameManager.getGameByID(gameID);
		return Serializer.getInstance().serialize(game);
	}

	public Game getGameByID(int gameID) {
		return this.gameManager.getGameByID(gameID);
	}

	@Override
	public String addAI(String aiType, int gameID) throws ServerException {
		return "Success";
	}
	

	@Override
	public String getListAI() {
		JsonArray jsonArr = new JsonArray();
		jsonArr.add("LARGEST_ARMY");
		return jsonArr.toString();
	}
	
	

// MOVES
	@Override
	public String sendChat(SendChat sendChat, int gameID) {
		Game game = this.gameManager.getGameByID(gameID);
		GameChat chat = game.getGameChat();
		Player player = game.getPlayers().getPlayerByIndex(sendChat.playerIndex);
		chat.addMessage(new Message(player.getName(), sendChat.content));
		game.setGameChat(chat);
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}

	
	@Override
	public String rollNumber(RollNumber rollNumber, int gameID) {
		Game game = this.gameManager.getGameByID(gameID);
		game.processRoll(rollNumber);
		Player player = game.getPlayers().getPlayerByIndex(rollNumber.playerIndex);
		game.getGameLog().addMessage(new Message(player.getName(), player.getName() + " rolled a " + rollNumber.roll));
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}
	

	@Override
	public String robPlayer(RobPlayer robPlayer, int gameID) {
		Game game = this.gameManager.getGameByID(gameID);
		game.getMap().setRobberLocation(new RobberLocation(robPlayer.newLocation));
		game.robPlayer(robPlayer.playerIndex, robPlayer.victimIndex);
		String robberName = game.getPlayers().getPlayerByIndex(robPlayer.playerIndex).getName();
		if (robPlayer.playerIndex == -1 || robPlayer.victimIndex == -1) {
			game.getGameLog().addMessage(new Message(robberName, robberName + " moved the robber but couldn't rob anyone"));
		} else {
			String youGotRobbedName = game.getPlayers().getPlayerByIndex(robPlayer.victimIndex).getName();
			game.getGameLog().addMessage(new Message(robberName, robberName + " moved the robber and robbed " + youGotRobbedName));
		}
		game.incrementVersionID();
		game.setGameState(GameState.MYTURN);
		return Serializer.getInstance().serialize(game);
	}

	
	@Override
	public String finishTurn(FinishTurn finishTurn, int gameID) {
		Game game = this.gameManager.getGameByID(gameID);
		if (game.getGameState() == GameState.SETUP2 && game.getTurnManager().getPlayerIndex() == 0) {
			game.setGameState(GameState.ROLLING);
			game.getTurnManager().finishRound2Turn(finishTurn.playerIndex);
		}
		else if (game.getGameState() == GameState.SETUP1 && game.getTurnManager().getPlayerIndex() == 3) {
			game.setGameState(GameState.SETUP2);
			game.getTurnManager().repeatFinishTurn(finishTurn.playerIndex);
		}
		else if (game.getGameState() == GameState.SETUP2) {
			game.getTurnManager().reverseFinishTurn(finishTurn.playerIndex);
		}
		else {
			game.getTurnManager().finishTurn(finishTurn.playerIndex);
			if (game.getGameState() != GameState.SETUP1 && game.getGameState() != GameState.SETUP2) {
				game.setGameState(GameState.ROLLING);
			}
		}
		String playerName = game.getPlayers().getPlayerByIndex(finishTurn.playerIndex).getName();
		game.getGameLog().addMessage(new Message(playerName, playerName + "'s turn just ended"));
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}
	

	@Override
	public String buyDevCard(BuyDevCard buyDevCard, int gameID) throws ServerException {
		Game game = this.gameManager.getGameByID(gameID);
		try {
			game.buyDevelopmentCard(buyDevCard.playerIndex);
			String playerName = game.getPlayers().getPlayerByIndex(buyDevCard.playerIndex).getName();
			game.getGameLog().addMessage(new Message(playerName, playerName + " bought a Development Card"));
		} catch (InsufficientCardNumberException e) {
			throw new ServerException("Error buying dev card");
		}
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}
	

	@Override
	public String yearOfPlenty(YearOfPlenty yearOfPlenty, int gameID) {
		Game game = this.gameManager.getGameByID(gameID);
		game.playYearOfPlentyCard(yearOfPlenty.playerIndex, yearOfPlenty.resourceOne, yearOfPlenty.resourceTwo);
		String playerName = game.getPlayers().getPlayerByIndex(yearOfPlenty.playerIndex).getName();
		game.getGameLog().addMessage(new Message(playerName, playerName + " played a Year-Of-Plenty Card"));
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}

	
	@Override
	public String roadBuilding(RoadBuilding roadBuilding, int gameID) throws ServerException {
		Game game = this.gameManager.getGameByID(gameID);
		try {
			game.playRoadBuildingCard(roadBuilding.playerIndex, roadBuilding.firstSpot, roadBuilding.secondSpot);
			String playerName = game.getPlayers().getPlayerByIndex(roadBuilding.playerIndex).getName();
			game.getGameLog().addMessage(new Message(playerName, playerName + " played a Road Building Card"));
		} catch (InvalidTypeException e) {
			throw new ServerException("Error playing road building card");
		}
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}
	

	@Override
	public String moveSoldier(SoldierMove soldierMove, int gameID) {
		Game game = this.gameManager.getGameByID(gameID);
		game.playSoldierCard(soldierMove.playerIndex, soldierMove.newLocation, soldierMove.victimIndex);
		String playerName = game.getPlayers().getPlayerByIndex(soldierMove.playerIndex).getName();
		game.getGameLog().addMessage(new Message(playerName, playerName + " played a Soldier Card"));
		if (soldierMove.victimIndex == -1) {
			game.getGameLog().addMessage(new Message(playerName, playerName + " moved the robber but couldn't rob anyone"));
		} else {
			String youGotRobbedName = game.getPlayers().getPlayerByIndex(soldierMove.victimIndex).getName();
			game.getGameLog().addMessage(new Message(playerName, playerName + " moved the robber and robbed " + youGotRobbedName));
		}
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}

	@Override
	public String playMonopolyCard(Monopoly monopoly, int gameID) {
		Game game = this.gameManager.getGameByID(gameID);
		game.playMonopolyCard(monopoly.playerIndex, monopoly.resource);
		String playerName = game.getPlayers().getPlayerByIndex(monopoly.playerIndex).getName();
		game.getGameLog().addMessage(new Message(playerName, playerName + " played a Monopoly Card"));
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}
	

	@Override
	public String playMonumentCard(MonumentMove monumentMove, int gameID) {
		Game game = this.gameManager.getGameByID(gameID);
		game.playMonumentCard(monumentMove.playerIndex);
		String playerName = game.getPlayers().getPlayerByIndex(monumentMove.playerIndex).getName();
		game.getGameLog().addMessage(new Message(playerName, playerName + " played a Monument Card"));
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}
	

	@Override
	public String buildRoad(BuildRoad buildRoad, int gameID) throws ServerException {
		Game game = this.gameManager.getGameByID(gameID);
		try {
			game.buildRoad(buildRoad.playerIndex, buildRoad.roadLocation, buildRoad.free);
			String playerName = game.getPlayers().getPlayerByIndex(buildRoad.playerIndex).getName();
			game.getGameLog().addMessage(new Message(playerName, playerName + " built a road"));
		} catch (InsufficientCardNumberException | InvalidTypeException e) {
			throw new ServerException("Error building road");
		}
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}
	

	@Override
	public String buildCity(BuildCity buildCity, int gameID) throws ServerException {
		Game game = this.gameManager.getGameByID(gameID);
		try {
			game.buildCity(buildCity.playerIndex, buildCity.vertexLocation);
			String playerName = game.getPlayers().getPlayerByIndex(buildCity.playerIndex).getName();
			game.getGameLog().addMessage(new Message(playerName, playerName + " upgraded to a city"));
		} catch (InsufficientCardNumberException | InvalidTypeException e) {
			throw new ServerException("Error building city");
		}
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}
	

	@Override
	public String buildSettlement(BuildSettlement buildSettlement, int gameID) throws ServerException {
		Game game = this.gameManager.getGameByID(gameID);
		try {
			game.buildSettlement(buildSettlement.playerIndex, buildSettlement.vertexLocation, buildSettlement.free);
			String playerName = game.getPlayers().getPlayerByIndex(buildSettlement.playerIndex).getName();
			game.getGameLog().addMessage(new Message(playerName, playerName + " built a settlement"));
		} catch (InsufficientCardNumberException | InvalidTypeException e) {
			throw new ServerException("Error building settlement");
		}
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}
	

	@Override
	public String offerTrade(OfferTrade offerTrade, int gameID) {
		Game game = this.gameManager.getGameByID(gameID);
		game.getTurnManager().getTradeManager().offerTrade(offerTrade);
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}
	

	@Override
	public String acceptTrade(AcceptTrade acceptTrade, int gameID) {
		Game game = this.gameManager.getGameByID(gameID);
		game.getTurnManager().getTradeManager().acceptTrade(acceptTrade);
		String playerName = game.getPlayers().getPlayerByIndex(acceptTrade.playerIndex).getName();
		if (acceptTrade.response) {
			game.getGameLog().addMessage(new Message(playerName, "The trade was accepted"));
		} else {
			game.getGameLog().addMessage(new Message(playerName, "The trade was not accepted"));
		}
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}
	

	@Override
	public String maritimeTrade(MaritimeTrade maritimeTrade, int gameID) throws ServerException {
		Game game = this.gameManager.getGameByID(gameID);
		try {
			game.tradeResourcesWithBank(maritimeTrade.playerIndex, maritimeTrade.ratio, maritimeTrade.givingUp, maritimeTrade.getting);
		} catch (InsufficientCardNumberException e) {
			throw new ServerException("Error doing maritime trade");
		}
		game.incrementVersionID();
		return Serializer.getInstance().serialize(game);
	}
	

	@Override
	public String discardCards(DiscardedCards discardedCards, int gameID) throws ServerException {
		Game game = this.gameManager.getGameByID(gameID);
		ArrayList<ResourceType> res = new ArrayList<ResourceType>();
		res = addResources(res, ResourceType.BRICK, discardedCards.brick);
		res = addResources(res, ResourceType.WOOD, discardedCards.wood);
		res = addResources(res, ResourceType.WHEAT, discardedCards.wheat);
		res = addResources(res, ResourceType.SHEEP, discardedCards.sheep);
		res = addResources(res, ResourceType.ORE, discardedCards.ore);
		ResourceType[] resources = new ResourceType[res.size()];
		resources = res.toArray(resources);
		try {
			game.discard(discardedCards.playerIndex, resources);
			List<Player> gp = game.getPlayers().getPlayers();
			boolean needDiscard = false;
			for (Player p : gp) {
				if( p.isDiscarding()) {
					needDiscard = true;
					break;
				}
			}
			if (!needDiscard) {
				game.setGameState(GameState.ROBBER);
			}
		} catch (InsufficientCardNumberException e) {
			throw new ServerException("Error discarding cards");
		}
		//this.gameManager.addGame(game);
		return Serializer.getInstance().serialize(game);
	}

	
	
// HELPERS
	private ArrayList<ResourceType> addResources(ArrayList<ResourceType> res, ResourceType resource, int num) {
		for (int i = 0; i < num; ++i) {
			res.add(resource);
		}
		return res;
	}
	
	
	@Override
	public int getPlayerIDFromCredentials(Credentials credentials) throws ServerException {
		return userManager.login(credentials);
	}


	

	@Override
	public void restoreAllUsers(List<Credentials> credentials) {
		for (Credentials credential : credentials) {
			try {
				userManager.addUser(credential);
			} catch (ServerException e) {
				e.printStackTrace();
			}
		}
	}



	@Override
	public void restoreAllGames(List<Game> games) {
		for (Game game: games) {
			gameManager.addGame(game);
		}
	}



	@Override
	public void runAllCommands(List<ACommand> commands) {
		for (ACommand command: commands) {
			try {
				command.execute();
			} catch (ServerException e) {
				e.printStackTrace();
			}
		}
	}
}