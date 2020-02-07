package stats.persistence.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

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
	
	@Override
	public List<Match> retrieveAllMatches() throws DAOException {
		MongoClient mongoClient = null;
		MongoCursor<Document> cursor = null;
		List<Match> matches = new ArrayList<Match>();
		List<League> leagues = new ArrayList<League>();
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			cursor = mongoDatabase.getCollection("leagues").find().iterator();
			while (cursor.hasNext()) {
				leagues.add(League.leagueFromJson(cursor.next().toJson()));
			}
			for (League league : leagues) {
				matches.addAll(league.getMatches());
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
		return matches;
	}
	
	@Override
	public List<Match> retrieveMatchesbyRound(int round, League league) throws DAOException {
		MongoClient mongoClient = null;
		MongoCursor<Document> cursor = null;
		List<Match> matches = new ArrayList<Match>();
		
		try {
			mongoClient = Utils.getMongoClient();
			MongoDatabase mongoDatabase = mongoClient.getDatabase("footballDB");
			//String roundStr = "Round " + round;
			//System.out.println("Round: " + roundStr);
			cursor = mongoDatabase.getCollection("leagues").aggregate(
					Arrays.asList(new Document("$unwind", 
							new Document("path", "$matches")), 
						    new Document("$match", 
						    new Document("matches.round", "Round " + round)
						            .append("name", league.getName())), 
						    new Document("$project", 
						    new Document("yellowCardsHome", "$matches.yellowCardsHome")
						            .append("yellowCardsAway", "$matches.yellowCardsAway")
						            .append("round", "$matches.round")
						            .append("nameHome", "$matches.nameHome")
						            .append("nameAway", "$matches.nameAway")
						            .append("scoreHome", "$matches.scoreHome")
						            .append("scoreAway", "$matches.scoreAway")
						            .append("possesionBallHome", "$matches.possesionBallHome")
						            .append("possesionBallAway", "$matches.possesionBallAway")
						            .append("date", "$matches.date")
						            .append("time", "$matches.time")
						            .append("shotsOnGoalHome", "$matches.shotsOnGoalHome")
						            .append("shotsOnGoalAway", "$matches.shotsOnGoalAway")
						            .append("shotsOffGoalHome", "$matches.shotsOffGoalHome")
						            .append("shotsOffGoalAway", "$matches.shotsOffGoalAway")
						            .append("cornerKiksHome", "$matches.cornerKiksHome")
						            .append("cornerKiksAway", "$matches.cornerKiksAway")
						            .append("offsideHome", "$matches.offsideHome")
						            .append("offsideAway", "$matches.offsideAway")
						            .append("foulsHome", "$matches.foulsHome")
						            .append("foulsAway", "$matches.foulsAway")
						            .append("redCardsHome", "$matches.redCardsHome")
						            .append("redCardsAway", "$matches.redCardsAway")
						            .append("completedPassessHome", "$matches.completedPassessHome")
						            .append("completedPassesAway", "$matches.completedPassesAway")
						            .append("goalAttemptsHome", "$matches.goalAttemptsHome")
						            .append("goalAttemptsAway", "$matches.goalAttemptsAway")
						            .append("freeKicksHome", "$matches.freeKicksHome")
						            .append("freeKicksAway", "$matches.freeKicksAway")
						            .append("goalkeeperSavedH", "$matches.goalkeeperSavedH")
						            .append("goalkeeperSavedA", "$matches.goalkeeperSavedA")))).cursor();
			while (cursor.hasNext()) {
				Match match = Match.matchFromJson(cursor.next().toJson());
				matches.add(match);
				
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
		return matches;
	}
	

}
