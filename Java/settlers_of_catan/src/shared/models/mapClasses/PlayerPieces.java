package shared.models.mapClasses;

import java.util.ArrayList;

import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

public class PlayerPieces 
{
	ArrayList<VertexLocation> Settlements = new ArrayList<VertexLocation>();
	ArrayList<VertexLocation> Cities = new ArrayList<VertexLocation>();
	ArrayList<EdgeLocation> Roads = new ArrayList<EdgeLocation>();
	

	
// CONSTRUCTOR
	public PlayerPieces () {}
	


// GETTERS
	public ArrayList<VertexLocation> getSettlements() {
		return this.Settlements;
	}
	
	
	public ArrayList<VertexLocation> getCities() {
		return this.Cities;
	}
	
	
	public ArrayList<EdgeLocation> getLocation() { 
		return this.Roads;
	}
	
	
	
// SETTERS
	public void setSettlements(ArrayList<VertexLocation> settlements) {
		this.Settlements = settlements;
	}
	
	
	public void setCities(ArrayList<VertexLocation> cities) {
		this.Cities = cities; 
	}
	
	
	public void setRoads(ArrayList<EdgeLocation> roads) {
		this.Roads = roads;
	}
	


// Public METHODS
	public void addSettlement(VertexLocation loc) {
		this.Settlements.add(loc);
	}
	
	
	public void removeSettlement(VertexLocation loc) {
		this.Settlements.remove(loc);
	}
	
	
	public void addCity(VertexLocation loc) {
		this.Cities.add(loc); 
	}
	
	
	public void addRoad(EdgeLocation loc) {
		this.Roads.add(loc);
	}
	
	

}
