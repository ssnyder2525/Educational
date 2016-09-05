package shared.communication.proxy;

import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class BuildCity
{

	/**
	 * The index of the player placing the city
	 */
	public int playerIndex;
	
	public VertexLocation vertexLocation;
	
	
	public BuildCity() 
	{}
	
	public BuildCity(int playerIndex, VertexLocation vertexLocation) {
		this.playerIndex = playerIndex;
		this.vertexLocation = vertexLocation;
	}
	
	public BuildCity(int playerIndex, int vertexLocX, int vertexLocY, String vertexLocDir) {
		this.playerIndex = playerIndex;
		HexLocation hexLoc = new HexLocation(vertexLocX, vertexLocY);
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
