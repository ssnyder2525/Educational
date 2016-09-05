package shared.communication;

public class SubmitBatchInput 
{

	/**
	 * This is the input user
	 */
	private String user;
	/**
	 * This is the password provided for the user
	 */
	private String password;
	/**
	 * This is the batch provided to the function
	 */
	private String batch;
	/**
	 * This is the values in the recorded fields
	 */
	private String fieldValues;
	
	public SubmitBatchInput() 
	{}
	
	/**
	 * This object stores the input data of the SubmitBatch function
	 * @param user
	 * @param password
	 * @param batch
	 * @param fieldValues
	 * @param recordValues
	 */
	public SubmitBatchInput(String user, String password, String batch, String fieldValues) 
	{
		this.setUser(user);
		this.setPassword(password);
		this.setBatch(batch);
		this.setFieldValues(fieldValues);
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
	 * @return the batch
	 */
	public String getBatch()
	{
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setBatch(String batch)
	{
		this.batch = batch;
	}

	/**
	 * @return the fieldValues
	 */
	public String getFieldValues()
	{
		return fieldValues;
	}

	/**
	 * @param fieldValues the fieldValues to set
	 */
	public void setFieldValues(String fieldValues)
	{
		this.fieldValues = fieldValues;
	}

}
