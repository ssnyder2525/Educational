package server.command.game;

import java.util.Map;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;

/**
 * Command for adding an AI to a given game
 * 	Server end-point: /game/addAI POST
 * 
 * @author benthompson
 */
public class AddAICommand extends ACommand {

	/**
	 * {
		  "AIType": "string"
		}
	 * 
	 * @param cookies
	 * @param facade
	 * @param jsonBody
	 * @throws ServerException  
	 */
	public AddAICommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game")));
		this.jsonBody = jsonBody;
	}

	@Override
	public void execute() {
		return;
	}

	@Override
	public String getResponse() {
		return null;
	}

	@Override
	public String getCookie() {
		return null;
	}

}
