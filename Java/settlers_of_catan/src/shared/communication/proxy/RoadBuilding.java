package shared.communication.proxy;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

public class RoadBuilding
{

	/**
	 * The index of the player playing the Road Building card
	 */
	public int playerIndex;
	
	/**
	 * Is true when a card has been played
	 */
	public boolean cardPlayed = false;
	
	/**
	 * The first spot they want to build a road
	 */
	public EdgeLocation firstSpot;
	
	/**
	 * The second spot they want to build a road
	 */
	public EdgeLocation secondSpot;
	
	public RoadBuilding() 
	{}

	public RoadBuilding(int playerIndex, EdgeLocation firstSpot, EdgeLocation secondSpot) {
		this.playerIndex = playerIndex;
		this.firstSpot = firstSpot;
		this.secondSpot = secondSpot;
	}
	
	public RoadBuilding(int playerIndex, int firstSpotX, int firstSpotY, String firstSpotDirection, int secondSpotX, int secondSpotY, String secondSpotDirection) {
		this.playerIndex = playerIndex;
		HexLocation firstHexLoc = new HexLocation(firstSpotX, firstSpotY);
		HexLocation secondHexLoc = new HexLocation(secondSpotX, secondSpotY);
		EdgeLocation firstEdgeLoc = new EdgeLocation(firstHexLoc, EdgeDirection.NorthWest);
		EdgeLocation secondEdgeLoc = new EdgeLocation(secondHexLoc, EdgeDirection.NorthWest);
		switch(firstSpotDirection) {
		case "NW":
			firstEdgeLoc = new EdgeLocation(firstHexLoc, EdgeDirection.NorthWest);
			break;
		case "N":
			firstEdgeLoc = new EdgeLocation(firstHexLoc, EdgeDirection.North);
			break;
		case "NE":
			firstEdgeLoc = new EdgeLocation(firstHexLoc, EdgeDirection.NorthEast);
			break;
		case "SE":
			firstEdgeLoc = new EdgeLocation(firstHexLoc, EdgeDirection.SouthEast);
			break;
		case "S":
			firstEdgeLoc = new EdgeLocation(firstHexLoc, EdgeDirection.South);
			break;
		case "SW":
			firstEdgeLoc = new EdgeLocation(firstHexLoc, EdgeDirection.SouthWest);
			break;
		}
		switch(secondSpotDirection) {
		case "NW":
			secondEdgeLoc = new EdgeLocation(secondHexLoc, EdgeDirection.NorthWest);
			break;
		case "N":
			secondEdgeLoc = new EdgeLocation(secondHexLoc, EdgeDirection.North);
			break;
		case "NE":
			secondEdgeLoc = new EdgeLocation(secondHexLoc, EdgeDirection.NorthEast);
			break;
		case "SE":
			secondEdgeLoc = new EdgeLocation(secondHexLoc, EdgeDirection.SouthEast);
			break;
		case "S":
			secondEdgeLoc = new EdgeLocation(secondHexLoc, EdgeDirection.South);
			break;
		case "SW":
			secondEdgeLoc = new EdgeLocation(secondHexLoc, EdgeDirection.SouthWest);
			break;
		}
		this.firstSpot = firstEdgeLoc;
		this.secondSpot = secondEdgeLoc;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public EdgeLocation getFirstSpot() {
		return firstSpot;
	}

	public void setFirstSpot(EdgeLocation firstSpot) {
		this.firstSpot = firstSpot;
	}

	public EdgeLocation getSecondSpot() {
		return secondSpot;
	}

	public void setSecondSpot(EdgeLocation secondSpot) {
		this.secondSpot = secondSpot;
	}
	
	public boolean placedFirst() {
		return !(firstSpot == null);
	}

	public boolean placedSecond() {
		return !(secondSpot == null);
	}
	
	public boolean cardPlayed() {
		return this.cardPlayed;
	}
	
	public void setCardPlayed(boolean b) {
		this.cardPlayed = b;
	}
	
	public void clear() {
		firstSpot = null;
		secondSpot = null;
	}
	
}
