package shared.locations;

public enum VertexDirection
{
	West, NorthWest, NorthEast, East, SouthEast, SouthWest;
	
	private VertexDirection opposite;
	private String string;
	
	static
	{
		West.opposite = East;
		NorthWest.opposite = SouthEast;
		NorthEast.opposite = SouthWest;
		East.opposite = West;
		SouthEast.opposite = NorthWest;
		SouthWest.opposite = NorthEast;
		West.string = "W";
		NorthWest.string = "NW";
		NorthEast.string = "NE";
		East.string = "E";
		SouthEast.string = "SE";
		SouthWest.string = "SW";
	}
	
	public VertexDirection getOppositeDirection()
	{
		return opposite;
	}
	
	public String getString() {
		return string;
	}
}

