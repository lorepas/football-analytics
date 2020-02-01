package stats.persistence.n4j;

import java.util.List;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Query;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.exceptions.ClientException;
import static org.neo4j.driver.Values.parameters;

import stats.App;
import stats.model.League;
import stats.model.Match;
import stats.persistence.DAOException;
import stats.utility.Utils;

public class DAOLeagueN4J implements IDAOLeagueGraph {

	@Override
	public boolean exists(League league) throws DAOException {
		// TODO Auto-generated method stub
		return false;
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
			//Collega i due team alla league alla league
		}
		
	}

	@Override
	public void delete(League league) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(League league) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public League retrieve(String fullName) throws DAOException {
		// TODO Auto-generated method stub
		return null;
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


}
