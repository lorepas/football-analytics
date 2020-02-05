package stats.model;

import javafx.beans.property.SimpleStringProperty;

public class MatchPerformanceTable {
	private SimpleStringProperty home;
	private SimpleStringProperty statistic;
	private SimpleStringProperty away;
	public SimpleStringProperty getHome() {
		return home;
	}
	public SimpleStringProperty getStatistic() {
		return statistic;
	}
	public SimpleStringProperty getAway() {
		return away;
	}
	public void setHome(SimpleStringProperty home) {
		this.home = home;
	}
	public void setStatic(SimpleStringProperty statistic) {
		this.statistic = statistic;
	}
	public void setAway(SimpleStringProperty away) {
		this.away = away;
	}

	
}
