package stats.persistence;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import stats.model.Player;
import stats.utility.Utils;

public class DAOPlayerMongo implements IDAOPlayer {

	@Override
	public void createPlayer(Player player) {
		
		
	}

	@Override
	public void updatePlayer(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePlayer(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Player> retrievePlayers(String surname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Player> retrieveAllPlayers() {
		MongoClient mongoClient = MongoClients.create("mongodb://172.16.0.132:27018");
		MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
		MongoCursor<Document> cursor = mongoDatabase.getCollection("players").find().iterator();
		List<Player> players = new ArrayList<Player>();
		try {
			while (cursor.hasNext()) { 
				players.add(Utils.playerFromJson(cursor.next().toJson()));
			}
		} finally {
			cursor.close(); 
		}
        mongoClient.close();
		return null;
	}

}
