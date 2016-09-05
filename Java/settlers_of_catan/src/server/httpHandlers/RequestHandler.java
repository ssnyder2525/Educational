package server.httpHandlers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.ServerException;
import server.command.ACommand;
import server.command.CommandFactory;
import server.database.DatabaseException;
import server.database.IPersistencePlugin;
import server.facade.IServerFacade;
import server.facade.ServerFacade;
import server.managers.User;
import shared.communication.proxy.Credentials;
import shared.models.Game;
import shared.serializerJSON.Deserializer;
import shared.serializerJSON.Serializer;

/**
 * Handles the HTTP requests for the server. Calls the appropriate factory based on the url of the request
 * @author ssnyder
 *
 */
public class RequestHandler implements HttpHandler
{

	Logger logger = Logger.getLogger("settlers");
	IServerFacade facade;
	IPersistencePlugin plugin;
	

// Constructor
	public RequestHandler(IServerFacade facade, IPersistencePlugin plugin) {
		this.facade = facade;
		this.plugin = plugin;
	}
	
	
	
// Implemented Method
	/**
	 * Handles the request from the client. Will call the appropriate factory
	 * based on the first part of the url.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		
		try {
			
			this.validateHTTPMethod(exchange.getRequestMethod(), this.getCommandType(exchange));
			//System.out.println(this.getCommandType(exchange)[1]);
			
			String[] commandType = this.getCommandType(exchange);
			String json = this.getJson(exchange);
			HashMap<String, String> cookies = (HashMap<String, String>)this.parseCookie(exchange);
			String requestMethod = exchange.getRequestMethod();
			
			String commandJson = Serializer.getInstance().serializeCommand(
				commandType,
				json,
				cookies,
				requestMethod
			);
			
			JsonParser parser = new JsonParser();
			JsonObject commandJO = parser.parse(commandJson).getAsJsonObject();
			
			ACommand command = CommandFactory.getInstance().buildCommand(
				commandType,
				json,
				cookies,
				this.facade,
				requestMethod
			);
			
			// TODO - make this the response with the appropriate cookie
			command.execute();
			
			if (commandJO.getAsJsonObject("type").get("path2").getAsString().equals("register")) {
				this.addUser(commandJson);
			} else if (commandJO.getAsJsonObject("type").get("path2").getAsString().equals("create")) {
				this.saveGame(command.getGameID());
			} else if (commandJO.getAsJsonObject("type").get("path2").getAsString().equals("join")) {
				this.addCommand(command.getGameID(), commandJson);
			} else if (commandJO.getAsJsonObject("type").get("path1").getAsString().equals("moves")) {
				this.addCommand(command.getGameID(), commandJson);
			}
			
			sendResponse(command, exchange);
			
		} catch (ServerException e) {
			e.printStackTrace();
			this.handleError(exchange, e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendResponse(ACommand command, HttpExchange exchange) throws IOException, ServerException {
		String commandType = this.getCommandType(exchange)[1];
		String response = command.getResponse();
		
		if (commandType.equals("login") || commandType.equals("register") || commandType.equals("join")) {
			exchange.getResponseHeaders().set("set-cookie", command.getCookie());
		}
		
		ArrayList<String> mimetypes = new ArrayList<String>();
		addMimeTypes(mimetypes, commandType);
		
		exchange.getResponseHeaders().put("Content-Type", mimetypes);
		exchange.sendResponseHeaders(200, response.length());
		OutputStream os = exchange.getResponseBody(); 
		os.write(response.getBytes());
		os.close();
	}
	
	private void addMimeTypes(ArrayList<String> mimetypes, String commandType) {
		switch (commandType.toLowerCase()) {
			case "login":
			case "register":
			case "join":
			case "listAI":
			case "addAI":
				mimetypes.add("text/html");
				break;
			default:
				mimetypes.add("application/json");
				break;		
		}
	}
	
	
	
// Private METHODS
	/**
	 * Get's the cookie from a given HttpExchange object
	 * 
	 * @param exchange
	 * @return
	 * @throws ServerException 
	 */
	Map<String, String> parseCookie(HttpExchange exchange) throws ServerException {
		
		Map<String, String> cookies = new HashMap<String, String>();
		List<String> cookieList = exchange.getRequestHeaders().get("Cookie");
		
		if (cookieList == null) {
			return cookies;
		}
		
		for (String cookie : cookieList) {
			for (String subCookie: cookie.split(";"))
			{
				try {
					String decodedCookie = URLDecoder.decode(subCookie, "UTF-8"); 
					String[] values =  decodedCookie.split("=");
					cookies.put(values[0].trim(), values[1].trim());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					throw new ServerException("Unable to decode cookie: " + cookie);
				}
			}
		}
		return cookies;
	}
	
	
	/**
	 * Get's the json body from a given HttpExchange object
	 * 
	 * @param exchange
	 * @return
	 * @throws IOException
	 */
	String getJson(HttpExchange exchange) throws IOException {
		return IOUtils.toString(exchange.getRequestBody());
	}
	
	
	/**
	 * Get's the command type from the second folder of a uri. Uses the given 
	 * 	HttpExchange object to get the command
	 * 
	 * @param exchange
	 * @return
	 * @throws ServerException
	 */
	String[] getCommandType(HttpExchange exchange) throws ServerException {

		String[] path = exchange.getRequestURI().toString().split("/");
		if (path.length != 3) {
			throw new ServerException("Invalid URL path length");
		}
		String[] commandType = {path[1], path[2]};
		return commandType;
	}
	
	
	/**
	 * Handles errors thrown from unexpected pre conditions
	 * 
	 * @param exchange
	 * @throws IOException
	 */
	void handleError(HttpExchange exchange, String errorMessage) throws IOException {
		logger.log(Level.SEVERE, "Bad request " + errorMessage);
		
		exchange.getResponseHeaders().add("Content-Type", "application/text");
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
		exchange.getResponseBody().write(errorMessage.getBytes());
		exchange.getResponseBody().close();
		exchange.close();
	}
	
	
	/**
	 * Validates that only GET's are /games/list, /game/model, /game/listAI. Everything else should be a POST
	 * 
	 * @param method
	 * @param type
	 * @throws ServerException
	 */
	void validateHTTPMethod(String method, String[] type) throws ServerException {
		
		if (type[0].equals("games") && type[1].equals("list") || 
				type[0].equals("game") && type[1].equals("model") || 
				type[0].equals("game") && type[1].equals("listAI")) { // These are the only allowed GETs
			if (method.toUpperCase().equals("GET")) {
				return;
			}
		}
		else {
			if (method.toUpperCase().equals("POST")) { // Everything else should be a post
				return;
			}
		}
		
		throw new ServerException("Invalid HTTP method");
	}
	
	/**
	 * Saves a game to the database.
	 * @param gameID The ID of the game being saved.
	 * @param jsonGame The serialized game in the form of a JSON string.
	 */
	public void saveGame(int gameID) {
		String model = facade.getModel(gameID);
		JsonParser parser = new JsonParser();
		JsonObject gameJson = parser.parse(model).getAsJsonObject();
		Game game = new Game();
		Deserializer.getInstance().deserializeSavedGame(game, gameJson);
		plugin.startTransaction();
		try {
			plugin.getGameDAO().saveGame(facade.getGameByID(gameID));
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		plugin.endTransaction();
	}
	
	/**
	 * Queries the persistence plugin to see if the change threshold has been met for a game.
	 * @param gameID The game that we want to check.
	 * @return If the change threshold has been met (true or false).
	 */
	public boolean hasReachedDelta(int gameID) {
		try {
			plugin.startTransaction();
			int count;
			count = plugin.getCommandDAO().getCommandCount(gameID);
			plugin.getCommandDAO();
			if(count >= plugin.getCommandDAO().getDelta()) {
				plugin.endTransaction();
				return true;
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		plugin.endTransaction();
		return false;
	}
	
	/**
	 * Adds a command to a specified game.
	 * @param gameID The id of the game we're saving the command to.
	 * @param jsonCommand A serialized command object in the form of a JSON string.
	 */
	public void addCommand(int gameID, String jsonCommand) {
		try {
			if (!hasReachedDelta(gameID)) {
				plugin.startTransaction();
				plugin.getCommandDAO().createCommand(gameID, jsonCommand);
			} else {
				//saveGame(gameID);
				plugin.startTransaction();
				plugin.getGameDAO().saveGame(facade.getGameByID(gameID));
				plugin.getCommandDAO().clearCommands(gameID);
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		plugin.endTransaction();
	}
	
	/**
	 * Add a user to the database.
	 * @param jsonUser A serialized user in the form of a JSON string.
	 */
	public void addUser(String jsonUser) {
		JsonParser parser = new JsonParser();
		JsonObject userInfo = parser.parse(jsonUser).getAsJsonObject();
		JsonObject body = parser.parse(userInfo.get("body").getAsString()).getAsJsonObject();
		String username = body.get("username").getAsString();
		String password = body.get("password").getAsString();
		Credentials cred = new Credentials(username, password);
		
		plugin.startTransaction();
		
		try {
			int userID = facade.getPlayerIDFromCredentials(cred);
			User user = new User(cred, userID);
			plugin.getUserDAO().createUser(user);
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		plugin.endTransaction();
	}
}
