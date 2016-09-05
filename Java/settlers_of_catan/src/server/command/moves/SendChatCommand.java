package server.command.moves;


import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.communication.proxy.SendChat;

/**
 * Command for sending a chat
 * 	Server end-point: /moves/sendChat POST
 * 
 * @author benthompson & Bo Pace
 */
public class SendChatCommand extends ACommand {
	
	SendChat sendChat;

	/**
	 * {
		  "type": "sendChat",
		  "playerIndex": "integer",
		  "content": "string"
		}
	 * 
	 * @param cookies
	 * @param facade
	 * @param jsonBody
	 * @throws ServerException 
	 */
	public SendChatCommand(Map<String, String> cookies, IServerFacade facade, String jsonBody) throws ServerException {
		super(cookies.get("catan.user"), facade, Integer.parseInt(cookies.get("catan.game")));
		this.jsonBody = jsonBody;
		JsonObject json = new JsonParser().parse(jsonBody).getAsJsonObject();
		sendChat = new SendChat(
			json.get("playerIndex").getAsInt(),
			json.get("content").getAsString()
		);
	}

	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().sendChat(sendChat, this.getGameID());
	}

}