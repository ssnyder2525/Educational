package server.command.moves;


import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.RobPlayer;
import shared.locations.HexLocation;

/**
 * Command for robbing a given player
 * 	Server end-point: /moves/robPlayer POST
 * 
 * @author benthompson & Bo Pace
 */
public class RobPlayerCommand extends ACommand {
	
	RobPlayer robPlayer;

	/**
	 * {
		  "type": "robPlayer",
		  "playerIndex": "integer",
		  "victimIndex": "integer",
		  "location": {
		    "x": "string",
		    "y": "string"
		  }
		}
	 * 
	 * @param cookies
	 * @param facade
	 * @param jsonBody
	 * @throws ServerException 
	 */
	public RobPlayerCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game"))); 
		this.jsonBody = jsonBody;
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		robPlayer = new RobPlayer(
			json.get("playerIndex").getAsInt(),
			json.get("victimIndex").getAsInt(),
			new HexLocation(json.get("location").getAsJsonObject().get("x").getAsInt(), json.get("location").getAsJsonObject().get("y").getAsInt())
		);
	}

	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().robPlayer(robPlayer, this.getGameID());
	}

}
