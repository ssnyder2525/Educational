//***********************************************************************************************
//  Shape.java							Michael Gardiner
//***********************************************************************************************
package drawshapes;
import java.awt.*;

import java.awt.Graphics;

import java.util.Random;

public abstract class Shape
{ 

	protected Color color;
	
	protected int comparison;
	
	protected Random random;
	
/*  x and y	represent the coordinates of the shape's center point. */	
	
	protected int x, y, width, height; 
	
//***********************************************************************************************
	
	public Shape()
	{
		random = new Random();
		
		x = random.nextInt(699);
		
		y = random.nextInt(699);
		
		width = (random.nextInt(301) + 20);
		
		height = (random.nextInt(301) + 20);
	
		color = new Color(random.nextInt(255),
				random.nextInt(255) , random.nextInt(255));
	}
	
//***********************************************************************************************	
//  Returns the shape's x value.
//***********************************************************************************************	
	
	public int getX()
	{
		return x;
	}
	
//***********************************************************************************************	
//  Returns the shape's y value.
//***********************************************************************************************	
	
	public int getY()
	{
		return y;
	}
	
//***********************************************************************************************	
//  Returns the shape's upper left hand corner's x coordinate.
//***********************************************************************************************	
	
	public int getCornerX()
	{
		int centerX = x - (width / 2);
	
		return centerX;
	}
	
	
//***********************************************************************************************	
//  Returns the shape's upper left hand corner's y coordinate.
//***********************************************************************************************	
	
	public int getCornerY()
	{
		int centerY = y - (height / 2);
	
		return centerY;
	}
		
	
	
//***********************************************************************************************	
//  Returns the shape's width
//***********************************************************************************************	
	
	public int getWidth()
	{
		return width;
	}
	
//***********************************************************************************************	
//  Returns the shape's height
//***********************************************************************************************	
	
	public int getHeight()
	{
		return height;
	}
	
//***********************************************************************************************	
//  Returns the shape's random color.
//***********************************************************************************************
	
	public Color getColor()
	{
		return color;
	}
	
//***********************************************************************************************	
//  Returns a String containing the center coordinates of the shape.
//***********************************************************************************************
	
	public String toString()
	{
		String shapeString = "(" + getX() + ", " + getY() + ")";
	
		return shapeString;
	}
	
//***********************************************************************************************	
//  Translates a shape to the specified point (Center will be drawn at the coordinates of p1.)
//***********************************************************************************************
		
	public void Translate(Point p1)
	{
		x = p1.x;
	
		y = p1.y;
	}
	
	
//***********************************************************************************************	
//  Resizes a shape based on the absolute value of the distance from the center of a point.
//***********************************************************************************************

	public void Resize(Point p1)
	{
		width = Math.abs(getX() - p1.x );
	
		height = Math.abs(getY() - p1.y);
	}
	
	
	
//***********************************************************************************************	
//  Compares two shapes to determine the lesser center y-coordinate.
//***********************************************************************************************
	
	public int CompareY(Shape shape)
	{
		if(getY() <= shape.getY() )
		{
			if(getY() < shape.getY())
			{
				comparison = -1;
			}
			
			else
			{
				comparison = 0;
			}
		}
	
		else
		{
			comparison = 1;
		}
		
		return comparison;
	}
	
	
//***********************************************************************************************	
// Compares two shapes to determine the lesser center x-coordinate.
//***********************************************************************************************
		
	public int CompareX(Shape shape)
	{
		if(getX() <= shape.getX() )
		{
			if(getX() < shape.getX())
			{
				comparison = -1;
			}
			
			else
			{
				comparison = 0;
			}
		}
	
		else
		{
			comparison = 1;
		}
		
		return comparison;
	}
	
//***********************************************************************************************	
// Compares two shapes to determine the lesser width.
//***********************************************************************************************

	public int CompareWidth(Shape shape)
	{
		if(width <= shape.getWidth() )
		{
			if(width < shape.getWidth())
			{
				comparison = -1;
			}
			
			else
			{
				comparison = 0;
			}
		}
	
		else
		{
			comparison = 1;
		}
		
		return comparison;
	}
	
//***********************************************************************************************	
// Compares two shapes in all three forms.  If they are equal in all respects, 
// makes a choice for sorting order.
//***********************************************************************************************

	public int CompareWith(Shape shape)
	{
		CompareY(shape);
	
		if (comparison == 0)
		{
			CompareX(shape);
		}
	
		if (comparison == 0)
		{
			CompareWidth(shape);
		}
		
		if (comparison == 0)
		{
			comparison = -1;
		}
		
		return comparison;
	}

//***********************************************************************************************	
//  Abstract methods.
//***********************************************************************************************
		
	abstract public void Draw(Graphics graphic);

	abstract public void DrawFilled(Graphics graphic);
	
	abstract public boolean Contains(Point p1);
}
