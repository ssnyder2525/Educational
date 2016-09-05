package server.facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.database.*;
import shared.communication.*;
import shared.models.*;
/**
 * The server facade executes the commands from the client communicator. It recieves the direction to execute from the Http handlers and
 * Uses sql statements contained in the DAO classes to recieve the necessary information for the methods. It returns specialized packets of
 * information to be returned to the client.
 * @author ssnyder
 */
public class ServerFacade {
	
	/**
	 * Validates the username and password the client entered and returns a success or failure
	 * @param input
	 * @return
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public static ValidateUserOutput ValidateUser(ValidateUserInput input) throws DatabaseException, SQLException
	{
		DatabaseRepresentation db = new DatabaseRepresentation();
		
		db.startTransaction();
		User userToSearch = input.generateUser();
		User returnedUser = db.getUserDAO().getUser(userToSearch);
		if(returnedUser != null)
		{
			ValidateUserOutput newOutput = new ValidateUserOutput(returnedUser.getFirstName(), returnedUser.getLastName(), returnedUser.getBatchesCompleted(), true);
			db.endTransaction(true);
			return newOutput;
		}
		else
		{
			ValidateUserOutput newOutput = new ValidateUserOutput("", "", 0, false);
			db.endTransaction(false);
			return newOutput;
		}
		
	}
	
	/**
	 * Gets all projects from the database
	 * @param input
	 * @return
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public static GetProjectsOutput GetProjects(GetProjectsInput input) throws SQLException, DatabaseException
	{
		DatabaseRepresentation db = new DatabaseRepresentation();

		db.startTransaction();
		boolean validated = db.getUserDAO().validateUser(input.getUser(), input.getPassword());
		if(validated == false)
		{
			db.endTransaction(false);
			return null;
		}
		ArrayList<Project> result = db.getProjectDAO().getAllProjects();
		if(result != null)
		{
			GetProjectsOutput newOutput = new GetProjectsOutput(result);
			db.endTransaction(true);
			return newOutput;
		}
		else
		{
			
			db.endTransaction(false);
			return null;
		}
		
	}
	
	/**
	 * Gets the first image from the specified project and returns it's url
	 * @param input
	 * @return
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public static GetSampleImageOutput GetSampleImage(GetSampleImageInput input) throws DatabaseException, SQLException
	{
		DatabaseRepresentation db = new DatabaseRepresentation();

		db.startTransaction();
		boolean validated = db.getUserDAO().validateUser(input.getUser(), input.getPassword());
		if(validated == false)
		{
			db.endTransaction(false);
			return null;
		}
		Batch result = db.getBatchDAO().getSampleBatch(input.getProject());
		if(result != null)
		{
			GetSampleImageOutput newOutput = new GetSampleImageOutput(result.getSourceURL());
			db.endTransaction(true);
			return newOutput;
		}
		else
		{
			db.endTransaction(false);
			return null;
		}
	}
	
	/**
	 * Returns the first unused and incomplete Batch found in the specified project and returns it. The Batch is now assigned to the user
	 * @param input
	 * @return
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public static DownloadBatchOutput DownloadBatch(DownloadBatchInput input) throws DatabaseException, SQLException
	{
		DatabaseRepresentation db = new DatabaseRepresentation();
		
		db.startTransaction();
		User userToSearchFor = new User("-1", "", "", "", input.getUser(), input.getPassword(), "", -1);
		User returnedUser = db.getUserDAO().getUser(userToSearchFor);
		if((returnedUser == null) || !(returnedUser.getCurrentBatch().equals("-1")))
		{
			db.endTransaction(false);
			return null;
		}
		Project project = db.getProjectDAO().getProjectByID(input.getProject());
		Batch result = db.getBatchDAO().DownloadBatch(input.getProject());
		ArrayList<Field> fields = db.getFieldDAO().getFields(input.getProject());
		if(result != null)
		{
			//update the user's current batch
			returnedUser.setCurrentBatch(result.getBatchID());
			boolean success = db.getUserDAO().updateUser(returnedUser);
			result.setInUse(1);
			boolean success2 = db.getBatchDAO().updateBatch(result);
			if(success == false || success2 == false)
			{
				db.endTransaction(false);
				return null;
			}
			DownloadBatchOutput newOutput = new DownloadBatchOutput(result.getBatchID(), result.getProjectID(), result.getSourceURL(), 
					project.getFieldY(), project.getRecordHeight(), project.getRecordsPerBatch(), fields.size(), fields);
			db.endTransaction(true);
			return newOutput;
		}
		else
		{
			db.endTransaction(false);
			return null;
		}
	}
	
	/**
	 * Enters the values from the client into the database. The Batch is marked as completed
	 * @param input
	 * @return
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public static SubmitBatchOutput SubmitBatch(SubmitBatchInput input) throws DatabaseException, SQLException
	{
		DatabaseRepresentation db = new DatabaseRepresentation();
		
		db.startTransaction();
		//validate the user
		User userToSearchFor = new User("-1", "", "", "", input.getUser(), input.getPassword(), "", -1);
		User returnedUser = db.getUserDAO().getUser(userToSearchFor);
		if(returnedUser == null)
		{
			db.endTransaction(false);
			return null;
		}
		if(!returnedUser.getCurrentBatch().equals(input.getBatch()))
		{
			db.endTransaction(false);
			return null;
		}

		//get batch, project, and fields
		Batch batch = db.getBatchDAO().getBatch(input.getBatch());
		Project project = db.getProjectDAO().getProjectByID(batch.getProjectID());
		ArrayList<Field> fields = db.getFieldDAO().getFields(project.getProjectID());
		//Get the number of rows that must be filled
		int numberOfRecords = Integer.parseInt(project.getRecordsPerBatch());
		//Get the number of fields for this project
		int numberOfFields = batch.getFields().size();
		//split up the input into sets of values to add
		String[] setsOfValues = input.getFieldValues().split(";");
		//check that each one has the right amount of values
		if(!checkFields(batch.getFields().size(), setsOfValues))
		{
			db.endTransaction(false);
			return null;
		}
		//Check that there are not more than the possible number of records
		if(setsOfValues.length > numberOfRecords)
		{
			db.endTransaction(false);
			return null;
		}
		//for every set of values
		for(int i = 0; i < setsOfValues.length; i++)
		{
			//split up the string into individual values
			String[] values = setsOfValues[i].split(",");
			//for each value
			for(int j = 0; j< values.length; j++)
			{
				//add a new value to the database
				Value newValue = new Value("-1", Integer.toString(i + 1), values[j], fields.get(j).getFieldID(), input.getBatch(), batch.getSourceURL());
				String result = db.getValueDAO().addValue(newValue);
				if(result == null)
				{
					db.endTransaction(false);
					return null;
				}
			}
		}
		//fill in missing records
		for(int j = setsOfValues.length; j < numberOfRecords; j++)
		{
			for(int k = 0; k < numberOfFields; k++)
			{
				Value newValue = new Value("-1", Integer.toString(j + 1), "", fields.get(k).getFieldID(), input.getBatch(), batch.getSourceURL());
				String result = db.getValueDAO().addValue(newValue);
				if(result == null)
				{
					db.endTransaction(false);
					return null;
				}
			}
		}
		returnedUser.incrementBatchesCompleted(numberOfRecords);
		returnedUser.setCurrentBatch("-1");
		batch.setCompleted(1);
		batch.setInUse(0);
		project.incrementTotalIndexed();
		
		boolean success = db.getUserDAO().updateUser(returnedUser);
		boolean success2 = db.getBatchDAO().updateBatch(batch);
		boolean success3 = db.getProjectDAO().updateProject(project);
		if (success == false || success2 == false || success3 == false)
		{
			db.endTransaction(false);
			return null;
		}
		SubmitBatchOutput output = new SubmitBatchOutput(true);
		db.endTransaction(true);
		return output;		
	}
	
	/**
	 * Gets the fields of a specified Project or all fields
	 * @param input
	 * @return
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public static GetFieldsOutput GetFields(GetFieldsInput input) throws DatabaseException, SQLException
	{
		DatabaseRepresentation db = new DatabaseRepresentation();
		
		db.startTransaction();
		boolean validated = db.getUserDAO().validateUser(input.getUser(), input.getPassword());
		if(validated == false)
		{
			db.endTransaction(false);
			return null;
		}
		ArrayList<Field> fields = new ArrayList<Field>();
		if(input.getProject().equals(""))
		{
			fields = db.getFieldDAO().getAllFields();
		}
		else
		{
			fields = db.getFieldDAO().getFields(input.getProject());
		}
		if(fields.size() == 0)
		{
			db.endTransaction(false);
			return null;
		}
		GetFieldsOutput output = new GetFieldsOutput(fields);
		db.endTransaction(true);
		return output;
	}
	
	/**
	 * Searches the Values of specified fields for specified values
	 * @param input
	 * @return
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public static SearchOutput Search(SearchInput input) throws DatabaseException, SQLException
	{
		DatabaseRepresentation db = new DatabaseRepresentation();
		ArrayList<Value> result = new ArrayList<Value>();
		
		db.startTransaction();
		//validate User
		User userToSearchFor = new User("-1", "", "", "", input.getUser(), input.getPassword(), "", -1);
		User returnedUser = db.getUserDAO().getUser(userToSearchFor);
		if(returnedUser == null)
		{
			db.endTransaction(false);
			return null;
		}
		//prepare the fieldids and the search strings
		List<String> fields = input.getFields();
		List<String> searchValues = input.getSearch_values();
		//for each fieldid
		for(int i = 0; i < fields.size(); i++)
		{
			//make a list of it's values
			ArrayList<Value> searchResult = db.getValueDAO().searchValues(fields.get(i));
			if(searchResult != null)
			{
				//for each value returned
				for(int j = 0; j < searchResult.size(); j++)
				{
					//for each search string
					for(int k = 0; k < searchValues.size(); k++)
					{
						//if the value matches the search string, add it to the results
						if(searchResult.get(j).getValue().toLowerCase().equals(searchValues.get(k).toLowerCase()))
						{
							result.add(searchResult.get(j));
						}
					}
				}
			}
		}
		if(fields.size() == 0 || result.size() == 0)
		{
			db.endTransaction(false);
			return null;
		}
		SearchOutput output = new SearchOutput(result);
		db.endTransaction(true);
		return output;
	}
	
	/**
	 * Checks that the proper number of values were submitted. Protects against partially completed records
	 * @param numOfFields
	 * @param setsOfValues
	 * @return
	 */
	public static boolean checkFields(int numOfFields, String[] setsOfValues)
	{
		int i = 0;
		while(i < setsOfValues.length)
		{
			String[] values = setsOfValues[i].split(",");
			if(values.length != numOfFields)
			{
				return false;
			}
			i++;
		}
		return true;
	}
	
}