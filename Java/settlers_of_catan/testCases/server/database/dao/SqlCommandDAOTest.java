package server.database.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import server.ServerException;
import server.command.ACommand;
import server.command.games.ListCommand;
import server.database.DatabaseException;
import server.database.DatabaseRepresentation;
import shared.models.Game;

public class SqlCommandDAOTest {

	@Test
	public void testGetDelta() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDelta() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCommandCount() throws ServerException {
		DatabaseRepresentation db = new DatabaseRepresentation();
		try {
			db.startTransaction();
			ACommand command1 = new ListCommand(null, null, null);
			ACommand command2 = new ListCommand(null, null, null);
			
			db.getCommandDAO().setDelta(2);
			
			//assertTrue();
			
			db.endTransaction(false);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateCommand() {
		fail("Not yet implemented");
	}

	@Test
	public void testClear() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllCommands() {
		fail("Not yet implemented");
	}

	@Test
	public void testClearCommands() {
		fail("Not yet implemented");
	}

}
