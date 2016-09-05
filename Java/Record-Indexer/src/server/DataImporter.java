package server;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import server.database.DatabaseException;
import server.database.DatabaseRepresentation;
import shared.models.*;

public class DataImporter 
{
	
	public DataImporter() 
	{
	}
	/**
	 * Imports data into the database from an xml file
	 * @param args
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
	{
		try
		{
			DatabaseRepresentation.initialize();
		} catch (DatabaseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatabaseRepresentation.dropAndRecreateTables();
		
		File xmlInput = new File(args[0]);
		File parent = xmlInput.getParentFile();
		File destination = new File("FileStorage");
		try
		{
			if(!parent.getCanonicalFile().equals(destination.getCanonicalFile()))
			{
				FileUtils.deleteDirectory(destination);
			}
			FileUtils.copyDirectory(xmlInput.getParentFile(), destination);
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DataImporter di = new DataImporter();
		DocumentBuilder docB = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		File file = new File(args[0]);
		Document doc = docB.parse(file);
		//doc.getDocumentElement().normalize();
		DatabaseRepresentation db = new DatabaseRepresentation();
		try
		{
		db.startTransaction();
		
		di.parseUsers(db, doc);
		di.parseProjects(db, doc);
		
		db.endTransaction(true);
		} catch (DatabaseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * inserts all elements with the tag "user" into the database
	 * @param doc the documentparser of the XML file.
	 */
	public void parseUsers(DatabaseRepresentation db, Document doc)
	{
		NodeList nL = doc.getElementsByTagName("user");
		int i = 0;
		while(i < nL.getLength())
		{
			Element userElement = (Element) nL.item(i);
			Element usernameElement = (Element) userElement.getElementsByTagName("username").item(0);
			Element passwordElement = (Element) userElement.getElementsByTagName("password").item(0);
			Element firstnameElement = (Element) userElement.getElementsByTagName("firstname").item(0);
			Element lastnameElement = (Element) userElement.getElementsByTagName("lastname").item(0);
			Element emailElement = (Element) userElement.getElementsByTagName("email").item(0);
			Element indexedrecordsElement = (Element) userElement.getElementsByTagName("indexedrecords").item(0);
			
			String username = usernameElement.getTextContent();
			String password = passwordElement.getTextContent();
			String firstName = firstnameElement.getTextContent();
			String lastName = lastnameElement.getTextContent();
			String email = emailElement.getTextContent();
			int indexedRecords = Integer.parseInt(indexedrecordsElement.getTextContent());
			
			User user = new User("-1", firstName, lastName, email, username, password, "-1", indexedRecords);
			String userID;
			try
			{
				userID = db.getUserDAO().addUser(user);
				if(userID == null)
				{
					System.out.println("Failed adding a new User");
				}
			} catch (SQLException e)
			{
				System.out.println("Error thrown while adding new User");
				e.printStackTrace();
			}
			i++;
		}
	}
	
	/**
	 * Inserts all elements with the tag "project" into the database
	 * @param doc the documentparser of the XML file.
	 */
	public void parseProjects(DatabaseRepresentation db, Document doc)
	{
		NodeList nL = doc.getElementsByTagName("project");
		int i = 0;
		while(i < nL.getLength())
		{
			Element projectElement = (Element) nL.item(i);
			Element titleElement = (Element) projectElement.getElementsByTagName("title").item(0);
			Element recordsperimageElement = (Element) projectElement.getElementsByTagName("recordsperimage").item(0);
			Element firstycoordElement = (Element) projectElement.getElementsByTagName("firstycoord").item(0);
			Element recordheightElement = (Element) projectElement.getElementsByTagName("recordheight").item(0);
			
			String title = titleElement.getTextContent();
			String recordsPerImage = recordsperimageElement.getTextContent();
			String firstYCoordinate = firstycoordElement.getTextContent();
			String recordHeight = recordheightElement.getTextContent();
			
			Project project = new Project("-1", title, 0, recordsPerImage, firstYCoordinate, recordHeight);
			String projectID;
			try
			{
				projectID = db.getProjectDAO().addProject(project);
				ArrayList<String> fieldIDs = parseFields(db, projectElement, projectID);
				parseBatches(db, projectElement, projectID, fieldIDs, Integer.parseInt(recordsPerImage));
				
				if(projectID == null)
				{
					System.out.println("Failed adding a new Project");
				}
			} catch (SQLException e)
			{
				System.out.println("Error thrown while adding new Project");
				e.printStackTrace();
			}			
			i++;
		}
	}
	
	/**
	 * Inserts all elements labeled "field" into the database
	 * @param doc the documentparser of the XML file.
	 * @param projectID the project that this field is a part of
	 * @return
	 */
	public ArrayList<String> parseFields(DatabaseRepresentation db, Element project, String projectID)
	{
		ArrayList<String> fieldIDs = new ArrayList<String>();
		NodeList nL = project.getElementsByTagName("field");
		int i = 0;
		while(i < nL.getLength())
		{
			String title = null;
			String xCoord = null;
			String width = null;
			String helpHTML = null;
			String knownData = null;
			Element fieldElement = (Element) nL.item(i);
			Element titleElement = (Element) fieldElement.getElementsByTagName("title").item(0);
			title = titleElement.getTextContent();
			Element xcoordElement = (Element) fieldElement.getElementsByTagName("xcoord").item(0);
			xCoord = xcoordElement.getTextContent();
			Element widthElement = (Element) fieldElement.getElementsByTagName("width").item(0);
			width = widthElement.getTextContent();
			Element helphtmlElement = (Element) fieldElement.getElementsByTagName("helphtml").item(0);
			Element knowndataElement = (Element) fieldElement.getElementsByTagName("knowndata").item(0);
			helpHTML = "";
			knownData = "";
			if(helphtmlElement != null)
			{
				helpHTML = helphtmlElement.getTextContent();
			}
			if(knowndataElement != null)
			{
				knownData = knowndataElement.getTextContent();
			}
			
			Field field = new Field("-1", projectID, title, helpHTML, knownData, xCoord, width);
				String fieldID;
				try
				{
					fieldID = db.getFieldDAO().addField(field);
					if(fieldID != null)
					{
						fieldIDs.add(fieldID);
					}
					else
					{
						System.out.println("Failed adding a new Field");
					}
				} catch (SQLException e)
				{
					System.out.println("Error thrown while adding new Field");
					e.printStackTrace();
				}

			i++;
		}
		return fieldIDs;
	}
	
	/**
	 * inserts all elements with the tag labeled "image" into the database
	 * @param doc the documentparser of the XML file.
	 * @param projectID The project this batch is a part of
	 * @param fieldIDs The fields this batch contains
	 */
	public void parseBatches(DatabaseRepresentation db, Element project, String projectID, ArrayList<String> fieldIDs, int recordsPerImage)
	{
		//Get list of Batches
		NodeList nL = project.getElementsByTagName("image");
		String file = null;
		//for every batch
		for(int i = 0; i < nL.getLength(); i++)
		{
			Element batchElement = (Element) nL.item(i);
			Element fileElement = (Element) batchElement.getElementsByTagName("file").item(0);
			
			file = fileElement.getTextContent();
			Batch batch = new Batch("-1", projectID, 0, 0, file);
			String batchID;
			//get record list
			NodeList recordList = batchElement.getElementsByTagName("record");
			try
			{
				batchID = db.getBatchDAO().addBatch(batch);
				if(batchID != null)
				{
					if(recordList.getLength() != 0)
					{
						batch.setBatchID(batchID);
						batch.setCompleted(1);
						db.getBatchDAO().updateBatch(batch);
						for(int j = 0; j < recordList.getLength(); j++)
						{
							parseValues(db, (Element) recordList.item(j), batchID, fieldIDs, file, recordsPerImage, j+1);
						}
					}
				}
				else
				{
					System.out.println("Failed adding a new Batch");
				}
			} catch (SQLException e)
			{
				System.out.println("Error thrown while adding new Field");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * inserts all elements with the tag labeled "value" into the database
	 * @param doc the documentparser of the XML file.
	 * @param batchID the batch this value is a part of
	 * @param fieldIDs the field this value is under
	 * @throws SQLException 
	 */
	public void parseValues(DatabaseRepresentation db, Element record, String batchID, ArrayList<String> fieldIDs, String url, int recordsPerImage, int row) throws SQLException
	{
		NodeList valuesList = record.getElementsByTagName("values");
		Element valueGroup = (Element) valuesList.item(0);
		NodeList values = valueGroup.getElementsByTagName("value");
		//set the batch as completed if there are values
//		if(nL.getLength() > 0)
//		{
//			Batch batch = db.getBatchDAO().getBatch(batchID);
//			batch.setCompleted(1);
//			db.getBatchDAO().updateBatch(batch);
//		}
		for(int i = 0; i < values.getLength(); i++)
		{
			String valueContent = values.item(i).getTextContent();
			Value value = new Value("-1", Integer.toString(row), valueContent, fieldIDs.get(i), batchID, url);
			String valueID;
			try
			{
				valueID = db.getValueDAO().addValue(value);
				if(valueID == null)
				{
					System.out.println("Failed adding a new Field");
				}
			} catch (SQLException e)
			{
				System.out.println("Error thrown while adding new Field");
				e.printStackTrace();
			}
		}
	}
}
