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

import server.command.ACommand;
import shared.communication.proxy.Credentials;
import shared.models.Game;
import shared.serializerJSON.Deserializer;
import shared.serializerJSON.Serializer;

/**
 * 
 * @author Cody Burt
 *
 */
public class MongoGameDAO implements IGameDAO {
	
	private MongoDatabase db;
	
	public MongoGameDAO() {
		MongoClient mongoClient = new MongoClient();
		mongoClient.getDatabase("huh");
		db = mongoClient.getDatabase("gamesTest");
		MongoCollection<Document> col = db.getCollection("games");
		System.out.println(col.count());
	}

	@Override
	public Game getGame(int gameID) {
		final Game game = new Game();
		
		MongoCollection<Document> col = db.getCollection("games");
		FindIterable<Document> iterable = col.find(new Document().append("gameID", gameID));

		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	String gameJson = document.getString("gameJSON");
		    	game.setId(document.getInteger("gameID"));
		    	game.setTitle(document.getString("title"));
		    	JsonObject jsonObj = new JsonParser().parse(gameJson).getAsJsonObject();
				Deserializer.getInstance().deserialize(game, jsonObj);
		    }
		});
		
		return game;
	}

	@Override
	public void saveGame(Game game) {
		MongoCollection<Document> col = db.getCollection("games");
		col.deleteMany(new Document().append("gameID", game.getId()));
		
		col.insertOne(new Document().append("gameID", game.getId()).append("title", game.getTitle())
				.append("gameJSON", Serializer.getInstance().serialize(game)));
		
	}

	@Override
	public List<Game> getAllGames() {
		MongoCollection<Document> col = db.getCollection("games");
		FindIterable<Document> iterable = col.find();
		
		final ArrayList<Game> games = new ArrayList<Game>();
		
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	Game game = new Game();
		    	String gameJson = document.getString("gameJSON");
		        game.setId(document.getInteger("gameID"));
		        game.setTitle(document.getString("title"));
		        JsonObject jsonObj = new JsonParser().parse(gameJson).getAsJsonObject();
				Deserializer.getInstance().deserializeSavedGame(game, jsonObj);
				
				games.add(game);
		    }
		});
		
		return games;
	}

	@Override
	public void clear() {
		db.getCollection("games").drop();
	}

}
