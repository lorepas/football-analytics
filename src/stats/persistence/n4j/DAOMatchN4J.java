package stats.persistence.n4j;

import static org.neo4j.driver.Values.parameters;

import java.util.List;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Query;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.exceptions.ClientException;

import stats.App;
import stats.model.League;
import stats.model.Match;
import stats.model.Player;
import stats.model.Team;
import stats.persistence.DAOException;
import stats.persistence.IDAOMatch;
import stats.utility.Utils;

public class DAOMatchN4J implements IDAOMatchGraph {

	@Override
	public boolean exists(Match match) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void create(Match match) throws DAOException {
		Team homeTeam = new Team();
		Team awayTeam = new Team();
		homeTeam.setFullName(match.getNameHome());
		awayTeam.setFullName(match.getNameAway());
		App.getSharedInstance().getDaoTeamGraph().createTeam(homeTeam);
		App.getSharedInstance().getDaoTeamGraph().createTeam(awayTeam);
		Driver driver = null;
		Session session = null;
		Transaction transaction = null;
		try {
			driver = Utils.getNEO4JDriver();
			session = driver.session();
			transaction = session.beginTransaction();
			String query = "MATCH (homeTeam:Team {fullName: $homeTeam})";
			query += "MATCH (awayTeam:Team {fullName: $awayTeam})";
			query += "CREATE (homeTeam)-[:PLAYED_WITH {scoreHome: $scoreHome, scoreAway: $scoreAway}]->(awayTeam)";
			Query createMatchRelationship = new Query(query, 
					parameters("homeTeam", homeTeam.getFullName(), "awayTeam", awayTeam.getFullName(), 
							"scoreHome", match.getScoreHome(), "scoreAway", match.getScoreAway()));
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
	public void create(List<Match> matches) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Match match) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Match match) throws DAOException {
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

}
