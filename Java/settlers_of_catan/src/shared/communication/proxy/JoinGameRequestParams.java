package shared.communication.proxy;

public class JoinGameRequestParams
{

	/**
	 * The id of the game to join
	 */
	public int id;
	/**
	 * The color the player wants to be
	 */
	public String color;
	
	public JoinGameRequestParams() 
	{}
	
	public JoinGameRequestParams(int id, String color) {
		this.id = id;
		this.color = color;
	}


	
}
