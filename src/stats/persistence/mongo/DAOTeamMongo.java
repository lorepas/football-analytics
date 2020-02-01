package stats.persistence.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoClientImpl;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

import stats.model.Team;
import stats.persistence.DAOException;
import stats.persistence.IDAOTeam;
import stats.utility.Utils;

public class DAOTeamMongo implements IDAOTeam {
	
	@Override
	public boolean exists(Team team) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
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
		} catch (MongoException mwe) {
			throw new DAOException(mwe);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
	}

	@Override
	public void createTeam(Team team) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			Document obj = Document.parse(team.toJSON());
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("teams");
			mongoCollection.insertOne(obj);
		} catch(MongoException mwe) {
			throw new DAOException(mwe);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
	}
	
	@Override
	public void createListOfTeams(List<Team> teams) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			List<Document> documents = new ArrayList<>();
			for (Team team : teams) {
				Document obj = Document.parse(team.toJSON());
				documents.add(obj);
			}
			
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("teams");
			mongoCollection.insertMany(documents);
		} catch(MongoException mwe) {
			throw new DAOException(mwe);
		} finally {
			mongoClient.close();
		}
		
	}

	@Override
	public void updateTeam(String fullName, Team team) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("team");
			Bson query = eq("fullName", team.getFullName());
			Document setData = Document.parse(team.toJSON());
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
	public void deleteTeam(Team team) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("teams");
			Document query = new Document();
			query.append("fullName", team.getFullName());
			mongoCollection.deleteOne(query);
		} catch(MongoException mwe) {
			throw new DAOException(mwe);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
	}

	@Override
	public List<Team> retrieveTeams(String name) throws DAOException {
		MongoClient mongoClient = null;
		MongoCursor<Document> cursor = null;
		List<Team> teams = new ArrayList<Team>();
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			Document query = new Document();
			query.append("name", Pattern.compile(".*" + name + ".*" , Pattern.CASE_INSENSITIVE));
			cursor = mongoDatabase.getCollection("teams").find(query).iterator();
			while (cursor.hasNext()) { 
				teams.add(Team.teamFromJson(cursor.next().toJson()));
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
		return teams;
	}

	@Override
	public List<Team> retrieveAllTeams() throws DAOException {
		MongoClient mongoClient = null;
		MongoCursor<Document> cursor = null;
		List<Team> teams = new ArrayList<Team>();
		try {
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			cursor = mongoDatabase.getCollection("teams").find().iterator();
			mongoClient = Utils.getMongoClient();
			while (cursor.hasNext()) { 
				teams.add(Team.teamFromJson(cursor.next().toJson()));
			}
		} finally {
			if(cursor != null) {
				cursor.close(); 
			}
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
        
		return teams;
	}
	
	public void getTeamTotalMarketValue(Team team) throws DAOException {
		MongoClient mongoClient = null;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCursor<Document> cursor = mongoDatabase.getCollection("players").aggregate(
					Arrays.asList(
							Aggregates.match(Filters.eq("team",team.getFullName())),
							Aggregates.group("$team", Accumulators.sum("marketValue", "$marketValue"))
					)).iterator();
		} catch(MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
	}

}
