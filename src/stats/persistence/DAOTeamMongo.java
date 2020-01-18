package stats.persistence;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import stats.model.Team;

public class DAOTeamMongo implements IDAOTeam {

	@Override
	public void createTeam(Team team) throws DAOException {
		try {
			Document obj = Document.parse(team.toJSON());
			MongoClient mongoClient = MongoClients.create("mongodb://172.16.0.132:27018");
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
			MongoClient mongoClient = MongoClients.create("mongodb://172.16.0.132:27018");
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("players");
			mongoCollection.insertMany(documents);
		} catch(MongoWriteException mwe) {
			throw new DAOException(mwe);
		}
		
	}

	@Override
	public void updateTeam(Team player) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTeam(Team player) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Team> retrieveTeams(String surname) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Team> retrieveAllTeams() throws DAOException {
		MongoClient mongoClient = MongoClients.create("mongodb://172.16.0.132:27018");
		MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
		MongoCursor<Document> cursor = mongoDatabase.getCollection("teams").find().iterator();
		List<Team> teams = new ArrayList<Team>();
		try {
			while (cursor.hasNext()) { 
				teams.add(Team.playerFromJson(cursor.next().toJson()));
			}
		} finally {
			cursor.close(); 
		}
        mongoClient.close();
		return teams;
	}

}
