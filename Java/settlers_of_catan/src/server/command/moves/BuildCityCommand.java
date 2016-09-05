package server.command.moves;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.BuildCity;

/**
 * Command for building a city
 * 	Server end-point: /moves/buildCity POST
 * 
 * @author benthompson & Bo Pace
 */
public class BuildCityCommand extends ACommand {
	
	BuildCity buildCity;

	/**
	 * {
		  "type": "buildCity",
		  "playerIndex": "integer",
		  "vertexLocation": {
		    "x": "integer",
		    "y": "integer",
		    "direction": "string"
		  }
		}
	 * 
	 * @param cookies
	 * @param facade
	 * @param jsonBody
	 * @throws ServerException 
	 */
	public BuildCityCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game"))); 
		this.jsonBody = jsonBody;
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		buildCity = new BuildCity(
			json.get("playerIndex").getAsInt(),
			json.get("vertexLocation").getAsJsonObject().get("x").getAsInt(),
			json.get("vertexLocation").getAsJsonObject().get("y").getAsInt(),
			json.get("vertexLocation").getAsJsonObject().get("direction").getAsString()
		);
	}

	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().buildCity(buildCity, this.getGameID());
	}

}
