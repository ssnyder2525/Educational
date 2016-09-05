package server.database.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.managers.User;
import shared.communication.proxy.Credentials;

public class MongoUserDAOTest {

	@Before
	public void setUp() throws Exception {
		
		
	}

	@Test
	public void testGetUser() {
		MongoUserDAO dao = new MongoUserDAO();
		System.out.println(dao.getUser(1).username);
	}

	@Test
	public void testGetAllUsers() {
		MongoUserDAO dao = new MongoUserDAO();
		dao.getAllUsers();
	}

	@Test
	public void testCreateUser() {
		MongoUserDAO dao = new MongoUserDAO();
		dao.createUser(new User(new Credentials("cody", "burt")));
	}

	@Test
	public void testClear() {
		MongoUserDAO dao = new MongoUserDAO();
		dao.clear();
	}

}
