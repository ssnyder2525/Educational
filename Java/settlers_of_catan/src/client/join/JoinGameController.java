package client.join;

import javax.swing.JOptionPane;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import client.base.Controller;
import client.base.IAction;
import client.clientFacade.ClientFacade;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.misc.IMessageView;
import shared.communication.proxy.CreateGameRequestParams;
import shared.communication.proxy.JoinGameRequestParams;
import shared.definitions.CatanColor;
import shared.serializerJSON.Deserializer;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	private JoinGameRequestParams params;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
	}
	
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

	@Override
	public void start() {
		PlayerInfo loginUser = ClientFacade.getInstance().getUserData();
		
		String gamesString;
		try {
			gamesString = ClientFacade.getInstance().getGamesList();
			GameInfo[] games = Deserializer.getInstance().deserializeGamesList(gamesString);
			this.getJoinGameView().setGames(games, loginUser);
			getJoinGameView().showModal();
		} catch (Exception e) {
			JOptionPane.showMessageDialog((JoinGameView)this.getJoinGameView(), "An error occured while fetching a list of games");
		}	
	}

	@Override
	public void startCreateNewGame() {
		
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		
		getNewGameView().closeModal();
	}

	@Override
	public void createNewGame() {
		this.getJoinGameView().closeModal();
		CreateGameRequestParams newGame = new CreateGameRequestParams();
		newGame.name = this.getNewGameView().getTitle();
		newGame.randomNumbers = this.getNewGameView().getRandomlyPlaceNumbers();
		newGame.randomPorts = this.getNewGameView().getUseRandomPorts();
		newGame.randomTiles = this.getNewGameView().getRandomlyPlaceHexes();
		try {
			JsonParser parser = new JsonParser();
			JsonObject game = parser.parse(ClientFacade.getInstance().createGame(newGame)).getAsJsonObject();
			JoinGameRequestParams params = new JoinGameRequestParams();
			params.id = game.get("id").getAsInt();
			params.color = "blue";
			ClientFacade.getInstance().joinGame(params);
			getNewGameView().closeModal();
			this.start();
		} catch (Exception e) {
			JOptionPane.showMessageDialog((NewGameView)this.getNewGameView(), "An error occurred while trying to create your game");
		}
	}

	@Override
	public void startJoinGame(GameInfo game) {
		params = new JoinGameRequestParams();
		params.id = game.getId();
		getSelectColorView().showModal();
		for(PlayerInfo p : game.getPlayers()) {
			if (ClientFacade.getInstance().getUserData().getId() != p.getId()){
				getSelectColorView().setColorEnabled(p.getColor(), false);
			}
		}
	}

	@Override
	public void cancelJoinGame() {
	
		getJoinGameView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color) {		
		params.color = color.toString().toLowerCase();
		try {
			String result = ClientFacade.getInstance().joinGame(params);
			if(result.equals("Success")) {
				getSelectColorView().closeModal();
				getJoinGameView().closeModal();
				joinAction.execute();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog((SelectColorView) this.getSelectColorView(), "Failed to Join the Game");
		}
	}

}

