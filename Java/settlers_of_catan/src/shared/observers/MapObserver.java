package shared.observers;

import java.util.Observable;
import java.util.Observer;

import client.map.MapController;
import shared.models.Game;

public class MapObserver implements Observer 
{
	MapController mapController;
	
	
	public MapObserver(MapController mapController) {
		this.mapController = mapController;
	}

	@Override
	public void update(Observable o, Object arg) {
		Game g = (Game)o;
		mapController.update(g.getGameState());
	}

}
