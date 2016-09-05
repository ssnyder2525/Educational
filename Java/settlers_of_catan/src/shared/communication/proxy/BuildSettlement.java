package shared.communication.proxy;

import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class BuildSettlement
{

	/**
	 * The index of the player building the settlement
	 */
	public int playerIndex;
	
	/**
	 * Whether or not this placement is free
	 */
	public Boolean free;
	
	public VertexLocation vertexLocation;
	
	public BuildSettlement() 
	{}

	public BuildSettlement(int playerIndex, Boolean free, VertexLocation vertexLocation) {
		this.playerIndex = playerIndex;
		this.free = free;
		this.vertexLocation = vertexLocation;
	}
	
	public BuildSettlement(int playerIndex, Boolean free, int vertexLocX, int vertexLocY, String vertexLocDir) {
		this.playerIndex = playerIndex;
		this.free = free;
		HexLocation hexLoc = new HexLocation(vertexLocX, vertexLocY);
		this.vertexLocation = new VertexLocation(hexLoc, VertexDirection.West);
		switch(vertexLocDir) {
		case "W":
			this.vertexLocation = new VertexLocation(hexLoc, VertexDirection.West);
			break;
		case "NW":
			this.vertexLocation = new VertexLocation(hexLoc, VertexDirection.NorthWest);
			break;
		case "NE":
			this.vertexLocation = new VertexLocation(hexLoc, VertexDirection.NorthEast);
			break;
		case "E":
			this.vertexLocation = new VertexLocation(hexLoc, VertexDirection.East);
			break;
		case "SE":
			this.vertexLocation = new VertexLocation(hexLoc, VertexDirection.SouthEast);
			break;
		case "SW":
			this.vertexLocation = new VertexLocation(hexLoc, VertexDirection.SouthWest);
			break;
		}
	}

	
	
}
