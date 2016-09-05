package client.map.state;

import client.map.IMapView;


/**
 * Login State doesn't allow the user to see the map at all.
 * 	Can transition to pretty much any other State
 *
 */
public class LoginState extends BaseState {

	public LoginState(IMapView view) {
		super(view);
	}
}
