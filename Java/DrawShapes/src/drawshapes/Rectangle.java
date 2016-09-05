//***********************************************************************************************
//  Rectangle.java							Michael Gardiner
//***********************************************************************************************
package drawshapes;
import java.awt.*;
import java.awt.Graphics;

public class Rectangle extends Shape
{

	
	public Rectangle()
	{
		super();
	}
	
	
	public void Draw(Graphics graphic)
	{
		graphic.drawRect(getCornerX(), getCornerY(), width, height);
	}
	
	public void DrawFilled(Graphics graphic)
	{
		graphic.fillRect(getCornerX(), getCornerY(), width, height);
	}
	
	public boolean Contains(Point p1)
	{
		boolean contains = false;
		
		if( (p1.x >= getCornerX()) && (p1.x <= (getCornerX() + width) ) && (p1.y >= getCornerY()) && (p1.y <= getCornerY() + height)  )
		{
			contains = true;
		}
		
		return contains;
	}
	
	
	
	
}
