package server.command.moves;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.AcceptTrade;

/**
 * Command for accepting a trade
 * 	Server end-point: /moves/acceptTrade POST
 * 
 * @author benthompson & Bo Pace
 */
public class AcceptTradeCommand extends ACommand {
	
	AcceptTrade acceptTrade;

	/**
	 * {
		  "type": "acceptTrade",
		  "playerIndex": "integer",
		  "willAccept": "boolean"
		}
	 * 
	 * @param cookies
	 * @param facade
	 * @param jsonBody
	 * @throws ServerException 
	 */
	public AcceptTradeCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game")));
		this.jsonBody = jsonBody;
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		acceptTrade = new AcceptTrade(
			json.get("playerIndex").getAsInt(),
			json.get("willAccept").getAsBoolean()
		);
	}

	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().acceptTrade(acceptTrade, this.getGameID());
	}

}
