package shared.observers;

import java.util.Observable;
import java.util.Observer;

import client.base.IController;
import client.communication.ChatController;
import shared.models.Game;

public class ChatObserver implements Observer{
	ChatController controller;

	public ChatObserver(IController controller) {
		this.controller = (ChatController) controller;
	}

	@Override
	public void update(Observable obs, Object obj) {
		Game game = (Game) obs;
		controller.update(game.getGameState());
	}

}
