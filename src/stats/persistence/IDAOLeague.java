package stats.persistence;

import java.util.List;

import stats.model.League;
import stats.model.Team;

public interface IDAOLeague {

	public boolean exists(League league) throws DAOException;
	public void delete(League league) throws DAOException;
	List<League> retrieveAllLeagues() throws DAOException;
	void createLeague(League league) throws DAOException;
	List<League> retrieveLeagues(String fullName) throws DAOException;
}
