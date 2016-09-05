package server.database.dao;

import java.util.List;

import server.database.DatabaseException;
import server.managers.User;
import shared.communication.proxy.Credentials;

/**
 * 
 * @author Bo Pace
 *
 */

public interface IUserDAO {
	
	/**
	 * Gets a user by user ID
	 * @param userID The ID of the user we want to access.
	 * @return The user from the database.
	 * @throws DatabaseException 
	 */
	public Credentials getUser(int userID) throws DatabaseException;
	
	/**
	 * Gets all users saved in the database.
	 * @return A list of all users saved in the database.
	 * @throws DatabaseException 
	 */
	public List<Credentials> getAllUsers() throws DatabaseException;
	
	/**
	 * Creates a user in the database.
	 * @param user The user to save to the database
	 * @throws DatabaseException 
	 */
	public void createUser(User User) throws DatabaseException;

	/**
	 * Clears the users stored in the database.
	 * @throws DatabaseException 
	 */
	public void clear() throws DatabaseException;
}
