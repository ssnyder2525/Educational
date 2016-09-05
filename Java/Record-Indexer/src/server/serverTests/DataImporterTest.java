package server.serverTests;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import server.DataImporter;

public class DataImporterTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void test()
	{
		String[] args = new String[1];
		String file = "FileStorage/Records.xml";
		args[0] = file;
		try
		{
			DataImporter.main(args);
		} catch (ParserConfigurationException e)
		{
			System.out.println("Error1");
			e.printStackTrace();
		} catch (SAXException e)
		{
			System.out.println("Error2");
			e.printStackTrace();
		} catch (IOException e)
		{
			System.out.println("Error3");
			e.printStackTrace();
		}
	}

}
