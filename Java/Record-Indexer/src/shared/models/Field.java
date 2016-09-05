package shared.models;
/**
 * 
 * @author ssnyder
 *
 */
public class Field 
{
	
	/**This field's unique ID*/
	private String fieldID;
	/**The id of this field's associated project*/
	private String projectID;
	/**The column label to which this field belongs*/
	private String columnHeader;
	/**The file from which help can be drawn for this field*/
	private String fieldHelpFile;
	/**The data file associated with this field*/
	private String knownDataFile;
	/**The x value where this field begins*/
	private String fieldX;
	/**The width of this field*/
	private String fieldWidth;
	
	/**
	 * This is the model class of the Field. It represents a field that is stored in the database
	 */
	public Field(String newFieldID, String newProjectID, String newcolumnHeader, 
			String newFieldHelpFile, String newKnownDataFile, String newFieldX, String newFieldWidth) 
	{
		setFieldID(newFieldID);
		setProjectID(newProjectID);
		setColumnHeader(newcolumnHeader);
		setFieldHelpFile(newFieldHelpFile);
		setKnownDataFile(newKnownDataFile);
		setFieldX(newFieldX);
		setFieldWidth(newFieldWidth);
	}

	/**
	 * @return the fieldID
	 */
	public String getFieldID() 
	{
		return fieldID;
	}

	/**
	 * @param fieldID the fieldID to set
	 */
	public void setFieldID(String fieldID) 
	{
		this.fieldID = fieldID;
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
	 * @return the columnHeader
	 */
	public String getColumnHeader() 
	{
		return columnHeader;
	}

	/**
	 * @param columnHeader the columnHeader to set
	 */
	public void setColumnHeader(String columnHeader) 
	{
		this.columnHeader = columnHeader;
	}

	/**
	 * @return the fieldHelpFile
	 */
	public String getFieldHelpFile() 
	{
		return fieldHelpFile;
	}

	/**
	 * @param fieldHelpFile the fieldHelpFile to set
	 */
	public void setFieldHelpFile(String fieldHelpFile) 
	{
		this.fieldHelpFile = fieldHelpFile;
	}

	/**
	 * @return the knownDataFile
	 */
	public String getKnownDataFile() 
	{
		return knownDataFile;
	}

	/**
	 * @param knownDataFile the knownDataFile to set
	 */
	public void setKnownDataFile(String knownDataFile) 
	{
		this.knownDataFile = knownDataFile;
	}

	/**
	 * @return the fieldX
	 */
	public String getFieldX() 
	{
		return fieldX;
	}

	/**
	 * @param fieldX the fieldX to set
	 */
	public void setFieldX(String fieldX) 
	{
		this.fieldX = fieldX;
	}

	/**
	 * @return the fieldWidth
	 */
	public String getFieldWidth() 
	{
		return fieldWidth;
	}

	/**
	 * @param fieldWidth the fieldWidth to set
	 */
	public void setFieldWidth(String fieldWidth) 
	{
		this.fieldWidth = fieldWidth;
	}
}
