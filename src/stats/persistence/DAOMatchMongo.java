package stats.persistence;

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
import stats.utility.Utils;

public class DAOMatchMongo implements IDAOMatch{

	@Override
	public boolean exists(Match match) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("league");
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
	public void createMatch(Match match) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createListOfMatches(List<Match> matches) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMatch(Match match) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMatch(Match match) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Player> retrieveMatches(League league) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Player> retrieveMatches() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
