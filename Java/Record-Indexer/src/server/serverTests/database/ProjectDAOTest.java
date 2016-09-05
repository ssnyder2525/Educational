package server.serverTests.database;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.database.DatabaseRepresentation;
import shared.models.*;

public class ProjectDAOTest
{
	
	DatabaseRepresentation db = new DatabaseRepresentation();

	@Before
	public void setUp() throws Exception
	{
		db.startTransaction();
	}

	@After
	public void tearDown() throws Exception
	{
		db.endTransaction(false);
	}

	@Test
	public void addProjectTest() throws SQLException
	{
		Project project = new Project("-1", "Project 1", 0, "0", "45", "200");
		String projectID = db.getProjectDAO().addProject(project);
		assertFalse(projectID == null);
	}
	
	@Test
	public void getProjectTest() throws SQLException
	{
		Project project = new Project("-1", "Project 1", 0, "0", "45", "200");
		db.getProjectDAO().addProject(project);
		project = db.getProjectDAO().getProject(project);
		assertEquals(0, project.getTotalIndexed());
	}
	
	@Test
	public void updateProjectTest() throws SQLException
	{
		Project project = new Project("-1", "Project 1", 0, "0", "45", "200");
		db.getProjectDAO().addProject(project);
		project = db.getProjectDAO().getProject(project);
		project.setTotalIndexed(1);;
		Boolean success = db.getProjectDAO().updateProject(project);
		assertEquals(true, success);
		project = db.getProjectDAO().getProject(project);
		assertEquals(1, project.getTotalIndexed());
		project.setTotalIndexed(2);;
		success = db.getProjectDAO().updateProject(project);
		assertEquals(true, success);
		project = db.getProjectDAO().getProject(project);
		assertEquals(2, project.getTotalIndexed());
	}
	
	@Test
	public void getAllProjectsTest() throws SQLException
	{
		ArrayList<Project> projects = db.getProjectDAO().getAllProjects();
		assertEquals(3, projects.size());
	}
}
