package shared.models.mapClasses;

import shared.definitions.HexType;

/**
 * This class is a representation of a hex piece on the settlers of catan map. 
 *  It is one of 34.
 *  
 * @author benthompson
 *
 */
public class Hex 
{
	/**The Hex's type*/
	private HexType Type;
	private int Token;
	

	// CONSTRUTOR
	public Hex () {}
		
		
	// GETTER
	public int getToken() {
		return this.Token;
	}

	

// CONSTRUCTOR
	/**
	 * Constructor. HexType cannot be WATER. Token must be a valid number.
	 * 
	 * @param type
	 * @param token
	 * @throws InvalidHexTypeException
	 * @throws InvalidTokenException 
	 * @throws InvalidTypeException 
	 */
	public Hex(HexType type, int token) throws InvalidTokenException, InvalidTypeException {
		
		this.Type = type;
		if (type == HexType.WATER) {
			throw new InvalidTypeException();
		}
		if ((token < 2 || token > 12) && token != -1) { // -1 is the Dessert piece
			throw new InvalidTokenException();
		}
		this.Token = token;
	}
		
		
	/**
	 * This class is a representation of a hex piece on the settlers of catan map. 
	 *  It is one of 34.
	 * 
	 * @param type
	 */
	public Hex(HexType type) {
		this.Type = type;
		this.Token = -1;
	}
	
	

// GETTER
	/**
	 * Get the HexType of the Hex
	 * 
	 * @return
	 */
	public HexType getHexType() {
		return this.Type;
	}

}
