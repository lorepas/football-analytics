package stats.persistence.n4j;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Query;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Value;
import org.neo4j.driver.exceptions.ClientException;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.util.Pair;


import static org.neo4j.driver.Values.parameters;

import stats.App;
import stats.model.League;
import stats.model.Match;
import stats.model.Team;
import stats.persistence.DAOException;
import stats.utility.Utils;


public class DAOQuery implements IDAOQuery {

	public List<League> countLeague(Team team) throws DAOException {

		Team teamQuery = new Team();
		teamQuery = team;
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String query = "MATCH (t:Team {fullName: $teamFullName})-[rel:ENROLLED_IN]->(l:League)";
			query += "RETURN collect(l.fullName)";
			Query findAllLeague = new Query(query, 
					parameters("teamFullName", teamQuery.getFullName()));
			Result result=transaction.run(findAllLeague);
			List<Record> list=result.list();
			List<League> leagues = new ArrayList<>();
			for (Record record : list) {
				
				League l = new League();
				l.setFullname(record.get("collect(l.fullName)").get(0).asString());
				leagues.add(l);
			}
			transaction.commit();
			return leagues;
		} catch (ClientException ce) {
			System.out.println(ce.getLocalizedMessage());
			if (transaction != null) {
				// transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
			if (driver != null) {
				driver.close();
			}
		}
		return new ArrayList<League>();
	}
	/////////////////////////////////////
	////////////////////////////////////
	public List<Match> countWin(Team team) throws DAOException {

		Team teamQuery = new Team();
		teamQuery = team;
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String query = "MATCH(t:Team {fullName: $fullName})-[rel:PLAYED_WITH]->(l:Team)";
			query +=		"where rel.scoreHome>rel.scoreAway ";
			query += "RETURN t.fullName , rel.scoreHome, l.fullName , rel.scoreAway ";
			Query findHomeMatches = new Query(query, 
					parameters("fullName", teamQuery.getFullName()));
			Result result=transaction.run(findHomeMatches);
			List<Record> list=result.list();
			List<Match> matches = new ArrayList<>();
			int listSize1=list.size();
			for (Record record : list) {

				Match mt = new Match(); 
				mt.setNameHome(record.get("t.fullName").toString());
				mt.setNameAway(record.get("l.fullName").toString());
				mt.setScoreHome(Integer.parseInt(record.get("rel.scoreHome").toString()));
				mt.setScoreAway(Integer.parseInt(record.get("rel.scoreAway").toString()));
				matches.add(mt);

			}
			String query1 = "MATCH(l:Team)-[rel:PLAYED_WITH]->(t:Team {fullName: $fullName})";
			query1 +=		"where rel.scoreHome<rel.scoreAway ";
			query1 += "RETURN l.fullName , rel.scoreHome, t.fullName , rel.scoreAway";
			Query findAwayMatches = new Query(query1, 
					parameters("fullName", teamQuery.getFullName()));
			Result result2=transaction.run(findAwayMatches);
			List<Record> list2=result2.list();
			int count=listSize1+list2.size();

			for (Record record : list2) {

				Match mt = new Match(); 
				mt.setNameHome(record.get("t.fullName").toString());
				mt.setNameAway(record.get("l.fullName").toString());
				mt.setScoreHome(Integer.parseInt(record.get("rel.scoreHome").toString()));
				mt.setScoreAway(Integer.parseInt(record.get("rel.scoreAway").toString()));
				matches.add(mt);

			}

			//System.out.println("Number of matches won by "+teamQuery.getFullName()+":"+count);
			transaction.commit();
			return matches;
		} catch (ClientException ce) {
			System.out.println(ce.getLocalizedMessage());
			if (transaction != null) {
				// transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
			if (driver != null) {
				driver.close();
			}
		}
		return new ArrayList<Match>();
	}
	///////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////

	public List<Match> countLost(Team team) throws DAOException {

		Team teamQuery = new Team();
		teamQuery = team;
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String query = "MATCH(t:Team {fullName: $fullName})-[rel:PLAYED_WITH]->(l:Team)";
			query +=		"where rel.scoreHome<rel.scoreAway ";
			query += "RETURN t.fullName , rel.scoreHome, l.fullName , rel.scoreAway ";
			Query findHomeMatches = new Query(query, 
					parameters("fullName", teamQuery.getFullName()));
			Result result=transaction.run(findHomeMatches);
			List<Record> list=result.list();
			List<Match> matches = new ArrayList<>();
			int listSize1=list.size();
			for (Record record : list) {
				
				Match mt = new Match();

				mt.setNameHome(record.get("t.fullName").toString());
				mt.setNameAway(record.get("l.fullName").toString());
				mt.setScoreHome(Integer.parseInt(record.get("rel.scoreHome").toString()));
				mt.setScoreAway(Integer.parseInt(record.get("rel.scoreAway").toString()));
				matches.add(mt);
			}
			String query1 = "MATCH(l:Team)-[rel:PLAYED_WITH]->(t:Team {fullName: $fullName})";
			query1 +=		"where rel.scoreHome>rel.scoreAway ";
			query1 += "RETURN l.fullName , rel.scoreHome, t.fullName , rel.scoreAway";
			Query findAwayMatches = new Query(query1, 
					parameters("fullName", teamQuery.getFullName()));
			Result result2=transaction.run(findAwayMatches);
			List<Record> list2=result2.list();
			int count=listSize1+list2.size();

			for (Record record : list2) {
				
				Match mt = new Match(); 
				mt.setNameHome(record.get("l.fullName").toString());
				mt.setNameAway(record.get("t.fullName").toString());
				mt.setScoreHome(Integer.parseInt(record.get("rel.scoreHome").toString()));
				mt.setScoreAway(Integer.parseInt(record.get("rel.scoreAway").toString()));
				matches.add(mt);
			}
			//System.out.println("Number of matches lost by "+teamQuery.getFullName()+":"+count);
			transaction.commit();
			return matches;
		} catch (ClientException ce) {
			System.out.println(ce.getLocalizedMessage());
			if (transaction != null) {
				// transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
			if (driver != null) {
				driver.close();
			}
		}
		return new ArrayList<Match>();
	}
	////////////////////////////////////////////
	/////////////////////////////////////////////
	public List<Match> countDrawn(Team team) throws DAOException {

		Team teamQuery = new Team();
		teamQuery = team;
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String query = "MATCH(t:Team {fullName: $fullName})-[rel:PLAYED_WITH]->(l:Team)";
			query +=		"where rel.scoreHome=rel.scoreAway ";
			query += "RETURN t.fullName , rel.scoreHome, l.fullName , rel.scoreAway ";
			Query findHomeMatches = new Query(query, 
					parameters("fullName", teamQuery.getFullName()));
			Result result=transaction.run(findHomeMatches);
			List<Match> matches = new ArrayList<>();
			List<Record> list=result.list();
			int listSize1=list.size();
			for (Record record : list) {
				
				Match mt = new Match(); 
				mt.setNameHome(record.get("t.fullName").toString());
				mt.setNameAway(record.get("l.fullName").toString());
				mt.setScoreHome(Integer.parseInt(record.get("rel.scoreHome").toString()));
				mt.setScoreAway(Integer.parseInt(record.get("rel.scoreAway").toString()));
				matches.add(mt);
			}
			String query1 = "MATCH(l:Team)-[rel:PLAYED_WITH]->(t:Team {fullName: $fullName})";
			query1 +=		"where rel.scoreHome=rel.scoreAway ";
			query1 += "RETURN l.fullName , rel.scoreHome, t.fullName , rel.scoreAway";
			Query findAwayMatches = new Query(query1, 
					parameters("fullName", teamQuery.getFullName()));
			Result result2=transaction.run(findAwayMatches);
			List<Record> list2=result2.list();
			int count=listSize1+list2.size();

			for (Record record : list2) {
				
				Match mt = new Match(); 
				mt.setNameHome(record.get("l.fullName").toString());
				mt.setNameAway(record.get("t.fullName").toString());
				mt.setScoreHome(Integer.parseInt(record.get("rel.scoreHome").toString()));
				mt.setScoreAway(Integer.parseInt(record.get("rel.scoreAway").toString()));
				matches.add(mt);
			}
			//System.out.println("Number of matches drawn by "+teamQuery.getFullName()+":"+count);
			transaction.commit();
			return matches;
		} catch (ClientException ce) {
			System.out.println(ce.getLocalizedMessage());
			if (transaction != null) {
				// transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
			if (driver != null) {
				driver.close();
			}
		}
		return new ArrayList<Match>();
	}	
	public List<Team> countTeams(League league) throws DAOException {

		League selectedLeague= new League();
		selectedLeague = league;
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String query = "MATCH (t:Team)-[rel:ENROLLED_IN]->(l:League{fullName:$leagueFullName}) ";
			query += "RETURN t.fullName";
			Query findAllLeague = new Query(query, 
					parameters("leagueFullName", selectedLeague.getFullname()));
			Result result=transaction.run(findAllLeague);
			List<Record> list=result.list();
			List<Team> teams = new ArrayList<>();
			
			for (Record record : list) {
		
				Team tm = new Team();
				tm.setFullName(record.get("t.fullName").asString());
				teams.add(tm);
			}
			transaction.commit(); 
			return teams;
		} catch (ClientException ce) {
			System.out.println(ce.getLocalizedMessage());
			if (transaction != null) {
				// transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
			if (driver != null) {
				driver.close();
			}
		}
		return new ArrayList<Team>();
	}


}
