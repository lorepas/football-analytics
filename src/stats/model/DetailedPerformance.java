package stats.model;

import com.google.gson.Gson;

public class DetailedPerformance {

	private String season;
	private String team;
	private String fullName;
	private Integer goalConceded;
	private Integer cleanSheets;
	private int assists;
	private int penalityGoals;
	private double minutesPerGoal;
	private int calls;
	private int presences;
	private Double averagePoints;
	private int goals;
	private int ownGoals;
	private int substitutionOn;
	private int substitutionOff;
	private int yellowCards;
	private int doubleYellowCards;
	private int redCards;
	private double minutesPlayed;
	
	//getters and setters
	
	public static DetailedPerformance teamFromJson(String jsonString) {
		Gson g = new Gson();
		return g.fromJson(jsonString, DetailedPerformance.class);
	}
	
	public String toJSON() {
		Gson g = new Gson();
		return g.toJson(this);
	}
	
	public String getSeason() {
		return season;
	}
	public String getTeam() {
		return team;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public int getGoalConceded() {
		return goalConceded;
	}
	public int getCleanSheets() {
		return cleanSheets;
	}
	public int getAssists() {
		return assists;
	}
	public int getPenalityGoals() {
		return penalityGoals;
	}
	public double getMinutesPerGoal() {
		return minutesPerGoal;
	}
	public int getCalls() {
		return calls;
	}
	public int getPresences() {
		return presences;
	}
	public double getAveragePoints() {
		return averagePoints;
	}
	public int getGoals() {
		return goals;
	}
	public int getOwnGoals() {
		return ownGoals;
	}
	public int getSubstitutionOn() {
		return substitutionOn;
	}
	public int getSubstitutionOff() {
		return substitutionOff;
	}
	public int getYellowCards() {
		return yellowCards;
	}
	public int getDoubleYellowCards() {
		return doubleYellowCards;
	}
	public int getRedCards() {
		return redCards;
	}
	public double getMinutesPlayed() {
		return minutesPlayed;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public void setGoalConceded(Integer goalConceded) {
		this.goalConceded = goalConceded;
	}
	public void setCleanSheets(Integer cleanSheets) {
		this.cleanSheets = cleanSheets;
	}
	public void setAssists(Integer assists) {
		this.assists = assists;
	}
	public void setPenalityGoals(Integer penalityGoals) {
		this.penalityGoals = penalityGoals;
	}
	public void setMinutesPerGoal(Double minutesPerGoal) {
		this.minutesPerGoal = minutesPerGoal;
	}
	public void setCalls(int calls) {
		this.calls = calls;
	}
	public void setPresences(int presences) {
		this.presences = presences;
	}
	public void setAveragePoints(double averagePoints) {
		this.averagePoints = averagePoints;
	}
	public void setGoals(int goals) {
		this.goals = goals;
	}
	public void setOwnGoals(int ownGoals) {
		this.ownGoals = ownGoals;
	}
	public void setSubstitutionOn(int substitutionOn) {
		this.substitutionOn = substitutionOn;
	}
	public void setSubstitutionOff(int substitutionOff) {
		this.substitutionOff = substitutionOff;
	}
	public void setYellowCards(int yellowCards) {
		this.yellowCards = yellowCards;
	}
	public void setDoubleYellowCards(int doubleYellowCards) {
		this.doubleYellowCards = doubleYellowCards;
	}
	public void setRedCards(int redCards) {
		this.redCards = redCards;
	}
	public void setMinutesPlayed(double minutesPlayed) {
		this.minutesPlayed = minutesPlayed;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public DetailedPerformance(String season, String team, Integer goalConceded, Integer cleanSheets, Integer assists,
			Integer penalityGoals, Double minutesPerGoal, int calls, int presences, double averagePoints, int goals,
			int ownGoals, int substitutionOn, int substitutionOff, int yellowCards, int doubleYellowCards, int redCards,
			double minutesPlayed) {
		this.season = season;
		this.team = team;
		this.goalConceded = goalConceded;
		this.cleanSheets = cleanSheets;
		this.assists = assists;
		this.penalityGoals = penalityGoals;
		this.minutesPerGoal = minutesPerGoal;
		this.calls = calls;
		this.presences = presences;
		this.averagePoints = averagePoints;
		this.goals = goals;
		this.ownGoals = ownGoals;
		this.substitutionOn = substitutionOn;
		this.substitutionOff = substitutionOff;
		this.yellowCards = yellowCards;
		this.doubleYellowCards = doubleYellowCards;
		this.redCards = redCards;
		this.minutesPlayed = minutesPlayed;
	}
	
	

}
