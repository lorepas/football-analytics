package stats.persistence;

import java.util.List;

import stats.model.League;
import stats.model.Player;

public interface IDAOLeague {

	public boolean exists(League league) throws DAOException;
	public void create(League league) throws DAOException;
	public void create(List<League> leagues) throws DAOException;
	public void update(League league) throws DAOException;
	public void delete(League league) throws DAOException;
	public List<League> retrieve() throws DAOException;
	public League retrieve(String fullName) throws DAOException;
	
}
