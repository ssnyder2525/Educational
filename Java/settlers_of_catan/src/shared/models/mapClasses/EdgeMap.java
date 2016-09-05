package shared.models.mapClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import client.clientFacade.ClientFacade;
import shared.definitions.PieceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class EdgeMap 
{	
	private Map<EdgeLocation, Piece> Edges = new HashMap<EdgeLocation, Piece>();
	
	
	
// CONSTRUCTOR
	/**
	 * Constructor
	 * 
	 * @param json
	 */
	public EdgeMap() { }
	
	
	
// GETTER
	/**
	 * Return the Piece at the given EdgeLocation
	 * 
	 * @param loc
	 * @return
	 */
	public Piece getEdge(EdgeLocation loc) throws IndexOutOfBoundsException {
		
		loc = loc.getNormalizedLocation();
		if (!Edges.containsKey(loc)) {
			throw new IndexOutOfBoundsException();
		}
		return Edges.get(loc);
	}
	
	
	
// SETTER
	/**
	 * Set an Edge at a Given EdgeDirection, use null for empty
	 * 
	 * @param loc
	 * @param p
	 */
	public void setEdge(EdgeLocation loc, Piece p) throws InvalidTypeException {
		
		loc = loc.getNormalizedLocation();
		if (p.getType() != PieceType.ROAD) {
			throw new InvalidTypeException();
		}
		this.Edges.put(loc, p);
	}
	
	
	
// Public METHODS
	/**
	 * Checks if a player can build a road at a given location
	 * 
	 * @param loc
	 * @param ownerIndex
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public boolean canBuildRoad(EdgeLocation loc, int ownerIndex, boolean isSetup) throws IndexOutOfBoundsException {
		
		// check if outside edge
		loc = loc.getNormalizedLocation();
		
		if (this.Edges.get(loc) == null) {
			return this._canBuildRoad(loc, ownerIndex, isSetup);
		}
		return false;
	}
	
	
	/**
	 * Checks if a player can build a settlement
	 * 
	 * @return
	 */
	public boolean canBuildSettlement(EdgeLocation edgeLoc, int ownerIndex) {
		
		if (!this.Edges.containsKey(edgeLoc)) {
			throw new IndexOutOfBoundsException();
		}
		
		if (this.Edges.get(edgeLoc).getOwner() != ownerIndex) {
			return false;
		}
		return true;
	}
	
	
// Private METHODS
	/**
	 * Helper function for canBuildRoad
	 * 
	 * @param loc
	 * @param ownerIndex
	 * @return
	 */
	private boolean _canBuildRoad(EdgeLocation loc, int ownerIndex, boolean isSetup) {
		
		EdgeLocation new_loc; // Used for checking if neighbor Edges belong to the player
		EdgeDirectionPair pair = getNeighborDirection(loc.getDir());
		EdgeDirection dir1 = pair.Dir1;
		EdgeDirection dir2 = pair.Dir2;
		
		/* Check if the edge location is being used */
		if (this.Edges.containsKey(loc)) {
			return true;
		}
		
		/* Don't continue if it is setup */
		if (isSetup) {
			
			//Make sure that you can build a settlement on one end of this road
			ArrayList<VertexLocation> adjacentVertices = findAdjacentVertices(loc.getNormalizedLocation());
			
			if (adjacentVertices.get(0) == null || adjacentVertices.get(1) == null) {
				return false;
			}
			return ClientFacade.getInstance().canBuildSettlement(adjacentVertices.get(0), true, false)
					|| ClientFacade.getInstance().canBuildSettlement(adjacentVertices.get(1), true, false);

		}
		
		/* Check if any adjacent edge belongs to the player trying to place the road piece */
		// Check dir1
		new_loc = (new EdgeLocation(loc.getHexLoc(), dir1)).getNormalizedLocation();
		if (this.Edges.containsKey(new_loc) && this.Edges.get(new_loc).getOwner() == ownerIndex) {
			return true;
		}

		// Check dir2
		new_loc = (new EdgeLocation(loc.getHexLoc(), dir2)).getNormalizedLocation();
		if (this.Edges.containsKey(new_loc) && this.Edges.get(new_loc).getOwner() == ownerIndex) {
			return true;
		}

		// Check adjacent hex with opposite of dir1
		new_loc = new EdgeLocation(
				loc.getHexLoc().getNeighborLoc(loc.getDir()), 
				dir1.getOppositeDirection()
		).getNormalizedLocation();
		
		if (this.Edges.containsKey(new_loc) && this.Edges.get(new_loc).getOwner() == ownerIndex) {
			return true;
		}

		// Check adjacent hex with opposite of dir2
		new_loc = new EdgeLocation(
				loc.getHexLoc().getNeighborLoc(loc.getDir()), 
				dir2.getOppositeDirection()
		).getNormalizedLocation();
		
		if (this.Edges.containsKey(new_loc) && this.Edges.get(new_loc).getOwner() == ownerIndex) {
			return true;
		}

		// None of the adjacent edges match
		return false;
		
	}
	
	
	private ArrayList<VertexLocation> findAdjacentVertices(EdgeLocation loc) {
		
		ArrayList<VertexLocation> adjacentVertices = new ArrayList<VertexLocation>();
		VertexLocation first = null, second = null;
		
		switch (loc.getDir()) {
			case North:
				first = new VertexLocation(loc.getHexLoc(), VertexDirection.NorthWest);
				second = new VertexLocation(loc.getHexLoc(), VertexDirection.NorthEast);
				break;
				
			case NorthWest:
				first = new VertexLocation(loc.getHexLoc(), VertexDirection.NorthWest);
				second = new VertexLocation(loc.getHexLoc(), VertexDirection.West);
				break;
			
			case NorthEast:
				first = new VertexLocation(loc.getHexLoc(), VertexDirection.NorthEast);
				second = new VertexLocation(loc.getHexLoc(), VertexDirection.East);
				break;
			default:
				break;
		}
		adjacentVertices.add(first.getNormalizedLocation());
		adjacentVertices.add(second.getNormalizedLocation());
		return adjacentVertices;
	}



	/**
	 * Helper function for _canBuildRoad. Gets the neighbors of the given EdgeDirection (for a Hex)
	 * 
	 * @param dir
	 * @return
	 */
	private EdgeDirectionPair getNeighborDirection(EdgeDirection dir) {
		
		EdgeDirection dir1 = null;
		EdgeDirection dir2 = null;
		
		switch (dir) 
		{
			case NorthWest:
			{
				// Check North and adjacent hex South
				dir1 = EdgeDirection.North;
				// Check SouthWest and adjacent hex NorthEast
				dir2 = EdgeDirection.SouthWest;
				break;
			}
			case North:
			{
				// Check NorthWest and adjacent hex SouthEast
				dir1 = EdgeDirection.NorthWest;
				// Check NorthEast and adjacent hex SouthWest
				dir2 = EdgeDirection.NorthEast;
				break;
			}
			case NorthEast: 
			{
				// Check North and adjacent hex South
				dir1 = EdgeDirection.North;
				// Check SouthEast and adjacent hex NorthWest
				dir2 = EdgeDirection.SouthEast;
				break;
			}
			default:
			{
				assert false;
				return null;
			}
		}
		return new EdgeDirectionPair(dir1, dir2);
	}
	
	
	
	/**
	 * Container for return data of getNeighborDirection. Reduces code duplication
	 * 
	 * @author benthompson
	 *
	 */
	class EdgeDirectionPair {
		EdgeDirection Dir1;
		EdgeDirection Dir2;
		EdgeDirectionPair(EdgeDirection dir1, EdgeDirection dir2) {
			Dir1 = dir1;
			Dir2 = dir2;
		}
	}



	public Boolean settlementTouchesPlayerRoad(VertexLocation loc, int ownerID) {
		
		int x, y;

		switch (loc.getDir()) 
		{
			case NorthEast:
				x = loc.getHexLoc().getX();
				y = loc.getHexLoc().getY();
				EdgeLocation left = new EdgeLocation(loc.getHexLoc(), EdgeDirection.North).getNormalizedLocation();
				EdgeLocation upper = new EdgeLocation(new HexLocation(x+1, y-1), EdgeDirection.NorthWest).getNormalizedLocation();
				EdgeLocation lower = new EdgeLocation(loc.getHexLoc(), EdgeDirection.NorthEast).getNormalizedLocation();
				if (this.Edges.containsKey(left)) {
					if (this.Edges.get(left).getOwner() == ownerID) {
						return true;
					}
				}
				if (this.Edges.containsKey(upper)) {
					if (this.Edges.get(upper).getOwner() == ownerID) {
						return true;
					}
				}
				if (this.Edges.containsKey(lower)) {
					if (this.Edges.get(lower).getOwner() == ownerID) {
						return true;
					}
				}
				break;
				
			case NorthWest:
				x = loc.getHexLoc().getX();
				y = loc.getHexLoc().getY();
				EdgeLocation right = new EdgeLocation(loc.getHexLoc(), EdgeDirection.North).getNormalizedLocation();
				EdgeLocation upperLeft= new EdgeLocation(new HexLocation(x, y-1), EdgeDirection.SouthWest).getNormalizedLocation();
				EdgeLocation lowerLeft = new EdgeLocation(loc.getHexLoc(), EdgeDirection.NorthWest).getNormalizedLocation();
				if (this.Edges.containsKey(right)) {
					if (this.Edges.get(right).getOwner() == ownerID) {
						return true;
					}
				}
				if (this.Edges.containsKey(upperLeft)) {
					if (this.Edges.get(upperLeft).getOwner() == ownerID) {
						return true;
					}
				}
				if (this.Edges.containsKey(lowerLeft)) {
					if (this.Edges.get(lowerLeft).getOwner() == ownerID) {
						return true;
					}
				}
				break;
				
		}
		return false;
	}
	
}
