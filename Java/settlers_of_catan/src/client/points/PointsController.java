package client.points;

import client.base.*;
import client.clientFacade.ClientFacade;
import shared.definitions.GameState;
import shared.models.playerClasses.Player;
import shared.observers.PointsObserver;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController {

	private IGameFinishedView finishedView;
	private PointsObserver obs;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		obs = new PointsObserver(this);
		setFinishedView(finishedView);
		ClientFacade.getInstance().game.addObserver(obs);
	}
	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	private void initFromModel() {
		getPointsView().setPoints(getVictoryPoints());
	}

	private int getVictoryPoints() {
		Player player = ClientFacade.getInstance().game.getPlayers().getPlayerByIndex(
				ClientFacade.getInstance().getUserData().getPlayerIndex());
		int victoryPoints = player.getVictoryPoints();
		return victoryPoints;
	}

	public void update(GameState gameState) {
		for (Player p : ClientFacade.getInstance().game.getPlayers().getPlayers()) {
			if (p.getVictoryPoints() >= 10) {
				finishedView.setWinner(p.getName(), p.getID() == ClientFacade.getInstance().getUserData().getPlayerIndex());
				finishedView.showModal();
				ClientFacade.getInstance().game.setGameState(GameState.ENDOFGAME);
				//this.getClientFacade().game = null;
			}
		}
		if (ClientFacade.getInstance().game != null) {
			initFromModel();
		}
	}	
}