package client.searchGUI.components;

import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import client.ClientException;
import client.communication.ClientCommunicator;
import shared.communication.*;
import shared.models.*;

@SuppressWarnings("serial")
public class projectsList extends JPanel
{
	Map<String, JCheckBox> checkBoxes = new HashMap<String, JCheckBox>();
	public projectsList(String host, String port, String username, String password)
	{
		ClientCommunicator cc = new ClientCommunicator(host, port);
		GetProjectsInput in = new GetProjectsInput(username, password);
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to bottom
		this.setLayout(boxLayout);
		try
		{
			GetProjectsOutput out = (GetProjectsOutput) cc.GetProjects(in);
			for (Project project : out.getPROJECTS())
			{
				JLabel l = new JLabel(project.getTitle());
				this.add(l);
				GetFieldsInput in2 = new GetFieldsInput(username, password, project.getProjectID());
				GetFieldsOutput out2 = (GetFieldsOutput) cc.GetFields(in2);
				for(Field field : out2.getFields())
				{
					JCheckBox cB = new JCheckBox(field.getColumnHeader());
					checkBoxes.put(field.getFieldID(), cB);
					this.add(cB);
				}				
			}
		} 
		catch (ClientException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Map<String, JCheckBox> getCheckBoxes()
	{
		return checkBoxes;
	}

}
