package server.database.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import server.ServerException;
import server.command.ACommand;
import server.facade.IServerFacade;
import shared.serializerJSON.Deserializer;

public class MongoCommandDAO implements ICommandDAO {
	
	public int delta;
	
	public String collectionName;

	private MongoDatabase db;
	
	public MongoCommandDAO() {
		MongoClient mongoClient = new MongoClient();
		db = mongoClient.getDatabase("commandsTest");
		MongoCollection<Document> col = db.getCollection("commands");
		System.out.println(col.count());
	}

	@Override
	public int getCommandCount(int gameID) {
		MongoCollection<Document> col = db.getCollection("commands");
		FindIterable<Document> iterable = col.find(new Document().append("gameID", gameID));

		final int[] commandCount = {0};
		
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	int commandID = document.getInteger("commandID");
		    	if (commandCount[0] < commandID) {
		    		commandCount[0] = commandID;
		    	}
		    	
		    }
		});
		return commandCount[0];
	}

	@Override
	public void createCommand(int gameID, String jsonCommand) {
		MongoCollection<Document> col = db.getCollection("commands");
		
		int commandID = this.getCommandCount(gameID) + 1;
		col.insertOne(new Document().append("gameID", gameID).append("commandID", commandID).append("commandJSON", jsonCommand));
	}

	@Override
	public List<ACommand> getAllCommands(final IServerFacade facade) {
		MongoCollection<Document> col = db.getCollection("commands");
		FindIterable<Document> iterable = col.find();
		
		final ArrayList<ACommand> commands = new ArrayList<ACommand>();
		
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	ACommand command = null;
		    	String commandJSON = document.getString("commandJSON");
		    	try {
		    		System.out.println(commandJSON);
					command = Deserializer.getInstance().deserializeCommand(commandJSON, facade);
					commands.add(command);
				} catch (ServerException e) {
					e.printStackTrace();
				}
		    	
		    }
		});
		
		return commands;
	}
	
	

	@Override
	public void clear() {
		db.getCollection("commands").drop();

	}

	@Override
	public void clearCommands(int gameID) {
		db.getCollection("commands").deleteMany(new Document().append("gameID", gameID));
		
	}

	@Override
	public void setDelta(int delta) {
		this.delta = delta;
	}
	
	@Override
	public int getDelta() {
		return delta;
	}

}
