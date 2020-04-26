package stats.persistence.n4j;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Iterator;
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
			Query existsLeague = new Query(existsQuery, parameters("fullName", league.getFullname()));
			Result rs = transaction.run(existsLeague);
			Record record = rs.single();
			Value v = record.get(0);
			if(v.asInt() == 1) {
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
			for (Match match : league.getMatches()) {
				System.out.println("Match: " + match.toString());
				String existsQuery = "MATCH (team:Team {fullName: $fullName})";
				existsQuery += "RETURN count(team)";
				//Create home team
				Query existsHome = new Query(existsQuery, parameters("fullName", match.getNameHome()));
				Result rs = transaction.run(existsHome);
				Record record = rs.single();
				Value v = record.get(0);
				if(v.asInt() == 0) {
					System.out.println("Creation team: " + match.getNameHome());
					String queryHome = "CREATE(:Team {fullName: $fullName})";
					Query createTeamHomeNode = new Query(queryHome, 
							parameters("fullName", match.getNameHome()));
					transaction.run(createTeamHomeNode);
					String query = "MATCH (team:Team {fullName: $teamFullName})";
					query += "MATCH (league:League {fullName: $leagueFullName})";
					query += "CREATE (team)-[:ENROLLED_IN {season: $season}]->(league)";
					Query enrolledInRel = new Query(query, 
							parameters("teamFullName", match.getNameHome(), "leagueFullName", league.getFullname(), 
									"season", league.getYear()));
					transaction.run(enrolledInRel);
				}
				//Create away team
				Query existsAway = new Query(existsQuery, parameters("fullName", match.getNameAway()));
				Result rsAway = transaction.run(existsAway);
				Record recordAway = rsAway.single();
				v = recordAway.get(0);
				if(v.asInt() == 0) {
					System.out.println("Creation team: " + match.getNameAway());
					String queryAway = "CREATE(:Team {fullName: $fullName})";
					Query createTeamAwayNode = new Query(queryAway, 
							parameters("fullName", match.getNameAway()));
					transaction.run(createTeamAwayNode);
					String query = "MATCH (team:Team {fullName: $teamFullName})";
					query += "MATCH (league:League {fullName: $leagueFullName})";
					query += "CREATE (team)-[:ENROLLED_IN {season: $season}]->(league)";
					Query createMatchRelationship = new Query(query, 
							parameters("teamFullName", match.getNameAway(), "leagueFullName", league.getFullname(), 
									"season", league.getYear()));
					transaction.run(createMatchRelationship);
				}
				String query = "MATCH (homeTeam:Team {fullName: $homeTeam})";
				query += "MATCH (awayTeam:Team {fullName: $awayTeam})";
				query += "CREATE (homeTeam)-[:PLAYED_WITH {scoreHome: $scoreHome, scoreAway: $scoreAway}]->(awayTeam)";
				Query createMatchRelationship = new Query(query, 
						parameters("homeTeam", match.getNameHome(), "awayTeam", match.getNameAway(), 
								"scoreHome", match.getScoreHome(), "scoreAway", match.getScoreAway()));
				transaction.run(createMatchRelationship);
			}
			System.out.println("Match Size: " + league.getMatches().size());
			System.out.println("Creation of " +  league.getFullname() + " OK");
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
			String deleteLeagueNodeStr = "MATCH (league:League { fullName: $fullName })";
			deleteLeagueNodeStr += "DETACH DELETE league";
			Query delLeagueNode = new Query(deleteLeagueNodeStr, 
					parameters("fullName", league.getFullname()));
			transaction.run(delLeagueNode);
			for (Match match : league.getMatches()) {
				String existsQuery = "MATCH (team:Team {fullName: $fullName})";
				existsQuery += "RETURN count(team)";
				Query existsHome = new Query(existsQuery, parameters("fullName", match.getNameHome()));
				Result rs = transaction.run(existsHome);
				Record record = rs.single();
				Value v = record.get(0);
				if(v.asInt() > 0) {
					String deleteTeamNode = "MATCH (team:Team { fullName: $fullName })";
					deleteTeamNode += "DETACH DELETE team";
					Query createTeamNode = new Query(deleteTeamNode, 
							parameters("fullName", match.getNameHome()));
					transaction.run(createTeamNode);
					System.out.println("Deleted " + match.getNameHome());
				}
				Query existsAway = new Query(existsQuery, parameters("fullName", match.getNameAway()));
				Result rsAw = transaction.run(existsAway);
				Record recordAw = rsAw.single();
				Value vAw = recordAw.get(0);
				if(vAw.asInt() > 0 ) {
					String deleteTeamNode = "MATCH (team:Team { fullName: $fullName })";
					deleteTeamNode += "DETACH DELETE team";
					Query createTeamNode = new Query(deleteTeamNode, 
							parameters("fullName", match.getNameAway()));
					transaction.run(createTeamNode);
					System.out.println("Deleted " + match.getNameAway());
				}
			}
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
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		League league = new League();
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String existsQuery = "MATCH (league:League)";
			existsQuery += "RETURN league";
			Query existsLeague = new Query(existsQuery);
			Result rs = transaction.run(existsLeague);
			List<Record> list= rs.list();
			List<League> leagues = new ArrayList<>();
			//TODO: Re-factor the following code
			for (Record record : list) {
				League l = new League();
				Value v = record.get("league");
				Iterator<Value> values = v.asNode().values().iterator();
				l.setName(values.next().asString());
				l.setFullname(values.next().asString());
				l.setYear(values.next().asString());
				leagues.add(l);
			}
			transaction.commit();
			return leagues;
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
		return new ArrayList<League>();
	}

	@Override
	public void createListOfLeagues(List<League> leagues) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLeague(String fullName, League league) throws DAOException {
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			
			// Update league fields
			String updateLeagueNodeQueryStr = "MATCH (league:League { fullName: $fullName })";
			updateLeagueNodeQueryStr += "SET league.name = $name, league.season = $season";
			Query updateLeagueNodeQuery = new Query(updateLeagueNodeQueryStr, 
					parameters("fullName", fullName, "name", league.getName(), 
							"season", league.getYear()));
			Result rs = transaction.run(updateLeagueNodeQuery);
			System.out.println("Result: " + rs.consume());
			
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
	}

	@Override
	public List<League> retrieveLeagues(String fullName) throws DAOException {
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String existsQuery = "MATCH (league:League) ";
			existsQuery+="WHERE toLower(league.fullName) CONTAINS toLower($fullName) RETURN league";
			Query existsLeague = new Query(existsQuery, parameters("fullName", fullName));
			Result rs = transaction.run(existsLeague);
			List<Record> list= rs.list();
			List<League> leagues = new ArrayList<>();
			//TODO: Re-factor the following code
			for (Record record : list) {
				League l = new League();
				Value v = record.get("league");
				Iterator<Value> values = v.asNode().values().iterator();
				l.setName(values.next().asString());
				l.setFullname(values.next().asString());
				l.setYear(values.next().asString());
				leagues.add(l);
			}
			transaction.commit();
			return leagues;
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
		return new ArrayList<League>();
	}

	@Override
	public Team retrieveMostWinningHomeTeam(League league) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team retrieveMostWinningAwayTeam(League league) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team retrieveMostWinningTeam(League league) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team retrieveMostLosingTeam(League league) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public League retrieveLeague(String name) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}


}
