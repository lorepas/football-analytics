package stats.persistence;

import java.util.List;

import stats.model.League;
import stats.model.Match;
import stats.model.Player;

public interface IDAOMatch {
	
	public boolean exists(Match match) throws DAOException;
	public void createMatch(Match match) throws DAOException;
	public void createListOfMatches(List<Match> matches) throws DAOException;
	public void updateMatch(Match match) throws DAOException;
	public void deleteMatch(Match match) throws DAOException;
	public List<Player> retrieveMatches(League league)throws DAOException;
	public List<Player> retrieveMatches() throws DAOException;
}
