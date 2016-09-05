package server.clientTests.communication;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.ClientException;
import client.communication.ClientCommunicator;
import shared.communication.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientCommunicatorTest
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
	public void ValidateUserSuccessTest() throws ClientException
	{
		ClientCommunicator cc = new ClientCommunicator("localhost", "39640");
		ValidateUserInput in = new ValidateUserInput("test1", "test1");
		ValidateUserOutput out = (ValidateUserOutput) cc.ValidateUser(in);
		assertEquals("Test", out.getUSER_FIRST_NAME());
	}
	
	@Test
	public void ValidateUserFalseTest() throws ClientException
	{
		ClientCommunicator cc = new ClientCommunicator("localhost", "39640");
		ValidateUserInput in = new ValidateUserInput("test", "test1");
		ValidateUserOutput out = (ValidateUserOutput) cc.ValidateUser(in);
		assertFalse(out.isSuccess());
	}
	
	@Test
	public void ValidateUserFailTest() throws ClientException
	{
		ClientCommunicator cc = new ClientCommunicator("localhost", "9640");
		ValidateUserInput in = new ValidateUserInput("test1", "test1");
		boolean failed = false;
		try
		{
			cc.ValidateUser(in);
		}
		catch(ClientException e)
		{
			failed = true;
		}
		assertTrue(failed);
	}
	
	@Test
	public void GetProjectsTest() throws ClientException
	{
		ClientCommunicator cc = new ClientCommunicator("localhost", "39640");
		GetProjectsInput in = new GetProjectsInput("test1", "test1");
		GetProjectsOutput out = (GetProjectsOutput) cc.GetProjects(in);
		assertEquals(3, out.getPROJECTS().size());
	}
	
	@Test
	public void GetProjectsFailTest() throws ClientException
	{
		ClientCommunicator cc = new ClientCommunicator("localhost", "9640");
		GetProjectsInput in = new GetProjectsInput("test1", "test1");
		boolean failed = false;
		try
		{
			cc.GetProjects(in);
		}
		catch(ClientException e)
		{
			failed = true;
		}
		assertTrue(failed);
	}
	
	@Test
	public void DownloadBatchTest() throws ClientException
	{
		ClientCommunicator cc = new ClientCommunicator("localhost", "39640");
		DownloadBatchInput in = new DownloadBatchInput("test1", "test1", "1");
		DownloadBatchOutput out = (DownloadBatchOutput) cc.DownloadBatch(in);
		assertEquals(4, out.getFields().size());
	}
	
	@Test
	public void DownloadBatchFailTest() throws ClientException
	{
		ClientCommunicator cc = new ClientCommunicator("localhost", "39640");
		DownloadBatchInput in = new DownloadBatchInput("test", "test1", "1");
		boolean failed = false;
		try
		{
			cc.DownloadBatch(in);
		}
		catch(ClientException e)
		{
			failed = true;
		}
		assertTrue(failed);
	}
	
	@Test
	public void SubmitBatchTest() throws ClientException
	{		
		ClientCommunicator cc = new ClientCommunicator("localhost", "39640");
		SubmitBatchInput in = new SubmitBatchInput("test1", "test1", "1", "Bobson,Bob,20,Caucasian;Henry,Mortensen,40,Black;Tommy,Clark,35,Hispanic");
		SubmitBatchOutput out = (SubmitBatchOutput) cc.SubmitBatch(in);
		assertEquals(out.isSuccess(), true);
	}
	
	@Test
	public void SubmitBatchFailTest() throws ClientException
	{		
		ClientCommunicator cc = new ClientCommunicator("localhost", "39640");
		SubmitBatchInput in = new SubmitBatchInput("test1", "test1", "1", "Bob,20,Caucasian;Henry,Mortensen,40,Black;Tommy,Clark,35,Hispanic");
		boolean failed = false;
		try
		{
			cc.SubmitBatch(in);
		}
		catch(ClientException e)
		{
			failed = true;
		}
		assertTrue(failed);
	}
	
	@Test
	public void GetSampleImageTest() throws ClientException
	{	
		ClientCommunicator cc = new ClientCommunicator("localhost", "39640");
		GetSampleImageInput in = new GetSampleImageInput("test1", "test1", "2");
		GetSampleImageOutput out = (GetSampleImageOutput) cc.GetSampleImage(in);
		assertEquals("http://localhost:39640/images/1900_image0.png", out.getIMAGE_URL());
	}
	
	@Test
	public void GetSampleImageFailTest() throws ClientException
	{	
		ClientCommunicator cc = new ClientCommunicator("localhost", "39640");
		GetSampleImageInput in = new GetSampleImageInput("test1", "test", "2");
		boolean failed = false;
		try
		{
			cc.GetSampleImage(in);
		}
		catch(ClientException e)
		{
			failed = true;
		}
		assertTrue(failed);
	}
	
	@Test
	public void GetFieldsTest() throws ClientException
	{	
		ClientCommunicator cc = new ClientCommunicator("localhost", "39640");
		GetFieldsInput in = new GetFieldsInput("test1", "test1", "1");
		GetFieldsOutput out = (GetFieldsOutput) cc.GetFields(in);
		assertEquals(4, out.getFields().size());
	}
	
	@Test
	public void GetFieldsFailTest() throws ClientException
	{	
		ClientCommunicator cc = new ClientCommunicator("localhost", "39640");
		GetFieldsInput in = new GetFieldsInput("test1", "test", "1");
		boolean failed = false;
		try
		{
			cc.GetFields(in);
		}
		catch(ClientException e)
		{
			failed = true;
		}
		assertTrue(failed);
	}
	
	@Test
	public void SearchTest() throws ClientException
	{	
		ClientCommunicator cc = new ClientCommunicator("localhost", "39640");
		SearchInput in = new SearchInput("test1", "test1", "12", "20");
		SearchOutput out = (SearchOutput) cc.Search(in);
		assertEquals(6, out.getValuesFound().size());
	}
	
	@Test
	public void SearchFailTest() throws ClientException
	{	
		ClientCommunicator cc = new ClientCommunicator("localhost", "39640");
		SearchInput in = new SearchInput("test1", "test1", "12", "This string is not stored in 12.");
		boolean failed = false;
		try
		{
			cc.Search(in);
		}
		catch(ClientException e)
		{
			failed = true;
		}
		assertTrue(failed);
	}
}
