package shared.locations;

public class RobberLocation 
{
	private HexLocation HexLoc;
	
	
	
	public RobberLocation(HexLocation loc)
	{
		setHexLoc(loc);
	}
	
	
	public HexLocation getHexLoc()
	{
		return HexLoc;
	}
	
	
	
	/**
	 * Can do method for placing robber. Robber cannot be placed at the same location.
	 * 
	 * @param loc
	 * @return
	 */
	public boolean canPlaceRobber(HexLocation loc)
	{
		if (loc.equals(HexLoc))
		{
			return false;
		}
		return true;
	}
	
	
	private void setHexLoc(HexLocation hexLoc)
	{
		if(hexLoc == null)
		{
			throw new IllegalArgumentException("hexLoc cannot be null");
		}
		this.HexLoc = hexLoc;
	}

}
