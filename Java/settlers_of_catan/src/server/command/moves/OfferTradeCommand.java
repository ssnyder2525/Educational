package server.command.moves;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.OfferTrade;

/**
 * Command for offering a trade
 * 	Server end-point: /moves/offerTrade POST
 * 
 * @author benthompson & Bo Pace
 */
public class OfferTradeCommand extends ACommand {
	
	OfferTrade offerTrade;

	/**
	 * {
		  "type": "offerTrade",
		  "playerIndex": "integer",
		  "offer": {
		    "brick": "integer",
		    "ore": "integer",
		    "sheep": "integer",
		    "wheat": "integer",
		    "wood": "integer"
		  },
		  "receiver": "integer"
		}
	 * 
	 * @param cookies
	 * @param facade
	 * @param jsonBody
	 * @throws ServerException 
	 */
	public OfferTradeCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game")));
		this.jsonBody = jsonBody;
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		offerTrade = new OfferTrade(
			json.get("playerIndex").getAsInt(),
			json.get("receiver").getAsInt(),
			json.get("offer").getAsJsonObject().get("brick").getAsInt(),
			json.get("offer").getAsJsonObject().get("sheep").getAsInt(),
			json.get("offer").getAsJsonObject().get("ore").getAsInt(),
			json.get("offer").getAsJsonObject().get("wheat").getAsInt(),
			json.get("offer").getAsJsonObject().get("wood").getAsInt()
		);
	}

	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().offerTrade(offerTrade, this.getGameID());
	}

}
