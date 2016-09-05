package server.command.moves;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.SoldierMove;
import shared.locations.HexLocation;

/**
 * Command for playing a soldier card
 * 	Server end-point: /moves/Soldier POST
 * 
 * @author benthompson & Bo Pace
 */
public class SoldierCommand extends ACommand {
	
	SoldierMove soldierMove;

	/**
	 * {
		  "type": "Soldier",
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
	public SoldierCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game"))); 
		this.jsonBody = jsonBody;
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		soldierMove = new SoldierMove(
			json.get("playerIndex").getAsInt(),
			json.get("victimIndex").getAsInt(),
			new HexLocation(json.getAsJsonObject("location").get("x").getAsInt(), json.getAsJsonObject("location").get("y").getAsInt())
		);
	}

	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().moveSoldier(soldierMove, this.getGameID());
	}

}
