package shared.communication;

import java.util.ArrayList;

import shared.models.Field;

public class GetFieldsOutput 
{
	/**
	 * Stores the resulting list of fields
	 */
	private ArrayList<Field> fields = new ArrayList<Field>();
	
	public GetFieldsOutput() 
	{}

	/**
	 * This object represents the output of the GetFields function of the client communicator
	 * @param projectID
	 * @param fieldID
	 * @param fieldTitle
	 */
	public GetFieldsOutput(ArrayList<Field> fields) 
	{
		this.setFields(fields);
	}

	/**
	 * @return the fields
	 */
	public ArrayList<Field> getFields()
	{
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<Field> fields)
	{
		this.fields = fields;
	}
	
	@Override
	public String toString()
	{
		StringBuilder st = new StringBuilder();
		int i = 0;
		while (i != this.getFields().size())
		{
			st.append(this.getFields().get(i).getProjectID() + "\n");
			st.append(this.getFields().get(i).getFieldID() + "\n");
			st.append(this.getFields().get(i).getColumnHeader() + "\n");
			i++;
		}
		return st.toString();
	}

}
