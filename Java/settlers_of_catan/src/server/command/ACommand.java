package server.command;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.facade.IServerFacade;
import shared.communication.proxy.Credentials;

public abstract class ACommand {
	
	private Credentials credentials;
	private IServerFacade facade;
	private String userJson = null;
	protected String jsonBody = null;
	protected String response;
	protected int gameID;


// CONSTRUCTORS
	public ACommand(IServerFacade facade) {
		this.facade = facade;
	}
	
	public ACommand(String userJson, IServerFacade facade) throws ServerException {
		this.facade = facade;
		this.userJson = userJson;
		this.jsonDecode(userJson);
	}
	
	public ACommand(String userJson, IServerFacade facade, int gameID) throws ServerException {
		this.gameID = gameID;
		this.facade = facade;
		this.userJson = userJson;
		this.jsonDecode(userJson);
	}
	

// Protected GETTERS
	protected Credentials getCredentials() {
		return this.credentials;
	}
	
	
	protected IServerFacade getFacade() {
		return this.facade;
	}
	
	public int getGameID() {
		return this.gameID;
	}
	
	public String getJsonBody() {
		return this.jsonBody;
	}
	
	public String getUserJson() {
		return this.userJson;
	}

// Private METHODS
	/**
	 * Decodes the user credentials from the catan.user json within the cookie
	 * 
	 * @param userJson
	 * @throws ServerException 
	 */
	private void jsonDecode(String userJson) throws ServerException { // TODO parse the playerid
		try {
			JsonObject jsonObject = new JsonParser().parse(userJson).getAsJsonObject();
			String username;
			if (jsonObject.has("username")) {
				username = jsonObject.get("username").getAsString();
			}
			else {
				username = jsonObject.get("name").getAsString();
			}
			String password = jsonObject.get("password").getAsString();
			this.credentials = new Credentials(username, password);
			if (jsonObject.has("playerID")) {
				this.credentials.playerID = jsonObject.get("playerID").getAsInt();
			}
		} catch (NullPointerException e) {
			throw new ServerException("");
		}
	}


	
// Public METHODS
	/**
	 * Initiate the command. Calls the server facade. Throws an exception if the pre-conditions
	 * 	have not been met.
	 * 
	 * @return
	 * @throws server.ServerException 
	 */
	public abstract void execute() throws server.ServerException;
	
	
	/**
	 * Returns the response body for the server to use
	 * 
	 * @return
	 */
	public String getResponse() {
		return this.response;
	}
	
	
	/**
	 * If appropriate, returns the cookie for the server to set on the client.
	 * 	If not, throws an exception
	 * 
	 * @return
	 * @throws ServerException
	 */
	public String getCookie() throws ServerException {
		throw new ServerException("Invalid call");
	}
}
