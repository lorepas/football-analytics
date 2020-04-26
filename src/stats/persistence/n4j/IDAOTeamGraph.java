package stats.persistence.n4j;

import java.util.List;

import stats.model.Team;
import stats.persistence.DAOException;
import stats.persistence.IDAOTeam;

public interface IDAOTeamGraph extends IDAOTeam {
	Team retrieveTeam(String fullName) throws DAOException;
	List<Team> retrieveTeams(String fullName) throws DAOException;
}
