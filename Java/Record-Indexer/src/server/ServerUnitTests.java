package server;

import org.junit.Test;

public class ServerUnitTests
{

	@Test
	public void main()
	{
		String[] testClasses = new String[] 
		{
				"server.serverTests.DataImporterTest",
				"server.serverTests.database.UserDAOTest",
				"server.serverTests.database.BatchDAOTest",
				"server.serverTests.database.ProjectDAOTest",
				"server.serverTests.database.FieldDAOTest",
				"server.serverTests.database.ValueDAOTest",
				"server.clientTests.communication.ClientCommunicatorTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}

}
