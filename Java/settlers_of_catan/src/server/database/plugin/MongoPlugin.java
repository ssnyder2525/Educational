package server.database.plugin;

import server.database.IPersistencePlugin;
import server.database.dao.ICommandDAO;
import server.database.dao.IGameDAO;
import server.database.dao.IUserDAO;
import server.database.dao.MongoCommandDAO;
import server.database.dao.MongoGameDAO;
import server.database.dao.MongoUserDAO;

/**
 * 
 * @author Bo Pace, Ben Thompson
 *
 */

public class MongoPlugin implements IPersistencePlugin {
	
	IUserDAO UserDAO;
	IGameDAO GameDAO;
	ICommandDAO CommandDAO;
	
	
	public MongoPlugin() {

		UserDAO = new MongoUserDAO();
		GameDAO = new MongoGameDAO();
		CommandDAO = new MongoCommandDAO();
	}
	

	/**
	 * Starts a transaction to the database.
	 */
	@Override
	public void startTransaction() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Commits the changes to the database and ends the current transaction.
	 */
	@Override
	public void endTransaction() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Clears the current transaction.
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Gets an instance of the game DAO from the current persistence plugin.
	 * @return 
	 */
	@Override
	public IGameDAO getGameDAO() {
		return this.GameDAO;
	}

	/**
	 * Gets an instance of the user DAO from the current persistence plugin.
	 * @return 
	 */
	@Override
	public IUserDAO getUserDAO() {
		return this.UserDAO;
	}
	
	/**
	 * Gets an instance of the command DAO from the current persistence plugin.
	 * @return 
	 */
	@Override
	public ICommandDAO getCommandDAO() {
		return this.CommandDAO;
	}
	

	
	@Override
	public void setDelta(int delta) {
		CommandDAO.setDelta(delta);
	}


	@Override
	public void rollback() {
		// TODO Auto-generated method stub
		
	}

}
