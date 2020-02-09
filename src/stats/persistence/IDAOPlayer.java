package stats.persistence;

import java.util.List;

import stats.model.League;
import stats.model.Player;
import stats.model.Team;

public interface IDAOPlayer {
	
	public boolean exists(Player player) throws DAOException;
	public void createPlayer(Player player) throws DAOException;
	public void createListOfPlayers(List<Player> players) throws DAOException;
	public void updatePlayer(String fullName, Player player) throws DAOException;
	public void deletePlayer(Player player) throws DAOException;
	public List<Player> retrievePlayers(String surname)throws DAOException;
	public List<Player> retrieveAllPlayers() throws DAOException;
	public Player retrieveYoungerPlayer() throws DAOException;
	public Player retrieveOlderPlayer() throws DAOException;
	public Player retrieveMostValuedPlayer() throws DAOException;
	public Player retrieveYougerPlayer(League league) throws DAOException;
	public Player retrieveOlderPlayer(League league) throws DAOException;
	public Player retrieveMostValuedPlayer(League league) throws DAOException;
	public Player retrieveYougerPlayer(Team team) throws DAOException;
	public Player retrieveOlderPlayer(Team team) throws DAOException;
	public Player retrieveMostValuedPlayer(Team team) throws DAOException;
	List<Player> retrievePlayersFromTeam(Team team) throws DAOException;
	
}
