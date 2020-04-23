package stats.persistence.n4j;

import stats.model.Team;
import stats.persistence.DAOException;
import stats.persistence.IDAOTeam;

public interface IDAOTeamGraph extends IDAOTeam {

	public int numberOfWonMatches(Team team);
	public int numberOfDrawnMatches(Team team);
	public int numberOfLostMatches(Team team);
	Team retrieveTeam(String fullName) throws DAOException;
	
}
