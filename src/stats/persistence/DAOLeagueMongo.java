package stats.persistence;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import stats.model.League;
import stats.utility.Utils;

public class DAOLeagueMongo implements IDAOLeague{

	@Override
	public boolean exists(League league) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("league");
			Document filter = new Document();
			filter.append("fullName", league.getFullName());
			MongoCursor<Document> cursor = mongoCollection.find(filter).iterator();
			if(cursor.hasNext()) {
				return true;
			} else {
				return false;
			}
		} catch (MongoWriteException mwe) {
			throw new DAOException(mwe);
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
//			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
//			if(player.getBornDate() != null) {
//				obj.put("bornDate", dateFormatter.parse(player.getBornDate()));
//			}
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("leagues");
			mongoCollection.insertOne(obj);
		} catch(MongoWriteException mwe) {
			throw new DAOException(mwe);
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
			List<Document> documents = new ArrayList<>();
			for (League league : leagues) {
//				SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
				Document obj = Document.parse(league.toJSON());
//				obj.put("bornDate", dateFormatter.parse(player.getBornDate()));
				documents.add(obj);
			}
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("leagues");
			mongoCollection.insertMany(documents);
		} catch(MongoWriteException mwe) {
			throw new DAOException(mwe);
		} catch(MongoSocketOpenException msoe) {
			throw new DAOException(msoe);
		}  catch (MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
	}

	@Override
	public void updateLeague(League league) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("leagues");
//			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
			Bson query = eq("fullName", league.getFullName());
			Document setData = Document.parse(league.toJSON());
//			if(player.getBornDate() != null) {
//				setData.put("bornDate", dateFormatter.parse(player.getBornDate()));
//			}
			Document updateDocument = new Document("$set", setData);
			System.out.println("Update document: " + updateDocument);
			mongoCollection.updateOne(query, updateDocument);
			System.out.println("Query: " + query);
		} catch(MongoWriteException mwe) {
			throw new DAOException(mwe);
		} catch (MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
	}

	@Override
	public void deleteLeague(League league) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("leagues");
			Document query = new Document();
			query.append("fullName", league.getFullName());
			mongoCollection.deleteOne(query);
		} catch(MongoWriteException mwe) {
			throw new DAOException(mwe);
		} catch (MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
	}

	@Override
	public List<League> retrieveLeagues() throws DAOException {
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
			return leagues;
		}
	}

	
}
