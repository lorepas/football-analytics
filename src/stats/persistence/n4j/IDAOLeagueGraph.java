package stats.persistence.n4j;

import java.util.List;

import stats.model.League;
import stats.model.Team;
import stats.persistence.DAOException;
import stats.persistence.IDAOLeague;

public interface IDAOLeagueGraph extends IDAOLeague {
	
	public void enrollTeam(League league, String teamFullName);
	League retrieve(String fullName) throws DAOException;
	public void updateLeague(String fullname, League league);
}
