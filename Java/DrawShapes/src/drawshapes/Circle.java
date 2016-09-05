//***********************************************************************************************
//  Circle.java							Michael Gardiner
//***********************************************************************************************
package drawshapes;
import java.awt.Graphics;
import java.awt.Point;

public class Circle extends Oval
{
	
	
	public Circle()
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
