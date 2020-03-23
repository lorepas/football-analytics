package stats.persistence.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
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
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.TextSearchOptions;

import stats.model.League;
import stats.model.Player;
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
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("teams");
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
			Bson filter = Filters.and(
		              Filters.eq("fullName",team.getFullName()),
		              Filters.eq("championshipCode", team.getChampionshipCode()));
			mongoCollection.deleteOne(filter);
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
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			cursor = mongoDatabase.getCollection("teams").find().iterator();
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
	
	@Override
	public List<Team> retrieveTeamsFromLeague(League league) throws DAOException {
		MongoClient mongoClient = null;
		MongoCursor<Document> cursor = null;
		List<Team> teams = new ArrayList<Team>();
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			Document query = new Document();
			query.append("championshipCode", Pattern.compile(".*" + league.getChampionshipCode() + ".*" , Pattern.CASE_INSENSITIVE));
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
	public double retrieveTeamTotalMarketValue(Team team) throws DAOException {
		MongoClient mongoClient = null;
		double res = 0.0;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCursor<Document> cursor = mongoDatabase.getCollection("players").aggregate(
					Arrays.asList(
							Aggregates.match(Filters.eq("team",team.getFullName())),
							Aggregates.group("$team", Accumulators.sum("marketValue", "$marketValue"))
					)).cursor();
			if(cursor.hasNext()) {
				Gson gson = new Gson();
				res = gson.fromJson(String.valueOf(cursor.next().getDouble("marketValue")), double.class);
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
	public long retriveNativePlayers(Team team) throws DAOException {
		MongoClient mongoClient = null;
		long res = 0;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCursor<Document> cursor = mongoDatabase.getCollection("teams").aggregate(
					Arrays.asList(Aggregates.match(eq("fullName", team.getFullName())), Aggregates.lookup("players", "fullName", "team", "join"), Aggregates.unwind("$join"),
							 Aggregates.group("$join.nation", Accumulators.sum("count", 1L)), 
							Aggregates.match(eq("_id", team.getNation())))
					).cursor();
			if(cursor.hasNext()) {
				res = (long) cursor.next().get("count");
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
	public Player retriveMostRepresentativePlayer(Team team) throws DAOException {
		MongoClient mongoClient = null;
		Player res=null;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCursor<Document> cursor = mongoDatabase.getCollection("teams").aggregate(
					Arrays.asList(Aggregates.match(eq("fullName", team.getFullName())), Aggregates.lookup("players", "fullName", "team", "join"), 
							Aggregates.unwind("$join"), Aggregates.unwind("$join.detailedPerformances"), Aggregates.group(eq("fullName", "$join.fullName"), 
									Accumulators.sum("totalPresences", "$join.detailedPerformances.presences")),
							Aggregates.sort(Sorts.descending("totalPresences")),Aggregates.limit(1))
					).cursor();
			if(cursor.hasNext()) {
				//res = Player.playerFromJson(cursor.next().toJson());
				Document document = (Document) cursor.next().get("_id");
				res = Player.playerFromJson(document.toJson());
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
	public double retrievePercentageOfWins(League league, Team team) throws DAOException {
		MongoClient mongoClient = null;
		double percentage = 0.0;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCursor<Document> cursor = mongoDatabase.getCollection("leagues").aggregate(
					Arrays.asList(new Document("$unwind", 
						    new Document("path", "$matches")), 
						    new Document("$project", 
						    new Document("fullname", 1L)
						            .append("matches", 1L)
						            .append("homeTeam", "$matches.nameHome")
						            .append("awayTeam", "$matches.nameAway")
						            .append("result", 
						    new Document("$cond", Arrays.asList(new Document("$gt", Arrays.asList("$matches.scoreHome", "$matches.scoreAway")), "1", 
						                    new Document("$cond", Arrays.asList(new Document("$eq", Arrays.asList("$matches.scoreHome", "$matches.scoreAway")), "X", "2")))))), 
						    new Document("$match", 
						    new Document("$or", Arrays.asList(new Document("$and", Arrays.asList(new Document("matches.nameHome", team.getName()))), 
						                new Document("$and", Arrays.asList(new Document("matches.nameAway", team.getName())))))), 
						    new Document("$group", 
						    new Document("_id", "$fullname")
						            .append("totalWins", 
						    new Document("$sum", 
						    new Document("$cond", Arrays.asList(new Document("$or", Arrays.asList(new Document("$and", Arrays.asList(new Document("$eq", Arrays.asList("$matches.nameHome", team.getName())), 
						                                        new Document("$eq", Arrays.asList("$result", "1")))), 
						                                new Document("$and", Arrays.asList(new Document("$eq", Arrays.asList("$matches.nameAway",team.getName())), 
						                                        new Document("$eq", Arrays.asList("$result", "2")))))), 1L, 0L))))
						            .append("totalDraws", 
						    new Document("$sum", 
						    new Document("$cond", Arrays.asList(new Document("$eq", Arrays.asList("$result", "X")), 1L, 0L))))
						            .append("totalLosts", 
						    new Document("$sum", 
						    new Document("$cond", Arrays.asList(new Document("$or", Arrays.asList(new Document("$and", Arrays.asList(new Document("$eq", Arrays.asList("$matches.nameHome", team.getName())), 
						                                        new Document("$eq", Arrays.asList("$result", "2")))), 
						                                new Document("$and", Arrays.asList(new Document("$eq", Arrays.asList("$matches.nameAway", team.getName())), 
						                                        new Document("$eq", Arrays.asList("$result", "1")))))), 1L, 0L))))
						            .append("total", 
						    new Document("$sum", 1L))))
					).cursor();
			while(cursor.hasNext()) {
				//res = Player.playerFromJson(cursor.next().toJson());
				Document document = (Document) cursor.next();
				System.out.println("Document: " + document.toJson());
				if(document.getString("_id").equals(league.getFullname())) {
					Long totalWins = (Long)document.get("totalWins");
					Long totalMatches = (Long)document.get("total");
					percentage = (Double.valueOf(totalWins)/totalMatches) * 100;
				}
			}
		} catch(MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
		return percentage;
	}

	@Override
	public double retrievePercentageOfDraws(League league, Team team) throws DAOException {
		MongoClient mongoClient = null;
		double percentage = 0.0;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCursor<Document> cursor = mongoDatabase.getCollection("leagues").aggregate(
					Arrays.asList(new Document("$unwind", 
						    new Document("path", "$matches")), 
						    new Document("$project", 
						    new Document("fullname", 1L)
						            .append("matches", 1L)
						            .append("homeTeam", "$matches.nameHome")
						            .append("awayTeam", "$matches.nameAway")
						            .append("result", 
						    new Document("$cond", Arrays.asList(new Document("$gt", Arrays.asList("$matches.scoreHome", "$matches.scoreAway")), "1", 
						                    new Document("$cond", Arrays.asList(new Document("$eq", Arrays.asList("$matches.scoreHome", "$matches.scoreAway")), "X", "2")))))), 
						    new Document("$match", 
						    new Document("$or", Arrays.asList(new Document("$and", Arrays.asList(new Document("matches.nameHome", team.getName()))), 
						                new Document("$and", Arrays.asList(new Document("matches.nameAway", team.getName())))))), 
						    new Document("$group", 
						    new Document("_id", "$fullname")
						            .append("totalWins", 
						    new Document("$sum", 
						    new Document("$cond", Arrays.asList(new Document("$or", Arrays.asList(new Document("$and", Arrays.asList(new Document("$eq", Arrays.asList("$matches.nameHome", team.getName())), 
						                                        new Document("$eq", Arrays.asList("$result", "1")))), 
						                                new Document("$and", Arrays.asList(new Document("$eq", Arrays.asList("$matches.nameAway",team.getName())), 
						                                        new Document("$eq", Arrays.asList("$result", "2")))))), 1L, 0L))))
						            .append("totalDraws", 
						    new Document("$sum", 
						    new Document("$cond", Arrays.asList(new Document("$eq", Arrays.asList("$result", "X")), 1L, 0L))))
						            .append("totalLosts", 
						    new Document("$sum", 
						    new Document("$cond", Arrays.asList(new Document("$or", Arrays.asList(new Document("$and", Arrays.asList(new Document("$eq", Arrays.asList("$matches.nameHome", team.getName())), 
						                                        new Document("$eq", Arrays.asList("$result", "2")))), 
						                                new Document("$and", Arrays.asList(new Document("$eq", Arrays.asList("$matches.nameAway", team.getName())), 
						                                        new Document("$eq", Arrays.asList("$result", "1")))))), 1L, 0L))))
						            .append("total", 
						    new Document("$sum", 1L))))
					).cursor();
			while(cursor.hasNext()) {
				//res = Player.playerFromJson(cursor.next().toJson());
				Document document = (Document) cursor.next();
				System.out.println("Document: " + document.toJson());
				if(document.getString("_id").equals(league.getFullname())) {
					Long totalDraws = (Long)document.get("totalDraws");
					Long totalMatches = (Long)document.get("total");
					percentage = (Double.valueOf(totalDraws)/totalMatches) * 100;
				}
			}
		} catch(MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
		return percentage;
	}

	@Override
	public double retrievePercentageOfDefeats(League league, Team team) throws DAOException {
		MongoClient mongoClient = null;
		double percentage = 0.0;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCursor<Document> cursor = mongoDatabase.getCollection("leagues").aggregate(
					Arrays.asList(new Document("$unwind", 
						    new Document("path", "$matches")), 
						    new Document("$project", 
						    new Document("fullname", 1L)
						            .append("matches", 1L)
						            .append("homeTeam", "$matches.nameHome")
						            .append("awayTeam", "$matches.nameAway")
						            .append("result", 
						    new Document("$cond", Arrays.asList(new Document("$gt", Arrays.asList("$matches.scoreHome", "$matches.scoreAway")), "1", 
						                    new Document("$cond", Arrays.asList(new Document("$eq", Arrays.asList("$matches.scoreHome", "$matches.scoreAway")), "X", "2")))))), 
						    new Document("$match", 
						    new Document("$or", Arrays.asList(new Document("$and", Arrays.asList(new Document("matches.nameHome", team.getName()))), 
						                new Document("$and", Arrays.asList(new Document("matches.nameAway", team.getName())))))), 
						    new Document("$group", 
						    new Document("_id", "$fullname")
						            .append("totalWins", 
						    new Document("$sum", 
						    new Document("$cond", Arrays.asList(new Document("$or", Arrays.asList(new Document("$and", Arrays.asList(new Document("$eq", Arrays.asList("$matches.nameHome", team.getName())), 
						                                        new Document("$eq", Arrays.asList("$result", "1")))), 
						                                new Document("$and", Arrays.asList(new Document("$eq", Arrays.asList("$matches.nameAway",team.getName())), 
						                                        new Document("$eq", Arrays.asList("$result", "2")))))), 1L, 0L))))
						            .append("totalDraws", 
						    new Document("$sum", 
						    new Document("$cond", Arrays.asList(new Document("$eq", Arrays.asList("$result", "X")), 1L, 0L))))
						            .append("totalLosts", 
						    new Document("$sum", 
						    new Document("$cond", Arrays.asList(new Document("$or", Arrays.asList(new Document("$and", Arrays.asList(new Document("$eq", Arrays.asList("$matches.nameHome", team.getName())), 
						                                        new Document("$eq", Arrays.asList("$result", "2")))), 
						                                new Document("$and", Arrays.asList(new Document("$eq", Arrays.asList("$matches.nameAway", team.getName())), 
						                                        new Document("$eq", Arrays.asList("$result", "1")))))), 1L, 0L))))
						            .append("total", 
						    new Document("$sum", 1L))))
					).cursor();
			while(cursor.hasNext()) {
				//res = Player.playerFromJson(cursor.next().toJson());
				Document document = (Document) cursor.next();
				System.out.println("Document: " + document.toJson());
				if(document.getString("_id").equals(league.getFullname())) {
					Long totalDefeats = (Long)document.get("totalLosts");
					Long totalMatches = (Long)document.get("total");
					percentage = (Double.valueOf(totalDefeats)/totalMatches) * 100;
				}
			}
		} catch(MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
		return percentage;
	}

	@Override
	public String retrieveShield(Team team) throws DAOException {
		MongoClient mongoClient = null;
		String shield = "";
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			String name = Utils.prettyName(team.getName());
			System.out.println("Team name to retrieve: " + name);
			Document filter = new Document();
			filter.append("name", Pattern.compile(".*" + name + ".*" , Pattern.CASE_INSENSITIVE));
			filter.append("championshipCode", team.getChampionshipCode());
			System.out.println("Filter: \n" + filter.toJson());
			Document projection = new Document();
			projection.append("shield", 1);
			MongoCursor<Document> cursor = mongoDatabase.getCollection("teams").find(filter).projection(projection).cursor();
			if(cursor.hasNext()) {
				//res = Player.playerFromJson(cursor.next().toJson());
				Document document = (Document) cursor.next();
				shield = document.getString("shield");
			}
		} catch(MongoException me) {
			throw new DAOException(me);
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
		return shield;
	}
	
	@Override
	public double retrieveAverageAgeFromTeam(Team team) throws DAOException {
		MongoClient mongoClient = null;
		double res = 0;
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			MongoCursor<Document> cursor = mongoDatabase.getCollection("teams").aggregate(
					Arrays.asList(new Document("$match", 
						    new Document("fullName", team.getFullName())), 
						    new Document("$lookup", 
						    new Document("from", "players")
						            .append("localField", "fullName")
						            .append("foreignField", "team")
						            .append("as", "join")), 
						    new Document("$unwind", 
						    new Document("path", "$join")), 
						    new Document("$project", 
						    new Document("diff", 
						    new Document("$subtract", Arrays.asList(new java.util.Date(), "$join.bornDate")))
						            .append("fullName", 1L)
						            .append("millis", 
						    new Document("$multiply", Arrays.asList(1000L, 60L, 60L, 24L, 365L)))), 
						    new Document("$group", 
						    new Document("_id", "$fullName")
						            .append("average", 
						    new Document("$avg", "$diff"))), 
						    new Document("$project", 
						    new Document("_id", 1L)
						            .append("averageAge", 
						    new Document("$let", 
						    new Document("vars", 
						    new Document("millis", 
						    new Document("$multiply", Arrays.asList(1000L, 60L, 60L, 24L, 365L))))
						                    .append("in", 
						    new Document("$divide", Arrays.asList("$average", "$$millis")))))))).cursor();
			if(cursor.hasNext()) {
				Gson gson = new Gson();
				Document document = cursor.next();
				res = gson.fromJson(String.valueOf(document.getDouble("averageAge")), double.class);
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
