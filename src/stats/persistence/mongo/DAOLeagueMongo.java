package stats.persistence.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.BsonNull;
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
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;

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
	
	@Override
	public League retrieveLeague(String name) throws DAOException {
		MongoClient mongoClient = null;
		MongoCursor<Document> cursor = null;
		League league=null;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			Document query = new Document();
			query.append("fullname", Pattern.compile(".*" + name + ".*" , Pattern.CASE_INSENSITIVE));
			cursor = mongoDatabase.getCollection("leagues").find(query).iterator();
			if (cursor.hasNext()) { 
				league =League.leagueFromJson(cursor.next().toJson());
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
		return league;
	}
	
	public Team retrieveMostWinningHomeTeam(League league) throws DAOException {
		MongoClient mongoClient = null;
		Team res = new Team();
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCursor<Document> cursor = mongoDatabase.getCollection("leagues").aggregate(
					Arrays.asList(new Document("$unwind", 
						    new Document("path", "$matches")), 
						    new Document("$match", 
						    new Document("fullname", league.getFullname())), 
						    new Document("$project", 
						    new Document("matches", 1L)
						            .append("homeTeam", "$matches.nameHome")
						            .append("awayTeam", "$matches.nameAway")
						            .append("result", 
						    new Document("$cond", Arrays.asList(new Document("$gt", Arrays.asList("$matches.scoreHome", "$matches.scoreAway")), "1", 
						                    new Document("$cond", Arrays.asList(new Document("$eq", Arrays.asList("$matches.scoreHome", "$matches.scoreAway")), "X", "2")))))), 
						    new Document("$group", 
						    new Document("_id", "$matches.nameHome")
						            .append("homeWins", 
						    new Document("$sum", 
						    new Document("$cond", Arrays.asList(new Document("$eq", Arrays.asList("$result", "1")), 1L, 0L))))), 
						    new Document("$sort", 
						    new Document("homeWins", -1L)), 
						    new Document("$limit", 1L))
					).cursor();
			if(cursor.hasNext()) {
//				//res = Player.playerFromJson(cursor.next().toJson());
				Document document = (Document) cursor.next();
				String name = document.getString("_id");
				res.setName(name);
			}
		} catch(MongoException me) {
			throw new DAOException(me);
		} finally {
	
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
		return res;
	}

	@Override
	public Team retrieveMostWinningAwayTeam(League league) throws DAOException {
		MongoClient mongoClient = null;
		Team res = new Team();
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCursor<Document> cursor = mongoDatabase.getCollection("leagues").aggregate(
					Arrays.asList(new Document("$unwind", 
							new Document("path", "$matches")), 
						    new Document("$match", 
						    new Document("fullname", league.getFullname())), 
						    new Document("$project", 
						    new Document("matches", 1L)
						            .append("homeTeam", "$matches.nameHome")
						            .append("awayTeam", "$matches.nameAway")
						            .append("result", 
							new Document("$cond", Arrays.asList(new Document("$gt", Arrays.asList("$matches.scoreHome", "$matches.scoreAway")), "1", 
						                    new Document("$cond", Arrays.asList(new Document("$eq", Arrays.asList("$matches.scoreHome", "$matches.scoreAway")), "X", "2")))))), 
						    new Document("$group", 
						    new Document("_id", "$matches.nameAway")
						            .append("awayWins", 
						    new Document("$sum", 
						    new Document("$cond", Arrays.asList(new Document("$eq", Arrays.asList("$result", "2")), 1L, 0L))))), 
						    new Document("$sort", 
						    new Document("awayWins", -1L)), 
						    new Document("$limit", 1L))
					).cursor();
			if(cursor.hasNext()) {
//				//res = Player.playerFromJson(cursor.next().toJson());
				Document document = (Document) cursor.next();
				String name = document.getString("_id");
				res.setName(name);
			}
		} catch(MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
		return res;
	}

	@Override
	public Team retrieveMostWinningTeam(League league) throws DAOException {
		MongoClient mongoClient = null;
		Team res = new Team();
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCursor<Document> cursor = mongoDatabase.getCollection("leagues").aggregate(
					Arrays.asList(new Document("$unwind", 
						    new Document("path", "$matches")), 
						    new Document("$match", 
						    new Document("fullname", league.getFullname())), 
						    new Document("$project", 
						    new Document("nameHome", "$matches.nameHome")
						            .append("nameAway", "$matches.nameAway")
						            .append("scoreHome", "$matches.scoreHome")
						            .append("scoreAway", "$matches.scoreAway")
						            .append("matches", 1L)
						            .append("fullname", 1L)
						            .append("winner", 
						    new Document("$cond", Arrays.asList(new Document("$gt", Arrays.asList("$matches.scoreHome", "$matches.scoreAway")), "$matches.nameHome", 
						                    new Document("$cond", Arrays.asList(new Document("$lt", Arrays.asList("$matches.scoreHome", "$matches.scoreAway")), "$matches.nameAway", 
						                            new BsonNull())))))), 
						    new Document("$match", 
						    new Document("winner", 
						    new Document("$ne", 
						    new BsonNull()))), 
						    new Document("$group", 
						    new Document("_id", "$winner")
						            .append("wins", 
						    new Document("$sum", 1L))), 
						    new Document("$sort", 
						    new Document("wins", -1L)), 
						    new Document("$limit", 1L))
					).cursor();
			if(cursor.hasNext()) {
//				//res = Player.playerFromJson(cursor.next().toJson());
				Document document = (Document) cursor.next();
				String name = document.getString("_id");
				res.setName(name);
			}
		} catch(MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
		return res;
	}

	@Override
	public Team retrieveMostLosingTeam(League league) throws DAOException {
		MongoClient mongoClient = null;
		Team res = new Team();
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCursor<Document> cursor = mongoDatabase.getCollection("leagues").aggregate(
					Arrays.asList(new Document("$unwind", 
						    new Document("path", "$matches")), 
						    new Document("$match", 
						    new Document("fullname", league.getFullname())), 
						    new Document("$project", 
						    new Document("nameHome", "$matches.nameHome")
						            .append("nameAway", "$matches.nameAway")
						            .append("scoreHome", "$matches.scoreHome")
						            .append("scoreAway", "$matches.scoreAway")
						            .append("matches", 1L)
						            .append("fullname", 1L)
						            .append("loser", 
						    new Document("$cond", Arrays.asList(new Document("$gt", Arrays.asList("$matches.scoreHome", "$matches.scoreAway")), "$matches.nameAway", 
						                    new Document("$cond", Arrays.asList(new Document("$lt", Arrays.asList("$matches.scoreHome", "$matches.scoreAway")), "$matches.nameHome", 
						                            new BsonNull())))))), 
						    new Document("$match", 
						    new Document("loser", 
						    new Document("$ne", 
						    new BsonNull()))), 
						    new Document("$group", 
						    new Document("_id", "$loser")
						            .append("lost", 
						    new Document("$sum", 1L))), 
						    new Document("$sort", 
						    new Document("lost", -1L)), 
						    new Document("$limit", 1L))
					).cursor();
			if(cursor.hasNext()) {
//				//res = Player.playerFromJson(cursor.next().toJson());
				Document document = (Document) cursor.next();
				String name = document.getString("_id");
				res.setName(name);
			}
		} catch(MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
		return res;
	}
	
}
