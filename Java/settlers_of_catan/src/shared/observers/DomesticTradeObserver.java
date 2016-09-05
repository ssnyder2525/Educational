package shared.observers;

import java.util.Observable;
import java.util.Observer;

import client.base.IController;
import client.domestic.DomesticTradeController;
import shared.models.Game;

public class DomesticTradeObserver implements Observer{
	DomesticTradeController controller;

	public DomesticTradeObserver(IController controller) {
		this.controller = (DomesticTradeController) controller;
	}

	@Override
	public void update(Observable obs, Object obj) {
		Game game = (Game) obs;
		controller.update(game.getGameState());
	}

}
