package stats.persistence.n4j;

import java.util.List;

import stats.model.League;
import stats.model.Match;
import stats.model.Team;
import stats.persistence.DAOException;

public interface IDAOQuery {
	public List<League> countLeague(Team team) throws DAOException;
	public List<Match> countWin(Team team) throws DAOException;
	public List<Match> countLost(Team team) throws DAOException;
	public List<Match> drawnLost(Team team) throws DAOException;
	public List<League> countTeams(League league) throws DAOException;
}
