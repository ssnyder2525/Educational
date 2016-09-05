package server.database.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import server.managers.User;
import shared.communication.proxy.Credentials;

/**
 * 
 * @author Cody Burt
 *
 */

public class MongoUserDAO implements IUserDAO {
	
	private MongoDatabase db;
	
	public MongoUserDAO() {
		MongoClient mongoClient = new MongoClient();
		mongoClient.getDatabase("huh");
		db = mongoClient.getDatabase("userTest");
		MongoCollection<Document> col = db.getCollection("users");
		System.out.println(col.count());
	}

	public Credentials getUser(int userID) {
		MongoCollection<Document> col = db.getCollection("users");
		FindIterable<Document> iterable = col.find(new Document().append("userID", userID));

		final Credentials cred = new Credentials();
		
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        cred.username = document.getString("username");
		        cred.password = document.getString("password");
		        cred.playerID = document.getInteger("playerID");
		    }
		});
		
		return cred;
	}
	
	public List<Credentials> getAllUsers() {
		MongoCollection<Document> col = db.getCollection("users");
		FindIterable<Document> iterable = col.find();
		
		final ArrayList<Credentials> cred = new ArrayList<Credentials>();
		
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	Credentials newCred = new Credentials();
		        newCred.username = document.getString("username");
		        newCred.password = document.getString("password");
		        newCred.playerID = document.getInteger("playerID");
		        cred.add(newCred);
		    }
		});
		return cred;
	}
	
	public void createUser(User user) {
		MongoCollection<Document> col = db.getCollection("users");
		System.out.println(user.getUsername());
		col.insertOne(new Document().append("username", user.getUsername())
				.append("password", user.getPassword()).append("playerID", user.getPlayerID()));
		
	}
	
	public void clear() {
		db.getCollection("users").drop();
	}
}
