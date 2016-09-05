package shared.observers;

import java.util.Observable;
import java.util.Observer;

import client.base.IController;
import client.points.PointsController;
import shared.models.Game;

public class PointsObserver implements Observer{
	PointsController controller;

	public PointsObserver(IController controller) {
		this.controller = (PointsController) controller;
	}

	@Override
	public void update(Observable obs, Object obj) {
		Game game = (Game) obs;
		controller.update(game.getGameState());
	}

}
