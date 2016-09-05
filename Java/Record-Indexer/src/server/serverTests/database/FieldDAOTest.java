package server.serverTests.database;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.database.DatabaseRepresentation;
import shared.models.Field;

public class FieldDAOTest
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
		db.endTransaction(false);
	}

	@Test
	public void addTest() throws SQLException
	{
		Field field = new Field("-1", "1234", "Column1", "Your face", "blah", "0", "0");
		String fieldID = db.getFieldDAO().addField(field);
		assertFalse(fieldID == null);
		Field field1 = new Field("-1", "1234", "Column2", "Your face", "blah", "0", "0");
		Field field2 = new Field("-1", "1234", "Column3", "Your face", "blah", "0", "0");
		String fieldID2 = db.getFieldDAO().addField(field1);
		assertFalse(fieldID2 == null);
		String fieldID3 = db.getFieldDAO().addField(field2);
		assertFalse(fieldID3 == null);
	}
	
	@Test
	public void getTest() throws SQLException
	{
		Field field = new Field("-1", "1234", "Column1", "Your face", "blah", "0", "0");
		db.getFieldDAO().addField(field);
		Field field1 = new Field("-1", "1234", "Column2", "Your face", "blah", "0", "0");
		Field field2 = new Field("-1", "1234", "Column3", "Your face", "blah", "0", "0");
		db.getFieldDAO().addField(field1);
		db.getFieldDAO().addField(field2);
		ArrayList<Field> fields = db.getFieldDAO().getFields(field.getProjectID());	
		assertEquals(3, fields.size());
	}
	
}
