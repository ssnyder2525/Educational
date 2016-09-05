package server.databaseAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import server.database.DatabaseRepresentation;
import shared.models.Value;

public class ValueDAO extends DatabaseAccessObject
{

	DatabaseRepresentation db;
	/**
	 * This DAO contains the Database Access methods necessary for the Value object
	 * @param db
	 */
	public ValueDAO(DatabaseRepresentation db)
	{
		super(db);
	}
	
	/**
	 * Returns all values in the database
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Value> getAllValues(String table) throws SQLException
	{
		ArrayList<Value> result = new ArrayList<Value>();
		ArrayList<Object> results = getAll("Value");
		if(results.isEmpty())
		{
			return null;
		}

		for(int i = 0; i < results.size(); i = i + 6)
		{
			Value newValue = new Value(String.valueOf((Integer) results.get(i)), (String) results.get(i+1), (String) results.get(i+2), 
					(String) results.get(i+3), (String) results.get(i+4), (String) results.get(i+5));
			result.add(newValue);
		}
		return result;
	}
	
	/**
	 * Add a new Value to the database
	 * @param value
	 * @return the id of the value in the database
	 * @throws SQLException 
	 */
	public String addValue(Value value) throws SQLException
	{
		ArrayList<Object> values = new ArrayList<Object>();
		values.add(value.getRecordNum());
		values.add(value.getValue());
		values.add(value.getFieldID());
		values.add(value.getBatchID());
		values.add(value.getImageURL());
		
		String columnNames = "RecordNum, Value, FieldID, BatchID, ImageURL";
		String valueID = add("Value", columnNames, values);
		return valueID;
	}
	
	/**
	 * Searches a field for a particular value
	 * @param fieldValue
	 * @param searchValue
	 * @return A Search result or null
	 * @throws SQLException 
	 */
	public ArrayList<Value> getValues(Value value) throws SQLException
	{		
		ArrayList<Value> result = new ArrayList<Value>();
		String whereStatement = "BatchID = \"" + value.getBatchID() + "\"";
		ArrayList<Object> results = get("Value", whereStatement);
		if(results.isEmpty())
		{
			return null;
		}
		
		for(int i = 0; i < results.size(); i = i + 6)
		{
			Value newValue = new Value(String.valueOf((Integer) results.get(i)), (String) results.get(i+1), (String) results.get(i+2), 
					(String) results.get(i+3), (String) results.get(i+4), (String) results.get(i+5));
			result.add(newValue);
		}	
		return result;
	}
	
	/**
	 * searches in the database for a value
	 * @param fieldID the field id to search for
	 * @param searchValue the content of the value to search for
	 * @return an array of values that match
	 * @throws SQLException 
	 */
	public ArrayList<Value> searchValues(String fieldID) throws SQLException
	{
		ArrayList<Value> result = new ArrayList<Value>();
		String whereStatement = "FieldID = \"" + fieldID + "\"";
		ArrayList<Object> results = get("Value", whereStatement);
		if(results.isEmpty())
		{
			return null;
		}
		
		for(int i = 0; i < results.size(); i = i + 6)
		{
			Value newValue = new Value(String.valueOf((Integer) results.get(i)), (String) results.get(i+1), (String) results.get(i+2), 
					(String) results.get(i+3), (String) results.get(i+4), (String) results.get(i+5));
			result.add(newValue);
		}
		return result;
	}
	
	/**
	 * Changes the value of an existing value
	 * @param value
	 * @return Whether the update was successful
	 * @throws SQLException 
	 */
	public Boolean updateValue(Value value) throws SQLException
	{
		ArrayList<Object> values = new ArrayList<Object>();
		values.add(value.getValue());
		
		ArrayList<String> tableValues = new ArrayList<String>();
		tableValues.add("Value");
		
		String whereStatement = "ValueID = " + value.getValueID();
		Boolean success = update("Value", whereStatement, tableValues, values);
		return success;
	}	

}
