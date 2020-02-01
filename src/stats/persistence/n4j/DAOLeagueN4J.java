package stats.persistence.n4j;

import java.util.List;

import stats.model.League;
import stats.persistence.DAOException;
import stats.persistence.IDAOLeague;

public class DAOLeagueN4J implements IDAOLeague {

	@Override
	public boolean exists(League league) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createLeague(League league) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void createListOfLeagues(List<League> leagues) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLeague(League league) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteLeague(League league) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<League> retrieveLeagues() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
