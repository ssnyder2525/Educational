package client.recordIndexerGUI.components;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import client.recordIndexerGUI.Session;
import client.recordIndexerGUI.dataStorageClasses.BatchListener;

@SuppressWarnings("serial")
public class RightPane extends JTabbedPane implements BatchListener
{
	Session session;
	JScrollPane helpPaneHolder;
	HelpPane helpPane;
	JScrollPane imageNav;
	
	public RightPane(Session session)
	{
		this.session = session;
		helpPane = new HelpPane(session);
		imageNav = new JScrollPane();
		helpPaneHolder = new JScrollPane(helpPane);
		helpPaneHolder.setViewportView(helpPane);
		helpPaneHolder.getVerticalScrollBar();
		
		this.add("Field Help", helpPaneHolder);
		this.add("Image Navigator", imageNav);
	}
	
	public void createHelpPane()
	{
		helpPane.startUpHelpPane();		
		helpPaneHolder.removeAll();
		helpPaneHolder = new JScrollPane(helpPane);
		helpPaneHolder.add(helpPane);
		helpPaneHolder.getVerticalScrollBar();
		helpPaneHolder.setViewportView(helpPane);
		this.revalidate();
		this.removeAll();
		this.add("Field Help", helpPaneHolder);
		this.add("Image Navigator", imageNav);
		session.addListener(this);
	}
	
	public void createImageNav()
	{
		imageNav = new JScrollPane();
	}
	
	public void refresh()
	{
		this.revalidate();
		
	}

	public void removeRightContent()
	{
		helpPaneHolder.removeAll();
		helpPaneHolder.repaint();
	}

	@Override
	public void cellSelected(int row, int col, int prevRowSelected,
			int prevColumnSelected)
	{
		this.revalidate();
		
	}
}
