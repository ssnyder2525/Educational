package shared.observers;

import java.util.Observable;
import java.util.Observer;

import client.resources.ResourceBarController;
import shared.models.Game;

public class ResourceBarObserver implements Observer 
{
	ResourceBarController resourceBarController;
	
	
	public ResourceBarObserver(ResourceBarController resourceBarController) {
		this.resourceBarController = resourceBarController;
	}

	@Override
	public void update(Observable o, Object arg) {
		Game g = (Game)o;
		resourceBarController.update(g.getGameState());
	}

}
