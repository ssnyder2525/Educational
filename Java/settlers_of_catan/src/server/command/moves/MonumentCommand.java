package server.command.moves;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.MonumentMove;

/**
 * Command for playing a monument card
 * 	Server end-point: /moves/ POST
 * 
 * @author benthompson
 */
public class MonumentCommand extends ACommand {

	MonumentMove monumentMove;
	
	/**
	 * {
		  "type": "Monument",
		  "playerIndex": "integer"
		}
	 * 
	 * @param cookies
	 * @param facade
	 * @param jsonBody
	 * @throws ServerException 
	 */
	public MonumentCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game")));
		this.jsonBody = jsonBody;
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		monumentMove = new MonumentMove(
			json.get("playerIndex").getAsInt()
		);
	}

	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().playMonumentCard(monumentMove, this.getGameID());
	}
}
