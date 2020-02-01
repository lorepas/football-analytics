package stats.persistence.n4j;

import java.util.List;

import stats.model.League;
import stats.model.Match;
import stats.model.Player;
import stats.persistence.DAOException;
import stats.persistence.IDAOMatch;

public class DAOMatchN4J implements IDAOMatchGraph {

	@Override
	public boolean exists(Match match) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void create(Match match) throws DAOException {
		// TODO Auto-generated method stub
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
