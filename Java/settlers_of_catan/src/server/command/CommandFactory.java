package server.command;

import java.util.Map;

import server.ServerException;
import server.command.game.AddAICommand;
import server.command.game.ListAICommand;
import server.command.game.ModelCommand;
import server.command.games.CreateCommand;
import server.command.games.JoinCommand;
import server.command.games.ListCommand;
import server.command.moves.AcceptTradeCommand;
import server.command.moves.BuildCityCommand;
import server.command.moves.BuildRoadCommand;
import server.command.moves.BuildSettlementCommand;
import server.command.moves.BuyDevCardCommand;
import server.command.moves.DiscardCardsCommand;
import server.command.moves.FinishTurnCommand;
import server.command.moves.MaritimeTradeCommand;
import server.command.moves.MonopolyCommand;
import server.command.moves.MonumentCommand;
import server.command.moves.OfferTradeCommand;
import server.command.moves.RoadBuildingCommand;
import server.command.moves.RobPlayerCommand;
import server.command.moves.RollNumberCommand;
import server.command.moves.SendChatCommand;
import server.command.moves.SoldierCommand;
import server.command.moves.YearOfPlentyCommand;
import server.command.user.LoginCommand;
import server.command.user.RegisterCommand;
import server.facade.IServerFacade;

/**
 * Factory for building a command object. This is a singleton
 * 
 * @author benthompson
 *
 */
public class CommandFactory {

	public CommandFactory() { }
	
	private static CommandFactory factory;
	
	
	/**
	 * Supports the singleton pattern
	 * 
	 * @return
	 */
	public static CommandFactory getInstance() {
		if (factory == null) {
			factory = new CommandFactory();
		}
		return factory;
	}
	

	/**
	 * Returns the appropriate command in relation to the given type and uri. Must be a valid 
	 * 	URI and and HTTP method
	 * 
	 * @param type
	 * @param jsonBody
	 * @param cookies
	 * @param facade
	 * @param httpMethod
	 * @return
	 * @throws ServerException
	 */
	public ACommand buildCommand (String[] type, String jsonBody, Map<String, String> cookies, 
									IServerFacade facade, String httpMethod) throws ServerException {
		
		ACommand command;
		
		switch (type[0]) {
			case "user":
				command = this.buildUserCommand(type[1], jsonBody, facade);
				break;
			case "games":
				command = this.buildGamesCommand(type[1], cookies, facade, jsonBody);
				break;
			case "game":
				command = this.buildGameCommand(type[1], cookies, facade, jsonBody);
				break;
			case "moves":
				command = this.buildMovesCommand(type[1], cookies, facade, jsonBody);
				break;
			default:
				throw new ServerException("Invalid uri");
		}
		return command;
	}
	
	
	/**
	 * Returns the appropriate /user/xx command
	 * 
	 * @param type
	 * @param json
	 * @param facade
	 * @return
	 * @throws ServerException
	 */
	ACommand buildUserCommand(String type, String json, IServerFacade facade) throws ServerException {

		switch(type) {
			case "login":
				return new LoginCommand(json, facade);
			case "register":
				return new RegisterCommand(json, facade);
			default:
				throw new ServerException("Invalid uri");
		}
	}
	
	
	/**
	 * Returns the appropriate /games/xx command
	 * 
	 * @param type
	 * @param userJson
	 * @param facade
	 * @param jsonBody
	 * @return
	 * @throws ServerException
	 */
	ACommand buildGamesCommand(String type, Map<String, String> cookies, IServerFacade facade, String jsonBody) 
																			throws ServerException {

		switch(type) {
			case "list": // GET
				return new ListCommand(cookies, facade, jsonBody);
			case "create":
				return new CreateCommand(cookies, facade, jsonBody);
			case "join":
				return new JoinCommand(cookies, facade, jsonBody);
			default:
				throw new ServerException("Invalid uri");
		}
	}
	
	
	/**
	 * Returns the appropriate /game/xx command
	 * 
	 * @param type
	 * @param userJson
	 * @param facade
	 * @param jsonBody
	 * @return
	 * @throws ServerException
	 */
	ACommand buildGameCommand(String type, Map<String, String> cookies, IServerFacade facade, String jsonBody) 
																			throws ServerException {

		switch(type) {
			case "model": // GET
				return new ModelCommand(cookies, facade, jsonBody);
			case "addAI":
				return new AddAICommand(cookies, facade, jsonBody);
			case "listAI": // GET
				return new ListAICommand(cookies, facade, jsonBody);
			default:
				throw new ServerException("Invalid uri");
		}
	}
	
	
	/**
	 * Returns the appropriate /moves/xx command
	 * 
	 * @param type
	 * @param userJson
	 * @param facade
	 * @param jsonBody
	 * @return
	 * @throws ServerException
	 */
	ACommand buildMovesCommand(String type, Map<String, String> cookies, IServerFacade facade, String jsonBody) 
																			throws ServerException {

		switch(type) {
			case "sendChat":
				return new SendChatCommand(cookies, facade, jsonBody);
			case "rollNumber":
				return new RollNumberCommand(cookies, facade, jsonBody);
			case "robPlayer":
				return new RobPlayerCommand(cookies, facade, jsonBody);
			case "finishTurn":
				return new FinishTurnCommand(cookies, facade, jsonBody);
			case "buyDevCard":
				return new BuyDevCardCommand(cookies, facade, jsonBody);
			case "Year_of_Plenty":
				return new YearOfPlentyCommand(cookies, facade, jsonBody);
			case "Road_Building":
				return new RoadBuildingCommand(cookies, facade, jsonBody);
			case "Soldier":
				return new SoldierCommand(cookies, facade, jsonBody);
			case "Monopoly":
				return new MonopolyCommand(cookies, facade, jsonBody);
			case "Monument":
				return new MonumentCommand(cookies, facade, jsonBody);
			case "buildRoad":
				return new BuildRoadCommand(cookies, facade, jsonBody);
			case "buildSettlement":
				return new BuildSettlementCommand(cookies, facade, jsonBody);
			case "buildCity":
				return new BuildCityCommand(cookies, facade, jsonBody);
			case "offerTrade":
				return new OfferTradeCommand(cookies, facade, jsonBody);
			case "acceptTrade":
				return new AcceptTradeCommand(cookies, facade, jsonBody);
			case "maritimeTrade":
				return new MaritimeTradeCommand(cookies, facade, jsonBody);
			case "discardCards":
				return new DiscardCardsCommand(cookies, facade, jsonBody);
			default:
				throw new ServerException("Invalid uri");
		}
	}
}
