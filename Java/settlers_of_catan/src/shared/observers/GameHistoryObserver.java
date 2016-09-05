package shared.observers;

import java.util.Observable;
import java.util.Observer;

import client.base.IController;
import client.communication.GameHistoryController;
import shared.models.Game;

public class GameHistoryObserver implements Observer{
	GameHistoryController controller;

	public GameHistoryObserver(IController controller) {
		this.controller = (GameHistoryController) controller;
	}

	@Override
	public void update(Observable obs, Object obj) {
		Game game = (Game) obs;
		controller.update(game.getGameState());
	}
}
