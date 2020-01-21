package stats.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.ClusterDescription;
import com.mongodb.connection.ClusterType;

import stats.model.Player;
import stats.utility.Utils;

public class DAOPlayerMongo implements IDAOPlayer {
	
	@Override
	public boolean exists(Player player) throws DAOException {
		try {
			MongoClient mongoClient = MongoClients.create("mongodb://172.16.0.132:27018");
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("players");
			Document filter = new Document();
			filter.append("fullName", player.getFullName());
			MongoCursor<Document> cursor = mongoCollection.find(filter).iterator();
			if(cursor.hasNext()) {
				return true;
			} else {
				return false;
			}
		} catch (MongoWriteException mwe) {
			throw new DAOException(mwe);
		}
	}

	@Override
	public void createPlayer(Player player) throws DAOException{
		try {
			Document obj = Document.parse(player.toJSON());
			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
			obj.put("bornDate", dateFormatter.parse(player.getBornDate()));
			MongoClient mongoClient = MongoClients.create("mongodb://172.16.0.132:27018");
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("players");
			mongoCollection.insertOne(obj);
		} catch(MongoWriteException mwe) {
			throw new DAOException(mwe);
		} catch (ParseException e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public void createListOfPlayers(List<Player> players) throws DAOException {
		try {
			List<Document> documents = new ArrayList<>();
			for (Player player : players) {
				SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
				Document obj = Document.parse(player.toJSON());
				obj.put("bornDate", dateFormatter.parse(player.getBornDate()));
				documents.add(obj);
			}
			MongoClient mongoClient = MongoClients.create("mongodb://172.16.0.132:27018");
			ClusterDescription clusterDescription = mongoClient.getClusterDescription();
			if(clusterDescription.getType() == ClusterType.UNKNOWN) {
				throw new DAOException("Connection refused: you should connect with VPN");
			}
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("players");
			mongoCollection.insertMany(documents);
		} catch(MongoWriteException mwe) {
			throw new DAOException(mwe);
		} catch(MongoSocketOpenException msoe) {
			throw new DAOException(msoe);
		} catch(ParseException pe) {
			throw new DAOException(pe);
		}
	}

	@Override
	public void updatePlayer(String fullName, Player player) throws DAOException {
		MongoClient mongoClient = MongoClients.create("mongodb://172.16.0.132:27018");
		try {
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("players");
			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
			Document query = new Document();
			query.append("fullName", fullName);
			Document setData = new Document();
			setData.append("fullName", player.getFullName());
			setData.append("name", player.getName());
			setData.append("surname", player.getSurname());
			setData.append("marketValueString", player.getMarketValueString());
			setData.append("marketValue", player.getMarketValue());
			setData.append("bornDate", dateFormatter.parse(player.getBornDate()));
			setData.append("link", player.getLink());
			setData.append("nation", player.getNation());
			setData.append("role", player.getRole());
			setData.append("team", player.getTeam());
			mongoCollection.findOneAndUpdate(query, setData);
		} catch(MongoWriteException mwe) {
			throw new DAOException(mwe);
		} catch (ParseException e) {
			throw new DAOException(e);
		} finally {
			mongoClient.close();
		}
	}

	@Override
	public void deletePlayer(String playerName, Player player) throws DAOException {
		MongoClient mongoClient = MongoClients.create("mongodb://172.16.0.132:27018");
		try {
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("players");
			Document query = new Document();
			query.append("fullName", player.getFullName());
			mongoCollection.deleteOne(query);
		} catch(MongoWriteException mwe) {
			throw new DAOException(mwe);
		} finally {
			mongoClient.close();
		}
	}

	@Override
	public List<Player> retrievePlayers(String surname) throws DAOException {
		MongoClient mongoClient = MongoClients.create("mongodb://172.16.0.132:27018");
		MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
		Document query = new Document();
		query.append("surname", Pattern.compile(".*" + surname + ".*" , Pattern.CASE_INSENSITIVE));
		MongoCursor<Document> cursor = mongoDatabase.getCollection("players").find(query).iterator();
		List<Player> players = new ArrayList<Player>();
		try {
			while (cursor.hasNext()) { 
				Document document = cursor.next();
				Date bornDate = (Date) document.get("bornDate");
				String dateString = new SimpleDateFormat("dd/MM/yyyy").format(bornDate);
				document.put("bornDate", dateString);
				
				String json = document.toJson();
				System.out.println(json);
				Player player = Player.playerFromJson(json);
				players.add(player);
			}
		} finally {
			cursor.close(); 
		}
        mongoClient.close();
		return players;
	}

	@Override
	public List<Player> retrieveAllPlayers() throws DAOException {
		MongoClient mongoClient = MongoClients.create("mongodb://172.16.0.132:27018");
		MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
		MongoCursor<Document> cursor = mongoDatabase.getCollection("players").find().iterator();
		List<Player> players = new ArrayList<Player>();
		try {
			while (cursor.hasNext()) { 
				players.add(Player.playerFromJson(cursor.next().toJson()));
			}
		} finally {
			cursor.close(); 
		}
        mongoClient.close();
		return players;
	}

}
