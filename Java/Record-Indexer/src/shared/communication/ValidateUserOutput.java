package shared.communication;

public class ValidateUserOutput 
{

	/**
	 * The first name of the returned user
	 */
	private String USER_FIRST_NAME;
	/**
	 * The last name of the returned user
	 */
	private String USER_LAST_NAME;
	/**
	 * The number of records indexed by the returned user
	 */
	private Integer NUM_RECORDS;
	/**
	 * Indicates whether the function was successful
	 */
	private boolean success;
	
	public ValidateUserOutput() 
	{}

	/**
	 * This object contains the output data of the ValidateUser function
	 * @param first
	 * @param last
	 * @param records
	 */
	public ValidateUserOutput(String first, String last, Integer records, boolean success) 
	{
		this.setUSER_FIRST_NAME(first);
		this.setUSER_LAST_NAME(last);
		this.setNUM_RECORDS(records);
		this.setSuccess(success);
	}

	/**
	 * @return the uSER_FIRST_NAME
	 */
	public String getUSER_FIRST_NAME()
	{
		return USER_FIRST_NAME;
	}

	/**
	 * @param uSER_FIRST_NAME the uSER_FIRST_NAME to set
	 */
	public void setUSER_FIRST_NAME(String uSER_FIRST_NAME)
	{
		USER_FIRST_NAME = uSER_FIRST_NAME;
	}

	/**
	 * @return the uSER_LAST_NAME
	 */
	public String getUSER_LAST_NAME()
	{
		return USER_LAST_NAME;
	}

	/**
	 * @param uSER_LAST_NAME the uSER_LAST_NAME to set
	 */
	public void setUSER_LAST_NAME(String uSER_LAST_NAME)
	{
		USER_LAST_NAME = uSER_LAST_NAME;
	}

	/**
	 * @return the nUM_RECORDS
	 */
	public Integer getNUM_RECORDS()
	{
		return NUM_RECORDS;
	}

	/**
	 * @param nUM_RECORDS the nUM_RECORDS to set
	 */
	public void setNUM_RECORDS(Integer nUM_RECORDS)
	{
		NUM_RECORDS = nUM_RECORDS;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess()
	{
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success)
	{
		this.success = success;
	}
	
	@Override
	public String toString()
	{
		StringBuilder st = new StringBuilder();
		if(this.isSuccess())
		{
			st.append("TRUE\n");
			st.append(this.getUSER_FIRST_NAME() + "\n");
			st.append(this.getUSER_LAST_NAME() + "\n");
			st.append(this.getNUM_RECORDS() + "\n");
			return st.toString();
		}
		else
		{
			st.append("FALSE\n");
			return st.toString();
		}
	}

}
