package server.httpHandlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.logging.*;

//import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import server.database.DatabaseException;
import server.facade.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
/**
 * Handles the HTTP requests for ValidateUser. Calls the matching method in the Server Facade
 * @author ssnyder
 *
 */
public class ValidateUserHandler implements HttpHandler
{
	
	private Logger logger = Logger.getLogger("contactmanager"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	public ValidateUserHandler()
	{
		
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		ValidateUserInput params = (ValidateUserInput)xmlStream.fromXML(exchange.getRequestBody());
		try
		{
			ValidateUserOutput output = ServerFacade.ValidateUser(params);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(output, exchange.getResponseBody());
			exchange.getResponseBody().close();	
			return;
		} catch (DatabaseException e)
		{
			System.out.println("Validate User threw a database error");
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		} catch (SQLException e)
		{
			System.out.println("Validate User threw a sql error");
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
	}
}
