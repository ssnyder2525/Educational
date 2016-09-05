package shared.communication;

public class GetFieldsInput 
{

	/**
	 * The users name
	 */
	private String user;
	/**
	 * The user's entered password
	 */
	private String password;
	/**
	 * The project to draw from
	 */
	private String project;
	
	public GetFieldsInput() 
	{}
	/**
	 * This object stores the input of the GetFields function of the client communicator
	 * @param user
	 * @param password
	 * @param project
	 */
	public GetFieldsInput(String user, String password, String project) 
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
