package server.databaseAccess;
import java.sql.SQLException;
import java.util.ArrayList;

import server.database.DatabaseRepresentation;
import shared.models.User;

public class UserDAO extends DatabaseAccessObject
{
	/**
	 * This class contains all Database Access classes necessary for the User class
	 */
	public UserDAO(DatabaseRepresentation db)
	{
		super(db);
	}
	
	/**
	 * adds a user to the database based on the model object given to it
	 * @param user
	 * @return the id of the user in the database
	 * @throws SQLException 
	 */
	public String addUser(User user) throws SQLException
	{
		ArrayList<Object> values = new ArrayList<Object>();
		values.add(user.getFirstName());
		values.add(user.getLastName());
		values.add(user.getEmail());
		values.add(user.getUsername());
		values.add(user.getPassword());
		values.add(user.getCurrentBatch());
		values.add(user.getBatchesCompleted());
		
		String userID = add("User", "FirstName, LastName, Email, UserName, Password, CurrentBatch, BatchesCompleted", values);
		return userID;
	}

	/**
	 * Validates an inputed user
	 * @param user
	 * @param password
	 * @return whether the user exists or not
	 * @throws SQLException 
	 */
	public Boolean validateUser(String username, String password) throws SQLException
	{
		String whereStatements = "Username = " + "\"" + username + "\"" + " AND " + "Password = " + "\"" + password + "\"";
		ArrayList<Object> result = get("User", whereStatements);
		if(!result.isEmpty())
		{
			return true;		
		}
		return false;
	}
	
	/**
	 * Validates and returns the User
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public User getUser(User user) throws SQLException
	{
		User resultUser = null;
		String whereStatement = "Username = \"" + user.getUsername() + "\" AND Password = \"" + user.getPassword() + "\"";
		ArrayList<Object> results = get("User", whereStatement);
		if(results.isEmpty())
		{
			return null;
		}
		
		resultUser = new User(String.valueOf((Integer) results.get(0)), (String) results.get(1), (String) results.get(2), (String) results.get(3), 
				(String) results.get(4), (String) results.get(5), String.valueOf((Integer) results.get(6)), (Integer) results.get(7));
		
		return resultUser;
		
	}
	
	/**
	 * Updates information about a user in the database
	 * @param user
	 * @return whether the update was successful
	 * @throws SQLException 
	 */
	public Boolean updateUser(User user) throws SQLException
	{
		ArrayList<Object> values = new ArrayList<Object>();
		values.add(user.getEmail());
		values.add(user.getCurrentBatch());
		values.add(user.getBatchesCompleted());
		
		ArrayList<String> tableValues = new ArrayList<String>();
		tableValues.add("Email");
		tableValues.add("CurrentBatch");
		tableValues.add("BatchesCompleted");
		
		String whereStatement = "Username = \"" + user.getUsername() + "\" AND Password = \"" + user.getPassword() + "\"";
		Boolean success = update("User", whereStatement, tableValues, values);
		return success;
	}
}
