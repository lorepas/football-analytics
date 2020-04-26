package stats.persistence.n4j;

import static org.neo4j.driver.Values.parameters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Query;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Value;
import org.neo4j.driver.exceptions.ClientException;
import org.neo4j.driver.util.Pair;

import stats.model.League;
import stats.model.Team;
import stats.persistence.DAOException;
import stats.persistence.IDAOTeam;
import stats.utility.Utils;

public class DAOTeamN4J implements IDAOTeamGraph {

	@Override
	public void createTeam(Team team) throws DAOException {
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String query = "CREATE(:Team {fullName: $fullName})";
			Query createTeamNode = new Query(query, 
					parameters("fullName", team.getFullName()));
			transaction.run(createTeamNode);
			transaction.commit();
		} catch(ClientException ce) {
			if(transaction != null) {
				if(transaction.isOpen()) {
					transaction.rollback();
				}
			}
		} finally {
			if(session != null) {
				session.close();
			}
			if(driver != null) {
				driver.close();
			}
		}
	}

	@Override
	public void deleteTeam(Team team) throws DAOException {
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String deleteLeagueNode = "MATCH (team:Team { fullName: $fullName })";
			deleteLeagueNode += "DETACH DELETE team";
			Query createLeagueNode = new Query(deleteLeagueNode, 
					parameters("fullName", team.getName()));
			transaction.run(createLeagueNode);
			transaction.commit();
		} catch(ClientException ce) {
			if(transaction != null) {
				transaction.rollback();
			}
			throw new DAOException(ce);
		} finally {
			if(session != null) {
				session.close();
			}
			if(driver != null) {
				driver.close();
			}
		}
	}

	@Override
	public List<Team> retrieveTeams(String fullName) throws DAOException {
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String existsQuery = "MATCH (t:Team) ";
			existsQuery+="WHERE toLower(t.fullName) CONTAINS toLower($fullName) RETURN t";
			Query existsLeague = new Query(existsQuery, parameters("fullName", fullName));
			Result rs = transaction.run(existsLeague);
			List<Record> list= rs.list();
			List<Team> teams = new ArrayList<>();
			//TODO: Re-factor the following code
			for (Record record : list) {
				Team team = new Team();
				Value v = record.get("t");
				Iterator<Value> values = v.asNode().values().iterator();				
				team.setFullName(values.next().asString());
				teams.add(team);
			}
			transaction.commit();
			return teams;
		} catch(ClientException ce) {
			if(transaction != null) {
				if(transaction.isOpen()) {
					transaction.rollback();
				}
			}
		} finally {
			if(session != null) {
				session.close();
			}
			if(driver != null) {
				driver.close();
			}
		}
		return new ArrayList<Team>();
	}
	
	@Override
	public Team retrieveTeam(String fullName) throws DAOException{
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		Team team = new Team();
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String existsQuery = "MATCH(team:Team {fullName: $fullName})";
			existsQuery += "RETURN team";
			Query existsLeague = new Query(existsQuery, parameters("fullName", fullName));
			Result rs = transaction.run(existsLeague);
			List<Pair<String, Value>> list = rs.single().fields();
			//TODO: Re-factor the following code
			for (Pair<String, Value> pair : list) {
				Value value = pair.value();
				Iterable<Value> values = value.asNode().values();
				team.setFullName(values.iterator().next().asString());
			}
			transaction.commit();
		} catch(ClientException ce) {
			if(transaction != null) {
				if(transaction.isOpen()) {
					transaction.rollback();
				}
			}
		} finally {
			if(session != null) {
				session.close();
			}
			if(driver != null) {
				driver.close();
			}
		}
		return team;
	}

	@Override
	public List<Team> retrieveAllTeams() throws DAOException {
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String existsQuery = "MATCH (t:Team)";
			existsQuery += "RETURN t";
			Query existsLeague = new Query(existsQuery);
			Result rs = transaction.run(existsLeague);
			List<Record> list= rs.list();
			List<Team> teams = new ArrayList<>();
			//TODO: Re-factor the following code
			for (Record record : list) {
				Team team = new Team();
				Value v = record.get("t");
				Iterator<Value> values = v.asNode().values().iterator();				
				team.setFullName(values.next().asString());
				teams.add(team);
			}
			transaction.commit();
			return teams;
		} catch(ClientException ce) {
			if(transaction != null) {
				if(transaction.isOpen()) {
					transaction.rollback();
				}
			}
		} finally {
			if(session != null) {
				session.close();
			}
			if(driver != null) {
				driver.close();
			}
		}
		return new ArrayList<Team>();
	}
	
	public Query createTeamQuery(Team team) {
		String queryHome = "CREATE(:Team {fullName: $fullName})";
		Query createHomeNodeQuery = new Query(queryHome, 
				parameters("fullName", team.getFullName()));
		return createHomeNodeQuery;
	}
	
	public Query teamExistenceQuery(Team team) {
		String existsQuery = "MATCH (team:Team {fullName: $fullName})";
		existsQuery += "RETURN count(team)";
		//Create home team
		Query exists = new Query(existsQuery, parameters("fullName", team.getFullName()));
		return exists;
	}
	
	public Query deleteTeamQuery(Team team) {
		String deleteTeamNode = "MATCH (team:Team { fullName: $fullName })";
		deleteTeamNode += "DETACH DELETE team";
		Query delTeamNode = new Query(deleteTeamNode, 
				parameters("fullName", team.getFullName()));
		return delTeamNode;
	}

}
