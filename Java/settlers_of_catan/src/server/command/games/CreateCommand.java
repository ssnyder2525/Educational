package server.command.games;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.CreateGameRequestParams;

/**
 * Command for creating a new game
 * 	Server end-point: /games/create POST
 * 
 * @author benthompson
 */
public class CreateCommand extends ACommand {
	
	CreateGameRequestParams params;
	
	
	/**
	 * 
	  	{
		  "randomTiles": "boolean",
		  "randomNumbers": "boolean",
		  "randomPorts": "boolean",
		  "name": "string"
		}
	 * 
	 * 
	 * @param username
	 * @param password
	 * @param title
	 * @param id
	 * @param players
	 * @throws ServerException 
	 */
	public CreateCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		
		super(facade);
		this.jsonBody = jsonBody;
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		params = new CreateGameRequestParams(
			json.get("name").getAsString(),
			json.get("randomTiles").getAsBoolean(),
			json.get("randomNumbers").getAsBoolean(),
			json.get("randomPorts").getAsBoolean()
		);
	}

	@Override
	public void execute() {
		this.response = this.getFacade().createGame(params);
		String jsonResponse = this.response;
		JsonParser parser = new JsonParser();
		JsonObject info = parser.parse(jsonResponse).getAsJsonObject();
		this.gameID = info.get("id").getAsInt();
	}
}
