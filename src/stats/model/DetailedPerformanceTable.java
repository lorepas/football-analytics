package stats.model;

import javafx.beans.property.*;

public class DetailedPerformanceTable {
	private SimpleStringProperty season;
	private SimpleStringProperty team;
	private SimpleIntegerProperty goalConceded;
	private SimpleIntegerProperty cleanSheets;
	private SimpleIntegerProperty assists;
	private SimpleIntegerProperty penalityGoals;
	private SimpleDoubleProperty minutesPerGoal;
	private SimpleIntegerProperty calls;
	private SimpleIntegerProperty presences;
	private SimpleDoubleProperty averagePoints;
	private SimpleIntegerProperty goals;
	private SimpleIntegerProperty ownGoals;
	private SimpleIntegerProperty substitutionOn;
	private SimpleIntegerProperty substitutionOff;
	private SimpleIntegerProperty yellowCards;
	private SimpleIntegerProperty doubleYellowCards;
	private SimpleIntegerProperty redCards;
	private SimpleDoubleProperty minutesPlayed;
	
	public DetailedPerformanceTable(DetailedPerformance dp) {
		this.season = new SimpleStringProperty(dp.getSeason());
		this.team = new SimpleStringProperty(dp.getTeam());
		this.goalConceded = new SimpleIntegerProperty(dp.getGoalConceded());
		this.cleanSheets = new SimpleIntegerProperty(dp.getCleanSheets());
		this.assists = new SimpleIntegerProperty(dp.getAssists());
		this.penalityGoals = new SimpleIntegerProperty(dp.getPenalityGoals());
		this.minutesPerGoal = new SimpleDoubleProperty(dp.getMinutesPerGoal());
		this.calls = new SimpleIntegerProperty(dp.getCalls());
		this.presences = new SimpleIntegerProperty(dp.getPresences());
		this.averagePoints = new SimpleDoubleProperty(dp.getAveragePoints());
		this.goals = new SimpleIntegerProperty(dp.getGoals());
		this.ownGoals = new SimpleIntegerProperty(dp.getOwnGoals());
		this.substitutionOn = new SimpleIntegerProperty(dp.getSubstitutionOn());
		this.substitutionOff = new SimpleIntegerProperty(dp.getSubstitutionOff());
		this.yellowCards = new SimpleIntegerProperty(dp.getYellowCards());
		this.doubleYellowCards = new SimpleIntegerProperty(dp.getDoubleYellowCards());
		this.redCards = new SimpleIntegerProperty(dp.getRedCards());
		this.minutesPlayed = new SimpleDoubleProperty(dp.getMinutesPlayed());
	}
	
	public DetailedPerformanceTable(String season, String team, int goalConceded, int cleanSheets, int assists,
			int penalityGoals, double minutesPerGoal, int calls, int presences, double averagePoints, int goals,
			int ownGoals, int substitutionOn, int substitutionOff, int yellowCards, int doubleYellowCards, int redCards,
			double minutesPlayed) {
		this.season = new SimpleStringProperty(season);
		this.team = new SimpleStringProperty(team);
		this.goalConceded = new SimpleIntegerProperty(goalConceded);
		this.cleanSheets = new SimpleIntegerProperty(cleanSheets);
		this.assists = new SimpleIntegerProperty(assists);
		this.penalityGoals = new SimpleIntegerProperty(penalityGoals);
		this.minutesPerGoal = new SimpleDoubleProperty(minutesPerGoal);
		this.calls = new SimpleIntegerProperty(calls);
		this.presences = new SimpleIntegerProperty(presences);
		this.averagePoints = new SimpleDoubleProperty(averagePoints);
		this.goals = new SimpleIntegerProperty(goals);
		this.ownGoals = new SimpleIntegerProperty(ownGoals);
		this.substitutionOn = new SimpleIntegerProperty(substitutionOn);
		this.substitutionOff = new SimpleIntegerProperty(substitutionOff);
		this.yellowCards = new SimpleIntegerProperty(yellowCards);
		this.doubleYellowCards = new SimpleIntegerProperty(doubleYellowCards);
		this.redCards = new SimpleIntegerProperty(redCards);
		this.minutesPlayed = new SimpleDoubleProperty(minutesPlayed);
	}

	public StringProperty getSeason() {
		return season;
	}

	public void setSeason(SimpleStringProperty season) {
		this.season = season;
	}

	public StringProperty getTeam() {
		return team;
	}

	public void setTeam(SimpleStringProperty team) {
		this.team = team;
	}

	public IntegerProperty getGoalConceded() {
		return goalConceded;
	}

	public void setGoalConceded(SimpleIntegerProperty goalConceded) {
		this.goalConceded = goalConceded;
	}

	public IntegerProperty getCleanSheets() {
		return cleanSheets;
	}

	public void setCleanSheets(SimpleIntegerProperty cleanSheets) {
		this.cleanSheets = cleanSheets;
	}

	public IntegerProperty getAssists() {
		return assists;
	}

	public void setAssists(SimpleIntegerProperty assists) {
		this.assists = assists;
	}

	public IntegerProperty getPenalityGoals() {
		return penalityGoals;
	}

	public void setPenalityGoals(SimpleIntegerProperty penalityGoals) {
		this.penalityGoals = penalityGoals;
	}

	public DoubleProperty getMinutesPerGoal() {
		return minutesPerGoal;
	}

	public void setMinutesPerGoal(SimpleDoubleProperty minutesPerGoal) {
		this.minutesPerGoal = minutesPerGoal;
	}

	public IntegerProperty getCalls() {
		return calls;
	}

	public void setCalls(SimpleIntegerProperty calls) {
		this.calls = calls;
	}

	public IntegerProperty getPresences() {
		return presences;
	}

	public void setPresences(SimpleIntegerProperty presences) {
		this.presences = presences;
	}

	public DoubleProperty getAveragePoints() {
		return averagePoints;
	}

	public void setAveragePoints(SimpleDoubleProperty averagePoints) {
		this.averagePoints = averagePoints;
	}

	public IntegerProperty getGoals() {
		return goals;
	}

	public void setGoals(SimpleIntegerProperty goals) {
		this.goals = goals;
	}

	public IntegerProperty getOwnGoals() {
		return ownGoals;
	}

	public void setOwnGoals(SimpleIntegerProperty ownGoals) {
		this.ownGoals = ownGoals;
	}

	public IntegerProperty getSubstitutionOn() {
		return substitutionOn;
	}

	public void setSubstitutionOn(SimpleIntegerProperty substitutionOn) {
		this.substitutionOn = substitutionOn;
	}

	public IntegerProperty getSubstitutionOff() {
		return substitutionOff;
	}

	public void setSubstitutionOff(SimpleIntegerProperty substitutionOff) {
		this.substitutionOff = substitutionOff;
	}

	public IntegerProperty getYellowCards() {
		return yellowCards;
	}

	public void setYellowCards(SimpleIntegerProperty yellowCards) {
		this.yellowCards = yellowCards;
	}

	public IntegerProperty getDoubleYellowCards() {
		return doubleYellowCards;
	}

	public void setDoubleYellowCards(SimpleIntegerProperty doubleYellowCards) {
		this.doubleYellowCards = doubleYellowCards;
	}

	public IntegerProperty getRedCards() {
		return redCards;
	}

	public void setRedCards(SimpleIntegerProperty redCards) {
		this.redCards = redCards;
	}

	public DoubleProperty getMinutesPlayed() {
		return minutesPlayed;
	}

	public void setMinutesPlayed(SimpleDoubleProperty minutesPlayed) {
		this.minutesPlayed = minutesPlayed;
	}

}
