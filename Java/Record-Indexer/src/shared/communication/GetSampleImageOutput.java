package shared.communication;

public class GetSampleImageOutput 
{

	/**
	 * The output url for this function
	 */
	private String IMAGE_URL;
	
	public GetSampleImageOutput() 
	{
		
	}
	
	/**
	 * This object stores the output data for the GetSampleImage function
	 * @param url
	 */
	public GetSampleImageOutput(String url) 
	{
		this.setIMAGE_URL(url);
	}

	/**
	 * @return the iMAGE_URL
	 */
	public String getIMAGE_URL()
	{
		return IMAGE_URL;
	}

	/**
	 * @param iMAGE_URL the iMAGE_URL to set
	 */
	public void setIMAGE_URL(String iMAGE_URL)
	{
		IMAGE_URL = iMAGE_URL;
	}
	
	@Override
	public String toString()
	{
		StringBuilder st = new StringBuilder();
		st.append(this.getIMAGE_URL() + "\n");
		return st.toString();
	}

}
