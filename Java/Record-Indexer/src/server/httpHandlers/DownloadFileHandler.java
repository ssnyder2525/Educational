package server.httpHandlers;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
//import java.util.logging.*;


import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;



//import com.sun.net.httpserver.*;
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.DomDriver;
//
//import server.facade.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles the HTTP requests for DownloadFile. Calls the matching method in the Server Facade
 * @author ssnyder
 *
 */
public class DownloadFileHandler implements HttpHandler
{

	//private Logger logger = Logger.getLogger("contactmanager"); 
	
	public DownloadFileHandler()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		File file = new File("./FileStorage/" + File.separator + exchange.getRequestURI().getPath());
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,  0);;
		Files.copy(file.toPath(), exchange.getResponseBody());
		exchange.getResponseBody().close();
		
		
//		String s = "." + exchange.getRequestURI().toString();
//		Path p = Paths.get(s);
//		Byte[] b = Path.
//		exchange.sendResponseHeaders(200, b.length);
//		exchange.getResponseBody().write(b);
//		exchange.getResponseBody().close();
//		return;	
	}

}
