package shared.observers;

import java.util.Observable;
import java.util.Observer;

import client.base.IController;
import client.join.PlayerWaitingController;
import shared.models.Game;

public class PlayerWaitingObserver implements Observer{
	PlayerWaitingController controller;

	public PlayerWaitingObserver(IController controller) {
		this.controller = (PlayerWaitingController) controller;
	}

	@Override
	public void update(Observable obs, Object obj) {
		Game game = (Game) obs;
		controller.update(game.getGameState());
	}

}
