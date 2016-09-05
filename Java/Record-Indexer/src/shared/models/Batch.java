package shared.models;

import java.util.ArrayList;

/**
 * 
 * @author ssnyder
 *
 */
public class Batch 
{
	
	/**The ID for this Batch*/
	private String batchID;
	/**The project this batch belongs to**/
	private String projectID;
	/**Tells whether the batch is in use*/
	private int inUse;
	/**Tells whether the batch has been submitted*/
	private int completed;
	/**Stores the location of the image file for this batch*/
	private String sourceURL;
	/**Stores the fields associated with this batch*/
	private ArrayList<Field> fields= new ArrayList<Field>();
	
	/**
	 * This is the model class of the Batch. It represents a Batch that is stored in the database
	 */
	public Batch(String batchID, String projectID, int inUse, int completed, String sourceURL) 
	{
		setBatchID(batchID);
		setProjectID(projectID);
		setInUse(inUse);
		setCompleted(completed);
		setSourceURL(sourceURL);
	}

	/**
	 * @return the batchID
	 */
	public String getBatchID() 
	{
		return batchID;
	}

	/**
	 * @param batchID the batchID to set
	 */
	public void setBatchID(String batchID) 
	{
		this.batchID = batchID;
	}

	/**
	 * @return the inUse
	 */
	public int isInUse() 
	{
		return inUse;
	}

	/**
	 * @param inUse the inUse to set
	 */
	public void setInUse(int inUse) 
	{
		this.inUse = inUse;
	}

	/**
	 * @return the completed
	 */
	public int isCompleted() 
	{
		return completed;
	}

	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(int completed) 
	{
		this.completed = completed;
	}

	/**
	 * @return the sourceURL
	 */
	public String getSourceURL() 
	{
		return sourceURL;
	}

	/**
	 * @param sourceURL the sourceURL to set
	 */
	public void setSourceURL(String sourceURL) 
	{
		this.sourceURL = sourceURL;
	}

	/**
	 * @return the fields
	 */
	public ArrayList<Field> getFields()
	{
		return fields;
	}

	/**
	 * @param fields2 the fields to set
	 */
	public void setFields(ArrayList<Field> fields2)
	{
		this.fields = fields2;
	}

	/**
	 * @return the projectID
	 */
	public String getProjectID()
	{
		return projectID;
	}

	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(String projectID)
	{
		this.projectID = projectID;
	}

	
}
