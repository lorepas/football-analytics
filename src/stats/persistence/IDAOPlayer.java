package stats.persistence;

import java.util.List;

import stats.model.Player;

public interface IDAOPlayer {
	
	public void createPlayer(Player player);
	public void updatePlayer(Player player);
	public void deletePlayer(Player player);
	public List<Player> retrievePlayers(String surname);
	public List<Player> retrieveAllPlayers();
	
}
