package server.command.games;


import java.util.Map;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;

/**
 * Command for getting the list of games on the server
 * 	Server end-point: /games/list GET
 * 
 * @author benthompson
 */
public class ListCommand extends ACommand {

	public ListCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(facade);
		this.jsonBody = jsonBody;
	}
	
	
	@Override
	public void execute() {
		System.out.println("ListCommand execute");
		// TODO use this.getCredentials() to verify that this is an actual user
		this.response = this.getFacade().getGamesList();
	}

}
