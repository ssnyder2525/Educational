package client.recordIndexerGUI.dataStorageClasses;

import java.net.URL;


//import com.thoughtworks.xstream.annotations.XStreamOmitField;




import shared.models.Batch;
import shared.models.Project;

@SuppressWarnings("serial")
public class UserData implements java.io.Serializable
{
	private String username;
	private String password;
	private Batch batch = null;
	private Project project = null;
	private URL imageUrl;
	
	private InputData inputData = null;
	
	private WindowData windowData = new WindowData();
	
	public UserData(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
//getters
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public InputData getInputData()
	{
		return inputData;
	}
	
	public Batch getBatch()
	{
		return batch;
	}
	
	public Project getProject()
	{
		return project;
	}
	
	public URL getImageUrl()
	{
		return this.imageUrl;
	}
	
	public WindowData getWindowData()
	{
		return windowData;
	}
	
//setters
	public void setUsername(String username)
	{
		this.username = username;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setInputData(InputData tableData)
	{
		this.inputData = tableData;
	}

	public void setBatch(Batch batch)
	{
		this.batch = batch;
	}

	public void setProject(Project project)
	{
		this.project = project;
	}

	public void setImageUrl(URL url)
	{
		this.imageUrl = url;
	}
	
//other functionality
	public void clearInputData()
	{
		this.inputData = null;		
	}

	public boolean isMispelled(String value, int column)
	{
		return inputData.isMispelled(batch, value, column);
	}
}
