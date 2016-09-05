package shared.observers;

import java.util.Observable;
import java.util.Observer;

import client.base.IController;
import client.turntracker.TurnTrackerController;
import shared.models.Game;

public class TurnTrackerObserver implements Observer {
	TurnTrackerController controller;

	public TurnTrackerObserver(IController controller) {
		this.controller = (TurnTrackerController) controller;
	}

	@Override
	public void update(Observable obs, Object obj) {
		Game game = (Game) obs;
		controller.update(game.getGameState());
	}

}
