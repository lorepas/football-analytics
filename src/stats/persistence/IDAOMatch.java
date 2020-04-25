package stats.persistence;

import stats.model.Match;

public interface IDAOMatch {
	public void create(Match match) throws DAOException;
}
