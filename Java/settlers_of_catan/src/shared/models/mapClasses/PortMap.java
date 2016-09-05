package shared.models.mapClasses;

import java.util.ArrayList;
import java.util.HashMap;
import shared.definitions.PortType;

public class PortMap 
{
	HashMap<Integer, ArrayList<PortType>> Ports =  new HashMap<Integer, ArrayList<PortType>>();
	
	
// Constructor
	public PortMap () {}
	
	
	
// Public METHODS
	/**
	 * Add's an owner to the list of Owners referenced by a PortType. 
	 * 
	 * @param p
	 * @param ownerIndex
	 * @throws InvalidTypeException 
	 */
	public void addPort(PortType p, int ownerIndex) throws InvalidTypeException {
		
		if (p == null) {
			throw new InvalidTypeException();
		}
		
		ArrayList<PortType> portList;
		if (!this.Ports.containsKey(ownerIndex)) {
			portList = new ArrayList<PortType>();
			this.Ports.put(ownerIndex, portList);
		}
		else {
			portList = this.Ports.get(ownerIndex);
		}
		
		portList.add(p);
	}

	
	/**
	 * Check's if a given user can perform a Maritime Trade
	 * 
	 * @param ownerIndex
	 * @return
	 */
	public boolean canMaritimeTrade(int ownerIndex) {
		return this.Ports.containsKey(ownerIndex);
	}
	
}
