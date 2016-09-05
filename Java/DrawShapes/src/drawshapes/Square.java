package drawshapes;

import java.awt.Graphics;
import java.awt.Point;

//***********************************************************************************************
//  Square.java							Michael Gardiner
//***********************************************************************************************

public class Square extends Rectangle
{
	
	public Square()
	{
		super();
	
		height = width;
	}
	
	
//***********************************************************************************************	
//  Resizes a shape based on the absolute value of the distance from the center of a point.
//***********************************************************************************************

	public void Resize(Point p1)
	{
		width = Math.abs(getX() - p1.x );
	
		height = width;
	}
	
	
}
