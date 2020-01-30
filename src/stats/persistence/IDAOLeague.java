package stats.persistence;

import java.util.List;

import stats.model.League;
import stats.model.Match;
import stats.model.Player;

public interface IDAOLeague {

	public boolean exists(League league) throws DAOException;
	public void createMatch(League league) throws DAOException;
	public void createListOfLeagues(List<League> leagues) throws DAOException;
	public void updateLeague(League league) throws DAOException;
	public void deleteLeague(League league) throws DAOException;
	public List<Player> retrieveLeagues()throws DAOException;
	
}
