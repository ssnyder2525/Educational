package shared.communication.proxy;

import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

public class BuildRoad {

	/**
	 * The index of the player sending the message
	 */
	public int playerIndex;
	
	/**
	 * Whether or not this is a free placement
	 */
	public Boolean free;
	
	public EdgeLocation roadLocation;

	
	
	public BuildRoad() 
	{}
	
	
	public BuildRoad(int playerIndex, Boolean free, EdgeLocation roadLocation) {
		this.playerIndex = playerIndex;
		this.free = free;
		this.roadLocation = roadLocation;
	}
	
	public BuildRoad(int playerIndex, Boolean free, int roadLocX, int roadLocY, String roadLocDir) {
		this.playerIndex = playerIndex;
		this.free = free;
		HexLocation hexLoc = new HexLocation(roadLocX, roadLocY);
		this.roadLocation = new EdgeLocation(hexLoc, EdgeDirection.NorthWest);
		switch(roadLocDir) {
		case "NW":
			this.roadLocation = new EdgeLocation(hexLoc, EdgeDirection.NorthWest);
			break;
		case "N":
			this.roadLocation = new EdgeLocation(hexLoc, EdgeDirection.North);
			break;
		case "NE":
			this.roadLocation = new EdgeLocation(hexLoc, EdgeDirection.NorthEast);
			break;
		case "SE":
			this.roadLocation = new EdgeLocation(hexLoc, EdgeDirection.SouthEast);
			break;
		case "S":
			this.roadLocation = new EdgeLocation(hexLoc, EdgeDirection.South);
			break;
		case "SW":
			this.roadLocation = new EdgeLocation(hexLoc, EdgeDirection.SouthWest);
			break;
		}
	}


	public BuildRoad(EdgeLocation edgeLoc, boolean isFree)
	{
		this.roadLocation = edgeLoc;
		this.free = isFree;
	}
	
}
