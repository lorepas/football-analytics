package stats.persistence;

import java.util.List;

import stats.model.League;
import stats.model.Player;
import stats.model.Team;

public interface IDAOTeam {

	public boolean exists(Team team) throws DAOException;
	public void createTeam(Team team) throws DAOException;
	public void createListOfTeams(List<Team> teams) throws DAOException;
	public void updateTeam(String fullName, Team player) throws DAOException;
	public void deleteTeam(Team player) throws DAOException;
	public List<Team> retrieveTeams(String surname)throws DAOException;
	public List<Team> retrieveAllTeams() throws DAOException;
	List<Team> retrieveTeamsFromLeague(String name) throws DAOException;
	double retrieveTeamTotalMarketValue(Team team) throws DAOException;
	long retriveNativePlayers(Team team) throws DAOException;
	Player retriveMostRepresentativePlayer(Team team) throws DAOException;
	double retrievePercentageOfWins(League league, Team team) throws DAOException;
	double retrievePercentageOfDraws(League league, Team team) throws DAOException;
	double retrievePercentageOfDefeats(League league, Team team) throws DAOException;
	
}
