package shared.models.mapClasses;

import java.util.ArrayList;
import java.util.HashMap;

import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

public class PlayerMap 
{
	HashMap<Integer, PlayerPieces> PlayerPieces = new HashMap<Integer, PlayerPieces>();
	
	
	
// CONSTRUCTOR
	public PlayerMap() {
		for (int i = 0; i < 4; i++) {
			PlayerPieces.put(i, new PlayerPieces());
		}
	}


	
// Public METHODS
	public void addSettlement(VertexLocation loc, int playerIndex) {
		loc = loc.getNormalizedLocation();
		this.PlayerPieces.get(playerIndex).addSettlement(loc);
	}
	

	public void removeSettlement(VertexLocation loc, int playerIndex) {
		loc = loc.getNormalizedLocation();
		this.PlayerPieces.get(playerIndex).removeSettlement(loc);
	}
	
	
	public void addCity(VertexLocation loc, int playerIndex) {
		loc = loc.getNormalizedLocation();
		this.PlayerPieces.get(playerIndex).addCity(loc);
	}
	
	
	public void addRoad(EdgeLocation loc, int playerIndex) {
		loc = loc.getNormalizedLocation();
		this.PlayerPieces.get(playerIndex).addRoad(loc);
	}
	
	
	public ArrayList<VertexLocation> getVertexPieceList(int ownerIndex) {
		ArrayList<VertexLocation> vertexes = this.PlayerPieces.get(ownerIndex).getSettlements();
		vertexes.addAll(this.PlayerPieces.get(ownerIndex).getCities());
		return vertexes;
	}
	
	public ArrayList<VertexLocation> getSettlementList(int ownerIndex) {
		return this.PlayerPieces.get(ownerIndex).getSettlements();
		
	}
	
	public ArrayList<VertexLocation> getCityList(int ownerIndex) {
		return this.PlayerPieces.get(ownerIndex).getCities();
	}
	
	public ArrayList<EdgeLocation> getEdgePieceList(int ownerIndex) {
		ArrayList<EdgeLocation> edges = this.PlayerPieces.get(ownerIndex).getLocation();
		return edges;
	}
	
	
}
