package client.recordIndexerGUI.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import client.recordIndexerGUI.Session;

@SuppressWarnings("serial")
public class RightClickMenu extends JPopupMenu
{
	Session session;
	String value;
	int column;
	int row;
	
	public RightClickMenu(Session s, String v, int r, int c)
	{
		this.session = s;
		this.value = v;
		this.row = r;
		this.column = c;
		
		JMenuItem seeSuggestions = new JMenuItem("See Suggestions");
        add(seeSuggestions);
        
        seeSuggestions.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                //create suggestion box
            	@SuppressWarnings("unused")
				SuggestionBox suggestionBox = new SuggestionBox(session, value, row, column);
            }
        });
	}
}
