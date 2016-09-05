package server.command.moves;


import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.YearOfPlenty;
import shared.definitions.ResourceType;

/**
 * Command for for playing a year of plenty card
 * 	Server end-point: /moves/Year_Of_Plenty POST
 * 
 * @author benthompson & Bo Pace
 */
public class YearOfPlentyCommand extends ACommand {
	
	YearOfPlenty yearOfPlenty;

	/**
	 * {
		  "type": "Year_of_Plenty",
		  "playerIndex": "integer",
		  "resource1": "Resource",
		  "resource2": "Resource"
		}
	 * 
	 * @param cookies
	 * @param facade
	 * @param jsonBody
	 * @throws ServerException 
	 */
	public YearOfPlentyCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game")));
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		this.jsonBody = jsonBody;
		ResourceType type1 = null;
		try {
			type1 = ResourceType.valueOf(json.get("resource1").getAsString().toUpperCase());
		} catch (Exception e) {
			type1 = null;
		}
		
		ResourceType type2 = null;
		try {
			type2 = ResourceType.valueOf(json.get("resource2").getAsString().toUpperCase());
		} catch (Exception e) {
			type2 = null;
		}
		
		yearOfPlenty = new YearOfPlenty(
			json.get("playerIndex").getAsInt(),
			type1,
			type2
		);
	}

	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().yearOfPlenty(yearOfPlenty, this.getGameID());
	}

}
