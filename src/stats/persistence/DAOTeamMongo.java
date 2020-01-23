package stats.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoWriteException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoClientImpl;

import stats.model.Team;
import stats.utility.Utils;

public class DAOTeamMongo implements IDAOTeam {
	
	@Override
	public boolean exists(Team team) throws DAOException {
		MongoClient mongoClient = Utils.getMongoClient();
		try {
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("teams");
			Document filter = new Document();
			filter.append("fullName", team.getFullName());
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
	public void createTeam(Team team) throws DAOException {
		try {
			Document obj = Document.parse(team.toJSON());
			MongoClient mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("teams");
			mongoCollection.insertOne(obj);
		} catch(MongoWriteException mwe) {
			throw new DAOException(mwe);
		}
	}
	
	@Override
	public void createListOfTeams(List<Team> teams) throws DAOException {
		try {
			List<Document> documents = new ArrayList<>();
			for (Team team : teams) {
				Document obj = Document.parse(team.toJSON());
				documents.add(obj);
			}
			MongoClient mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("teams");
			mongoCollection.insertMany(documents);
		} catch(MongoWriteException mwe) {
			throw new DAOException(mwe);
		}
		
	}

	@Override
	public void updateTeam(String fullName, Team team) throws DAOException {
		MongoClient mongoClient = Utils.getMongoClient();
		try {
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("teams");
			Document query = new Document();
			query.append("fullName", fullName);
			Document setData = new Document();
			setData.append("fullName", team.getFullName());
			setData.append("name", team.getName());
			setData.append("rosterSize", team.getRosterSize());
			setData.append("shield", team.getShield());
			mongoCollection.findOneAndUpdate(query, setData);
		} catch(MongoWriteException mwe) {
			throw new DAOException(mwe);
		} finally {
			mongoClient.close();
		}
		
	}

	@Override
	public void deleteTeam(Team team) throws DAOException {
		MongoClient mongoClient = Utils.getMongoClient();
		try {
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("teams");
			Document query = new Document();
			query.append("fullName", team.getFullName());
			mongoCollection.deleteOne(query);
		} catch(MongoWriteException mwe) {
			throw new DAOException(mwe);
		} finally {
			mongoClient.close();
		}
	}

	@Override
	public List<Team> retrieveTeams(String name) throws DAOException {
		MongoClient mongoClient = Utils.getMongoClient();
		MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
		Document query = new Document();
		query.append("name", Pattern.compile(".*" + name + ".*" , Pattern.CASE_INSENSITIVE));
		MongoCursor<Document> cursor = mongoDatabase.getCollection("teams").find(query).iterator();
		List<Team> teams = new ArrayList<Team>();
		try {
			while (cursor.hasNext()) { 
				teams.add(Team.teamFromJson(cursor.next().toJson()));
			}
		} finally {
			cursor.close(); 
		}
        mongoClient.close();
		return teams;
	}

	@Override
	public List<Team> retrieveAllTeams() throws DAOException {
		MongoClient mongoClient = Utils.getMongoClient();
		MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
		MongoCursor<Document> cursor = mongoDatabase.getCollection("teams").find().iterator();
		List<Team> teams = new ArrayList<Team>();
		try {
			while (cursor.hasNext()) { 
				teams.add(Team.teamFromJson(cursor.next().toJson()));
			}
		} finally {
			cursor.close(); 
		}
        mongoClient.close();
		return teams;
	}

}
