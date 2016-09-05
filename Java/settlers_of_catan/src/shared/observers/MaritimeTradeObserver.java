package shared.observers;

import java.util.Observable;
import java.util.Observer;

import client.base.IController;
import client.communication.ChatController;
import client.maritime.MaritimeTradeController;
import shared.models.Game;

public class MaritimeTradeObserver implements Observer{
	MaritimeTradeController controller;

	public MaritimeTradeObserver(IController controller) {
		this.controller = (MaritimeTradeController) controller;
	}

	@Override
	public void update(Observable obs, Object obj) {
		Game game = (Game) obs;
		controller.update(game.getGameState());
	}
}