package server.managers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import server.ServerException;
import shared.communication.proxy.Credentials;



/**
 * UserManager class
 * @author Skyler
 *
 */
public class UserManager {
	
	private Map<String, User> users;
	
	public UserManager()
	{
		users = new HashMap<String, User>();
	}
	
	/**
	 * Adds a user to the list of users
	 * @param user
	 * @throws InvalidCredentialsException 
	 */
	public void addUser(Credentials userCredentials) throws ServerException
	{
		if (users.containsKey(userCredentials.username)) {
			throw new ServerException("Failed to register - someone already has that username.");
		}
		users.put(userCredentials.username, new User(userCredentials));
		userCredentials.playerID = users.get(userCredentials.username).getPlayerID();
	}
	
	
	public User getUser(String username) {
		return this.users.get(username);
	}
	

	/**
	 * Returns the list of users
	 * @return List<User>
	 */
	public Map<String, User> getUsers()
	{
		return users;
	}
	
	
	/**
	 * Performs all the logic for logging in
	 * 
	 * @param credentials
	 * @return
	 * @throws InvalidCredentialsException
	 */
	public int login(Credentials credentials) throws ServerException {
		
		if (!this.users.containsKey(credentials.username)) {
			throw new ServerException("Failed to login - bad username or password.");
		}
		
		return this.getUser(credentials.username).login(credentials);
	}
	
	
	/**
	 * Performs all the logic for registering
	 * 
	 * @param credentials
	 * @throws InvalidCredentialsException
	 */
	public void register(Credentials credentials) throws ServerException {
		
		if (this.users.containsKey(credentials.username)) {
			throw new ServerException("Failed to register - someone already has that username.");
		}
		this.addUser(credentials);
	}
}
