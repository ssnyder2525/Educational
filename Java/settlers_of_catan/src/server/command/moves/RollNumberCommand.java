package server.command.moves;


import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.RollNumber;

/**
 * Command for rolling a number
 * 	Server end-point: /moves/rollNumber POST
 * 
 * @author benthompson & Bo Pace
 */
public class RollNumberCommand extends ACommand {
	
	RollNumber rollNumber;

	/**
	 * {
		  "type": "rollNumber",
		  "playerIndex": "integer",
		  "number": "integer"
		}
	 * 
	 * @param cookies
	 * @param facade
	 * @param jsonBody
	 * @throws ServerException 
	 */
	public RollNumberCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game")));
		this.jsonBody = jsonBody;
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		rollNumber = new RollNumber(
			json.get("playerIndex").getAsInt(), json.get("number").getAsInt()	
		);
	}

	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().rollNumber(rollNumber, this.getGameID());
	}

}
