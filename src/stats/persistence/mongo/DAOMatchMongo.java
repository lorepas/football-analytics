package stats.persistence.mongo;

import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import stats.model.League;
import stats.model.Match;
import stats.model.Player;
import stats.persistence.DAOException;
import stats.persistence.IDAOMatch;
import stats.utility.Utils;

public class DAOMatchMongo implements IDAOMatch{

	@Override
	public boolean exists(Match match) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("leagues");
			Document filter = new Document();
			//TODO: Insert a filter
			//filter.append("fullName", match.getFullName());
			MongoCursor<Document> cursor = mongoCollection.find(filter).iterator();
			if(cursor.hasNext()) {
				return true;
			} else {
				return false;
			}
		} catch (MongoException mwe) {
			throw new DAOException(mwe);
		} finally {
			mongoClient.close();
		}
	}

	@Override
	public void create(Match match) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(List<Match> matches) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Match match) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Match match) throws DAOException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Player> retrieve(League league) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Player> retrieve() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
