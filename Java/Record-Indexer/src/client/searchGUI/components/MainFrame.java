package client.searchGUI.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import shared.communication.ValidateUserInput;
import shared.communication.ValidateUserOutput;
import client.ClientException;
import client.communication.ClientCommunicator;
import client.searchGUI.pages.SearchPage;
import client.searchGUI.pages.SignInPage;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	ClientCommunicator cc;
	SignInPage signIn = new SignInPage();
	SearchPage search;
	String currentUsername;
	String currentPassword;
	
	public MainFrame()
	{
		super("Search GUI");
		setSize(800,1600);
		setResizable(true);
		
		this.add(signIn);
		this.pack();
		signIn.getB().addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e)
			{
				ClientCommunicator cc = new ClientCommunicator(signIn.getHostInput().getT().getText(), signIn.getPortInput().getT().getText());
				ValidateUserInput userParams = new ValidateUserInput(signIn.getUsernameInput().getT().getText(), signIn.getPasswordInput().getT().getText());
				try
				{
					ValidateUserOutput response = (ValidateUserOutput) cc.ValidateUser(userParams);
					if(response.isSuccess() == true)
					{
						JOptionPane.showMessageDialog(null, "Welcome, " + response.getUSER_FIRST_NAME());
						currentUsername = signIn.getUsernameInput().getT().getText();
						currentPassword = signIn.getPasswordInput().getT().getText();
						search = new SearchPage(signIn.getHostInput().getT().getText(), signIn.getPortInput().getT().getText(), currentUsername, currentPassword);
						validateUser();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Failed to sign you in....");
					}
				} catch (ClientException e1)
				{
					
				}
				reval();
				
			}

		});
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void reval()
	{
		this.revalidate();
	}
	
	public void validateUser()
	{
		remove(signIn);
		this.add(search);
		this.pack();
	}
}
