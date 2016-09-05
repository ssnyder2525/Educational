package client.searchGUI;

import java.awt.*;
import client.searchGUI.components.MainFrame;

public class SearchGUI
{
		
	public static void main(String[] args)
	{
		/*
		In Swing, all user interface operations must occur on the �UI thread�.
		All components should be created on the UI thread.
		All method calls on UI components should happen on the UI thread.
		EventQueue.invokeLater runs the specified code on the UI thread.
		The main method for Swing programs should call EventQueue.invokeLater to create the UI.
		The main thread exits immediately after calling EventQueue.invokeLater, 
			but the UI thread keeps the program running.
	 */
	EventQueue.invokeLater(new Runnable() {		
		public void run() {
			// Create the frame window object
			MainFrame frame = new MainFrame();

			// Make the frame window visible
			frame.setVisible(true);
		}
	});
	}
}
