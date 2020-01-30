package stats.persistence;

import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import stats.model.League;
import stats.model.Player;
import stats.utility.Utils;

public class DAOLeagueMongo implements IDAOLeague{

	@Override
	public boolean exists(League league) throws DAOException {
		MongoClient mongoClient = Utils.getMongoClient();
		try {
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
		} finally {
			mongoClient.close();
		}
	}

	@Override
	public void createMatch(League league) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createListOfLeagues(List<League> leagues) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLeague(League league) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteLeague(League league) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Player> retrieveLeagues() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
