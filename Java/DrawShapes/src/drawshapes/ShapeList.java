//***********************************************************************************************
//  Shape.java							Michael Gardiner
//***********************************************************************************************
package drawshapes;
import java.util.ArrayList;

import java.awt.*;

public class ShapeList 
{
	private ArrayList<Shape> list;
	
//--------------------------------------------------------------------------------
//  Constructor
//--------------------------------------------------------------------------------
		
	public ShapeList()
	{
		list = new ArrayList<Shape>();
	}
	
//--------------------------------------------------------------------------------
//  Adds a new shape to the list.
//--------------------------------------------------------------------------------

	public void AddShape(Shape newShape)
	{
		list.add(newShape);
	}

//---------------------------------------------------------------------------------
//  Returns the length of the list of shapes.
//---------------------------------------------------------------------------------
	
	public int getNumShapes()
	{
		return list.size();
	}
	
//---------------------------------------------------------------------------------
//  Returns the shape at the specified index.
//---------------------------------------------------------------------------------
	
	public Shape getShape(int index)
	{
		Shape shape = list.get(index);
		
		return shape;
	}
	
	public String toString()
	{
		return list.toString();
	}
	
//---------------------------------------------------------------------------------
//  Searches the list to determine if a shape in the list has the given center
//  point, and returns that shape, beginning at the end of the list.
//---------------------------------------------------------------------------------

	public Shape FindShape(Point p1)
	{
		Shape temp = null;
		
		int count = list.size() -1;
		
		boolean shapeContained = false;
		
		while ( count > -1 &&  shapeContained == false   )
		{
			
			if(getShape(count).Contains(p1) == true)
			{
				shapeContained = true;
				
				temp = getShape(count);
			}
		
			count--;
		}
		
		return temp;
	}
	

//---------------------------------------------------------------------------------
//  Sorts the shapes in the list by center y-coordinate (smallest to largest), then
//  center x-coordinate (smallest to largest) if the y-coordinates are the same, 
//  and then by width (smallest to largest) for equal center points.
//---------------------------------------------------------------------------------
	
	public void insertionSort()
	{
		for(int index = 1; index < list.size(); index++)
		{
			Shape key = getShape(index);
			
			int position = index;
			
			while(position > 0  && key.CompareWith(getShape(position - 1)) < 0)
			{
				list.set(position, getShape(position -1));
				
				position--;
			}
			
			list.set(position, key);
		}
	}
	
	
	
	
	
	
	
}
