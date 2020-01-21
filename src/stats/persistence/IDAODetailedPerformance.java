package stats.persistence;

import java.util.List;

import stats.model.DetailedPerformance;
import stats.model.Player;

public interface IDAODetailedPerformance {

	public void add(DetailedPerformance dp, Player player);
	public void remove(DetailedPerformance dp, Player player);
	public List<DetailedPerformance> retrieveAll(Player player);
	public List<DetailedPerformance> retrieveAll();
	public List<DetailedPerformance> retrieveAll(Player player, String season);
	
}
