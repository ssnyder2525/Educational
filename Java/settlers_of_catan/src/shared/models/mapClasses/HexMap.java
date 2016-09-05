package shared.models.mapClasses;

import java.util.HashMap;

import shared.definitions.HexType;
import shared.locations.HexLocation;

public class HexMap 
{
	private HashMap<HexLocation, Hex> Hexes = new HashMap<HexLocation, Hex>();
	
	
	
// CONSTRUCTOR
	public HexMap() {}
	
	
	
// GETTER
	/**
	 * Returns the Hex at a given location
	 * 
	 * @param loc
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public Hex getHex(HexLocation loc) throws IndexOutOfBoundsException {
		if (!this.Hexes.containsKey(loc)) {
			throw new IndexOutOfBoundsException();
		}
		return Hexes.get(loc);
	}
	
	
	
// SETTER
	/**
	 * Returns the Hex at a given location
	 * 
	 * @param loc
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public void setHex(HexLocation loc, Hex hex) {
		this.Hexes.put(loc, hex);
	}
	
	
	
// METHODS
	/**
	 * Checks if a Hex is of type WATER. Robbers cannot be placed on water
	 * 
	 * @param loc
	 * @return
	 */
	public boolean canPlaceRobber(HexLocation loc) {
		return this.getHexType(loc) != HexType.WATER;
	}
	
	
	/**
	 * Returns the Hex Type of a given HexLocation
	 * 
	 * @param loc
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public HexType getHexType(HexLocation loc) {
		
		HexType hexType;
		try{
			hexType = this.getHex(loc).getHexType();
		} catch (IndexOutOfBoundsException e) {
			hexType = HexType.WATER;
		}
		return hexType;
	}
	
}
