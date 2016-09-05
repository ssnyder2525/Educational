package client.communication;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import shared.models.*;
import client.ClientException;
/**
 * The Client-Side Communicator to the Server. Sends Http Requests to be handled by the server
 * @author ssnyder
 *
 */
public class ClientCommunicator {

//	private static final String SERVER_HOST = "localhost";
//	private static final int SERVER_PORT = 8080;
	//private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	private static final String HTTP_POST = "POST";
	private static String HOST;
	private static String PORT;
	private static String URLPREFIX;

	private XStream xmlStream;

	/**
	 * All methods for this class send and recieve specialized packets of input for easy transportation across the network.
	 * @param host must be the name of the server
	 * @param port must be the port that the server is currently running on
	 */
	public ClientCommunicator(String host, String port) {
		xmlStream = new XStream(new DomDriver());
		HOST = host;
		PORT = port;
		URLPREFIX = "http://" + HOST + ":" + PORT;
	}
	
	/**
	 * Retrieves an unused and incomplete Batch of names from the database if any exist in the current project
	 * @param input
	 * @return
	 * @throws ClientException
	 */
	public Object DownloadBatch(DownloadBatchInput input) throws ClientException 
	{
		DownloadBatchOutput out =  (DownloadBatchOutput)Post("/DownloadBatch", input);
		String url = makeUrl(out.getIMAGE_URL());
		out.setIMAGE_URL(url);
		ArrayList<Field> edit = out.getFields();
		for (int i = 0; i < edit.size(); i++)
		{
			if(edit.get(i).getFieldHelpFile().length() > 0)
			{
				String url2 = makeUrl(edit.get(i).getFieldHelpFile());
				edit.get(i).setFieldHelpFile(url2);
			}
			if(edit.get(i).getKnownDataFile().length() > 0)
			{
				String url3 = makeUrl(edit.get(i).getKnownDataFile());
				edit.get(i).setKnownDataFile(url3);
			}
		}
			out.setFields(edit);
		
		return out;
	}
	
	/**
	 * Gets all the fields of a project or all fields in the database
	 * @param input
	 * @return
	 * @throws ClientException
	 */
	public Object GetFields(GetFieldsInput input) throws ClientException 
	{
		return (GetFieldsOutput)Post("/GetFields", input);
	}
	
	/**
	 * Gets all the projects in the database
	 * @param input
	 * @return
	 * @throws ClientException
	 */
	public Object GetProjects(GetProjectsInput input) throws ClientException 
	{
		return (GetProjectsOutput)Post("/GetProjects", input);
	}
	
	/**
	 * Gets a sample image from a project to allow the user to see what the records look like
	 * @param input
	 * @return
	 * @throws ClientException
	 */
	public Object GetSampleImage(GetSampleImageInput input) throws ClientException 
	{
		GetSampleImageOutput out = (GetSampleImageOutput)Post("/GetSampleImage", input);
		String url = makeUrl(out.getIMAGE_URL());
		out.setIMAGE_URL(url);
		return out;
	}
	
	/**
	 * 
	 * Searches the values of specified fields for specified values
	 * @param input
	 * @return
	 * @throws ClientException
	 */
	public Object Search(SearchInput input) throws ClientException 
	{
		SearchOutput out = (SearchOutput)Post("/Search", input);
		ArrayList<Value> edit = out.getValuesFound();
		for(int i = 0; i < edit.size(); i++)
		{
			String url = makeUrl(edit.get(i).getImageURL());
			edit.get(i).setImageURL(url);
		}
		out.setValuesFound(edit);
		return out;
	}
	
	/**
	 * Adds the values inputed by the user for the current batch being used
	 * @param input
	 * @return
	 * @throws ClientException
	 */
	public Object SubmitBatch(SubmitBatchInput input) throws ClientException 
	{
		return (SubmitBatchOutput)Post("/SubmitBatch", input);
	}
	
	/**
	 * Confirms a username and password
	 * @param input
	 * @return
	 * @throws ClientException
	 */
	public Object ValidateUser(ValidateUserInput input) throws ClientException 
	{
		return (ValidateUserOutput)Post("/ValidateUser", input);
	}
	
	/**
	 * Sends a post request
	 * @param urlPath
	 * @param postData
	 * @return
	 * @throws ClientException
	 */
	private Object Post(String urlPath, Object postData) throws ClientException 
	{
		try {
			URL url = new URL(URLPREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
			xmlStream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				Object result = xmlStream.fromXML(connection.getInputStream());
				connection.getInputStream().close();
				return result;
			}
			else 
			{
				throw new ClientException(String.format("doGet failed: %s (http code %d)",
											urlPath, connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new ClientException(String.format("doGet failed: %s", e.getMessage()), e);
		}
	}
	
	/**
	 * Builds a url from a given file path
	 * @param path
	 * @return
	 */
	private String makeUrl(String path)
	{
		StringBuilder url = new StringBuilder();
		url.append("http://");
		url.append(HOST);
		url.append(':');
		url.append(PORT);
		url.append('/');
		url.append(path);
		return url.toString();
	}
}
