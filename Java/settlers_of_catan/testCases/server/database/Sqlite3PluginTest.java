package server.database;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.database.dao.IUserDAO;
import server.database.plugin.Sqlite3Plugin;
import server.managers.User;
import shared.communication.proxy.Credentials;

public class Sqlite3PluginTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTransaction() {
		
		IPersistencePlugin plugin = new Sqlite3Plugin();
		plugin.startTransaction();
		IUserDAO user = plugin.getUserDAO();
		try {
			user.createUser(new User(new Credentials("Sam","sam")));
			assertTrue(user.getAllUsers().size() > 0);
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail("should not throw an exception");
		}
		plugin.endTransaction();
		plugin.clear();
	}
	
	 
	@Test
	public void testClear() {
		
		IPersistencePlugin plugin = new Sqlite3Plugin();
		plugin.startTransaction();
		IUserDAO user = plugin.getUserDAO();
		try {
			User u = new User(new Credentials("Samm","samm"),7);
			user.createUser(u);
			assertTrue(user.getAllUsers().size() > 0);
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail("should not throw an exception");
		}
		plugin.endTransaction();
		
		plugin.clear();
		plugin.startTransaction();
		try {
			assertTrue(user.getAllUsers().size() == 0);
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail("should not throw an exception");
		}
		plugin.endTransaction();
	}

}
