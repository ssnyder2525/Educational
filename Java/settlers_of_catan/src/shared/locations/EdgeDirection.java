package shared.locations;

public enum EdgeDirection
{
	
	NorthWest, North, NorthEast, SouthEast, South, SouthWest;
	
	private EdgeDirection opposite;
	private String string;
	
	static
	{
		NorthWest.opposite = SouthEast;
		North.opposite = South;
		NorthEast.opposite = SouthWest;
		SouthEast.opposite = NorthWest;
		South.opposite = North;
		SouthWest.opposite = NorthEast;
		NorthWest.string = "NW";
		North.string = "N";
		NorthEast.string = "NE";
		SouthEast.string = "SE";
		South.string = "S";
		SouthWest.string = "SW";
	}
	
	public EdgeDirection getOppositeDirection()
	{
		return opposite;
	}
	
	public String getString() {
		return string;
	}
}

