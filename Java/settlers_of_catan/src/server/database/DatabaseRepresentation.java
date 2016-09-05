package server.database;

import java.sql.*;
import java.util.logging.*;

import server.database.dao.SqlCommandDAO;
import server.database.dao.SqlGameDAO;
import server.database.dao.SqlUserDAO;

//import server.databaseAccess.*;

/**
 * The Database Representation class is the only class aware of the database location. It is the only class through which transactions can be 
 * started and committed.
 * @author ssnyder
 *
 */
public class DatabaseRepresentation 
{
	
	private static final String DATABASE_FILE = "db.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_FILE;

	private static Logger logger;
	
	static 
	{
		logger = Logger.getLogger("contactmanager");
	}

	/**
	 * Starts the driver for the database
	 * @throws DatabaseException
	 */
	public static void initialize() throws DatabaseException 
	{
		try 
		{
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(ClassNotFoundException e) 
		{
			
			DatabaseException serverEx = new DatabaseException("Could not load database driver", e);
			
			logger.throwing("server.database.Database", "initialize", serverEx);

			throw serverEx; 
		}
	}
	
	/**
	 * drops all table and inserts them empty
	 */
	public static void dropAndRecreateTables()
	{
		DatabaseRepresentation db = new DatabaseRepresentation();
		try 
		{
			db.startTransaction();
		} catch (DatabaseException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection cnt = db.getConnection();
		String dropUsers = "DROP TABLE IF EXISTS Users;";
		String dropGames = "DROP TABLE IF EXISTS Games;";
		String dropCommands = "DROP TABLE IF EXISTS Commands;";
		String createUsers = "CREATE TABLE Users (" +
				"userID INTEGER UNIQUE PRIMARY KEY," +
				"username TEXT UNIQUE NOT NULL," +
				"password TEXT UNIQUE NOT NULL);";
		String createGames = "CREATE TABLE Games (" +
				"gameID INTEGER PRIMARY KEY, " +
				"Title TEXT, " +
				"gameJSON TEXT NOT NULL,  " +
				"lastCommandSaved INTEGER);";
		String createCommands = "CREATE TABLE Commands ("+ 
			"commandID INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"commandJSON TEXT NOT NULL, " +
			"gameID INTEGER NOT NULL);";
		try 
		{
			Statement smt = cnt.createStatement();
			smt.execute(dropUsers);
			smt.execute(dropGames);
			smt.execute(dropCommands);
			smt.execute(createUsers);
			smt.execute(createGames);
			smt.execute(createCommands);
			
			smt.close();
			db.endTransaction(true);
			cnt.close();
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * All the DAO's store the specified sql commands for each type of object
	 */

	static SqlGameDAO gamedao;
	static SqlUserDAO userdao;
	static SqlCommandDAO commanddao;
	private Connection connection;
	
	public DatabaseRepresentation() 
	{
		gamedao = new SqlGameDAO(this);
		userdao = new SqlUserDAO(this);
		commanddao = new SqlCommandDAO(this);
		connection = null;
	}
	/**
	 * 
	 * @return a connection to the database
	 */
	public Connection getConnection() 
	{
		return connection;
	}

	/**
	 * Begins a transaction with the database. Changes can now be made
	 * @throws DatabaseException
	 */
	public void startTransaction() throws DatabaseException 
	{
		try 
		{
			assert (connection == null);			
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);
		}
		catch (SQLException e) 
		{
			throw new DatabaseException("Could not connect to database. Make sure " + 
				DATABASE_FILE + " is available in ./", e);
		}
	}
	
	/**
	 * Ends the transaction with the database
	 * @param commit if set to true, your changes you have made will be final, otherwise they are rolled back
	 */
	public void endTransaction(boolean commit)
	{
		if (connection != null) 
		{		
			try 
			{
				if (commit) 
				{
					connection.commit();
				}
				else 
				{
					connection.rollback();
				}
			}
			catch (SQLException e) 
			{
				System.out.println("Could not end transaction");
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			finally 
			{
				safeClose(connection);
				connection = null;
			}
		}
	}
	
	public static void safeClose(Connection conn) 
	{
		if (conn != null) 
		{
			try 
			{
				conn.close();
			}
			catch (SQLException e) 
			{
				System.out.println("Connection failed to Close!");
			}
		}
	}
	
	public static void safeClose(Statement stmt) 
	{
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				
			}
		}
	}
	
	public static void safeClose(PreparedStatement stmt) 
	{
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(ResultSet rs) 
	{
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}

	public SqlGameDAO getGameDAO()
	{
		return gamedao;
	}
	
	public SqlUserDAO getUserDAO()
	{
		return userdao;
	}
	
	public SqlCommandDAO getCommandDAO()
	{
		return commanddao;
	}
}