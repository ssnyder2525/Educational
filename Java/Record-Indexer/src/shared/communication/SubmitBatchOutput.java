package shared.communication;

public class SubmitBatchOutput 
{

	/**
	 * Indicates if the function was successful
	 */
	private boolean success;
	
	/**
	 * This object stores the output data for the SubmitBatch function
	 */
	public SubmitBatchOutput(boolean success) 
	{
		this.success = success;
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
		}
		else
		{
			st.append("FAILED\n");
		}
		return st.toString();
	}

}
