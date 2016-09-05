package client.searchGUI.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JScrollPane;



import shared.communication.SearchInput;
import shared.communication.SearchOutput;
import shared.models.Value;
import client.ClientException;
import client.communication.ClientCommunicator;
import client.searchGUI.components.SearchInputField;
import client.searchGUI.components.UrlButton;
import client.searchGUI.components.projectsList;

@SuppressWarnings("serial")
public class SearchPage extends JPanel
{
	String username;
	String password;
	String host;
	String port;
	JLabel header = new JLabel("Search");
	JPanel p1 = new JPanel();
	projectsList pL;
	SearchInputField words = new SearchInputField("Key words:");
	JPanel p2 = new JPanel();
	JButton b = new JButton("Search");
	JPanel urls = new JPanel();
	
	public SearchPage(String currentHost, String currentPort, String currentUsername, String currentPassword)
	{		
		this.host = currentHost;
		this.port = currentPort;
		this.username = currentUsername;
		this.password = currentPassword;
		pL = new projectsList(host, port, username, password);
		
		p1.add(words);
		p2.add(b);
		
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.X_AXIS); // top to bottom
		this.setLayout(boxLayout);
		
		BoxLayout boxLayout2 = new BoxLayout(urls, BoxLayout.Y_AXIS); // top to bottom
		urls.setLayout(boxLayout2);
		
		add(header);
		add(pL);
		add(p1);
		add(p2);
		add(urls);
		
		b.addActionListener(new ActionListener(){
	
			public void actionPerformed(ActionEvent e)
			{
				//check all checkboxes to build id list
				StringBuilder fieldIDs = new StringBuilder();
				Set<String> keys = pL.getCheckBoxes().keySet();
				for(String key : keys)
				{
					if(pL.getCheckBoxes().get(key).isSelected())
					{
						if(fieldIDs.length() == 0)
						{
							fieldIDs.append(key);
						}
						else
						{
							fieldIDs.append("," + key);
						}
					}
				}
				
				String searchString = words.getT().getText();
				SearchInput sI = new SearchInput(username, password, fieldIDs.toString(), searchString);
				ClientCommunicator cc = new ClientCommunicator(host, port);
				try
				{
					SearchOutput sO = (SearchOutput) cc.Search(sI);
					ArrayList<Value> results = sO.getValuesFound();
					urls.removeAll();
					for (int i = 0; i < results.size(); i++)
					{
						UrlButton button = new UrlButton(results.get(i).getImageURL());
						urls.add(button);
					}
				} catch (ClientException e1)
				{
					urls.removeAll();
				}
				urls.revalidate();
				urls.repaint();
			}
		});
	}
	
	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

}
