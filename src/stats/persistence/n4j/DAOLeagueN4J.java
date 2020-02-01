package stats.persistence.n4j;

import java.util.List;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Query;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Value;
import org.neo4j.driver.exceptions.ClientException;
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
	public void create(League league) throws DAOException {
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			Query createLeagueNode = new Query("CREATE(:League {fullName: $fullName, name: $name, season: $season})", 
					parameters("fullName", league.getFullName(), "name", league.getName(), "season", league.getYear()));
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
					parameters("fullName", league.getFullName()));
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
	public void update(League league) throws DAOException {
		// TODO Auto-generated method stub
		
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
			Record record = rs.single();
//			Value value = record.fields().
//			league.setFullName(rs.single().get(0).asString());
//			league.setYear(rs.single().get(1).asString());
			System.out.println("League: " + league.getFullName());
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
	public List<League> retrieve() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(List<League> leagues) throws DAOException {
		// TODO Auto-generated method stub
		
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
					parameters("teamFullName", teamFullName, "leagueFullName", league.getFullName(), 
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


}
