package server.database.dao;

import java.util.List;

import server.command.ACommand;
import server.database.DatabaseException;
import server.facade.IServerFacade;

public interface ICommandDAO {	

	public void setDelta(int delta);
	
	public int getDelta();

	/**
	 * Returns the number of commands performed on the game in the database.
	 * @param gameID The ID of the game whose command count we need to access.
	 * @return The number of commands performed on the specified game.
	 * @throws DatabaseException 
	 */
	public int getCommandCount(int gameID) throws DatabaseException;
	
	/**
	 * Creates a command in the database.
	 * @param jsonCommand The command we're creating in the database, serialized with JSON.
	 */
	public void createCommand(int gameID, String jsonCommand) throws DatabaseException;
	
	/**
	 * Gets all of the commands from all games in the database.
	 * @param facade The server facade, needed for deserialization of commands
	 * @return Returns a list of the commands.
	 */
	public List<ACommand> getAllCommands(IServerFacade facade) throws DatabaseException;
	
	/**
	 * Clears all of the commands for the specified game in the database
	 * @param gameID The ID of the game we are clearing the commands for
	 * @throws DatabaseException 
	 */
	public void clearCommands(int gameID) throws DatabaseException;
	
	/**
	 * Clears all of the commands from the database
	 * @throws DatabaseException 
	 */
	public void clear() throws DatabaseException;
}
