package stats.persistence;

import java.util.List;

import stats.model.League;
import stats.model.Match;
import stats.model.Player;

public interface IDAOMatch {
	
	public boolean exists(Match match) throws DAOException;
	public void create(Match match) throws DAOException;
	public void create(List<Match> matches) throws DAOException;
	public void update(Match match) throws DAOException;
	public void delete(Match match) throws DAOException;
	public List<Player> retrieve(League league)throws DAOException;
	public List<Player> retrieve() throws DAOException;
	public List<Match> retrieveAllMatches() throws DAOException;
}
