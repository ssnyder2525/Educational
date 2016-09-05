package server.database.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import server.database.DatabaseException;
import server.database.DatabaseRepresentation;
import server.managers.User;
import shared.communication.proxy.Credentials;

public class SqlUserDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			DatabaseRepresentation.initialize();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateUsers() {
		DatabaseRepresentation db = new DatabaseRepresentation();
		try {
			db.startTransaction();
			//add users
			Credentials cred1 = new Credentials("test1","test1");
			Credentials cred2 = new Credentials("test2","test2");
			Credentials cred3 = new Credentials("test3","test3");
			Credentials cred4 = new Credentials("test4","test4");
			User user1 = new User(cred1);
			User user2 = new User(cred2);
			User user3 = new User(cred3);
			User user4 = new User(cred4);
			
			db.getUserDAO().createUser(user1);
			db.getUserDAO().createUser(user2);
			db.getUserDAO().createUser(user3);
			db.getUserDAO().createUser(user4);
			
			db.endTransaction(false);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testClearAndRepopulate() {
		DatabaseRepresentation db = new DatabaseRepresentation();
		try {
			db.startTransaction();
			List<Credentials> allUsers = db.getUserDAO().getAllUsers();
			if(allUsers.size() != 0) {
				db.getUserDAO().clear();
			}
			allUsers = db.getUserDAO().getAllUsers();
			assertTrue(allUsers.size() == 0);
			
			//add users
			Credentials cred1 = new Credentials("test1","test1");
			Credentials cred2 = new Credentials("test2","test2");
			Credentials cred3 = new Credentials("test3","test3");
			Credentials cred4 = new Credentials("test4","test4");
			User user1 = new User(cred1);
			int id = user1.getPlayerID();
			User user2 = new User(cred2);
			User user3 = new User(cred3);
			User user4 = new User(cred4);
			
			
			db.getUserDAO().createUser(user1);
			db.getUserDAO().createUser(user2);
			db.getUserDAO().createUser(user3);
			db.getUserDAO().createUser(user4);
			
			Credentials test1 = db.getUserDAO().getUser(id);
			assertTrue(test1.username.equals("test1"));
			
			allUsers = db.getUserDAO().getAllUsers();
			assertTrue(allUsers.size() == 4);
			
			db.endTransaction(false);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

}
