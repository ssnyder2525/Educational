package server.command.moves;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.BuildSettlement;

/**
 * Command for building a settlement
 * 	Server end-point: /moves/buildSettlement POST
 * 
 * @author benthompson & Bo Pace
 */
public class BuildSettlementCommand extends ACommand {
	
	BuildSettlement buildSettlement;

	/**
	 * {
		  "type": "buildSettlement",
		  "playerIndex": "integer",
		  "vertexLocation": {
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
	public BuildSettlementCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game")));
		this.jsonBody = jsonBody;
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		buildSettlement = new BuildSettlement(
			json.get("playerIndex").getAsInt(),
			json.get("free").getAsBoolean(),
			json.get("vertexLocation").getAsJsonObject().get("x").getAsInt(),
			json.get("vertexLocation").getAsJsonObject().get("y").getAsInt(),
			json.get("vertexLocation").getAsJsonObject().get("direction").getAsString()
		);
	}

	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().buildSettlement(buildSettlement, this.getGameID());
	}

}
