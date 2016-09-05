package server.command.user;

import java.net.URLEncoder;

import com.google.gson.JsonObject;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;

/**
 * Command used for user registration
 * 	Server end-point: /user/register POST
 * 
 * @author benthompson
 */
public class RegisterCommand extends ACommand {

	public RegisterCommand(String userJson, IServerFacade facade) throws ServerException {
		super(userJson, facade);
	}
	
	
	
	@Override
	public void execute() throws ServerException {
		this.response = this.getFacade().register(this.getCredentials());
	}


	@Override
	public String getCookie() {
		String cookie = "catan.user=";
		int playerID;
		
		try {
			 playerID = this.getFacade().getPlayerIDFromCredentials(this.getCredentials());
		}
		catch (Exception e) {
			return "";
		}
		
		JsonObject cookieJson = new JsonObject();
		cookieJson.addProperty("name", this.getCredentials().username);
		cookieJson.addProperty("password", this.getCredentials().password);
		cookieJson.addProperty("playerID", playerID);
		
		@SuppressWarnings("deprecation")
		String encodedCookie = URLEncoder.encode(cookieJson.toString());
		
		cookie += encodedCookie + ";Path=/;";
		
		return cookie;
	}

}
