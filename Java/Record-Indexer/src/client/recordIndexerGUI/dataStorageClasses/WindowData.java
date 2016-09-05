package client.recordIndexerGUI.dataStorageClasses;

import java.awt.Dimension;

public class WindowData
{
	//MainFrame
	int frameXPosition;
	int frameYPosition;
	Dimension frameSize;
	
	//dividers
	int divider1Position;
	int divider2Position;

	//imagePane
	Double scale;
	int xTransition;
	int yTransition;
	Boolean showHighlights = true;
	Boolean imageInverted = false;
	
	public WindowData()
	{
		frameXPosition = 0;
		frameYPosition = 0;
		frameSize = new Dimension(0,0);
		divider1Position = 400;
		scale = .5;
		xTransition = 0;
		yTransition = 0;
		showHighlights = true;
	}
	
//getters	
	public int getFrameXPosition()
	{
		return frameXPosition;
	}
	
	public int getFrameYPosition()
	{
		return frameYPosition;
	}
	
	public Dimension getFrameSize()
	{
		return frameSize;
	}
	
	public int getDivider1Position()
	{
		return divider1Position;
	}
	
	public int getDivider2Position()
	{
		return divider2Position;
	}
	
	public int getxTransition()
	{
		return xTransition;
	}
	
	public int getyTransition()
	{
		return yTransition;
	}
	
	public Double getScale()
	{
		return scale;
	}

//setters
	public void setFrameXPosition(int frameXPosition)
	{
		this.frameXPosition = frameXPosition;
	}

	public void setFrameYPosition(int frameYPosition)
	{
		this.frameYPosition = frameYPosition;
	}

	public void setFrameSize(Dimension frameSize)
	{
		this.frameSize = frameSize;
	}

	public void setDivider1Position(int divider1Position)
	{
		this.divider1Position = divider1Position;
	}

	public void setDivider2Position(int divider2Position)
	{
		this.divider2Position = divider2Position;
	}

	public void setxTransition(int xTransition)
	{
		this.xTransition = xTransition;
	}

	public void setyTransition(int yTransition)
	{
		this.yTransition = yTransition;
	}
	
//Other functionality
	public void incrementScale()
	{
		if(scale < 3)
		{
			this.scale = this.scale + .1;
		}
	}
	
	public void decrementScale()
	{
		if(scale >.2)
		{
			this.scale = this.scale - .1;
		}
	}
	
	public void toggleHighlights()
	{
		if(showHighlights == true)
		{
			showHighlights = false;
		}
		else
		{
			showHighlights = true;
		}
	}

	public boolean isHighlighted()
	{
		return this.showHighlights;
	}

	public void invertImage()
	{
		if(imageInverted == true)
		{
			imageInverted = false;
		}
		else
		{
			imageInverted = true;
		}
	}

	public boolean isInverted()
	{
		return imageInverted;
	}
}
