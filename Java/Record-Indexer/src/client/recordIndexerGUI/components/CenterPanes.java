package client.recordIndexerGUI.components;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import client.recordIndexerGUI.Session;
import client.recordIndexerGUI.components.imagePanelClasses.DrawingComponent;

@SuppressWarnings("serial")
public class CenterPanes extends JSplitPane
{
	Session session;
	BottomPanes bottomPanes;
	DrawingComponent drawingComponent;
	public CenterPanes(Session session)
	{
		super(JSplitPane.VERTICAL_SPLIT);
		this.session = session;
		
		bottomPanes = new BottomPanes(session);
		
		Dimension minimum = new Dimension();
		minimum.setSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 0);
		Dimension CenterPanelSize = new Dimension();
		CenterPanelSize.setSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 1600);
		this.setSize(CenterPanelSize);
		this.setPreferredSize(CenterPanelSize);
		this.setMaximumSize(CenterPanelSize);
		this.setMinimumSize(minimum);
		
		this.add(new JPanel());
		this.add(bottomPanes);
		this.setDividerLocation(400);
	}
	
//getters
	public BottomPanes getBottomPanes()
	{
		return bottomPanes;
	}
	
//setters	
	public void setBottomPanes(BottomPanes bottom)
	{
		this.bottomPanes = bottom;
	}
	
//other functionality
	public void getImage(String urlString)
	{
		if(session.getBatch() != null)
		{
			try
			{
				URL url;
				url = new URL(urlString);
				session.getUserData().setImageUrl(url);
				BufferedImage image = ImageIO.read(url);			
				drawingComponent = new DrawingComponent(session, image);
				drawingComponent.setScale(session.getScale());
				drawingComponent.setTranslation(session.getXTranslation(), session.getYTranslation());
				if(session.isInverted())
				{
					drawingComponent.invertImage();
				}
				this.setTopComponent(drawingComponent);
				this.setBottomComponent(bottomPanes);
				this.setDividerLocation(session.getUserData().getWindowData().getDivider1Position());
				drawingComponent.repaint();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			this.removeAll();
			this.add(bottomPanes);
		}
	}
	
	public void zoomIn()
	{
		session.incrementScale();
		drawingComponent.setScale(session.getScale());
		drawingComponent.repaint();
	}
	
	public void zoomOut()
	{
		session.decrementScale();
		drawingComponent.setScale(session.getScale());
		drawingComponent.repaint();
	}
	
	public void invertImage()
	{
		drawingComponent.invertImage();
	}
	
	public void clearImage()
	{
		drawingComponent.clear();
	}
	
	public void refresh()
	{
		this.revalidate();
		this.bottomPanes.refresh();
	}
	
	public void repaintImage()
	{
		drawingComponent.repaint();
	}

	public void toggleHighlights()
	{
		if(session.isHighlighted())
		{
			drawingComponent.getSelection(session.getCurrentRow(), session.getCurrentColumn(), session.getPrevRow(), session.getPrevColumn());
		}
		else
		{
			drawingComponent.removeSelection(session.getPrevRow(), session.getPrevColumn());
		}
	}

	public void saveTranslation()
	{
		drawingComponent.saveTranslation();
	}

	public void getSliderPositions()
	{
		session.getUserData().getWindowData().setDivider1Position(this.getDividerLocation());
		session.getUserData().getWindowData().setDivider2Position(bottomPanes.getDividerLocation());
	}

	public void setSliderPositions()
	{
		this.setDividerLocation(session.getUserData().getWindowData().getDivider1Position());
		bottomPanes.setDividerLocation(session.getUserData().getWindowData().getDivider2Position());
	}
	
}
