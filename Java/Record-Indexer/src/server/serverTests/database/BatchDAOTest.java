package server.serverTests.database;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.database.DatabaseException;
import server.database.DatabaseRepresentation;
import shared.models.Batch;

public class BatchDAOTest
{

	DatabaseRepresentation db = new DatabaseRepresentation();

	@Before
	public void setUp() throws Exception
	{
		db.startTransaction();
	}

	@After
	public void tearDown() throws Exception
	{
		db.endTransaction(true);
	}
	
	@Test
	public void addBatchTest() throws DatabaseException, SQLException
	{
		Batch firstBatch = new Batch("-1", "1", 1, 0, "FileStorage/baby.jpg");
		Batch secondBatch = new Batch("-1", "1", 0, 1, "FileStorage/baby.jpg");
		Batch thirdBatch = new Batch("-1", "1", 1, 1, "FileStorage/baby.jpg");
		db.getBatchDAO().addBatch(firstBatch);
		db.getBatchDAO().addBatch(secondBatch);
		db.getBatchDAO().addBatch(thirdBatch);
		Batch batch = new Batch("-1", "1", 0, 0, "FileStorage/baby.jpg");
		String batchID = db.getBatchDAO().addBatch(batch);
		assertFalse(batchID == null);
		batch.setSourceURL("one");
		db.getBatchDAO().addBatch(batch);
		batch.setSourceURL("two");
		batch.setProjectID("2");
		db.getBatchDAO().addBatch(batch);
		batch.setSourceURL("three");
		db.getBatchDAO().addBatch(batch);
		Batch sampleImage = db.getBatchDAO().getSampleBatch(batch.getProjectID());
		assertEquals(batch.getProjectID(), sampleImage.getProjectID());
		batch = db.getBatchDAO().DownloadBatch(batch.getProjectID());
		assertEquals(0, batch.isInUse());
		assertEquals(0, batch.isCompleted());
	}
	
	@Test
	public void getBatchTest() throws DatabaseException, SQLException
	{
		Batch firstBatch = new Batch("-1", "1", 1, 0, "FileStorage/baby.jpg");
		Batch secondBatch = new Batch("-1", "1", 0, 1, "FileStorage/baby.jpg");
		Batch thirdBatch = new Batch("-1", "1", 1, 1, "FileStorage/baby.jpg");
		db.getBatchDAO().addBatch(firstBatch);
		db.getBatchDAO().addBatch(secondBatch);
		db.getBatchDAO().addBatch(thirdBatch);
		Batch batch = new Batch("-1", "1", 0, 0, "FileStorage/baby.jpg");
		String batchID = db.getBatchDAO().addBatch(batch);
		assertFalse(batchID == null);
		batch.setSourceURL("one");
		db.getBatchDAO().addBatch(batch);
		batch.setSourceURL("two");
		batch.setProjectID("2");
		db.getBatchDAO().addBatch(batch);
		batch.setSourceURL("three");
		db.getBatchDAO().addBatch(batch);
		Batch sampleImage = db.getBatchDAO().getSampleBatch(batch.getProjectID());
		assertEquals(batch.getProjectID(), sampleImage.getProjectID());
	}
	
	@Test
	public void getSampleBatchTest() throws DatabaseException, SQLException
	{
		Batch firstBatch = new Batch("-1", "1", 1, 0, "FileStorage/baby.jpg");
		Batch secondBatch = new Batch("-1", "1", 0, 1, "FileStorage/baby.jpg");
		Batch thirdBatch = new Batch("-1", "1", 1, 1, "FileStorage/baby.jpg");
		db.getBatchDAO().addBatch(firstBatch);
		db.getBatchDAO().addBatch(secondBatch);
		db.getBatchDAO().addBatch(thirdBatch);
		Batch batch = new Batch("-1", "1", 0, 0, "FileStorage/baby.jpg");
		String batchID = db.getBatchDAO().addBatch(batch);
		assertFalse(batchID == null);
		batch.setSourceURL("one");
		db.getBatchDAO().addBatch(batch);
		batch.setSourceURL("two");
		batch.setProjectID("2");
		db.getBatchDAO().addBatch(batch);
		batch.setSourceURL("three");
		db.getBatchDAO().addBatch(batch);
		Batch sampleImage = db.getBatchDAO().getSampleBatch(batch.getProjectID());
		assertEquals(batch.getProjectID(), sampleImage.getProjectID());
		batch = db.getBatchDAO().DownloadBatch(batch.getProjectID());
		assertEquals(0, batch.isInUse());
		assertEquals(0, batch.isCompleted());
	}

}
