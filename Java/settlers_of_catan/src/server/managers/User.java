package server.managers;



import server.ServerException;
import shared.communication.proxy.Credentials;

/**
 * User's are contained within the UserManager class
 * 
 * @author benthompson
 *
 */
public class User {

	Credentials credentials;
	int playerID;
	
	static int nextPlayerID = 1;
	
	
// CONSTRUCTOR
	public User(Credentials credentials) {
		
		this.credentials = credentials;
		this.playerID = this.getNextID();
	}
	

	public User(Credentials credentials, int id) {
		
		this.credentials = credentials;
		this.playerID = id;
	}
	

// GETTERS
	public String getUsername() {
		return this.credentials.username;
	}
	
	
	public String getPassword() {
		return this.credentials.password;
	}
	
	
	public int getPlayerID() {
		return this.playerID;
	}
	
	

// Public METHODS
	public int login(Credentials credentials) throws ServerException {
		
		if (this.getUsername().equals(credentials.username) && this.getPassword().equals(credentials.password)) {
			return this.playerID;
		}

		throw new ServerException("Failed to login - bad username or password.");
	}
	
	
	@Override // TODO: implement this
    public String toString() {
		return "" + credentials.username + ": " + credentials.password + ": " + playerID;
	}

	

// Private METHODS
	private int getNextID() {
		
		int id = nextPlayerID;
		nextPlayerID++;
		return id;
	}
	
	
	

}
