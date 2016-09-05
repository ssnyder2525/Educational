package shared.observers;

import java.util.Observable;
import java.util.Observer;

import client.base.IController;
import client.discard.DiscardController;
import client.domestic.DomesticTradeController;
import shared.models.Game;

public class DiscardObserver implements Observer{
	DiscardController controller;

	public DiscardObserver(IController controller) {
		this.controller = (DiscardController) controller;
	}

	@Override
	public void update(Observable obs, Object obj) {
		Game game = (Game) obs;
		controller.update(game.getGameState());
	}

}
