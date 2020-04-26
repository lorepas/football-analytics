package stats.persistence.n4j;

import java.util.List;

import org.neo4j.driver.Query;

import stats.model.League;
import stats.model.Team;
import stats.persistence.DAOException;
import stats.persistence.IDAOTeam;

public interface IDAOTeamGraph extends IDAOTeam {
	Team retrieveTeam(String fullName) throws DAOException;
	List<Team> retrieveTeams(String fullName) throws DAOException;
	Query teamExistenceQuery(Team team);
	Query createTeamQuery(Team team);
	Query deleteTeamQuery(Team team);
}
