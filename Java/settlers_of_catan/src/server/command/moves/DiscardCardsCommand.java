package server.command.moves;


import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.DiscardedCards;

/**
 * Command for discarding cards
 * 	Server end-point: /moves/discardCards POST
 * 
 * @author benthompson & Bo Pace
 */
public class DiscardCardsCommand extends ACommand {
	
	DiscardedCards discardedCards;

	/**
	 * {
		  "type": "discardCards",
		  "playerIndex": "integer",
		  "discardedCards": {
		    "brick": "integer",
		    "ore": "integer",
		    "sheep": "integer",
		    "wheat": "integer",
		    "wood": "integer"
		  }
		}
	 * 
	 * @param cookies
	 * @param facade
	 * @param jsonBody
	 * @throws ServerException 
	 */
	public DiscardCardsCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game")));
		this.jsonBody = jsonBody;
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		discardedCards = new DiscardedCards(
				json.get("playerIndex").getAsInt(),
				json.get("discardedCards").getAsJsonObject().get("sheep").getAsInt(),
				json.get("discardedCards").getAsJsonObject().get("wood").getAsInt(),
				json.get("discardedCards").getAsJsonObject().get("ore").getAsInt(),
				json.get("discardedCards").getAsJsonObject().get("wheat").getAsInt(),
				json.get("discardedCards").getAsJsonObject().get("brick").getAsInt()
		);
	}

	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().discardCards(discardedCards, this.getGameID());
	}

}
