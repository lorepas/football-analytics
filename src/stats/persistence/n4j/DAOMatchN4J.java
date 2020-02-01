package stats.persistence.n4j;

import java.util.List;

import stats.model.League;
import stats.model.Match;
import stats.model.Player;
import stats.persistence.DAOException;
import stats.persistence.IDAOMatch;

public class DAOMatchN4J implements IDAOMatch {

	@Override
	public boolean exists(Match match) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createMatch(Match match) throws DAOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void createListOfMatches(List<Match> matches) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateMatch(Match match) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMatch(Match match) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Player> retrieveMatches(League league) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Player> retrieveMatches() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
