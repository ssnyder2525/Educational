package server.serverTests.database;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.database.DatabaseRepresentation;
import shared.models.Value;

public class ValueDAOTest
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
		Value value = new Value("-1", "1", "value", "3", "4", "myurl");
		String valueID = db.getValueDAO().addValue(value);
		assertFalse(valueID == null);
		Value value2 = new Value("-1", "1", "value2", "3", "4", "myurl");
		valueID = db.getValueDAO().addValue(value2);
		Value value3 = new Value("-1", "1", "value3", "3", "4", "myurl");
		valueID = db.getValueDAO().addValue(value3);
	}
	
	@Test
	public void getTest() throws SQLException
	{
		Value value = new Value("-1", "1", "value", "3", "4", "myurl");
		db.getValueDAO().addValue(value);
		ArrayList<Value> results = db.getValueDAO().getValues(value);
		assertEquals("value", results.get(0).getValue());
	}
	
	@Test
	public void searchTest() throws SQLException
	{
		Value value = new Value("-1", "1", "value", "3", "4", "myurl");
		db.getValueDAO().addValue(value);
		ArrayList<Value> results = db.getValueDAO().searchValues(value.getFieldID());
		assertEquals(1, results.size());
	}

}
