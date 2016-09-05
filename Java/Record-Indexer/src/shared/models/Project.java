package shared.models;

import java.util.ArrayList;

/**
 * 
 * @author ssnyder
 *
 */
public class Project 
{
	
	/**The unique ID of this project*/
	private String projectID;
	/**The title of this project*/
	private String title;
	/**The total number of records indexed for this project*/
	private int totalIndexed;
	/**The number of records given per batch in this project*/
	private String recordsPerBatch;
	/**Where does the first record start in the image*/
	private String fieldY;
	/**The height of each record.*/
	private String recordHeight;
	/**Stores the batches in this project*/
	private ArrayList<String> batches = new ArrayList<String>();
	
	/**
	 * This is the model class of the Project. It represents a project that is stored in the database
	 */
	public Project(String projectID, String title, int totalIndexed, String recordsPerBatch, String fieldY, String recordHeight) 
	{
		setProjectID(projectID);
		setTitle(title);
		setTotalIndexed(totalIndexed);
		setRecordsPerBatch(recordsPerBatch);
		setFieldY(fieldY);
		setRecordHeight(recordHeight);
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

	/**
	 * @return the title
	 */
	public String getTitle() 
	{
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) 
	{
		this.title = title;
	}

	/**
	 * @return the totalIndexed
	 */
	public int getTotalIndexed() 
	{
		return totalIndexed;
	}

	/**
	 * @param totalIndexed the totalIndexed to set
	 */
	public void setTotalIndexed(int totalIndexed) 
	{
		this.totalIndexed = totalIndexed;
	}
	
	public void incrementTotalIndexed()
	{
		this.totalIndexed = this.totalIndexed + Integer.parseInt(this.recordsPerBatch);
	}

	/**
	 * @return the recordsPerBatch
	 */
	public String getRecordsPerBatch() 
	{
		return recordsPerBatch;
	}

	/**
	 * @param recordsPerBatch the recordsPerBatch to set
	 */
	public void setRecordsPerBatch(String recordsPerBatch) 
	{
		this.recordsPerBatch = recordsPerBatch;
	}

	/**
	 * @return the fieldY
	 */
	public String getFieldY() 
	{
		return fieldY;
	}

	/**
	 * @param fieldY the fieldY to set
	 */
	public void setFieldY(String fieldY) 
	{
		this.fieldY = fieldY;
	}

	/**
	 * @return the recordHeight
	 */
	public String getRecordHeight() 
	{
		return recordHeight;
	}

	/**
	 * @param recordHeight the recordHeight to set
	 */
	public void setRecordHeight(String recordHeight) 
	{
		this.recordHeight = recordHeight;
	}

	/**
	 * @return the batches
	 */
	public ArrayList<String> getBatches()
	{
		return batches;
	}

	/**
	 * @param batches the batches to set
	 */
	public void setBatches(ArrayList<String> batches)
	{
		this.batches = batches;
	}

}
