package shared.communication;

import java.util.ArrayList;

import shared.models.Value;

public class SearchOutput 
{
	/**
	 * Stores the Values returned by the requests
	 */
	private ArrayList<Value> valuesFound = new ArrayList<Value>();
	
	public SearchOutput() 
	{}

	/**
	 * This object stores the data for the output of the search function
	 * @param batchID
	 * @param imageURL
	 * @param recordNum
	 * @param fieldID
	 */
	public SearchOutput(ArrayList<Value> valuesFound) 
	{
		this.setValuesFound(valuesFound);
	}

	/**
	 * @return the valuesFound
	 */
	public ArrayList<Value> getValuesFound()
	{
		return valuesFound;
	}

	/**
	 * @param valuesFound the valuesFound to set
	 */
	public void setValuesFound(ArrayList<Value> valuesFound)
	{
		this.valuesFound = valuesFound;
	}
	
	@Override
	public String toString()
	{
		StringBuilder st = new StringBuilder();
		int i = 0;
		while (i != this.valuesFound.size())
		{
			st.append(this.valuesFound.get(i).getBatchID() + "\n");
			st.append(this.valuesFound.get(i).getImageURL() + "\n");
			st.append(this.valuesFound.get(i).getRecordNum() + "\n");
			st.append(this.valuesFound.get(i).getFieldID() + "\n");
			
			i++;
		}
		return st.toString();
	}

}
