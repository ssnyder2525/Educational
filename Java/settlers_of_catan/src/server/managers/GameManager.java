package server.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shared.communication.proxy.CreateGameRequestParams;
import shared.definitions.GameState;
import shared.models.Game;
import shared.models.cardClasses.Bank;
import shared.models.cardClasses.CardDeck;
import shared.models.chatClasses.GameChat;
import shared.models.logClasses.GameLog;
import shared.models.playerClasses.GamePlayers;
import shared.models.playerClasses.TurnManager;

/**
 * GameManager Class 
 * @author Skyler
 *
 */
public class GameManager {

	/* gameID => Game */
	private Map<Integer, Game> gamesList;
	
	public GameManager() 
	{
		this.gamesList = new HashMap<Integer, Game>();
	}
	
	/**
	 * Adds a game to the list of games
	 * @param game
	 */
	public void addGame(Game game)
	{
		game.setId(game.getNextID());
		gamesList.put(game.getId(), game);
	}
	
	/**
	 * Sets the gamesList to the passed in gamesList
	 * @param gamesList
	 */
	public void setGames(Map<Integer, Game> gamesList)
	{
		this.gamesList = gamesList;
	}

	/**
	 * Returns the list of games
	 * @return List<Game>
	 */
	public List<Game> getGames()
	{
		return Arrays.asList(gamesList.values().toArray(new Game[0]));
	}
	
	public Game getGameByID(int id) {
		return this.gamesList.get(id);
	}
}
