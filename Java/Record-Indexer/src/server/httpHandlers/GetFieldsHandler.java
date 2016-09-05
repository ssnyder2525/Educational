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
 * Handles the HTTP requests for GetFields. Calls the matching method in the Server Facade
 * @author ssnyder
 *
 */
public class GetFieldsHandler implements HttpHandler
{

	private Logger logger = Logger.getLogger("contactmanager"); 
	
	private XStream xmlStream = new XStream(new DomDriver());
	
	public GetFieldsHandler()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		GetFieldsInput params = (GetFieldsInput)xmlStream.fromXML(exchange.getRequestBody());
		try
		{
			GetFieldsOutput output = ServerFacade.GetFields(params);
			if(output == null)
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
				return;
			}
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(output, exchange.getResponseBody());
			exchange.getResponseBody().close();		
			return;
		} catch (DatabaseException e)
		{
			System.out.println("GetFields threw a database error");
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		} catch (SQLException e)
		{
			System.out.println("GetFields threw a sql error");
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
	}

}