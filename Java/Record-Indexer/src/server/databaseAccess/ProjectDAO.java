package server.databaseAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import server.database.DatabaseRepresentation;
import shared.models.Project;

public class ProjectDAO extends DatabaseAccessObject
{

	DatabaseRepresentation db;
	
	/**
	 * This class contains all Database Access classes necessary for the Project class
	 */
	public ProjectDAO(DatabaseRepresentation db)
	{
		super(db);
	}
	
	/**
	 * returns all the projects in the database
	 * @return array of all projects
	 * @throws SQLException
	 */
	public ArrayList<Project> getAllProjects() throws SQLException
	{
		ArrayList<Project> result = new ArrayList<Project>();
		ArrayList<Object> results = getAll("Project");
		if(results.isEmpty())
		{
			return null;
		}
		
		for(int i = 0; i < results.size();i = i + 6)
		{
			Project project = new Project(String.valueOf((Integer) results.get(i)), (String) results.get(i+1), (Integer) results.get(i+2), 
					(String) results.get(i+3), (String) results.get(i+4), (String) results.get(i+5));
			result.add(project);
		}
		
		return result;
	}
	
	/**
	 * adds a project to the database based on the model class it is given
	 * @param project
	 * @return the id of the new project
	 * @throws SQLException 
	 */
	public String addProject(Project project) throws SQLException
	{
		ArrayList<Object> values = new ArrayList<Object>();
		values.add(project.getTitle());
		values.add(project.getTotalIndexed());
		values.add(project.getRecordsPerBatch());
		values.add(project.getFieldY());
		values.add(project.getRecordHeight());
		
		String columnNames = "Title, TotalIndexed, RecordsPerBatch, FieldY, RecordHeight";
		String projectID = add("Project", columnNames, values);
		return projectID;
	}
	
	/**
	 * Returns all Projects in the database
	 * @param user The user object used for this function
	 * @return One or more Projects from the database or null if empty
	 * @throws SQLException 
	 */
	public Project getProject(Project project) throws SQLException
	{
		Project result = null;
		String whereStatement = "Title = \"" + project.getTitle() + "\"";
		ArrayList<Object> results = get("Project", whereStatement);
		if(results.isEmpty())
		{
			return null;
		}
		if (results.isEmpty())
		{
			return null;
		}
		result = new Project(String.valueOf((Integer) results.get(0)), (String) results.get(1), (Integer) results.get(2), 
				(String) results.get(3), (String) results.get(4), (String) results.get(5));
		
		return result;
	}
	
	/**
	 * gets a project by it's ID
	 * @param projectID
	 * @return
	 * @throws SQLException
	 */
	public Project getProjectByID(String projectID) throws SQLException
	{
		Project result = null;
		String whereStatement = "ProjectID = \"" + projectID + "\"";
		ArrayList<Object> results = get("Project", whereStatement);
		if (results.isEmpty())
		{
			return null;
		}
		result = new Project(String.valueOf((Integer) results.get(0)), (String) results.get(1), (Integer) results.get(2), 
				(String) results.get(3), (String) results.get(4),(String) results.get(5));
		
		return result;
	}
	
	/**
	 * updates information about a project
	 * @param project
	 * @return whether the update was successful
	 * @throws SQLException 
	 */
	public Boolean updateProject(Project project) throws SQLException
	{
		ArrayList<Object> values = new ArrayList<Object>();
		values.add(project.getTotalIndexed());
		
		ArrayList<String> tableValues = new ArrayList<String>();
		tableValues.add("TotalIndexed");
		
		String whereStatement = "ProjectID = " + project.getProjectID();
		Boolean success = update("Project", whereStatement, tableValues, values);
		return success;
	}
}
