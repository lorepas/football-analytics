package stats.persistence;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import stats.model.User;
import stats.utility.Utils;

public class DAOUserMongo implements IDAOUser{
	
	public boolean login(User user) throws DAOException {
		MongoClient mongoClient = Utils.getMongoClient();
		try {
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");
			Document filter = new Document();
			String username = user.getUsername();
			String pwd = user.getPwd();
			filter.append("username", username);
			filter.append("pwd", pwd);
			MongoCursor<Document> cursor = mongoCollection.find(filter).iterator();
			if(cursor.hasNext()) {
				return true;
			} else {
				return false;
			}
			
		} catch (MongoWriteException mwe){
			throw new DAOException(mwe);
		} finally {
			mongoClient.close();
		}
	}

	@Override
	public void putUser(User user) throws DAOException {
		// TODO Auto-generated method stub
		
	}

}
