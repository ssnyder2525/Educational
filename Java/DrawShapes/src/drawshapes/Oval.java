
//***********************************************************************************************
//  Oval.java							Michael Gardiner
//***********************************************************************************************
package drawshapes;

import java.awt.*;
import java.awt.Graphics;

public class Oval extends Shape
{

	public Oval()
	{
		super();
	}
	
	
	public void Draw(Graphics graphic)
	{	
		graphic.drawOval(getCornerX(), getCornerY(), width, height);
	}
	
	public void DrawFilled(Graphics graphic)
	{
		graphic.fillOval(getCornerX(), getCornerY(), width, height);
	}

	public boolean Contains(Point p1)
	{
		boolean contains = false;
		
		double distanceX = (double) getX() - p1.x;
		
		double distanceY =  (double) getY() - p1.y;
		
		double w =  (double) getWidth();
		
		double h = (double) getHeight();
		
		
		double result = ( Math.pow(distanceX / w, 2) ) + ( Math.pow(distanceY / h, 2) );
		
		if(result <= .25)
		{
			contains = true;
		}
		
		return contains;
	}
	
	
	
	
	
}

