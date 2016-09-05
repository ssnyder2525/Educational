package client.serverProxy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shared.communication.proxy.AcceptTrade;
import shared.communication.proxy.BuildCity;
import shared.communication.proxy.BuildRoad;
import shared.communication.proxy.BuildSettlement;
import shared.communication.proxy.BuyDevCard;
import shared.communication.proxy.ChangeLogLevelRequest;
import shared.communication.proxy.CreateGameRequestParams;
import shared.communication.proxy.Credentials;
import shared.communication.proxy.DiscardedCards;
import shared.communication.proxy.FinishTurn;
import shared.communication.proxy.JoinGameRequestParams;
import shared.communication.proxy.ListOfCommands;
import shared.communication.proxy.LoadGameRequestParams;
import shared.communication.proxy.MaritimeTrade;
import shared.communication.proxy.Monopoly;
import shared.communication.proxy.MonumentMove;
import shared.communication.proxy.OfferTrade;
import shared.communication.proxy.RoadBuilding;
import shared.communication.proxy.RobPlayer;
import shared.communication.proxy.RollNumber;
import shared.communication.proxy.SaveGameRequestParams;
import shared.communication.proxy.SendChat;
import shared.communication.proxy.SoldierMove;
import shared.communication.proxy.YearOfPlenty;

/** RealProxy class
 * 
 * @author Cody Burt
 *
 */
public class RealProxy implements ProxyInterface {

	//Usually localhost:8081
	private String server_url;
	
	private String usercookie;
	private String gamecookie;
	
	//Directions come from the facade as full words, we
	//need to abbreviate them before sending them to the server
	private Map<String, String> directions;
	
	
	
	public RealProxy(String url_in) {
		server_url = url_in;
		directions = new HashMap<String, String>();
		directions.put("NorthWest", "NW");
		directions.put("North", "N");
		directions.put("NorthEast", "NE");
		directions.put("SouthWest", "SW");
		directions.put("South", "S");
		directions.put("SouthEast", "SE");
	}
	
	public String getUserCookie() {
		@SuppressWarnings("deprecation")
		String decodedUserCookie = URLDecoder.decode(usercookie.substring(11));
		return decodedUserCookie;
	}
	
	
	/**
	 * This function will call the server API at 
	 * user / login
	 * 
	 * @param Credentials object that contains the username and password
	 * @return JSON String of the format:
	 * name: String,
	 * password: String,
	 * playerID: Integer
	 */ 
	public String loginUser(Credentials credentials) throws Exception {

		String url = server_url + "/user/login";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		//String urlParameters = "username=" + credentials.username + "&password=" + credentials.password;
		JsonObject urlParameters = new JsonObject();
		urlParameters.addProperty("username", credentials.username);
		urlParameters.addProperty("password", credentials.password);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		
		if (responseCode != 200) {
			return "Failure";
		}
		////System.out.println("\nSending 'POST' request to URL : " + url);
		////System.out.println("Post parameters : " + urlParameters);
		////System.out.println("Response Code : " + responseCode);
		
		if (responseCode == 400) {
			throw new Exception();
		}
		
		//When we log in the server gives us a cookie that keeps the user identity
		usercookie = con.getHeaderField("set-cookie");
		usercookie = usercookie.substring(0, usercookie.length() - 8);

//		@SuppressWarnings("deprecation")
//		String decodedUserCookie = URLDecoder.decode(usercookie.substring(11));
//		
//		JsonParser jsonParser = new JsonParser();
//		JsonObject element = jsonParser.parse(decodedUserCookie).getAsJsonObject();

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * user / register
	 * @param Credentials object that contains the username and password
	 * @return JSON String that indicates success/failure
	 * 
	 */
	public String registerUser(Credentials credentials) throws Exception {
		
		String url = server_url + "/user/register";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		//String urlParameters = "username=" + credentials.username + "&password=" + credentials.password;
		JsonObject urlParameters = new JsonObject();
		urlParameters.addProperty("username", credentials.username);
		urlParameters.addProperty("password", credentials.password);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
//		//System.out.println("\nSending 'POST' request to URL : " + url);
//		//System.out.println("Post parameters : " + urlParameters);
//		//System.out.println("Response Code : " + responseCode);
		
		if (responseCode == 400) {
			throw new Exception();
		}

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * games / list
	 * 
	 * @return JSON String that indicates success/failure
	 * 
	 */
	public String getGamesList() throws Exception {
		
		String url = server_url + "/games/list";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional, default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
//		//System.out.println("\nSending 'GET' request to URL : " + url);
//		//System.out.println("Response Code : " + responseCode);
		
		if (responseCode == 400) {
			throw new Exception();
		}

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * games / create
	 * @param CreateGameRequestParams object that contains the name of 
	 * the game and settings for randomTiles, randomNumbers, and
	 * randomPorts
	 * @return JSON String that contains the game's title, id, and 
	 * a list of empty players
	 */
	public String createGame(CreateGameRequestParams params) throws Exception { 
		
		String url = server_url + "/games/create";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		//String urlParameters = "name=" + params.name + "&randomTiles=" + params.randomTiles + "&randomNumbers=" + params.randomNumbers + "&randomPorts=" + params.randomPorts;
		JsonObject urlParameters = new JsonObject();
		urlParameters.addProperty("name", params.name);
		urlParameters.addProperty("randomTiles", params.randomTiles);
		urlParameters.addProperty("randomNumbers", params.randomNumbers);
		urlParameters.addProperty("randomPorts", params.randomPorts);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
//		//System.out.println("\nSending 'POST' request to URL : " + url);
//		//System.out.println("Post parameters : " + urlParameters);
//		//System.out.println("Response Code : " + responseCode);
		
		if (responseCode == 400) {
			throw new Exception();
		}

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * games / join
	 * @param JoinGameRequestParams object that contains the id of the
	 * game the player wants to join and the color they want to be
	 * @return JSON String that indicates whether it was a success or
	 * failure
	 */
	public String joinGame(JoinGameRequestParams params) throws Exception {
		
		String url = server_url + "/games/join";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie);

		//String urlParameters = "id=" + params.id + "&color=" + params.color;
		JsonObject urlParameters = new JsonObject();
		urlParameters.addProperty("id", params.id);
		urlParameters.addProperty("color", params.color);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		////System.out.println("\nSending 'POST' request to URL : " + url);
		////System.out.println("Post parameters : " + urlParameters);
		////System.out.println("Response Code : " + responseCode);
		
		if (responseCode == 400) {
			throw new Exception();
		}

		gamecookie = con.getHeaderField("set-cookie");
		gamecookie = gamecookie.substring(0, gamecookie.length() - 8);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * games / save
	 * @param SaveGameRequestParams object that contains the id of the
	 * game the player wants to join and file name they want to save it as
	 * @return JSON String that indicates whether it was a success or
	 * failure
	 */
	//Will never be called here
	public String saveGame(SaveGameRequestParams saveGameRequest){return "";}
	
	/**
	 * This function will call the server API at
	 * games / load
	 * @param LoadGameRequestParams object that contains the file name 
	 * they want to load from
	 * @return JSON String that indicates whether it was a success or
	 * failure
	 */
	//Will never be called here
	public String loadGame(LoadGameRequestParams loadGameRequest){return "";}
	
	/**
	 * This function will call the server API at
	 * game / model
	 * @param The version number of the current state
	 * @return JSON String that contains the current game state
	 */
	public String getGameModel(int versionNumber) {
		try {
			String url = server_url + "/game/model";
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
			// optional default is GET
			con.setRequestMethod("GET");
	
			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
	
			int responseCode = con.getResponseCode();
//			//System.out.println("\nSending 'GET' request to URL : " + url);
//			//System.out.println("Response Code : " + responseCode);
			
			if (responseCode == 400) {
				throw new Exception();
			}
	
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	
			//print result
			////System.out.println(response.toString());
			return response.toString();
		}
		catch (Exception e) {
			return "error";
		}
	}
	
	/**
	 * This function will call the server API at
	 * game / reset
	 * @return JSON String that contains the current game state
	 */
	public String resetGame() throws Exception {
		String url = server_url + "/game/reset";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);

		String urlParameters = "";
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'POST' request to URL : " + url);
//		System.out.println("Post parameters : " + urlParameters);
//		System.out.println("Response Code : " + responseCode);
		
		if (responseCode == 400) {
			throw new Exception();
		}

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		//////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * game / commands
	 * @param ListOfCommands object that contains the desired
	 * commands to be executed
	 * @return JSON String that contains the client model after
	 * that list of commands have been executed
	 */
	//Will never be called here
	public String executeGameCommands(ListOfCommands listOfCommands){return "";}
	
	/**
	 * This function will call the server API at
	 * game / commands
	 * @return JSON String that contains list of commands
	 * executed in the game
	 */
	//Will never be called here
	public String getGameCommands(){return "";}
	
	/**
	 * This function will call the server API at
	 * moves / sendChat
	 * @param SendChat object that contains the player index
	 * of the message sender and the message content
	 * @return JSON String that contains the client model
	 */
	public String sendChat(SendChat sendChat) throws Exception {
		String url = server_url + "/moves/sendChat";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","sendChat");
		info.addProperty("playerIndex", sendChat.playerIndex);
		info.addProperty("content", sendChat.content);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / rollNumber
	 * @param RollNumber object that contains the player index
	 * and what number they rolled
	 * @return JSON String that contains the client model
	 */
	public String rollNumber(RollNumber rollNumber) throws Exception {
		String url = server_url + "/moves/rollNumber";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","rollNumber");
		info.addProperty("playerIndex", rollNumber.playerIndex);
		info.addProperty("number", rollNumber.roll);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / robPlayer
	 * @param RobPlayer object that contains the index of the
	 * player robbing, and the new location of the robber
	 * @return JSON String that contains the client model
	 */
	public String robPlayer(RobPlayer robPlayer) throws Exception {
		String url = server_url + "/moves/robPlayer";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		//System.out.println(usercookie + "; " + gamecookie);
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","robPlayer");
		info.addProperty("playerIndex", robPlayer.playerIndex);
		info.addProperty("victimIndex", robPlayer.victimIndex);
		JsonObject loc = new JsonObject();
		loc.addProperty("x", robPlayer.newLocation.getX());
		loc.addProperty("y", robPlayer.newLocation.getY());
		info.add("location", loc);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / finishTurn
	 * @param FinishTurn object that contains the player index
	 * that's ending their turn
	 * @return JSON String that contains the client model
	 */
	public String finishTurn(FinishTurn finishTurn) throws Exception {
		String url = server_url + "/moves/finishTurn";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		//System.out.println(usercookie + "; " + gamecookie);
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","finishTurn");
		info.addProperty("playerIndex", finishTurn.playerIndex);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / buyDevCard
	 * @param BuyDevCard object that contains the player index
	 * buying the card
	 * @return JSON String that contains the client model
	 */
	public String buyDevCard(BuyDevCard buyDevCard) throws Exception {
		String url = server_url + "/moves/buyDevCard";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		//System.out.println(usercookie + "; " + gamecookie);
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","buyDevCard");
		info.addProperty("playerIndex", buyDevCard.playerIndex);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / Year_Of_Plenty
	 * @param YearOfPlenty object that contains the player index
	 * playing the card and the two resources they gain
	 * @return JSON String that contains the client model
	 */
	public String yearOfPlenty(YearOfPlenty yearOfPlenty) throws Exception {
		String url = server_url + "/moves/Year_of_Plenty";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","Year_of_Plenty");
		info.addProperty("playerIndex", yearOfPlenty.playerIndex);
		info.addProperty("resource1", yearOfPlenty.resourceOne.name().toLowerCase());
		info.addProperty("resource2", yearOfPlenty.resourceTwo.name().toLowerCase());
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / Road_Building
	 * @param RoadBuilding object that contains the player index
	 * and the two locations they want to build roads
	 * @return JSON String that contains the client model
	 */
	public String roadBuilding(RoadBuilding roadBuilding) throws Exception {
		String url = server_url + "/moves/Road_Building";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","Road_Building");
		info.addProperty("playerIndex", roadBuilding.playerIndex);
		
		JsonObject loc1 = new JsonObject();
		loc1.addProperty("x", roadBuilding.firstSpot.getHexLoc().getX());
		loc1.addProperty("y", roadBuilding.firstSpot.getHexLoc().getY());
		loc1.addProperty("direction", directions.get(roadBuilding.firstSpot.getDir().name()));
		info.add("spot1", loc1);
		
		JsonObject loc2 = new JsonObject();
		loc2.addProperty("x", roadBuilding.secondSpot.getHexLoc().getX());
		loc2.addProperty("y", roadBuilding.secondSpot.getHexLoc().getY());
		loc2.addProperty("direction", directions.get(roadBuilding.secondSpot.getDir().name()));
		info.add("spot2", loc2);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / Soldier
	 * @param SoldierMove object that contains the player index
	 * doing the robbing, the player index of the one they're
	 * robbing, and the new location of the robber
	 * @return JSON String that contains the client model
	 */
	public String moveSoldier(SoldierMove soldierMove) throws Exception {
		String url = server_url + "/moves/Soldier";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","Soldier");
		info.addProperty("playerIndex", soldierMove.playerIndex);
		info.addProperty("victimIndex", soldierMove.victimIndex);
		
		JsonObject loc = new JsonObject();
		loc.addProperty("x", soldierMove.newLocation.getX());
		loc.addProperty("y", soldierMove.newLocation.getY());
		info.add("location", loc);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / Monopoly
	 * @param Monopoly object that contains the player index
	 * and the resource they will monopolize
	 * @return JSON String that contains the client model
	 */
	public String playMonopolyCard(Monopoly monopoly) throws Exception {
		String url = server_url + "/moves/Monopoly";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","Monopoly");
		info.addProperty("playerIndex", monopoly.playerIndex);
		info.addProperty("resource", monopoly.resource.name().toLowerCase());
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / Monument
	 * @param MonumentMove object that contains the player index
	 * playing the monument card
	 * @return JSON String that contains the client model
	 */
	public String playMonumentCard(MonumentMove monumentMove) throws Exception {
		String url = server_url + "/moves/Monument";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","Monument");
		info.addProperty("playerIndex", monumentMove.playerIndex);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / buildRoad
	 * @param BuildRoad object that contains the player index
	 * building the road, the location where they want to
	 * build, and whether or not it's free or not
	 * @return JSON String that contains the client model
	 */
	public String buildRoad(BuildRoad buildRoad) throws Exception {
		String url = server_url + "/moves/buildRoad";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","buildRoad");
		info.addProperty("playerIndex", buildRoad.playerIndex);
		
		JsonObject loc = new JsonObject();
		loc.addProperty("x", buildRoad.roadLocation.getHexLoc().getX());
		loc.addProperty("y", buildRoad.roadLocation.getHexLoc().getY());
		loc.addProperty("direction", directions.get(buildRoad.roadLocation.getDir().name()));
		info.add("roadLocation", loc);
		
		info.addProperty("free", buildRoad.free);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / buildCity
	 * @param BuildCity object that contains the player index
	 * building the city and the location of the city
	 * @return JSON String that contains the client model
	 */
	public String buildCity(BuildCity buildCity) throws Exception {
		String url = server_url + "/moves/buildCity";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","buildCity");
		info.addProperty("playerIndex", buildCity.playerIndex);
		
		JsonObject loc = new JsonObject();
		loc.addProperty("x", buildCity.vertexLocation.getHexLoc().getX());
		loc.addProperty("y", buildCity.vertexLocation.getHexLoc().getY());
		loc.addProperty("direction", directions.get(buildCity.vertexLocation.getDir().name()));
		info.add("vertexLocation", loc);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / buildSettlement
	 * @param BuildSettlement object that contains the player index
	 * building the settlement, the location, and whether it's free
	 * @return JSON String that contains the client model
	 */
	public String buildSettlement(BuildSettlement buildSettlement) throws Exception {
		String url = server_url + "/moves/buildSettlement";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","buildSettlement");
		info.addProperty("playerIndex", buildSettlement.playerIndex);
		info.addProperty("free", buildSettlement.free);
		
		JsonObject loc = new JsonObject();
		loc.addProperty("x", buildSettlement.vertexLocation.getHexLoc().getX());
		loc.addProperty("y", buildSettlement.vertexLocation.getHexLoc().getY());
		loc.addProperty("direction", directions.get(buildSettlement.vertexLocation.getDir().name()));
		info.add("vertexLocation", loc);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / offerTrade
	 * @param OfferTrade object that contains the player index
	 * sending the offer, the player index receiving the offer,
	 * and the list resources offered and desired
	 * @return JSON String that contains the client model
	 */
	public String offerTrade(OfferTrade offerTrade) throws Exception {
		String url = server_url + "/moves/offerTrade";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","offerTrade");
		info.addProperty("playerIndex", offerTrade.playerIndex);
		info.addProperty("receiver", offerTrade.receiverIndex);
		
		JsonObject offer = new JsonObject();
		offer.addProperty("wood", offerTrade.wood);
		offer.addProperty("sheep", offerTrade.sheep);
		offer.addProperty("ore", offerTrade.ore);
		offer.addProperty("wheat", offerTrade.wheat);
		offer.addProperty("brick", offerTrade.brick);
		info.add("offer", offer);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / acceptTrade
	 * @param AcceptTrade object that contains the player index
	 * responding to the trade and whether they accept or reject it
	 * @return JSON String that contains the client model
	 */
	public String acceptTrade(AcceptTrade acceptTrade) throws Exception {
		String url = server_url + "/moves/acceptTrade";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","acceptTrade");
		info.addProperty("playerIndex", acceptTrade.playerIndex);
		info.addProperty("willAccept", acceptTrade.response);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / maritimeTrade
	 * @param MaritimeTrade object that contains the player index
	 * of the player trading, the ratio they're trading at, the
	 * desired resource and the offered resource
	 * @return JSON String that contains the client model
	 */
	public String maritimeTrade(MaritimeTrade maritimeTrade) throws Exception {
		String url = server_url + "/moves/maritimeTrade";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","maritimeTrade");
		info.addProperty("playerIndex", maritimeTrade.playerIndex);
		info.addProperty("ratio", maritimeTrade.ratio);
		info.addProperty("inputResource", maritimeTrade.givingUp.name().toLowerCase());
		info.addProperty("outputResource", maritimeTrade.getting.name().toLowerCase());
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * moves / discardedCards
	 * @param DiscardedCards object that contains the player index
	 * discarding cards and the list of resources they're discarding
	 * @return JSON String that contains the client model
	 */
	public String discardCards(DiscardedCards discardedCards) throws Exception {
		String url = server_url + "/moves/discardCards";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
		
		JsonObject info = new JsonObject();
		info.addProperty("type","discardCards");
		info.addProperty("playerIndex", discardedCards.playerIndex);
		
		JsonObject discarded = new JsonObject();
		discarded.addProperty("wood", discardedCards.wood);
		discarded.addProperty("sheep", discardedCards.sheep);
		discarded.addProperty("ore", discardedCards.ore);
		discarded.addProperty("wheat", discardedCards.wheat);
		discarded.addProperty("brick", discardedCards.brick);
		info.add("discardedCards", discarded);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(info.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + info.toString());
		//System.out.println("Response Code : " + responseCode);

		if (responseCode == 400) {
			throw new Exception();
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		////System.out.println(response.toString());
		return response.toString();
	}
	
	/**
	 * This function will call the server API at
	 * util / changeLogLevel
	 * @param ChangeLogLevelRequest that contains the server's
	 * new log level
	 * @return JSON String that indicates whether it succeded
	 */
	//Will never be implemented
	public String changeLogLevel(ChangeLogLevelRequest logLevel){return "";}


	@Override
	public String addAI(String aiType) {
		
		try {
			String url = server_url + "/game/addAI";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
			//add request header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
			
			JsonObject info = new JsonObject();
			info.addProperty("AIType", aiType);
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(info.toString());
			wr.flush();
			wr.close();
	
			int responseCode = con.getResponseCode();
	
			if (responseCode == 400) {
				throw new Exception();
			}
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//print result
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Failure";
	}
	

	@Override
	public ArrayList<String> getListAI() {

		try {
			String url = server_url + "/game/listAI";
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
			// optional default is GET
			con.setRequestMethod("GET");
	
			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Cookie", usercookie + "; " + gamecookie);
	
			int responseCode = con.getResponseCode();
	
			if (responseCode == 400) {
				throw new Exception();
			}
	
			BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream())
			);
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			
			JsonArray res = new JsonParser().parse(response.toString()).getAsJsonArray();
			
			ArrayList<String> listAI = new ArrayList<String>();
			
			for (int i = 0; i < res.size(); i ++) {
				listAI.add(res.get(i).getAsString());
			}
			
			return listAI;
		}
		catch (Exception e) {
		}
		return new ArrayList<String>();
	}
}
