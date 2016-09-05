package client.recordIndexerGUI.components;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import shared.communication.SubmitBatchOutput;
import client.ClientException;
import client.recordIndexerGUI.Session;

@SuppressWarnings("serial")
public class TopToolBar extends JToolBar
{
	Session session;
	JButton zoomIn = new JButton("Zoom In");
	JButton zoomOut = new JButton("Zoom Out");
	JButton invertImage = new JButton("Invert Image");
	JButton toggleHighlights = new JButton("Toggle Highlights");
	JButton save = new JButton("Save");
	JButton submit = new JButton("Submit");
	public TopToolBar(Session session)
	{
		super();
		this.session = session;
		Dimension toolBarSize = new Dimension();
		toolBarSize.setSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 40);
		this.setSize(toolBarSize);
		this.setPreferredSize(toolBarSize);
		this.setMaximumSize(toolBarSize);
		this.setMinimumSize(toolBarSize);
		this.add(zoomIn);
		this.add(zoomOut);
		this.add(invertImage);
		this.add(toggleHighlights);
		this.add(save);
		this.add(submit);
		zoomIn.addActionListener(new ToolbarListener());
		zoomOut.addActionListener(new ToolbarListener());
		invertImage.addActionListener(new ToolbarListener());
		toggleHighlights.addActionListener(new ToolbarListener());
		save.addActionListener(new ToolbarListener());
		submit.addActionListener(new ToolbarListener());
	}

	public class ToolbarListener implements ActionListener
	{
		public ToolbarListener()
		{
			
		}
		
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Zoom In"))
			{
				session.zoomIn();
			}
			if(e.getActionCommand().equals("Zoom Out"))
			{
				session.zoomOut();
			}
			if(e.getActionCommand().equals("Invert Image"))
			{
				session.invertImage();
			}
			if(e.getActionCommand().equals("Toggle Highlights"))
			{
				session.toggleHighlight();
			}
			if(e.getActionCommand().equals("Save"))
			{
				session.save();
			}
			if(e.getActionCommand().equals("Submit"))
			{
				if(session.getBatch() != null)
				{
					StringBuilder fieldValues = new StringBuilder();
					String[][] s = session.getInputData();
					for(int i = 0; i < s.length; i++)
					{
						if(i != 0)
						{
							fieldValues.append(";");
						}
						for(int j = 0; j < s[i].length; j++)
						{
							String toAdd = s[i][j];
							if(toAdd == null || toAdd.equals(""))
							{
								toAdd = " ";
							}
							if(j == 0)
							{
								fieldValues.append(toAdd);
							}
							else
							{
								fieldValues.append("," + toAdd);
							}
						}
					}
					
					try
					{
						SubmitBatchOutput out = session.submitBatch(fieldValues.toString());
						if(out.isSuccess())
						{
							JOptionPane.showMessageDialog(session.getMainFrame(), "Submission successful");
							session.clearComponents();
						}
						else
						{
							JOptionPane.showMessageDialog(session.getMainFrame(), "You must submit your current batch before downloading another.");
						}
					} 
					catch (ClientException e1)
					{
						JOptionPane.showMessageDialog(session.getMainFrame(), "Failed to submit");
						e1.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(session.getMainFrame(), "You must Download a Batch First");
				}
			}
		}
	}

}
