package stats.persistence;

import java.util.List;

import stats.model.Team;

public interface IDAOTeam {

	public boolean existsTeam(Team team) throws DAOException;
	public void createTeam(Team team) throws DAOException;
	public void createListOfTeams(List<Team> teams) throws DAOException;
	public void updateTeam(String fullName, Team player) throws DAOException;
	public void deleteTeam(Team player) throws DAOException;
	public List<Team> retrieveTeams(String surname)throws DAOException;
	public List<Team> retrieveAllTeams() throws DAOException;
	
}