package client.recordIndexerGUI.components;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

import client.recordIndexerGUI.Session;

@SuppressWarnings("serial")
public class BottomPanes extends JSplitPane
{
	Session session;
	LeftPane leftPane;
	RightPane rightPane;
	JScrollPane leftPanel;
	JScrollPane rightPanel;
	public BottomPanes(Session session)
	{
		super(JSplitPane.HORIZONTAL_SPLIT);

		this.session = session;
		leftPane = new LeftPane(session);
		rightPane = new RightPane(session);
		leftPanel = new JScrollPane(leftPane);
		rightPanel = new JScrollPane(rightPane);
		
		this.setLeftComponent(leftPanel);
		this.setRightComponent(rightPanel);
		
		Dimension BottomPanesSize = new Dimension();
		Dimension minimum = new Dimension();
		minimum.setSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 0);
		BottomPanesSize.setSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 200);
		this.setSize(BottomPanesSize);
		this.setPreferredSize(BottomPanesSize);
		this.setMaximumSize(BottomPanesSize);
		this.setMinimumSize(minimum);
		
		this.setDividerLocation(675);
	}
	
//getters
	public LeftPane getLeftPane()
	{
		return leftPane;
	}
	
	public RightPane getRightPane()
	{
		return rightPane;
	}
	
//setters
	public void setLeftPane(LeftPane leftPane)
	{
		this.leftPane = leftPane;
	}
	
	public void setRightPane(RightPane rightPane)
	{
		this.rightPane = rightPane;
	}
	
//other functionality
	public void refresh()
	{
		this.revalidate();		
	}
}
