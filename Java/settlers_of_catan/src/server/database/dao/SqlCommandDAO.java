package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ServerException;
import server.command.ACommand;
import server.database.DatabaseException;
import server.database.DatabaseRepresentation;
import server.facade.IServerFacade;
import shared.serializerJSON.Deserializer;

public class SqlCommandDAO implements ICommandDAO {
	
	private int delta;
	
	private DatabaseRepresentation db;
	
	public SqlCommandDAO(int delta, DatabaseRepresentation db) {
		this.delta = delta;
		this.db = db;
	}
	
	public SqlCommandDAO(DatabaseRepresentation db) {
		this.db = db;
	}

	/**
	 * Returns the delta value
	 * @return delta
	 */
	@Override
	public int getDelta() {
		return delta;
	}
	
	/**
	 * Sets the delta value
	 * @param delta
	 */
	@Override
	public void setDelta(int delta) {
		this.delta = delta;
	}

	@Override
	public int getCommandCount(int gameID) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT COUNT(*) FROM Commands WHERE gameID = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, gameID);
			
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				throw new DatabaseException("Unable to retrieve command count");
			}
		} catch (SQLException e) {
			throw new DatabaseException("Unable to retrieve command count", e);
		} finally {
			DatabaseRepresentation.safeClose(rs);
			DatabaseRepresentation.safeClose(stmt);
		}
	}

	@Override
	public void createCommand(int gameID, String jsonCommand) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "INSERT INTO Commands (commandJSON, gameID) VALUES (?, ?)";
			JsonParser parser = new JsonParser();
			JsonObject command = parser.parse(jsonCommand).getAsJsonObject();
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, jsonCommand);
			stmt.setInt(2, gameID);
			
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not create command");
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not create command");
		} finally {
			DatabaseRepresentation.safeClose(stmt);
		}
	}

	@Override
	public void clear() throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String clearCommands = "DELETE FROM Commands";
			stmt = db.getConnection().prepareStatement(clearCommands);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Could not clear commands", e);
		} finally {
			DatabaseRepresentation.safeClose(stmt);
		}
	}

	@Override
	public List<ACommand> getAllCommands(IServerFacade facade) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<ACommand> commands = new ArrayList<ACommand>();
		try {
			String query = "SELECT commandID, commandJSON, gameID FROM Commands";
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				int commandID = rs.getInt(1);
				String commandJSON = rs.getString(2);
				int gameID = rs.getInt(3);
				//TODO Uncomment the following line when a method is written to implement it.
				ACommand command;
				try {
					command = Deserializer.getInstance().deserializeCommand(commandJSON, facade);
					commands.add(command);
				} catch (ServerException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not get all commands", e);
		} finally {
			DatabaseRepresentation.safeClose(rs);
			DatabaseRepresentation.safeClose(stmt);
		}
		return commands;
	}

	@Override
	public void clearCommands(int gameID) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String clearCommands = "DELETE FROM Commands WHERE gameID = ?";
			stmt = db.getConnection().prepareStatement(clearCommands);
			stmt.setInt(1, gameID);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Could not delete commands from game");
		} finally {
			DatabaseRepresentation.safeClose(stmt);
		}
	}

}
