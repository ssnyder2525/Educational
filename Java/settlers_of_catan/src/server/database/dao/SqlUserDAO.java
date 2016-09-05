package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import server.database.DatabaseException;
import server.database.DatabaseRepresentation;
import server.managers.User;
import shared.communication.proxy.Credentials;
import shared.serializerJSON.Serializer;

/**
 * 
 * @author Bo Pace
 *
 */
public class SqlUserDAO implements IUserDAO {
	
	DatabaseRepresentation db;
	
	public SqlUserDAO(DatabaseRepresentation db) {
		this.db = db;
	}
	
	public Credentials getUser(int userID) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT userID, username, password FROM Users WHERE userID = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, userID);
			
			rs = stmt.executeQuery();
			if(rs.next()) {
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				Credentials cred = new Credentials();
				cred.playerID = id;
				cred.username = username;
				cred.password = password;
				return cred;
			} else {
				throw new DatabaseException("Could not find game with id " + userID);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not get game with id " + userID, e);
		} finally {
			DatabaseRepresentation.safeClose(rs);
			DatabaseRepresentation.safeClose(stmt);
		}
	}
	
	public List<Credentials> getAllUsers() throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Credentials> users = new ArrayList<Credentials>();
		try {
			String query = "SELECT userID, username, password FROM Users;";
			stmt = db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next()) {
				int userID = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				Credentials cred = new Credentials();
				cred.playerID = userID;
				cred.username = username;
				cred.password = password;
				users.add(cred);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not get all users", e);
		} finally {
			DatabaseRepresentation.safeClose(rs);
			DatabaseRepresentation.safeClose(stmt);
		}
		return users;
	}
	
	public void createUser(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		
		try {
			String query = "INSERT INTO Users (userID, username, password) values (?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, user.getPlayerID());
			stmt.setString(2, user.getUsername());
			stmt.setString(3, user.getPassword());
			
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not create user");
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not create user", e);
		} finally {
			DatabaseRepresentation.safeClose(stmt);
		}
	}
	
	public void clear() throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String clearGames = "DELETE FROM Users";
			stmt = db.getConnection().prepareStatement(clearGames);
			
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not clear users");
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not clear users", e);
		} finally {
			DatabaseRepresentation.safeClose(stmt);
		}
	}

}
