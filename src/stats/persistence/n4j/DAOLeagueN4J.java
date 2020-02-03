package stats.persistence.n4j;

import java.util.AbstractMap.SimpleImmutableEntry;
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

public class DAOLeagueN4J implements IDAOLeagueGraph {

	@Override
	public boolean exists(League league) throws DAOException {
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		boolean value = false;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String existsQuery = "MATCH (league:League {fullName: $fullName})";
			existsQuery += "RETURN count(league)";
			Query existsLeague = new Query(existsQuery);
			Result rs = transaction.run(existsLeague);
			if(rs.single().get(0).asInt() == 1) {
				value = true;
			}
			transaction.commit();
		} catch(ClientException ce) {
			if(transaction != null) {
				if(transaction.isOpen()) {
					transaction.rollback();
				}
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
		return value;
	}

	@Override
	public void createLeague(League league) throws DAOException {
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			Query createLeagueNode = new Query("CREATE(:League {fullName: $fullName, name: $name, season: $season})", 
					parameters("fullName", league.getFullname(), "name", league.getName(), "season", league.getYear()));
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
		for (Match match : league.getMatches()) {
			App.getSharedInstance().getDaoMatchGraph().create(match);
			this.enrollTeam(league, match.getNameHome());
			this.enrollTeam(league, match.getNameAway());
		}
		
	}

	@Override
	public void delete(League league) throws DAOException {
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String deleteLeagueNode = "MATCH (league:League { fullName: $fullName })";
			deleteLeagueNode += "DETACH DELETE league";
			Query createLeagueNode = new Query(deleteLeagueNode, 
					parameters("fullName", league.getFullname()));
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
	public League retrieve(String fullName) throws DAOException {
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		League league = new League();
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String existsQuery = "MATCH (league:League {fullName: $fullName})";
			existsQuery += "RETURN league";
			Query existsLeague = new Query(existsQuery, parameters("fullName", fullName));
			Result rs = transaction.run(existsLeague);
			List<Pair<String, Value>> list = rs.single().fields();
			//TODO: Re-factor the following code
			for (Pair<String, Value> pair : list) {
				Value value = pair.value();
				Iterable<Value> values = value.asNode().values();
				league.setFullname(values.iterator().next().asString());
			}
			System.out.println("League: " + league.getFullname());
			transaction.commit();
		} catch(ClientException ce) {
			if(transaction != null) {
				if(transaction.isOpen()) {
					transaction.rollback();
				}
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
		return league;
	}

	@Override
	public List<League> retrieve(){
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void enrollTeam(League league, String teamFullName) {
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String query = "MATCH (team:Team {fullName: $teamFullName})";
			query += "MATCH (league:League {fullName: $leagueFullName})";
			query += "CREATE (team)-[:ENROLLED_IN {season: $season}]->(league)";
			Query createMatchRelationship = new Query(query, 
					parameters("teamFullName", teamFullName, "leagueFullName", league.getFullname(), 
							"season", league.getYear()));
			transaction.run(createMatchRelationship);
			transaction.commit();
		} catch(ClientException ce) {
			if(transaction != null) {
				transaction.rollback();
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
	public int numberOfEnrolledTeams(League league) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Team> enrolledTeams(League league) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<League> retrieveAllLeagues() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createListOfLeagues(List<League> leagues) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLeague(String fullName, League league) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<League> retrieveLeagues(String fullName) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}


}
