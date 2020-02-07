package stats.persistence;

import java.util.List;

import stats.model.League;
import stats.model.Player;
import stats.model.Team;

public interface IDAOLeague {

	public boolean exists(League league) throws DAOException;
	public void createListOfLeagues(List<League> leagues) throws DAOException;
	public void delete(League league) throws DAOException;
	List<League> retrieveAllLeagues() throws DAOException;
	void createLeague(League league) throws DAOException;
	void updateLeague(String fullName, League league) throws DAOException;
	List<League> retrieveLeagues(String fullName) throws DAOException;
	Team retrieveMostWinningHomeTeam(League league) throws DAOException;
	Team retrieveMostWinningAwayTeam(League league) throws DAOException;
	Team retrieveMostWinningTeam(League league) throws DAOException;
	Team retrieveMostLosingTeam(League league) throws DAOException;
}
