package shared.observers;

import java.util.Observable;
import java.util.Observer;

import client.base.IController;
import client.roll.RollController;
import shared.models.Game;

public class RollObserver implements Observer{
	RollController controller;

	public RollObserver(IController controller) {
		this.controller = (RollController) controller;
	}

	@Override
	public void update(Observable obs, Object obj) {
		Game game = (Game) obs;
		controller.update(game.getGameState());
	}
}