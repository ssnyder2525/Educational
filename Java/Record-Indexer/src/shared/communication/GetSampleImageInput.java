package shared.communication;

public class GetSampleImageInput 
{
	
	/**
	 * This is the input user for the GetSampleImage function
	 */
	private String user;
	/**
	 * This the password input for the GetSampleImage function
	 */
	private String password;
	/**
	 * This is the project id input for the GetSampleImage function
	 */
	private String project;
	
	public GetSampleImageInput() 
	{}
	
	/**
	 * This object stores the input data for the GetSampleImage input
	 * @param user
	 * @param password
	 * @param project
	 */
	public GetSampleImageInput(String user, String password, String project) 
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
