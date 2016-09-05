package client.searchGUI.pages;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.searchGUI.components.SearchInputField;

@SuppressWarnings("serial")
public class SignInPage extends JPanel
{
	JLabel header = new JLabel("Sign In");
	JPanel p1 = new JPanel();
	SearchInputField hostInput = new SearchInputField("Host: ");
	SearchInputField portInput = new SearchInputField("Port: ");
	SearchInputField usernameInput = new SearchInputField("Username: ");
	SearchInputField passwordInput = new SearchInputField("Password: ");
	JPanel p2 = new JPanel();
	JButton b = new JButton("Sign In");
	
	public SignInPage() 
	{
		p1.add(hostInput);
		p1.add(portInput);
		p1.add(usernameInput);
		p1.add(passwordInput);
		p2.add(b);
		
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to bottom
		this.setLayout(boxLayout);
		
		add(header);
		add(p1);
		add(p2);
		
		
	}
	public SearchInputField getHostInput()
	{
		return hostInput;
	}

	public SearchInputField getUsernameInput()
	{
		return usernameInput;
	}
	public SearchInputField getPasswordInput()
	{
		return passwordInput;
	}
	public SearchInputField getPortInput()
	{
		return portInput;
	}
	
	public JButton getB()
	{
		return b;
	}
}
