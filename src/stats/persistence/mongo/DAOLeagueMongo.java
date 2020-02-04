package stats.persistence.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import stats.model.League;
import stats.model.Player;
import stats.model.Team;
import stats.persistence.DAOException;
import stats.persistence.IDAOLeague;
import stats.utility.Utils;

public class DAOLeagueMongo implements IDAOLeague{

	@Override
	public boolean exists(League league) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("leagues");
			Document filter = new Document();
			filter.append("fullname", league.getFullname());
			MongoCursor<Document> cursor = mongoCollection.find(filter).iterator();
			if(cursor.hasNext()) {
				return true;
			} else {
				return false;
			}
		} catch (MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
			
		}
	}

	@Override
	public void createLeague(League league) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			Document obj = Document.parse(league.toJSON());
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("leagues");
			mongoCollection.insertOne(obj);
		} catch (MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
		
	}

	@Override
	public void createListOfLeagues(List<League> leagues) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			List<Document> documents = new ArrayList<>();
			for (League league : leagues) {
				Document obj = Document.parse(league.toJSON());
				documents.add(obj);
			}
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("leagues");
			mongoCollection.insertMany(documents);
		} catch (MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
	}

	@Override
	public void updateLeague(String fullName, League league) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("leagues");
			Bson query = eq("fullname", league.getFullname());
			Document setData = Document.parse(league.toJSON());
			Document updateDocument = new Document("$set", setData);
			System.out.println("Update document: " + updateDocument);
			mongoCollection.updateOne(query, updateDocument);
			System.out.println("Query: " + query);
		} catch(MongoException mwe) {
			throw new DAOException(mwe);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
		
	}

	@Override
	public void delete(League league) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("leagues");
			Document query = new Document();
			query.append("fullname", league.getFullname());
			mongoCollection.deleteOne(query);
		} catch (MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
	}

	@Override
	public List<League> retrieveAllLeagues() throws DAOException {
		MongoClient mongoClient = null;
		MongoCursor<Document> cursor = null;
		List<League> leagues = new ArrayList<League>();
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			cursor = mongoDatabase.getCollection("leagues").find().iterator();
			while (cursor.hasNext()) { 
				leagues.add(League.leagueFromJson(cursor.next().toJson()));
			}
		} catch(MongoException me){
			throw new DAOException(me);
		} finally {
			if(cursor != null) {
				cursor.close();
			}
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
		return leagues;
	}

	@Override
	public List<League> retrieveLeagues(String name) throws DAOException {
		MongoClient mongoClient = null;
		MongoCursor<Document> cursor = null;
		List<League> leagues = new ArrayList<League>();
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			Document query = new Document();
			query.append("name", Pattern.compile(".*" + name + ".*" , Pattern.CASE_INSENSITIVE));
			cursor = mongoDatabase.getCollection("leagues").find(query).iterator();
			while (cursor.hasNext()) { 
				leagues.add(League.leagueFromJson(cursor.next().toJson()));
			}
		} catch(MongoException me) {
			throw new DAOException(me);
		} finally {
			if(cursor != null) {
				cursor.close(); 
			}
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
		return leagues;
	}
	
}
