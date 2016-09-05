package shared.models;
/**
 * @author ssnyder
 */
public class User 
{
	
	/**The User's unique ID*/
	private String userID;
	/**The User's first name.*/
	private String firstName;
	/**The User's last name.*/
	private String lastName;
	/**The User's emailAddress.*/
	private String email;
	/**The User's chosen user name.*/
	private String username;
	/**The User's chosen password.*/
	private String password;
	/**The id of the batch this user is currently working on.*/
	private String currentBatch;
	/**The number of batches the user has completed*/
	private int batchesCompleted;
	
	/**
	 * This is the model class of the User. It represents a user that is stored in the database
	 */
	public User(String newUserID, String newFirstName, String newLastName, String newEmail, String newUsername, String newPassword, 
			String newCurrentBatch, int newBatchesCompleted) 
	{
		setUserID(newUserID);
		setFirstName(newFirstName);
		setLastName(newLastName);
		setEmail(newEmail);
		setUsername(newUsername);
		setPassword(newPassword);
		setCurrentBatch(newCurrentBatch);
		setBatchesCompleted(newBatchesCompleted);
	}

	/**
	 * @return the userID
	 */
	public String getUserID() 
	{
		return userID;
	}
	
	/**
	 * @param UserID the UserID to set
	 */
	public void setUserID(String UserID)
	{
		this.userID = UserID;
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() 
	{
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() 
	{
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() 
	{
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) 
	{
		this.email = email;
	}

	/**
	 * @return the username
	 */
	public String getUsername() 
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) 
	{
		this.username = username;
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
	 * @return the currentBatch
	 */
	public String getCurrentBatch() 
	{
		return currentBatch;
	}

	/**
	 * @param currentBatch the currentBatch to set
	 */
	public void setCurrentBatch(String currentBatch) 
	{
		this.currentBatch = currentBatch;
	}

	/**
	 * @return the batchesCompleted
	 */
	public int getBatchesCompleted() 
	{
		return batchesCompleted;
	}

	/**
	 * @param batchesCompleted the batchesCompleted to set
	 */
	public void setBatchesCompleted(int batchesCompleted) 
	{
		this.batchesCompleted = batchesCompleted;
	}
	
	/**
	 * Increases the number of records this user has submitted
	 * @param numberOfRecords
	 */
	public void incrementBatchesCompleted(int numberOfRecords)
	{
		this.batchesCompleted = this.batchesCompleted + numberOfRecords;
	}


}
