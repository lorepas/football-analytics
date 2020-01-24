package stats.persistence;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import stats.utility.Utils;

public class DAOUserMongo implements IDAOUser{
	
	public boolean Login(String username, String pwd) throws DAOException {
		MongoClient mongoClient = Utils.getMongoClient();
		try {
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");
			Document filter = new Document();
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

}
