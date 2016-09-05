package shared.models.mapClasses;

import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.locations.EdgeDirection;
import shared.locations.VertexDirection;

/**
 * Represents a WaterHex piece located within the Map class
 * 
 * @author benthompson
 *
 */
public class WaterHex extends Hex
{
	PortType Port = null;
	EdgeDirection Dir;
	
	
// CONSTRUCTOR
	/**
	 * Constructor. Pass in null for no port.
	 * 
	 * @param type
	 * @param port
	 */
	public WaterHex(PortType port, EdgeDirection dir) {
		super(HexType.WATER);
		setPortType(port);
		setDir(dir);
	}
	
	
	
// GETTERS
	public PortType getPortType() {
		return this.Port;
	}
	
	
	public EdgeDirection getDir() {
		return this.Dir;
	}
	

	
// SETTERS
	public void setPortType(PortType type) {
		this.Port = type;
	}
	
	
	public void setDir(EdgeDirection dir) {
		this.Dir = dir;
	}
	
	
	
// PUBLIC METHOD
	/**
	 * Checks if this Water Hex is a Port or not
	 * 
	 * @return
	 */
	public boolean isPort(EdgeDirection dir) {
		if (Port == null) {
			return false;
		}
		else {
			return Dir.equals(dir);
		}
	}

}
