package shared.observers;

import java.util.Observable;
import java.util.Observer;

import client.base.IController;
import client.devcards.DevCardController;
import shared.models.Game;

public class DevCardObserver implements Observer {
	
	DevCardController controller;

	public DevCardObserver(IController controller) {
		this.controller = (DevCardController) controller;
	}

	@Override
	public void update(Observable o, Object arg1) {
		Game g = (Game)o;
		controller.update(g.getGameState());
	}

}
