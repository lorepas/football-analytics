package stats.persistence.n4j;

import java.util.List;

import stats.model.Team;
import stats.persistence.DAOException;
import stats.persistence.IDAOTeam;

public class DAOTeamN4J implements IDAOTeam {

	@Override
	public boolean exists(Team team) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createTeam(Team team) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void createListOfTeams(List<Team> teams) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTeam(String fullName, Team player) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTeam(Team player) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Team> retrieveTeams(String surname) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Team> retrieveAllTeams() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
