package server.database.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import server.database.DatabaseException;
import server.database.DatabaseRepresentation;
import shared.models.Game;

public class SqlGameDAOTest {

	@Test
	public void testGetGame() {
		DatabaseRepresentation db = new DatabaseRepresentation();
		try {
			db.startTransaction();
			//add games
			int id = 93456;
					
			Game game1 = new Game();
			game1.setId(id);
			
			db.getGameDAO().saveGame(game1);
			
			Game game2 = db.getGameDAO().getGame(id);
			
			assertTrue(game2.getId() == game1.getId());
			
			db.endTransaction(false);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGameInDB() {
		DatabaseRepresentation db = new DatabaseRepresentation();
		try {
			db.startTransaction();
			//add games
			int id = 93456;
					
			Game game1 = new Game();
			game1.setId(id);
			
			db.getGameDAO().saveGame(game1);
			
			assertTrue(db.getGameDAO().gameInDB(id));
			
			db.endTransaction(false);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSaveGame() {
		DatabaseRepresentation db = new DatabaseRepresentation();
		try {
			db.startTransaction();
			//add games
			int id = 93456;
					
			Game game1 = new Game();
			game1.setId(id);
			
			db.getGameDAO().saveGame(game1);
			
			assertTrue(db.getGameDAO().gameInDB(id));
			
			db.endTransaction(false);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllGames() {
		DatabaseRepresentation db = new DatabaseRepresentation();
		try {
			db.startTransaction();
			//add games
					
			Game game1 = new Game();
			Game game2 = new Game();
			Game game3 = new Game();
			
			db.getGameDAO().saveGame(game1);
			db.getGameDAO().saveGame(game2);
			db.getGameDAO().saveGame(game3);
			
			assertTrue(db.getGameDAO().getAllGames().size() == 3);
			
			db.endTransaction(false);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testClear() {
		DatabaseRepresentation db = new DatabaseRepresentation();
		try {
			db.startTransaction();
			//add games
					
			Game game1 = new Game();
			Game game2 = new Game();
			Game game3 = new Game();
			
			db.getGameDAO().saveGame(game1);
			db.getGameDAO().saveGame(game2);
			db.getGameDAO().saveGame(game3);
			
			assertTrue(db.getGameDAO().getAllGames().size() == 3);
			
			db.getGameDAO().clear();
			
			assertTrue(db.getGameDAO().getAllGames().size() == 0);
			
			db.endTransaction(false);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

}
