package client.recordIndexerGUI.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import shared.communication.ValidateUserInput;
import shared.communication.ValidateUserOutput;
import client.ClientException;
import client.recordIndexerGUI.Session;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	Session session;
	MainMenu mainMenu;
	TopToolBar toolBar;
	CenterPanes centerPanes;
	
	public MainFrame(String label, String host, String port)
	{
		super(label);
		
		session = new Session(this, host, port,"","");
		
		//create components
		mainMenu = new MainMenu(session);
		toolBar = new TopToolBar(session);
		centerPanes = new CenterPanes(session);
		
		//initialize session
		session.initialize();

		//set main frame dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize);
		Dimension minimum = new Dimension();
		minimum.setSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-400, Toolkit.getDefaultToolkit().getScreenSize().getHeight()-200);
		this.setPreferredSize(screenSize);
		this.setMinimumSize(minimum);
		this.setMaximumSize(screenSize);
		
		//add the components
		this.setJMenuBar(mainMenu);
		this.add(toolBar,BorderLayout.NORTH);
		this.add(centerPanes, BorderLayout.CENTER);
		this.setResizable(true);
		this.addWindowListener(new WindowEventHandler(session));
		this.setVisible(true);
		
		//force the user to log in first
		logIn();
	}

	
//Getters
	public Session getSession()
	{
		return session;
	}
	
	public MainMenu getMainMenu()
	{
		return mainMenu;
	}
	
	public TopToolBar getTopToolBar()
	{
		return toolBar;
	}
	
	public CenterPanes getCenterPanes()
	{
		return centerPanes;
	}

//setters
	public void setSession(Session session)
	{
		this.session = session;
	}

	public void setMainMenu(MainMenu mainMenu)
	{
		this.mainMenu = mainMenu;
	}

	public void setTopToolBar(TopToolBar topToolBar)
	{
		this.toolBar = topToolBar;
	}

	public void setCenterPanes(CenterPanes centerPanes)
	{
		this.centerPanes = centerPanes;
	}
	
	
//Other functionality
	public void logIn()
	{
		boolean validated = false;
		while(validated == false)
		{
			//sign up box
			JTextField username = new JTextField();
			JTextField password = new JPasswordField();
			Object[] message = 
			{
			    "Username:", username,
			    "Password:", password
			};

			int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) 
			{
			   ValidateUserInput validateInput = new ValidateUserInput(username.getText(), password.getText());
			   try
			   {
				  ValidateUserOutput validateOutput = session.ValidateUser(validateInput);
				  if(validateOutput.isSuccess() == true)
				   {
					   JOptionPane.showMessageDialog(this, "Welcome " + validateOutput.getUSER_FIRST_NAME() + " " + validateOutput.getUSER_LAST_NAME()
							   + "\nYou have indexed " + validateOutput.getNUM_RECORDS() + " records");
					   validated = true;	
					   Map<String, Object> params = new HashMap<String, Object>();
					   params.put("username", validateInput.getUser());
					   params.put("password", validateInput.getPassword());
					   session.addToUserData(params);
					   session.load();
				   }
				   else
				   {
					   JOptionPane.showMessageDialog(this, "Incorrect Username or Password");
				   }
			   }
				catch (ClientException | IOException e)
				{
					JOptionPane.showMessageDialog(this, "Validation request failed!");
				}
			} 
			else 
			{
			    System.exit(0);
			}
		}
	}


	public void refresh()
	{
		this.revalidate();
		this.centerPanes.refresh();
	}
	
//listener
	class WindowEventHandler extends WindowAdapter 
	{
		Session session;
		
		WindowEventHandler(Session session)
		{
			this.session = session;
		}
		public void windowClosing(WindowEvent evt) 
		{
			session.save();
			System.exit(0);
		}
	}
	
}
