package client.map.state;

import client.map.IMapView;

/**
 * This is the last phase of the Setup. Players take turns in reverse
 * 	order and are only allowed to place one settlement with adjoining 
 * 	road
 * 		Transitions to NotMyTurn
 * 
 */
public class Setup2State extends Setup1State {

	public Setup2State(IMapView view) {
		super(view);
		this.num_roads = 14;
		this.num_settlements = 4;
	}
}
