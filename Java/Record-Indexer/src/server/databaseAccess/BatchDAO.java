package server.databaseAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import server.database.DatabaseRepresentation;
import shared.models.Batch;
import shared.models.Field;

public class BatchDAO extends DatabaseAccessObject
{
	/**
	 * This class contains all Database Access methods necessary for the Batch class.
	 */
	public BatchDAO(DatabaseRepresentation db)
	{
		super(db);
	}
	
	/**
	 * Submits a new batch to the database
	 * @param batchID
	 * @param fieldValue
	 * @param recordValue
	 * @return success or failure
	 * @throws SQLException 
	 */
	public String addBatch(Batch batch) throws SQLException
	{
		ArrayList<Object> values = new ArrayList<Object>();
		
		values.add(batch.getProjectID());
		values.add(batch.isInUse());
		values.add(batch.isCompleted());
		values.add(batch.getSourceURL());
		
		String batchID = add("Batch", "ProjectID, InUse, Completed, SourceURL", values);
		return batchID;
	}
	
	/**
	 * gets a sample batch from the database
	 * @param projectID
	 * @return the sample batch
	 * @throws SQLException 
	 */
	public Batch getSampleBatch(String projectID) throws SQLException
	{
		ArrayList<Object> results = get("Batch", "ProjectID = \"" + projectID +"\"");
		if(results.isEmpty())
		{
			return null;
		}
		
		Batch result = new Batch(String.valueOf((Integer) results.get(0)), String.valueOf((Integer) results.get(1)), (Integer) results.get(2), 
				(Integer) results.get(3), (String) results.get(4));
		
		return result;
	}
	
	/**
	 * Gets a batch that is not currently checked out from the database
	 * @param projectID
	 * @return a batch that is not checked out
	 * @throws SQLException 
	 */
	public Batch DownloadBatch(String projectID) throws SQLException
	{
		ArrayList<Object> results = get("Batch", "ProjectID = \"" + projectID +"\"");
		if(results.isEmpty())
		{
			return null;
		}

		for(int i = 0; i < results.size(); i = i + 5)
		{
			if((Integer) results.get(i+2) == 0 && (Integer) results.get(i+3) == 0)
			{
				ArrayList<Field> fields = new ArrayList<Field>();
				
				Batch result = new Batch(String.valueOf((Integer) results.get(i)), String.valueOf((Integer) results.get(i+1)), (Integer) results.get(i+2), (Integer) results.get(i+3), 
						(String) results.get(i+4));
				
				ArrayList<Object> fieldResults = get("Field", "ProjectID = \"" + result.getProjectID() + "\"");
				if(!fieldResults.isEmpty())
				{
					for(int j = 0; j < fieldResults.size(); j = j + 7)
					{
						Field newField = new Field(String.valueOf((Integer) fieldResults.get(j)), (String) fieldResults.get(j+1), 
								(String) fieldResults.get(j+2), (String) fieldResults.get(j+3), (String) fieldResults.get(j+4), 
								(String) fieldResults.get(j+5), (String) fieldResults.get(j+6));
						
						fields.add(newField);
					}
					result.setFields(fields);
					return result;
				}
			}
		}
		return null;
	}		
	
	/**
	 * Gets a batch by ID
	 * @param batchID
	 * @return Batch
	 * @throws SQLException
	 */
	public Batch getBatch(String batchID) throws SQLException
	{
		ArrayList<Object> results = get("Batch", "BatchID = \"" + batchID +"\"");
		if(results.isEmpty())
		{
			return null;
		}
		ArrayList<Field> fields = new ArrayList<Field>();
		
		Batch result = new Batch(String.valueOf((Integer) results.get(0)), String.valueOf((Integer) results.get(1)), (Integer) results.get(2), 
				(Integer) results.get(3), (String) results.get(4));
		
		ArrayList<Object> fieldResults = get("Field", "ProjectID = \"" + result.getProjectID() + "\"");
		if(!fieldResults.isEmpty())
		{
			for(int j = 0; j < fieldResults.size(); j = j + 7)
			{
				Field newField = new Field(String.valueOf((Integer) fieldResults.get(j)), (String) fieldResults.get(j+1), (String) fieldResults.get(j+2), 
						(String) fieldResults.get(j+3), (String) fieldResults.get(j+4), (String) fieldResults.get(j+5), (String) fieldResults.get(j+6));
				
				fields.add(newField);
			}
			result.setFields(fields);
			return result;
		}
		return null;
	}		
	
	/**
	 * Updates the information within a batch
	 * @param batch
	 * @return whether or not the update was a success
	 * @throws SQLException
	 */
	public Boolean updateBatch(Batch batch) throws SQLException
	{
		ArrayList<Object> values = new ArrayList<Object>();
		values.add(batch.isInUse());
		values.add(batch.isCompleted());
		
		ArrayList<String> tableValues = new ArrayList<String>();
		tableValues.add("InUse");
		tableValues.add("Completed");
		
		String whereStatement = "batchID = \"" + batch.getBatchID() + "\"";
		Boolean success = update("Batch", whereStatement, tableValues, values);
		return success;
	}
}
