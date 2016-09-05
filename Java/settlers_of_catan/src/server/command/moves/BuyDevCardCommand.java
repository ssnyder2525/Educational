package server.command.moves;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.BuyDevCard;

/**
 * Command for buying a dev card
 * 	Server end-point: /moves/buyDevCard POST
 * 
 * @author benthompson & Bo Pace
 */
public class BuyDevCardCommand extends ACommand {
	
	BuyDevCard buyDevCard;

	/**
	 * {
		  "type": "buyDevCard",
		  "playerIndex": "integer"
		}
	 * 
	 * @param cookies
	 * @param facade
	 * @param jsonBody
	 * @throws ServerException 
	 */
	public BuyDevCardCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game"))); 
		this.jsonBody = jsonBody;
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		buyDevCard = new BuyDevCard(
			json.get("playerIndex").getAsInt()
		);
	}

	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().buyDevCard(buyDevCard, this.getGameID());
	}

}
