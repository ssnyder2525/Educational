package client.recordIndexerGUI.components;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import shared.communication.DownloadBatchOutput;
import shared.communication.GetProjectsOutput;
import shared.models.Batch;
import shared.models.Project;
import client.ClientException;
import client.recordIndexerGUI.Session;

@SuppressWarnings("serial")
public class MainMenu extends JMenuBar
{
	Session session;
	JMenu menu;
	JMenuItem downloadBatch = new JMenuItem("Download Batch");
	JMenuItem logout = new JMenuItem("Logout");
	JMenuItem exit = new JMenuItem("Exit");
	
	public MainMenu(Session session)
	{
		super();
		this.session = session;
		
		//create the menu
		downloadBatch = new JMenuItem("Download Batch");
		logout = new JMenuItem("Logout");
		exit = new JMenuItem("Exit");
		
		menu = new JMenu("File");
		
		menu.setMnemonic(KeyEvent.VK_A);
		
		menu.add(downloadBatch);
		menu.add(logout);
		menu.add(exit);
		
		menu.getAccessibleContext().setAccessibleDescription("The File menu for this program");
		
		this.add(menu);
		
		//add the listeners for each option
		downloadBatch.addActionListener(new FileListener());
		logout.addActionListener(new FileListener());
		exit.addActionListener(new FileListener());
	}
	
	public void logOut()
	{
		session.logout();
	}
	
//listener	
	public class FileListener implements ActionListener
	{
		public FileListener() 
		{
		}

		public void actionPerformed(ActionEvent e) 
		{
			if(e.getActionCommand().equals("Download Batch"))
			{
				buildProjectSelectionBox();
			}
			if(e.getActionCommand().equals("Logout"))
			{
				logOut();
			}
			if(e.getActionCommand().equals("Exit"))
			{
				session.save();
				System.exit(0);
			}
		}

		private void buildProjectSelectionBox()
		{
			Map<String,Project> projectsByTitle = new HashMap<String,Project>();
			JComboBox<String> projects = new JComboBox<String>();
			
			try
			{
				GetProjectsOutput out = session.GetProjects();
				for(Project p : out.getPROJECTS())
				{
					projects.addItem(p.getTitle());
					projectsByTitle.put(p.getTitle(), p);
				}
				
				Object[] options = new Object[4];
				options[0] = projects;
				options[1] = "Get Sample";
				options[2] = "Ok";
				options[3] = "Cancel";
				
				int option = JOptionPane.showOptionDialog(null, "Select a Project", "Select a Project",
				        JOptionPane.WARNING_MESSAGE, 0, null, options, options[0]);
				
				if (option == 2) 
				{
					Project project = projectsByTitle.get(projects.getSelectedItem());
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("project", project); 
					session.addToUserData(params);
					downloadBatch();
				}
				else if (option == 1)
				{
					class ImageBoxClosingHandler extends WindowAdapter 
					{
						@SuppressWarnings("deprecation")
						public void windowClosing(WindowEvent evt) 
						{
							session.getMainFrame().setFocusable(true);
							JDialog dialog = (JDialog)evt.getSource();
							dialog.hide();
							buildProjectSelectionBox();
						}
					}
					Project project = projectsByTitle.get(projects.getSelectedItem());
					URL url = session.getSampleImage(project.getProjectID());
					Image image = ImageIO.read(url);
					JDialog dialog = new JDialog(session.getMainFrame(), Dialog.ModalityType.APPLICATION_MODAL);
					dialog.setMinimumSize(new Dimension(image.getWidth(dialog)/2,image.getHeight(dialog)/2));
					dialog.setMaximumSize(new Dimension(image.getWidth(null),image.getHeight(null)));
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					dialog.setResizable(false);
					dialog.addWindowListener(new ImageBoxClosingHandler());
					image = image.getScaledInstance(image.getWidth(dialog) / 2, image.getHeight(dialog) / 2, Image.SCALE_DEFAULT);
					JLabel imageLabel = new JLabel(new ImageIcon(image));
					dialog.add(imageLabel);
					dialog.setVisible(true);
				}
				else if (option == 3)
				{
				}
			} 
			catch (ClientException | IOException e)
			{
				System.out.println("Failed to build the Select Project Box");
				e.printStackTrace();
			}
		}

		private void downloadBatch()
		{
			try
			{
				DownloadBatchOutput out = session.downloadBatch();
				Batch batch = new Batch(out.getBATCH_ID(), out.getPROJECT_ID(), 1, 0, out.getIMAGE_URL());
				Map<String, Object> params = new HashMap<String, Object>();
				batch.setFields(out.getFields());
				params.put("batch", batch);
				params.put("InputData", "");
				session.addToUserData(params);
				
				session.initializeQualityChecker();
				session.createTable();
				session.createFieldPane();
				session.createHelpPane();
				session.createImageNav();
				session.refresh();
				session.save();
			} 
			catch (ClientException | IOException e)
			{
				if(session.getProject() == null)
				{
					JOptionPane.showMessageDialog(session.getMainFrame(), "There are no available batches in this project.");
				}
				else
				{
					JOptionPane.showMessageDialog(session.getMainFrame(), "You must submit your current batch before downloading another.");
				}
			}
		}
	}
}
