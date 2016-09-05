package server.database.dao;

import java.util.List;

import server.database.DatabaseException;
import shared.models.Game;

/**
 * 
 * @author Bo Pace
 *
 */

public interface IGameDAO {
	
	/**
	 * Gets a game by game ID
	 * @param gameID The ID of the game we want to access.
	 * @return The game from the database.
	 */
	public Game getGame(int gameID) throws DatabaseException;
	
	/**
	 * Saves a game to the database.
	 * @param game The game we are saving to the database.
	 */
	public void saveGame(Game game) throws DatabaseException;
	
	/**
	 * Gets all games saved in the database.
	 * @return A list of all of the games in the database.
	 * @throws DatabaseException 
	 */
	public List<Game> getAllGames() throws DatabaseException;
	
	/**
	 * Clears all games in the database
	 * @throws DatabaseException
	 */
	public void clear() throws DatabaseException;

}
