package server.serverTests.database;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.database.DatabaseException;
import server.database.DatabaseRepresentation;
import server.databaseAccess.UserDAO;
import shared.models.User;

public class UserDAOTest {

	DatabaseRepresentation db;
	
	@Before
	public void setUp() throws Exception 
	{
		db = new DatabaseRepresentation();
		db.startTransaction();
	}

	@After
	public void tearDown() throws Exception 
	{
		db.endTransaction(false);
	}

	@Test
	public void addUserTest() throws DatabaseException, SQLException 
	{
		UserDAO userDAO = db.getUserDAO();
		User user = new User("-1", "Bob", "Bobson", "bbson@yahoo.com", "bbobson", "mypassword", "9643265", 0);
		String userID = userDAO.addUser(user);
		assertFalse(userID == null);		
		
	}
	
	@Test
	public void ValidateUserTest() throws DatabaseException, SQLException 
	{
		UserDAO userDAO = db.getUserDAO();
		User user = new User("-1", "Bob", "Bobson", "bbson@yahoo.com", "bbobson", "mypassword", "9643265", 0);
		String userID = userDAO.addUser(user);
		assertFalse(userID == null);		
		Boolean validated = userDAO.validateUser(user.getUsername(), user.getPassword());
		assertEquals(true, validated);	
	}

	@Test
	public void updateUserTest() throws DatabaseException, SQLException 
	{
		UserDAO userDAO = db.getUserDAO();
		User user = new User("-1", "Bob", "Bobson", "bbson@yahoo.com", "bbobson", "mypassword", "9643265", 0);
		String userID = userDAO.addUser(user);
		assertFalse(userID == null);	
		user.setBatchesCompleted(100);
		user.setCurrentBatch("9");
		user.setEmail("newEmail");
		Boolean success = userDAO.updateUser(user);
		assertEquals(true, success);
	}
	
	@Test
	public void getUserTest() throws DatabaseException, SQLException 
	{
		UserDAO userDAO = db.getUserDAO();
		User user = new User("-1", "Bob", "Bobson", "bbson@yahoo.com", "bbobson", "mypassword", "9643265", 0);
		String userID = userDAO.addUser(user);
		assertFalse(userID == null);	
		user = userDAO.getUser(user);
		assertEquals(0, user.getBatchesCompleted());
	}
}
