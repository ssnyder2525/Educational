package server.command.moves;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.BuildRoad;

/**
 * Command for building a road
 * 	Server end-point: /moves/buildRoad POST
 * 
 * @author benthompson & Bo Pace
 */
public class BuildRoadCommand extends ACommand {
	
	BuildRoad buildRoad;

	/**
	 * {
		  "type": "buildRoad",
		  "playerIndex": "integer",
		  "roadLocation": {
		    "x": "integer",
		    "y": "integer",
		    "direction": "string"
		  },
		  "free": "Boolean"
		}
	 * 
	 * @param cookies
	 * @param facade
	 * @param jsonBody
	 * @throws ServerException 
	 */
	public BuildRoadCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game"))); 
		this.jsonBody = jsonBody;
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		buildRoad = new BuildRoad(
			json.get("playerIndex").getAsInt(),
			json.get("free").getAsBoolean(),
			json.get("roadLocation").getAsJsonObject().get("x").getAsInt(),
			json.get("roadLocation").getAsJsonObject().get("y").getAsInt(),
			json.get("roadLocation").getAsJsonObject().get("direction").getAsString()
		);
	}

	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().buildRoad(buildRoad, this.getGameID());
	}

}
