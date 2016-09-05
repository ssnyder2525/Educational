package server.databaseAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import server.database.DatabaseRepresentation;
import shared.models.Field;

public class FieldDAO extends DatabaseAccessObject
{

	DatabaseRepresentation db;
	
	/**
	 * This class contains all the Database Access methods necessary for the Field Class
	 */
	public FieldDAO(DatabaseRepresentation db)
	{
		super(db);
	}
	
	/**
	 * Returns all fields in the database
	 * @return arraylist of all fields
	 * @throws SQLException
	 */
	public ArrayList<Field> getAllFields() throws SQLException
	{
		ArrayList<Object> objects = getAll("Field");
		ArrayList<Field> result = new ArrayList<Field>();
		for(int i = 0; i < objects.size(); i = i+7)
		{
			Field newField = new Field(String.valueOf((Integer) objects.get(i)), (String) objects.get(i+1), (String) objects.get(i+2), (String) objects.get(i+3), 
					(String) objects.get(i+4), (String) objects.get(i+5), (String) objects.get(i+6));
			
			result.add(newField);
		}
		return result;
	}
	
	/**
	 * Returns a Field in the database
	 * Uses a Select statement with the values given in the input field class.
	 * @param user The user object used for this function
	 * @return One or more Fields from the database or null if none are returned
	 * @throws SQLException 
	 */
	public ArrayList<Field> getFields(String projectID) throws SQLException
	{
		ArrayList<Field> result = new ArrayList<Field>();
		String whereStatement = "ProjectID = \"" + projectID + "\"";
		ArrayList<Object> results = get("Field", whereStatement);
		if(results.isEmpty())
		{
			return null;
		}

		for(int i = 0; i < results.size(); i = i + 7)
		{
			Field newField = new Field(String.valueOf((Integer) results.get(i)), (String) results.get(i+1), (String) results.get(i+2), (String) results.get(i+3), 
					(String) results.get(i+4), (String) results.get(i+5), (String) results.get(i+6));
			
			result.add(newField);
		}	
		return result;
	}
	
	/**
	 * Adds a field to the database
	 * @param field
	 * @return Whether the add was successful
	 * @throws SQLException 
	 */
	public String addField(Field field) throws SQLException
	{
		ArrayList<Object> values = new ArrayList<Object>();
		values.add(field.getProjectID());
		values.add(field.getColumnHeader());
		values.add(field.getFieldHelpFile());
		values.add(field.getKnownDataFile());
		values.add(field.getFieldX());
		values.add(field.getFieldWidth());
		
		String columnNames = "ProjectID, ColumnHeader, FieldHelpFile, KnownDataFile, FieldX, FieldWidth";
		String fieldID = add("Field", columnNames, values);
		return fieldID;
	}

}
