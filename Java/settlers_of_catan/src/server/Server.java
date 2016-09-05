package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import server.database.DatabaseException;
import server.database.DatabaseRepresentation;
import server.database.IPersistencePlugin;
import server.database.Registry;
import server.facade.FakeServerFacade;
import server.facade.IServerFacade;
import server.facade.ServerFacade;
import server.httpHandlers.Handlers;
import server.httpHandlers.RequestHandler;

/**
 * The HTTP Server. Passes all requests to the handler
 * @author ssnyder
 *
 */
public class Server {

	private static final int DEFAULT_PORT_NUMBER = 8081;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private int port;
	private String host;
	private HttpHandler handler;
	private static Logger logger;
	private HttpServer server;
	
	
	// Initialize the log
	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	
	
	private Server() {
		this.port = DEFAULT_PORT_NUMBER;
		IServerFacade serverFacade = new ServerFacade();
		this.handler = new RequestHandler(serverFacade, null);
	}
	
	
	
	private Server(String host, int port, boolean isTest, String pluginClass, int delta, Boolean clear) {
		this.host = host;
		this.port = port;
		if (isTest) { // don't worry about the plugins here
			System.out.println("Creating a fake facade for testing purposes");
			IServerFacade serverFacade = new FakeServerFacade();
			this.handler = new RequestHandler(serverFacade, null);
		}
		else {
			IServerFacade serverFacade = new ServerFacade();
			IPersistencePlugin plugin = this.initializePlugin(pluginClass, delta);
			if (! clear) {
				try {
					plugin.startTransaction();
					serverFacade.restoreAllUsers(plugin.getUserDAO().getAllUsers());
					serverFacade.restoreAllGames(plugin.getGameDAO().getAllGames());
					serverFacade.runAllCommands(plugin.getCommandDAO().getAllCommands(serverFacade));
					plugin.endTransaction();
				} catch (DatabaseException e) {
					e.printStackTrace();
					plugin.rollback();
				}
			}
			else { //clear
				try {
					plugin.startTransaction();
					plugin.getUserDAO().clear();
					plugin.getGameDAO().clear();
					plugin.getCommandDAO().clear();
					plugin.endTransaction();
				} catch (DatabaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					plugin.rollback();
				}
			}
			this.handler = new RequestHandler(serverFacade, plugin);
		}
	}
	
	
	
	/**
	 * Initializes the log
	 * 
	 * @throws IOException
	 */
	private static void initLog() throws IOException {
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("catan"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}
	
	
	/**
	 * Set's up the server before starting it. // TODO add testing capabilities
	 * 
	 */
	private void run() {
		
		try
		{
			DatabaseRepresentation.initialize();
		} catch (DatabaseException e1)
		{
			System.out.println("Failed to initialize the Database");
			e1.printStackTrace();
		}
		
		logger.info("Initializing HTTP Server");
		
		try {
			server = HttpServer.create(new InetSocketAddress(port),
											MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}
		
		server.setExecutor(null); // use the default executor
		server.createContext("/user", handler);
		server.createContext("/game", handler);
		server.createContext("/games", handler);
		server.createContext("/moves", handler);
		
		server.createContext("/docs/api/data", new Handlers.JSONAppender("")); 
		server.createContext("/docs/api/view", new Handlers.BasicFile(""));

		
		logger.info("Starting HTTP Server on port: " + port);
		server.start();
	}
	
	/**
	 * This function starts up the persistence plugin to allow data permanence.
	 * @param plugin The name of the plugin the user wishes to start up.
	 * @param delta The number of commands between game state checkpoints.
	 */
	private IPersistencePlugin initializePlugin(String plugin, int delta) {
		Registry r = new Registry();
		r.loadConfig(plugin, delta);
		return r.getPlugin();
	}
	
	public static void main(String[] args) throws Exception {
		
		if (args.length == 5  || args.length ==  6) {
		
			String host = args[0];
			int port = Integer.parseInt(args[1]);
			boolean isTest = args[2].equals("true");
			String pluginClass = args[3];
			int delta = Integer.parseInt(args[4]);
			Boolean clear = false;
			if (args.length == 6) {
				if (args[5].equals("clear")) {
					clear = true;
				}
			}
			new Server(host, port, isTest, pluginClass, delta, clear).run();
		} else if (args.length == 0) {
			new Server().run();
		} else {
			System.out.println("Must run server with format: host(localhost) port-number(xxxx) test(true/false) plugin(Sqlite3Plugin/MongoPlugin delta(int)");
			throw new Exception();
		}
	}

}
