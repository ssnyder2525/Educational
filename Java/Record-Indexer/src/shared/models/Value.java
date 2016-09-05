package shared.models;

public class Value 
{
	
	/**The unique id for this object**/
	private String ValueID;
	/**The row number in the field**/
	private String RecordNum;
	/**The data stored in this value**/
	private String Value;
	/**The id of the field this value belongs to**/
	private String FieldID;
	/**The id of the batch this value belongs to**/
	private String BatchID;
	/**The url for the image file**/
	private String ImageURL;

	public Value() {
		
	}
	
	/**
	 * This object stores the data of the value of a record in a field.
	 * @param valueID
	 * @param value
	 * @param fieldID
	 * @param batchID
	 */
	public Value(String valueID, String recordNum, String value, String fieldID, String batchID, String imageURL) 
	{
		setValueID(valueID);
		setRecordNum(recordNum);
		setValue(value);
		setFieldID(fieldID);
		setBatchID(batchID);
		setImageURL(imageURL);
	}

	/**
	 * @return the valueID
	 */
	public String getValueID() 
	{
		return ValueID;
	}

	/**
	 * @param valueID the valueID to set
	 */
	public void setValueID(String valueID) 
	{
		ValueID = valueID;
	}
	
	/**
	 * @return the recordNum
	 */
	public String getRecordNum()
	{
		return RecordNum;
	}

	/**
	 * @param recordNum the recordNum to set
	 */
	public void setRecordNum(String recordNum)
	{
		RecordNum = recordNum;
	}

	/**
	 * @return the value
	 */
	public String getValue() 
	{
		return Value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) 
	{
		Value = value;
	}

	/**
	 * @return the fieldID
	 */
	public String getFieldID()
	{
		return FieldID;
	}

	/**
	 * @param fieldID the fieldID to set
	 */
	public void setFieldID(String fieldID)
	{
		FieldID = fieldID;
	}
	
	/**
	 * @return the batchID
	 */
	public String getBatchID() 
	{
		return BatchID;
	}

	/**
	 * @param batchID the batchID to set
	 */
	public void setBatchID(String batchID) 
	{
		BatchID = batchID;
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL()
	{
		return ImageURL;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL)
	{
		ImageURL = imageURL;
	}

}
