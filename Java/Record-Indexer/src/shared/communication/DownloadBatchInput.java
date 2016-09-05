package shared.communication;

public class DownloadBatchInput 
{

	/**
	 * The user of this input
	 */
	private String user;
	/**
	 * The password of this input
	 */
	private String password;
	/**
	 * The project id of this input
	 */
	private String project;
	
	public DownloadBatchInput() 
	{}
	
	/**
	 * This object stores the input of the Download Batch function
	 * @param user Name of user
	 * @param password Password of user
	 * @param project ID of project
	 */
	public DownloadBatchInput(String user, String password, String project) 
	{
		this.setUser(user);
		this.setPassword(password);
		this.setProject(project);
	}

	/**
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user)
	{
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the project
	 */
	public String getProject()
	{
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(String project)
	{
		this.project = project;
	}
	
}
