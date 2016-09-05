package server.database;

import java.sql.*;
import java.util.logging.*;

import server.databaseAccess.*;

/**
 * The Database Representation class is the only class aware of the database location. It is the only class through which transactions can be 
 * started and committed.
 * @author ssnyder
 *
 */
public class DatabaseRepresentation 
{
	
	private static final String DATABASE_FILE = "recordIndexerDatabase.sqlite";
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
		String dropUser = "Drop TABLE IF EXISTS User;";
		String dropProject = "Drop TABLE IF EXISTS Project;";
		String dropBatch = "Drop TABLE IF EXISTS Batch;";
		String dropField = "Drop TABLE IF EXISTS Field;";
		String dropValue = "Drop TABLE IF EXISTS Value;";
		String createUser = "CREATE TABLE User ("+ 
			"UserID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ," +
			"FirstName TEXT, " +
			"LastName TEXT, " +
			"Email TEXT, " +
			"Username TEXT NOT NULL UNIQUE, " +
			"Password TEXT, " +
			"CurrentBatch Integer, " +
			"BatchesCompleted INTEGER);";
		String createProject = "CREATE TABLE Project (" +
			"ProjectID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , " +
			"Title TEXT NOT NULL UNIQUE, " +
			"TotalIndexed INTEGER,  " +
			"RecordsPerBatch TEXT," +
			"FieldY TEXT, " +
			"RecordHeight TEXT);";
		String createBatch = "CREATE TABLE Batch (" +
			"BatchID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE, " +
			"ProjectID INTEGER NOT NULL, " +
			"InUse BOOL,"+
			"Completed BOOL DEFAULT (0) ," +
			"SourceURL TEXT);";
		String createField = "CREATE TABLE Field (" +
			"FieldID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ," +
			"ProjectID TEXT," +
			"ColumnHeader TEXT DEFAULT (null) , " +
			"FieldHelpFile TEXT, " +
			"KnownDataFile TEXT," +
			"FieldX TEXT, " +
			"FieldWidth TEXT);";
		String createValue = "CREATE TABLE Value (" +
			"ValueID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ," +
			"RecordNum TEXT," +
			"Value TEXT," +
			"FieldID TEXT," +
			"BatchID TEXT," +
			"ImageURL TEXT)";
		try 
		{
			Statement smt = cnt.createStatement();
			smt.execute(dropUser);
			smt.execute(dropProject);
			smt.execute(dropBatch);
			smt.execute(dropField);
			smt.execute(dropValue);
			smt.execute(createUser);
			smt.execute(createProject);
			smt.execute(createBatch);
			smt.execute(createField);
			smt.execute(createValue);
			
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
	private UserDAO userDAO;
	private ProjectDAO projectDAO;
	private BatchDAO batchDAO;
	private FieldDAO fieldDAO;
	private ValueDAO valueDAO;
	private Connection connection;
	
	public DatabaseRepresentation() 
	{
		userDAO = new UserDAO(this);
		projectDAO = new ProjectDAO(this);
		batchDAO = new BatchDAO(this);
		fieldDAO = new FieldDAO(this);
		valueDAO = new ValueDAO(this);
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
	
	public UserDAO getUserDAO()
	{
		return userDAO;
	}
	
	public ProjectDAO getProjectDAO()
	{
		return projectDAO;
	}
	
	public BatchDAO getBatchDAO()
	{
		return batchDAO;
	}
	
	public FieldDAO getFieldDAO()
	{
		return fieldDAO;
	}
	
	public ValueDAO getValueDAO()
	{
		return valueDAO;
	}
}