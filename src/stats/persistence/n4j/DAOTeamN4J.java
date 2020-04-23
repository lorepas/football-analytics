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

import stats.model.League;
import stats.model.Player;
import stats.model.Team;
import stats.persistence.DAOException;
import stats.persistence.IDAOTeam;
import stats.utility.Utils;

public class DAOTeamN4J implements IDAOTeamGraph {

	@Override
	public boolean exists(Team team) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

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
	public void createListOfTeams(List<Team> teams) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTeam(String fullName, Team player) throws DAOException {
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

	@Override
	public int numberOfWonMatches(Team team) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int numberOfDrawnMatches(Team team) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int numberOfLostMatches(Team team) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Team> retrieveTeamsFromLeague(League name) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double retrieveTeamTotalMarketValue(Team team) throws DAOException {
		return 0;
		// TODO Auto-generated method stub
		
	}

	@Override
	public long retriveNativePlayers(Team team) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Player retriveMostRepresentativePlayer(Team team) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double retrievePercentageOfWins(League league, Team team) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double retrievePercentageOfDraws(League league, Team team) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double retrievePercentageOfDefeats(League league, Team team) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String retrieveShield(Team team) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double retrieveAverageAgeFromTeam(Team team) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
