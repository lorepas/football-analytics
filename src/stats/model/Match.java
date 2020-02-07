package stats.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import javafx.beans.property.SimpleStringProperty;

public class Match {

	private String possesionBallHome; 
	private String possesionBallAway; 
	 
	private int shotsOnGoalHome; 
	private int shotsOnGoalAway;
		 
		 
	private int cornerKiksHome; 
	private int cornerKiksAway; 
		 
	private int offsideHome; 
	private int offsideAway; 
		 
	private int foulsHome; 
	private int foulsAway; 
		 
	private int redCardsHome; 
	private int redCardsAway; 
		 
	private int yellowCardsHome; 
	private int yellowCardsAway; 
		 
	private int completedPassessHome; 
	private int completedPassesAway; 
	
	private int totalPassessHome; //
	private int totalPassesAway; //
	
	private int goalAttemptsHome; 
	private int goalAttemptsAway; 
		 
	private int shotsOffGoalHome; 
	private int shotsOffGoalAway; 
		 
	private int freeKicksHome; 
	private int freeKicksAway; 
		 
	private int goalkeeperSavedH; 
	private int goalkeeperSavedA; 
	
//
	
	private String date; 
	private String time;

	private String nameHome;
	private String nameAway;
		 
	private int scoreHome;
	private int scoreAway;
	
	private String round;
	
	public String getPossesionBallHome() {
		return possesionBallHome;
	}
	
	public String getPossesionBallAway() {
		return possesionBallAway;
	}
	public int getShotsOnGoalHome() {
		return shotsOnGoalHome;
	}
	public int getShotsOnGoalAway() {
		return shotsOnGoalAway;
	}
	public int getCornerKiksHome() {
		return cornerKiksHome;
	}
	public int getCornerKiksAway() {
		return cornerKiksAway;
	}
	public int getOffsideHome() {
		return offsideHome;
	}
	public int getOffsideAway() {
		return offsideAway;
	}
	public int getFoulsHome() {
		return foulsHome;
	}
	public int getFoulsAway() {
		return foulsAway;
	}
	public int getRedCardsHome() {
		return redCardsHome;
	}
	public int getRedCardsAway() {
		return redCardsAway;
	}
	public int getYellowCardsHome() {
		return yellowCardsHome;
	}
	public int getYellowCardsAway() {
		return yellowCardsAway;
	}
	public int getCompletedPassessHome() {
		return completedPassessHome;
	}
	public int getCompletedPassesAway() {
		return completedPassesAway;
	}
	public int getGoalAttemptsHome() {
		return goalAttemptsHome;
	}
	public int getGoalAttemptsAway() {
		return goalAttemptsAway;
	}
	public int getShotsOffGoalHome() {
		return shotsOffGoalHome;
	}
	public int getShotsOffGoalAway() {
		return shotsOffGoalAway;
	}
	public int getFreeKicksHome() {
		return freeKicksHome;
	}
	public int getFreeKicksAway() {
		return freeKicksAway;
	}
	public int getGoalkeeperSavedH() {
		return goalkeeperSavedH;
	}
	public int getGoalkeeperSavedA() {
		return goalkeeperSavedA;
	}
	public String getDate() {
		return date;
	}
	public String getTime() {
		return time;
	}
	public String getNameHome() {
		return nameHome;
	}
	public String getNameAway() {
		return nameAway;
	}
	public int getScoreHome() {
		return scoreHome;
	}
	public int getScoreAway() {
		return scoreAway;
	}
	public void setPossesionBallHome(String possesionBallHome) {
		this.possesionBallHome = possesionBallHome;
	}
	public void setPossesionBallAway(String possesionBallAway) {
		this.possesionBallAway = possesionBallAway;
	}
	public void setShotsOnGoalHome(int shotsOnGoalHome) {
		this.shotsOnGoalHome = shotsOnGoalHome;
	}
	public void setShotsOnGoalAway(int shotsOnGoalAway) {
		this.shotsOnGoalAway = shotsOnGoalAway;
	}
	public void setCornerKiksHome(int cornerKiksHome) {
		this.cornerKiksHome = cornerKiksHome;
	}
	public void setCornerKiksAway(int cornerKiksAway) {
		this.cornerKiksAway = cornerKiksAway;
	}
	public void setOffsideHome(int offsideHome) {
		this.offsideHome = offsideHome;
	}
	public void setOffsideAway(int offsideAway) {
		this.offsideAway = offsideAway;
	}
	public void setFoulsHome(int foulsHome) {
		this.foulsHome = foulsHome;
	}
	public void setFoulsAway(int foulsAway) {
		this.foulsAway = foulsAway;
	}
	public void setRedCardsHome(int redCardsHome) {
		this.redCardsHome = redCardsHome;
	}
	public void setRedCardsAway(int redCardsAway) {
		this.redCardsAway = redCardsAway;
	}
	public void setYellowCardsHome(int yellowCardsHome) {
		this.yellowCardsHome = yellowCardsHome;
	}
	public void setYellowCardsAway(int yellowCardsAway) {
		this.yellowCardsAway = yellowCardsAway;
	}
	public void setCompletedPassessHome(int completedPassessHome) {
		this.completedPassessHome = completedPassessHome;
	}
	public void setCompletedPassesAway(int completedPassesAway) {
		this.completedPassesAway = completedPassesAway;
	}
	public void setGoalAttemptsHome(int goalAttemptsHome) {
		this.goalAttemptsHome = goalAttemptsHome;
	}
	public void setGoalAttemptsAway(int goalAttemptsAway) {
		this.goalAttemptsAway = goalAttemptsAway;
	}
	public void setShotsOffGoalHome(int shotsOffGoalHome) {
		this.shotsOffGoalHome = shotsOffGoalHome;
	}
	public void setShotsOffGoalAway(int shotsOffGoalAway) {
		this.shotsOffGoalAway = shotsOffGoalAway;
	}
	public void setFreeKicksHome(int freeKicksHome) {
		this.freeKicksHome = freeKicksHome;
	}
	public void setFreeKicksAway(int freeKicksAway) {
		this.freeKicksAway = freeKicksAway;
	}
	public void setGoalkeeperSavedH(int goalkeeperSavedH) {
		this.goalkeeperSavedH = goalkeeperSavedH;
	}
	public void setGoalkeeperSavedA(int goalkeeperSavedA) {
		this.goalkeeperSavedA = goalkeeperSavedA;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setNameHome(String nameHome) {
		this.nameHome = nameHome;
	}
	public void setNameAway(String nameAway) {
		this.nameAway = nameAway;
	}
	public void setScoreHome(int scoreHome) {
		this.scoreHome = scoreHome;
	}
	public void setScoreAway(int scoreAway) {
		this.scoreAway = scoreAway;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public int getTotalPassessHome() {
		return totalPassessHome;
	}

	public void setTotalPassessHome(int totalPassessHome) {
		this.totalPassessHome = totalPassessHome;
	}

	public int getTotalPassesAway() {
		return totalPassesAway;
	}

	public void setTotalPassesAway(int totalPassesAway) {
		this.totalPassesAway = totalPassesAway;
	}
	
	public static Match matchFromJson(String jsonString) {
		Gson g = new Gson();
		return g.fromJson(jsonString, Match.class);
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(nameHome + " ");
		builder.append(scoreHome + " - ");
		builder.append(scoreAway +  " ");
		builder.append(nameAway );
		return builder.toString();
	}

	public List<MatchPerformanceTable> getListOfStatistics(){
		
		List<MatchPerformanceTable> statistics = new ArrayList<MatchPerformanceTable>();
		
		MatchPerformanceTable ballPossession = new MatchPerformanceTable(); //
		MatchPerformanceTable shotsOnGoal = new MatchPerformanceTable(); //
		MatchPerformanceTable cornerKiks = new MatchPerformanceTable(); //
		MatchPerformanceTable getOffside = new MatchPerformanceTable(); //
		MatchPerformanceTable fouls = new MatchPerformanceTable();//
		MatchPerformanceTable redCards = new MatchPerformanceTable(); //
		MatchPerformanceTable yellowCards = new MatchPerformanceTable(); //
		MatchPerformanceTable completedPasses = new MatchPerformanceTable(); //
		MatchPerformanceTable totalPasses  = new MatchPerformanceTable(); //
		MatchPerformanceTable goalAttempts = new MatchPerformanceTable(); //
		MatchPerformanceTable shotsOffGoal = new MatchPerformanceTable(); //
		MatchPerformanceTable freeKicks= new MatchPerformanceTable(); //
		MatchPerformanceTable goalKeeper = new MatchPerformanceTable(); //
		
		ballPossession.setHome(new SimpleStringProperty(getPossesionBallHome().toString()));
		ballPossession.setAway(new SimpleStringProperty(getPossesionBallAway().toString()));
		ballPossession.setStatic(new SimpleStringProperty("Ball possession")); 
		
		shotsOnGoal.setHome(new SimpleStringProperty(String.valueOf(getShotsOnGoalHome())));
		shotsOnGoal.setAway(new SimpleStringProperty(String.valueOf(getShotsOnGoalAway())));
		shotsOnGoal.setStatic(new SimpleStringProperty("Shots on goal")); 
		
		cornerKiks.setHome(new SimpleStringProperty(String.valueOf(getCornerKiksHome())));
		cornerKiks.setAway(new SimpleStringProperty(String.valueOf(getCornerKiksAway())));
		cornerKiks.setStatic(new SimpleStringProperty("Corner kiks")); 
		
		getOffside.setHome(new SimpleStringProperty(String.valueOf(getOffsideHome())));
		getOffside.setAway(new SimpleStringProperty(String.valueOf(getOffsideAway())));
		getOffside.setStatic(new SimpleStringProperty("Off side")); 
		
		fouls.setHome(new SimpleStringProperty(String.valueOf(getFoulsHome())));
		fouls.setAway(new SimpleStringProperty(String.valueOf(getFoulsAway())));
		fouls.setStatic(new SimpleStringProperty("Fouls")); 
		
		redCards.setHome(new SimpleStringProperty(String.valueOf(getRedCardsHome())));
		redCards.setAway(new SimpleStringProperty(String.valueOf(getRedCardsAway())));
		redCards.setStatic(new SimpleStringProperty("Red cards")); 
		
		yellowCards.setHome(new SimpleStringProperty(String.valueOf(getYellowCardsHome())));
		yellowCards.setAway(new SimpleStringProperty(String.valueOf(getYellowCardsAway())));
		yellowCards.setStatic(new SimpleStringProperty("Yellow cards")); 
		
		completedPasses.setHome(new SimpleStringProperty(String.valueOf(getCompletedPassessHome())));
		completedPasses.setAway(new SimpleStringProperty(String.valueOf(getCompletedPassesAway())));
		completedPasses.setStatic(new SimpleStringProperty("Completed passes")); 
		
		totalPasses.setHome(new SimpleStringProperty(String.valueOf(getTotalPassessHome())));
		totalPasses.setAway(new SimpleStringProperty(String.valueOf(getTotalPassesAway())));
		totalPasses.setStatic(new SimpleStringProperty("Total passes")); 
		
		goalAttempts.setHome(new SimpleStringProperty(String.valueOf(getGoalAttemptsHome())));
		goalAttempts.setAway(new SimpleStringProperty(String.valueOf(getGoalAttemptsAway())));
		goalAttempts.setStatic(new SimpleStringProperty("Goal attempts")); 
		
		shotsOffGoal.setHome(new SimpleStringProperty(String.valueOf(getShotsOffGoalHome())));
		shotsOffGoal.setAway(new SimpleStringProperty(String.valueOf(getShotsOffGoalAway())));
		shotsOffGoal.setStatic(new SimpleStringProperty("Shots off goal"));  

		freeKicks.setHome(new SimpleStringProperty(String.valueOf(getFreeKicksHome())));
		freeKicks.setAway(new SimpleStringProperty(String.valueOf(getFreeKicksAway())));
		freeKicks.setStatic(new SimpleStringProperty("Free kicks"));  
		

		goalKeeper.setHome(new SimpleStringProperty(String.valueOf(getGoalkeeperSavedH())));
		goalKeeper.setAway(new SimpleStringProperty(String.valueOf(getGoalkeeperSavedA())));
		goalKeeper.setStatic(new SimpleStringProperty("Goal keeper"));  
		
		statistics.add(ballPossession);
		statistics.add(shotsOnGoal);
		statistics.add(cornerKiks);
		statistics.add(getOffside);
		statistics.add(fouls);
		statistics.add(redCards);
		statistics.add(yellowCards);
		statistics.add(completedPasses);
		statistics.add(totalPasses);
		statistics.add(goalAttempts);
		statistics.add(shotsOffGoal);
		statistics.add(freeKicks);
		statistics.add(goalKeeper);
		
		return statistics;
	}
	
	public List<Match> retrieveMatchFromRound(String round) {
		List<Match> matches = new ArrayList<>();
		
		return null;
		
	}
}
