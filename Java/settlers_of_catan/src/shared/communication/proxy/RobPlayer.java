package shared.communication.proxy;

import shared.locations.HexLocation;

public class RobPlayer
{

	/**
	 * The index of the player moving the robber
	 */
	public int playerIndex;
	
	/**
	 * The index of the victim of the robber
	 */
	public int victimIndex;
	
	/**
	 * The new hex location of the robber
	 */
	public HexLocation newLocation;
	
	public RobPlayer()
	{}
	
	public RobPlayer(int playerIndex, int victimIndex, HexLocation newLocation) {
		this.playerIndex = playerIndex;
		this.victimIndex = victimIndex;
		this.newLocation = newLocation;
	}
	
}
