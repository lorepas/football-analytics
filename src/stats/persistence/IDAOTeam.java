package stats.persistence;

import java.util.List;

import stats.model.League;
import stats.model.Team;

public interface IDAOTeam {

	public void createTeam(Team team) throws DAOException;
	public void deleteTeam(Team player) throws DAOException;
	public List<Team> retrieveAllTeams() throws DAOException;
	
}
