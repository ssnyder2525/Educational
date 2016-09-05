package client.recordIndexerGUI.components;

import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;

import client.recordIndexerGUI.Session;
import client.recordIndexerGUI.dataStorageClasses.BatchListener;

@SuppressWarnings("serial")
public class HelpPane extends JEditorPane implements BatchListener
{
	Session session;
	
	public HelpPane(Session session)
	{
		this.session = session;
		this.setContentType("text/html");
	}
	
	public void startUpHelpPane()
	{
		session.addListener(this);
		cellSelected(0,0,session.getPrevRow(), session.getPrevColumn());
		this.setVisible(true);
	}
	
	@Override
	public void cellSelected(int row, int col, int prevRowSelected, int prevColumnSelected)
	{
		String name = session.getBatch().getFields().get(col).getFieldHelpFile();
		try
		{
			URL url = new URL(name);
			this.setPage(url);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
