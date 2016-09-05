package shared.communication;

import java.util.Arrays;
import java.util.List;

public class SearchInput 
{

	/**
	 * The user calling this function
	 */
	private String user;
	/**
	 * The inputed password for the user
	 */
	private String password;
	/**
	 * The fields associated with this function
	 */
	private String fields;
	/**
	 * The values to search for this function
	 */
	private String search_values;
	
	public SearchInput() 
	{}
	
	/**
	 * This object stores the input data for the Search function
	 * @param user
	 * @param password
	 * @param fields
	 * @param search_values
	 */
	public SearchInput(String user, String password, String fields, String search_values) 
	{
		this.setUser(user);
		this.setPassword(password);
		this.setFields(fields);
		this.setSearch_values(search_values);
	}

	/**
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user)
	{
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the fields
	 */
	public List<String> getFields()
	{
		List<String> fieldIDs = Arrays.asList(this.fields.split("\\s*,\\s*"));
		return fieldIDs;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(String fields)
	{
		this.fields = fields;
	}

	/**
	 * @return the search_values
	 */
	public List<String> getSearch_values()
	{
		List<String> searchValues = Arrays.asList(this.search_values.split("\\s*,\\s*"));
		return searchValues;
	}

	/**
	 * @param search_values the search_values to set
	 */
	public void setSearch_values(String search_values)
	{
		this.search_values = search_values;
	}

}
