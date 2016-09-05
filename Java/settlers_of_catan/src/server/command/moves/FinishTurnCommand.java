package server.command.moves;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.FinishTurn;

/**
 * Command for finishing a player's turn
 * 	Server end-point: /moves/finishTurn POST
 * 
 * @author benthompson & Bo Pace
 */
public class FinishTurnCommand extends ACommand {
	
	FinishTurn finishTurn;

	/**
	 * {
		  "type": "finishTurn",
		  "playerIndex": "integer"
		}
	 * 
	 * @param cookies
	 * @param facade
	 * @param jsonBody
	 * @throws ServerException 
	 */
	public FinishTurnCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game"))); 
		this.jsonBody = jsonBody;
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		finishTurn = new FinishTurn(
			json.get("playerIndex").getAsInt()
		);
	}

	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().finishTurn(finishTurn, this.getGameID());
	}

}
