package stats.persistence;

import java.util.List;

import stats.model.Player;

public interface IDAOPlayer {
	
	public boolean exists(Player player) throws DAOException;
	public void createPlayer(Player player) throws DAOException;
	public void createListOfPlayers(List<Player> players) throws DAOException;
	public void updatePlayer(String fullName, Player player) throws DAOException;
	public void deletePlayer(String fullName, Player player) throws DAOException;
	public List<Player> retrievePlayers(String surname)throws DAOException;
	public List<Player> retrieveAllPlayers() throws DAOException;
	
}
