package server.database;

import server.database.dao.ICommandDAO;
import server.database.dao.IGameDAO;
import server.database.dao.IUserDAO;

/**
 * 
 * @author Bo Pace, Ben Thompson
 *
 */

public interface IPersistencePlugin {
	
	/**
	 * Starts a transaction.
	 * @throws DatabaseException 
	 */
	public void startTransaction();
	
	/**
	 * Commits the changes to the database and ends the current transaction.
	 */
	public void endTransaction();
	
	/**
	 * Clears the database.
	 */
	public void clear();
	
	/**
	 * Gets an instance of the game DAO from the current persistence plugin.
	 */
	public IGameDAO getGameDAO();
	
	/**
	 * Gets an instance of the user DAO from the current persistence plugin.
	 */
	public IUserDAO getUserDAO();
	
	/**
	 * Gets an instance of the command DAO from the current persistence plugin.
	 */
	public ICommandDAO getCommandDAO();

	
	public void setDelta(int delta);

	public void rollback();
}
