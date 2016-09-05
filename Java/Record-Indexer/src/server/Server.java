package server;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import com.sun.net.httpserver.*;

import server.database.DatabaseException;
import server.database.DatabaseRepresentation;
import server.httpHandlers.*;

/**
 * The HTTP Server. Passes all requests to their appropriate handlers
 * @author ssnyder
 *
 */
public class Server {

	private static final int SERVER_PORT_NUMBER = 8080;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	static int port = SERVER_PORT_NUMBER;
	
	private static Logger logger;
	
	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException {
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("contactmanager"); 
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

	
	private HttpServer server;
	
	private Server() {
		return;
	}
	
	private void run() {
		
		logger.info("Initializing Model");
		
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
		
		server.createContext("/ValidateUser", vUH);
		server.createContext("/GetProjects", gPH);
		server.createContext("/GetSampleImage", gSI);
		server.createContext("/DownloadBatch", dB);
		server.createContext("/SubmitBatch", sB);
		server.createContext("/GetFields", gFH);
		server.createContext("/Search", schH);
		server.createContext("/", dFH);
		
		logger.info("Starting HTTP Server");

		server.start();
	}

	private HttpHandler vUH = new ValidateUserHandler();
	private HttpHandler gPH = new GetProjectsHandler();
	private HttpHandler gSI = new GetSampleImageHandler();
	private HttpHandler dB = new DownloadBatchHandler();
	private HttpHandler sB = new SubmitBatchHandler();
	private HttpHandler gFH = new GetFieldsHandler();
	private HttpHandler schH = new SearchHandler();
	private HttpHandler dFH = new DownloadFileHandler();
	
	public static void main(String[] args) {
		port = Integer.parseInt(args[0]);
		new Server().run();
	}

}