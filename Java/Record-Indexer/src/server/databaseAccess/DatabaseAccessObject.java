package server.databaseAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import server.database.DatabaseRepresentation;

public class DatabaseAccessObject 
{
	
	DatabaseRepresentation db;
	//static number of columns in each table
	private Map<String, Integer> numberOfColumns = new HashMap<String, Integer>();
	
	/**
	 * This is a parent class to all the Access Classes. It contains general sql commands which all the Access classes will inherit.
	 */
	public DatabaseAccessObject(DatabaseRepresentation db) 
	{
		this.db = db;
		numberOfColumns.put("User", 8);
		numberOfColumns.put("Project", 6);
		numberOfColumns.put("Batch", 5);
		numberOfColumns.put("Field", 7);
		numberOfColumns.put("Value", 6);
		
	}
	
	/**
	 * Abstract get sql function which returns all the objects in a table
	 * @param table The table in which to search
	 * @return An array of objects from the table searched
	 * @throws SQLException 
	 */
	public ArrayList<Object> getAll(String table) throws SQLException
	{
		PreparedStatement pstmt = null;
		Connection conn = db.getConnection();
		//Build the command
		String cmd = "SELECT * FROM " + table;
		//prepare it
		pstmt = conn.prepareStatement(cmd);
		
		//execute
		ResultSet rs = pstmt.executeQuery();
		ArrayList<Object> result = getResultArray(rs, table);
		pstmt.close();
		return result;
		
	}

	/**
	 * Abstract get sql function which returns an object(s) in a table
	 * @param db A DatabaseRepresentation object
	 * @param conn A database connection
	 * @param table The table in which to search
	 * @param whereStatements Should be a string prepared to be inserted just after the where portion of the statement. Ex. "ID = \"394729383\" AND Name = \"Henry\""
	 * @return An array of objects from the table searched
	 * @throws SQLException 
	 */
	public ArrayList<Object> get(String table, String whereStatements) throws SQLException
	{
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		//Build the command
		String cmd = "SELECT * FROM " + table + " WHERE " + whereStatements;
		//prepare it
		pstmt = conn.prepareStatement(cmd);
		
		//execute
		ResultSet rs = pstmt.executeQuery();
		ArrayList<Object> result = getResultArray(rs, table);
		pstmt.close();
		rs.close();
		return result;
	}
	
	/**
	 * Abstract update sql function
	 * @param table The table in which the object will be found
	 * @param whereStatements Any limitations in the search
	 * @param valueNames A list of the names of each column for the object to be updated
	 * @param values The new values to be added for each column listed in valueNames
	 * @throws SQLException 
	 */
	public Boolean update(String table, String whereStatements, ArrayList<String> valueNames, ArrayList<Object> values) throws SQLException
	{
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		//build ? String for the values section of the query
		int numberOfValues = valueNames.size();
		StringBuilder setStatement = new StringBuilder();
		int i = 0;
		while (i != numberOfValues)
		{
			if (i == numberOfValues-1)
			{
				setStatement.append(valueNames.get(i) + " = ? ");
			}
			else
			{
				setStatement.append(valueNames.get(i) + " = ?, ");
			}
			i++;
		}
		
		//Command to fill up
		String cmd = "UPDATE " + table + " SET "  + setStatement + "WHERE " + whereStatements;
	
		//prepare it
		pstmt = conn.prepareStatement(cmd);
		
		//fill it
		i = 0;
		while (i != numberOfValues)
		{
			if(values.get(i).getClass() == String.class)
			{
				String value = (String) values.get(i);
				pstmt.setString(i+1, value);
			}
			else if(values.get(i).getClass() == Integer.class)
			{
				Integer value = (Integer) values.get(i);
				pstmt.setInt(i+1, value);
			}
			else if(values.get(i).getClass() == Boolean.class)
			{
				Boolean value = (Boolean) values.get(i);
				pstmt.setBoolean(i+1, value);
			}
			i++;
		}
		//execute
		int row = pstmt.executeUpdate();
		pstmt.close();
		if (row == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Abstract insert sql function
	 * @param table The table in which the object will be added
	 * @param valueNames The names of each column to be added in the database. Must correspond to the database columns for the table indicated
	 * @param values The values to be added for each of the columns listed in valueNames
	 * @return The object in memory representing the record just added in the database
	 * @throws SQLException 
	 */
	public String add(String table, String valueNames, ArrayList<Object> values) throws SQLException
	{
		Connection conn = db.getConnection();
		PreparedStatement pstmt = null;
		
		//build ? String for the values section of the query
		int numberOfValues = values.size();
		StringBuilder questionMarks = new StringBuilder();
		int i = numberOfValues;
		while (i != 0)
		{
			if (i == 1)
			{
				questionMarks.append("?");
			}
			else
			{
				questionMarks.append("?,");
			}
			i--;
		}
		//Command to fill up
		String cmd = "INSERT INTO " + table + "(" + valueNames + ") VALUES(" + questionMarks +")";		
		//prepare it
		pstmt = conn.prepareStatement(cmd);		
		//fill it
		i = 0;
		while (i != numberOfValues)
		{
			if(values.get(i).getClass() == String.class)
			{
				String value = (String) values.get(i);
				pstmt.setString(i+1, value);
			}
			else if(values.get(i).getClass() == Integer.class)
			{
				Integer value = (Integer) values.get(i);
				pstmt.setInt(i+1, value);
			}
			else if(values.get(i).getClass() == Boolean.class)
			{
				Boolean value = (Boolean) values.get(i);
				pstmt.setBoolean(i+1, value);
			}
			i++;
		}
		//execute
		int completed = pstmt.executeUpdate();
		if (completed == 1)
		{
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select last_insert_rowid()");
			rs.next();
			String newID = rs.getString(1);
			pstmt.close();
			rs.close();
			return newID;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Builds an arraylist from a result set so that the result set can be closed on this level
	 * @param rs
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> getResultArray(ResultSet rs, String table) throws SQLException
	{
		ArrayList<Object> resultArray = new ArrayList<Object>();
		while(rs.next())
		{
			int i = 1;
			while(i <= numberOfColumns.get(table))
			{
				Object nextObject = rs.getObject(i);
				resultArray.add(nextObject);
				i++;
			}
		}
		return resultArray;
	}
	
}
