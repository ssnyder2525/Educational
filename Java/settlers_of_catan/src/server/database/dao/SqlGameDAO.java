package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.database.DatabaseException;
import server.database.DatabaseRepresentation;
import shared.models.Game;
import shared.serializerJSON.Deserializer;
import shared.serializerJSON.Serializer;

/**
 * 
 * @author Bo Pace
 *
 */
public class SqlGameDAO implements IGameDAO {

	DatabaseRepresentation db;
	
	public SqlGameDAO(DatabaseRepresentation db) {
		this.db = db;
	}

	@Override
	public Game getGame(int gameID) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT gameID, title, gameJSON FROM Games WHERE gameID = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, gameID);
			
			rs = stmt.executeQuery();
			if(rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				String gameJSON = rs.getString(3);
				Game g = new Game();
				g.setId(id);
				g.setTitle(title);
				JsonObject jsonObj = new JsonParser().parse(gameJSON).getAsJsonObject();
				Deserializer.getInstance().deserialize(g, jsonObj);
				return g;
			} else {
				throw new DatabaseException("Could not find game with id " + gameID);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not get game with id " + gameID, e);
		} finally {
			DatabaseRepresentation.safeClose(rs);
			DatabaseRepresentation.safeClose(stmt);
		}
	}
	
	public boolean gameInDB(int gameID) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT count(*) FROM Games WHERE gameID = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, gameID);
			
			rs = stmt.executeQuery();
			if(rs.next() && rs.getInt(1) == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not get game with id " + gameID, e);
		} finally {
			DatabaseRepresentation.safeClose(rs);
			DatabaseRepresentation.safeClose(stmt);
		}
	}

	@Override
	public void saveGame(Game game) throws DatabaseException {
		PreparedStatement stmt = null;
		
		try {
			String query = "";
			String gameJSON = Serializer.getInstance().serialize(game);
			if (gameInDB(game.getId())) {
				query = "UPDATE Games SET gameJSON = ? WHERE gameID = ?";
				stmt = db.getConnection().prepareStatement(query);
				stmt.setString(1, gameJSON);
				stmt.setInt(2, game.getId());
			} else {
				query = "INSERT INTO Games (gameID, title, gameJSON) values (?, ?, ?)";
				stmt = db.getConnection().prepareStatement(query);
				stmt.setInt(1, game.getId());
				stmt.setString(2, game.getTitle());
				stmt.setString(3, gameJSON);
			}
			
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update game");
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not update game", e);
		} finally {
			DatabaseRepresentation.safeClose(stmt);
		}
	}

	public List<Game> getAllGames() throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Game> games = new ArrayList<Game>();
		try {
			String query = "SELECT gameID, title, gameJSON FROM Games";
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				int gameID = rs.getInt(1);
				String title = rs.getString(2);
				String gameJSON = rs.getString(3);
				Game g = new Game();
				g.setId(gameID);
				g.setTitle(title);
				JsonObject jsonObj = new JsonParser().parse(gameJSON).getAsJsonObject();
				Deserializer.getInstance().deserializeSavedGame(g, jsonObj);
				games.add(g);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not get all games", e);
		} finally {
			DatabaseRepresentation.safeClose(rs);
			DatabaseRepresentation.safeClose(stmt);
		}
		return games;
	}

	@Override
	public void clear() throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String clearGames = "DELETE FROM Games";
			stmt = db.getConnection().prepareStatement(clearGames);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Could not clear games", e);
		} finally {
			DatabaseRepresentation.safeClose(stmt);
		}
	}
}
