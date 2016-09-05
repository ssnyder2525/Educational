package shared.communication;

import shared.models.User;

public class GetProjectsInput 
{

	/**
	 * The user who called this function
	 */
	private String user;
	/**
	 * The password the user entered
	 */
	private String password;
	
	public GetProjectsInput() 
	{}
	
	/**
	 * This object represents the input of the GetProjects function
	 * @param user
	 * @param password
	 */
	public GetProjectsInput(String user, String password) 
	{
		this.setUser(user);
		this.setPassword(password);
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
	
	public User generateUser()
	{
		User newUser = new User("-1", "", "", "", this.user,this.password, "", 0);
		return newUser;
	}

}
