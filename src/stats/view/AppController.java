package stats.view;

import java.awt.Button;
import java.awt.TextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.LineChart.SortingPolicy;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import stats.App;
import stats.model.DetailedPerformance;
import stats.model.DetailedPerformanceTable;
import stats.model.League;
import stats.model.MarketValue;
import stats.model.Match;
import stats.model.MatchPerformanceTable;
import stats.model.Player;
import stats.model.Team;
import stats.persistence.DAOException;
import stats.utility.Utils;

public class AppController implements Initializable{
	
	@FXML javafx.scene.control.Button searchButton;//
	@FXML javafx.scene.control.TextField fieldTeam;//
	@FXML ListView<Team> listTeams; //
//	@FXML javafx.scene.control.Button searchPlayerButton;
//	@FXML javafx.scene.control.TextField fieldPlayer;
//	@FXML ListView<Player> listPlayer;
	@FXML javafx.scene.control.Button searchLeague; 
	@FXML javafx.scene.control.TextField fieldLeague; 
	@FXML ListView<League> listLeague; 
	@FXML ListView<Team> listTeamsOfALeague; 
	@FXML ListView<League> listLeaguesOfThatTeam; 
	@FXML javafx.scene.control.Button buttonUpdateTeam; 
	@FXML javafx.scene.control.Button buttonLogin; 
	@FXML javafx.scene.control.Button buttonUpdateLeague;
	@FXML javafx.scene.control.Button buttonDeleteLeague;
	@FXML javafx.scene.control.Button buttonDeleteTeam;
	@FXML javafx.scene.control.Button buttoUpdatePlayer; 
	@FXML javafx.scene.control.Button buttonDeletePlayer; 
//	@FXML BarChart<Team, Float> barCharLeague;
//	@FXML CategoryAxis teamsAxis;
//	@FXML NumberAxis averageAgeAxis;
//	@FXML Label labelMostWinningTeam;
//	@FXML Label labelMostLosingTeam;
//	@FXML Label labelYoungest;
//	@FXML Label labelOldest;
//	@FXML Label labelMostLosing;
//	@FXML Label labelHigest;
	@FXML Label labelNameTeam;
	@FXML Label labelNumberMatchesWin;
	@FXML Label labelNumberMatchesLost;
	@FXML Label labelNumberMatchesDrawn;
//	@FXML Label labelMarketValue;
//	@FXML Label labelMostRepresentative;
//	@FXML PieChart pieChartForeign;
//	@FXML PieChart pieChartResults;
//	@FXML Label labelNamePlayer;
//	@FXML Label labelDateBirth;
//	@FXML Label labelCitizenshipPlayer;
//	@FXML Label labelPositionPlayer;
//	@FXML Label labelLeaguePlayer;
//	@FXML Label labelTeamPlayer;
//	@FXML Label labelMarketValuePlayer;
//	@FXML TableView<DetailedPerformanceTable> tablePlayers;
//	@FXML TableColumn<DetailedPerformanceTable, String> columnSeason;
//	@FXML TableColumn<DetailedPerformanceTable, String> columnTeam;
//	@FXML TableColumn<DetailedPerformanceTable, Integer> columnGoalConceded;
//	@FXML TableColumn<DetailedPerformanceTable, Integer> columnCleanSheets;
//	@FXML TableColumn<DetailedPerformanceTable, Integer> columnAssists;
//	@FXML TableColumn<DetailedPerformanceTable, Integer> columnPenaltyGoals;
//	@FXML TableColumn<DetailedPerformanceTable, Double> columnMinutesPerGoal;
//	@FXML TableColumn<DetailedPerformanceTable, Integer> columnCalls;
//	@FXML TableColumn<DetailedPerformanceTable, Integer> columnPresences;
//	@FXML TableColumn<DetailedPerformanceTable, Double> columnAveragePoints;
//	@FXML TableColumn<DetailedPerformanceTable, Integer> columnGoals;
//	@FXML TableColumn<DetailedPerformanceTable, Integer> columnOwnGoals;
//	@FXML TableColumn<DetailedPerformanceTable, Integer> columnSobstitutionOn;
//	@FXML TableColumn<DetailedPerformanceTable, Integer> columnSobstitutionOff;
//	@FXML TableColumn<DetailedPerformanceTable, Integer> columnYellowCards;
//	@FXML TableColumn<DetailedPerformanceTable, Integer> columnDoubleYellowCards;
//	@FXML TableColumn<DetailedPerformanceTable, Integer> columnRedCards;
//	@FXML TableColumn<DetailedPerformanceTable, Double> columnMinutesPlayed;
//	@FXML LineChart<String, Double> lineChartTrend;
//	@FXML TableView<MatchPerformanceTable> matchesResults;
//	@FXML TableColumn<MatchPerformanceTable, String> columnHome;
//	@FXML TableColumn<MatchPerformanceTable, String> columnStatistics;
//	@FXML TableColumn<MatchPerformanceTable, String> columnAway;
//	@FXML Label labelResultMatch;
//	@FXML ComboBox<League> comboBoxLeaguesPlayer;
//	@FXML ComboBox<Team> comboBoxTeamsPlayer;
//	@FXML ComboBox<League> comboBoxLeaguesTeam; 
//	@FXML ComboBox<League> comboBoxLeaguesMatches;
//	@FXML ComboBox<Integer> comboBoxRoundMatches;
//	@FXML ImageView imageShield;
//	@FXML ImageView imageTeamHome;
//	@FXML ImageView imageTeamAway;
//	@FXML Tab playerTab;
//	@FXML TabPane tabPane;
	@FXML Label labelLeague; 
//	@FXML ListView<Match> listMatches;
	ObservableList comboDefault = FXCollections.observableArrayList();
	private League leagueSelectedToRound = null;
	private League leagueSelectedCombo = null;
//	XYChart.Series emptyChart = new XYChart.Series<>();
	
	public ObservableList<League> retriveLeagueFromComboBoxPlayer(){
		List<League> listSearchedLeagues = null;
		try {
			listSearchedLeagues = App.sharedInstance.getDaoLeagueGraph().retrieveAllLeagues();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.WARNING, "Connect to VPN", ButtonType.CLOSE);
			alert.showAndWait();
			System.exit(0);
		}
		ObservableList<League> list = FXCollections.observableArrayList(listSearchedLeagues);
		return list;
	}
	
//	public void ActionRetriveTeamFromComboBoxTeam(Event event) throws DAOException {
//		leagueSelectedCombo = comboBoxLeaguesTeam.getValue();
//		ObservableList list = FXCollections.observableArrayList(App.sharedInstance.getDaoTeam().retrieveTeamsFromLeague(leagueSelectedCombo));
//		listTeams.setItems(list);
//		listTeams.setOnMouseClicked(e->{
//			try {
//				onClickEventOnTeam(e);
//			} catch (DAOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		});
//		if (list.isEmpty()) {
//			Alert alert = new Alert(AlertType.WARNING, "No teams in the league", ButtonType.CLOSE);
//			alert.showAndWait();
//		}
//	}
	
//	public void ActionRetriveTeamFromComboBoxPlayer(ActionEvent e) throws DAOException {
//		League leagueSelected = comboBoxLeaguesPlayer.getValue();
//		List<Team> listSearchedTeams = App.sharedInstance.getDaoTeam().retrieveTeamsFromLeague(leagueSelected);
//		ObservableList<Team> list = FXCollections.observableArrayList(listSearchedTeams);
//		comboBoxTeamsPlayer.setItems(list);
//		
//	}
	
//	public void ActionRetrivePlayerFromComboBoxPlayer(ActionEvent event) throws DAOException {
//		Team teamSelected = comboBoxTeamsPlayer.getValue();
//		ObservableList<Player> listSearchedPlayers = FXCollections.observableArrayList(App.sharedInstance.getDaoPlayer().retrievePlayersFromTeam(teamSelected));
//		listPlayer.setItems(listSearchedPlayers);
//		listPlayer.setOnMouseClicked(e->onClickEventOnPlayer(e));
//		if (listSearchedPlayers.isEmpty()) {
//			Alert alert = new Alert(AlertType.WARNING, "No players in the team", ButtonType.CLOSE);
//			alert.showAndWait();
//		}
//	}
	
	public void ActionRetrieveTeam(ActionEvent event) {
		try {
			String text = fieldTeam.getText();
			if (text.isEmpty()) {
				List<Team> teams = new ArrayList<>();
				try {
					teams = App.sharedInstance.getDaoTeamGraph().retrieveAllTeams();
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ObservableList<Team> list = FXCollections.observableArrayList(teams);
				listTeams.getItems().clear();
				listTeams.setItems(list);
			} else {
				Team listSearchedTeams = App.sharedInstance.getDaoTeamGraph().retrieveTeam(text);
				ObservableList<Team> list = FXCollections.observableArrayList(listSearchedTeams);
				listTeams.getItems().clear();
				listTeams.setItems(list);
//				listTeams.setOnMouseClicked(e->{
//					try {
//						ActionClickOnTeam(e);
//					} catch (DAOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				});
			}
		} catch(DAOException e) {
			Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
			alert.showAndWait();
		}
		
	}
	public void ActionClickOnTeam(MouseEvent event) throws DAOException{
		Team teamSelected = listTeams.getSelectionModel().getSelectedItem();
		labelNameTeam.setText(teamSelected.getFullName().toUpperCase());
		labelNumberMatchesWin.setText(String.valueOf(App.sharedInstance.getDaoQuery().countWin(teamSelected).size()));
		labelNumberMatchesLost.setText(String.valueOf(App.sharedInstance.getDaoQuery().countLost(teamSelected).size()));
		labelNumberMatchesDrawn.setText(String.valueOf(App.sharedInstance.getDaoQuery().countDrawn(teamSelected).size()));
		List<League> leagues = App.sharedInstance.getDaoQuery().countLeague(teamSelected);
		ObservableList<League> list = FXCollections.observableArrayList(leagues);
		listLeaguesOfThatTeam.setItems(list);
//		long nativePlayer = App.sharedInstance.getDaoTeam().retriveNativePlayers(teamSelected);
//		long foreignPlayer = teamSelected.getRosterSize() - nativePlayer;
//		long percentageNativePlayer = (nativePlayer*100) / teamSelected.getRosterSize();
//		long percentageForeignPlayer = (foreignPlayer*100) / teamSelected.getRosterSize();
//		ObservableList<Data> listPie = FXCollections.observableArrayList(
//				new PieChart.Data(percentageNativePlayer + "% NATIVE", nativePlayer),
//				new PieChart.Data(percentageForeignPlayer + "% FOREIGN", foreignPlayer));
		//pieChartForeign.setData(listPie);
//		leagueSelectedCombo = comboBoxLeaguesTeam.getSelectionModel().getSelectedItem();
//		double percentageOfWin = App.getSharedInstance().getDaoTeam().retrievePercentageOfWins(leagueSelectedCombo, teamSelected);
//		double percentageOfDefeats = App.getSharedInstance().getDaoTeam().retrievePercentageOfDefeats(leagueSelectedCombo, teamSelected);
//		double percentageOfDrawn = App.getSharedInstance().getDaoTeam().retrievePercentageOfDraws(leagueSelectedCombo, teamSelected);
//		ObservableList<Data> listPieMatches = FXCollections.observableArrayList(
//				new PieChart.Data(Math.round(percentageOfWin) + "% WIN", percentageOfWin),
//				new PieChart.Data(Math.round(percentageOfDefeats) + "% DEFEATS", percentageOfDefeats),
//				new PieChart.Data(Math.round(percentageOfDrawn) + "% DRAWN", percentageOfDrawn));
		//pieChartResults.setData(listPieMatches);
//		Player playerMostRepresentative = App.sharedInstance.getDaoTeam().retriveMostRepresentativePlayer(teamSelected);
		//labelMostRepresentative.setText(playerMostRepresentative.getFullName());
//		try {
//			double res = App.sharedInstance.getDaoTeam().retrieveTeamTotalMarketValue(teamSelected);
//			BigDecimal resRound = new BigDecimal(res);
//			if(res > Math.pow(10,6)) {
//				BigDecimal div = new BigDecimal(Math.pow(10, 6));
//				BigDecimal marketValue = resRound.divide(div);
//				//labelMarketValue.setText(String.valueOf(marketValue)+" mln \u20ac");
//			}else {
//				//labelMarketValue.setText(String.valueOf(resRound)+" \u20ac");
//			}
//		} catch (DAOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//			Utils.decode(teamSelected.getShield(), "shardImage.png");
//			Image image = new Image("file:shardImage.png");
//			//imageShield.setImage(image);;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
//	public void initializedTable(Player playerSelected) {
//		List<DetailedPerformance> detailedPerformances = playerSelected.getDetailedPerformances();
//		ObservableList<DetailedPerformanceTable> list = FXCollections.observableArrayList();
//		for(DetailedPerformance dp: detailedPerformances) {
//			list.add(new DetailedPerformanceTable(dp));
//		}
//		columnSeason.setCellValueFactory(cellData -> cellData.getValue().getSeason());
//		columnTeam.setCellValueFactory(cellData -> cellData.getValue().getTeam());
//		columnCalls.setCellValueFactory(cellData -> cellData.getValue().getCalls().asObject());
//		columnPresences.setCellValueFactory(cellData -> cellData.getValue().getPresences().asObject());
//		columnAveragePoints.setCellValueFactory(cellData -> cellData.getValue().getAveragePoints().asObject());
//		columnGoals.setCellValueFactory(cellData -> cellData.getValue().getGoals().asObject());
//		columnOwnGoals.setCellValueFactory(cellData -> cellData.getValue().getOwnGoals().asObject());
//		columnSobstitutionOn.setCellValueFactory(cellData -> cellData.getValue().getSubstitutionOn().asObject());
//		columnSobstitutionOff.setCellValueFactory(cellData -> cellData.getValue().getSubstitutionOff().asObject());
//		columnYellowCards.setCellValueFactory(cellData -> cellData.getValue().getYellowCards().asObject());
//		columnDoubleYellowCards.setCellValueFactory(cellData -> cellData.getValue().getDoubleYellowCards().asObject());
//		columnRedCards.setCellValueFactory(cellData -> cellData.getValue().getRedCards().asObject());
//		columnMinutesPlayed.setCellValueFactory(cellData -> cellData.getValue().getMinutesPlayed().asObject());
//		columnAssists.setCellValueFactory(cellData -> cellData.getValue().getAssists().asObject());
//		columnCleanSheets.setCellValueFactory(cellData -> cellData.getValue().getCleanSheets().asObject());	
//		columnGoalConceded.setCellValueFactory(cellData -> cellData.getValue().getGoalConceded().asObject());
//		columnMinutesPerGoal.setCellValueFactory(cellData -> cellData.getValue().getMinutesPerGoal().asObject());
//		columnPenaltyGoals.setCellValueFactory(cellData -> cellData.getValue().getPenalityGoals().asObject());
//		if(playerSelected.getRole().equals("Portiere")) {
//			columnCleanSheets.setVisible(true);
//			columnGoalConceded.setVisible(true);
//			columnAssists.setVisible(false);
//			columnPenaltyGoals.setVisible(false);
//			columnMinutesPerGoal.setVisible(false);
//		}else {
//			columnCleanSheets.setVisible(false);
//			columnGoalConceded.setVisible(false);
//			columnAssists.setVisible(true);
//			columnPenaltyGoals.setVisible(true);
//			columnMinutesPerGoal.setVisible(true);
//		}
//		tablePlayers.setItems(list);
//	}
	
	public void onClickEventOnLeague(MouseEvent event) throws DAOException{
		League leagueSelected = listLeague.getSelectionModel().getSelectedItem();
		labelLeague.setText(leagueSelected.getFullname().toUpperCase());
		List<Team> teams = App.sharedInstance.getDaoQuery().countTeams(leagueSelected);
		ObservableList<Team> listTeams = FXCollections.observableArrayList(teams);
		listTeamsOfALeague.setItems(listTeams);
		//barCharLeague.getData().clear();
//		try {
			//labelMostWinningTeam.setText(App.sharedInstance.getDaoLeague().retrieveMostWinningTeam(leagueSelected).getName());
			//labelMostLosingTeam.setText(App.sharedInstance.getDaoLeague().retrieveMostLosingTeam(leagueSelected).getName());
//		} catch (DAOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//			Player playerOldest = App.sharedInstance.getDaoPlayer().retrieveOlderPlayer(leagueSelected);
//			if(playerOldest != null) {
//				//labelOldest.setText(playerOldest.getFullName());
//			}
//			Player playerYoungest = App.sharedInstance.getDaoPlayer().retrieveYougerPlayer(leagueSelected);
//			if(playerYoungest != null) {
//				//labelYoungest.setText(playerYoungest.getFullName());
//			}
//			Player playerMostPaid = App.getSharedInstance().getDaoPlayer().retrieveMostValuedPlayer(leagueSelected);
////			if(playerMostPaid != null) {
////				//labelHigest.setText(playerMostPaid.getFullName());
////			}
////		} catch (DAOException e) {
////		// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
	}
	
//	public void ActionShowAvgAge(ActionEvent event) throws DAOException {
//		if(!labelLeague.getText().isEmpty()) {
//			League leagueSelected = App.getSharedInstance().getDaoLeague().retrieveLeague(labelLeague.getText());
//			barCharLeague.getData().clear();
//			XYChart.Series series = new XYChart.Series<>();
//			List<Team> teamsOnLeague = App.sharedInstance.getDaoTeam().retrieveTeamsFromLeague(leagueSelected);
//			for(Team team: teamsOnLeague) {
//				String teamName = team.getName();
//				double avg = App.sharedInstance.getDaoTeam().retrieveAverageAgeFromTeam(team);
//				BigDecimal average = new BigDecimal(avg);
//				BigDecimal averageRounded = average.round(new MathContext(2));
//				series.getData().add(new XYChart.Data(teamName, averageRounded));
//				series.setName("TEAM AGE");
//				
//			}
//			barCharLeague.getData().addAll(series);
//		}
//	}
	
//	public void onClickEventOnPlayer(MouseEvent event) {
//        Player playerSelected = listPlayer.getSelectionModel().getSelectedItem();
//    	if (playerSelected.getFullName().isEmpty()) {
//    		labelNamePlayer.setText(playerSelected.getSurname().toUpperCase());
//    	} else {
//    		labelNamePlayer.setText(playerSelected.getFullName().toUpperCase());
//    	}
//    	labelDateBirth.setText(playerSelected.getBornDate());
//    	labelCitizenshipPlayer.setText(playerSelected.getNation());
//    	labelPositionPlayer.setText(playerSelected.getRole());
//    	labelLeaguePlayer.setText(playerSelected.getLeague());
//    	labelTeamPlayer.setText(playerSelected.getTeam());
//    	labelMarketValuePlayer.setText(playerSelected.getMarketValueString());
//    	lineChartTrend.getData().clear();
//		XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();
//		List<MarketValue> listMarketValue = playerSelected.getMarketValueHistory();
//		for (MarketValue value : listMarketValue)  {
//			series.getData().add(new XYChart.Data<String, Double>(value.getDateString(),value.getMarketValue()));		
//		}
//		lineChartTrend.setTitle("TREND MARKET VALUE");
//		series.setName("MARKET VALUE");
//		lineChartTrend.getData().addAll(series);
//		initializedTable(playerSelected);
//    }
	
//	public void ActionRetrievePlayer(ActionEvent event) {
//		try {
//			String textPlayer = fieldPlayer.getText();
//			if (textPlayer.isEmpty()) {
//				Alert alert = new Alert(AlertType.WARNING, " Empty field", ButtonType.CLOSE);
//				alert.showAndWait();
//			} else {
//				List<Player> listSearchedPlayers = App.sharedInstance.getDaoPlayer().retrievePlayers(textPlayer);
//				ObservableList listP = FXCollections.observableArrayList(listSearchedPlayers);
//				listPlayer.setItems(listP);
//				listPlayer.setOnMouseClicked(e->onClickEventOnPlayer(e));
//				if (listSearchedPlayers.isEmpty()) {
//					Alert alert = new Alert(AlertType.WARNING, "No player selected", ButtonType.CLOSE);
//					alert.showAndWait();
//				}
//			}
//			
//		} catch(DAOException e) {
//			Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
//			alert.show();
//		}
//		
//		
//		}
//	
	//TODO
	public void ActionRetrieveLeague(ActionEvent event) {
		try {
			String text = fieldLeague.getText();
			if (text.isEmpty()) {
				listLeague.getItems().clear();
				listLeague.setItems(retriveLeagueFromComboBoxPlayer());
			} else {
				//TO CHECK
				League listSearchedLeagues = App.sharedInstance.getDaoLeagueGraph().retrieve(text);
				ObservableList<League> list = FXCollections.observableArrayList(listSearchedLeagues);
				listLeague.getItems().clear();
				listLeague.setItems(list);
				listLeague.setOnMouseClicked(e->{
					try {
						onClickEventOnLeague(e);
					} catch (DAOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
			}
		} catch(DAOException e) {
			Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
			alert.showAndWait();
		}
		
	}
	
	//TODO
	public void ActionUpdateLeague(ActionEvent event) {
		try {
			System.out.println("AppController.ActionUpdateLeague()");
			FileChooser chooser = new FileChooser();
			File file = chooser.showOpenDialog(App.getSharedInstance().getPrimaryStage());
			if (file != null) {
				String filePath = file.getAbsolutePath();
				String json = App.getSharedInstance().getReadFromFile().readLocalJSON(filePath);
				if(!json.contains("matches") && !json.contains("year") && !json.contains("link")) {
					Alert alert = new Alert(AlertType.ERROR, "File is not in the correct format:\nit must be the json file of"
							+ " the object League", ButtonType.CLOSE);
					alert.show();
					return;
				}
				League league = new Gson().fromJson(json, League.class);
				if(App.getSharedInstance().getDaoLeagueGraph().exists(league)) {
					App.getSharedInstance().getDaoLeagueGraph().updateLeague(league.getFullname(), league);
					//
					listLeague.getItems().add(league);
//					//comboBoxLeaguesPlayer.getItems().clear();
//					//comboBoxLeaguesTeam.getItems().clear();
//					//comboBoxLeaguesMatches.getItems().clear();
//					//comboBoxLeaguesPlayer.getItems().addAll(retriveLeagueFromComboBoxPlayer());
//					//comboBoxLeaguesTeam.getItems().addAll(retriveLeagueFromComboBoxPlayer());
//					//comboBoxLeaguesMatches.getItems().addAll(retriveLeagueFromComboBoxPlayer());
//					//
					System.out.println(league.getName() + " already exists");
				} else {
					App.getSharedInstance().getDaoLeagueGraph().createLeague(league);
					listLeague.getItems().add(league);
//					//comboBoxLeaguesPlayer.getItems().clear();
//					//comboBoxLeaguesTeam.getItems().clear();
//					//comboBoxLeaguesMatches.getItems().clear();
//					//comboBoxLeaguesPlayer.getItems().addAll(retriveLeagueFromComboBoxPlayer());
//					//comboBoxLeaguesTeam.getItems().addAll(retriveLeagueFromComboBoxPlayer());
//					//comboBoxLeaguesMatches.getItems().addAll(retriveLeagueFromComboBoxPlayer());
					System.out.println(league.getName() + " doesn't exist");
					
				}
			} else {
				System.out.println("File is null");
			}
		} catch(DAOException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
			alert.show();
		} catch(JsonSyntaxException jse) {
			Alert alert = new Alert(AlertType.ERROR, "The file isn't in the correct format", ButtonType.CLOSE);
			alert.show();
		}
	}

	public void ActionUpdateTeams(ActionEvent event) {
//		try {
//			System.out.println("UPDATE TEAM OK");
//			FileChooser chooser = new FileChooser();
//			File file = chooser.showOpenDialog(App.getSharedInstance().getPrimaryStage());
//			if (file != null) {
//				String filePath = file.getAbsolutePath();
//
//				String json = App.getSharedInstance().getReadFromFile().readLocalJSON(filePath);
//				if(!json.contains("rosterSize") && !json.contains("shield") && !json.contains("championshipCode")) {
//					Alert alert = new Alert(AlertType.ERROR, "File is not in the correct format", ButtonType.CLOSE);
//					alert.showAndWait();
//					return;
//				}
//				Team[] teams = new Gson().fromJson(json, Team[].class);
//				for (Team team : teams) {
//					if(App.getSharedInstance().getDaoTeam().exists(team)) {
//						App.getSharedInstance().getDaoTeam().updateTeam(team.getFullName(), team);
//						//listTeams.getItems().add(team);
//						System.out.println(team.getFullName() + " already exists");
//					} else {
//						App.getSharedInstance().getDaoTeam().createTeam(team);
//						//listTeams.getItems().add(team);
//						System.out.println(team.getFullName() + " doesn't exist");
//					}
//				}
//			} else {
//				System.out.println("File is null");
//			}
//		} catch(DAOException e) {
//			Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
//			alert.showAndWait();
//		} catch(JsonSyntaxException jse) {
//			Alert alert = new Alert(AlertType.ERROR, "The file isn't in the correct format", ButtonType.CLOSE);
//			alert.showAndWait();
//		}
		
	}
	//TODO
//	public void ActionUpdatePlayers(ActionEvent event) {
//		try {
//			System.out.println("UPDATE TEAM OK");
//			FileChooser chooser = new FileChooser();
//			File file = chooser.showOpenDialog(App.getSharedInstance().getPrimaryStage());
//			if (file != null) {
//				String filePath = file.getAbsolutePath();
//
//				String json = App.getSharedInstance().getReadFromFile().readLocalJSON(filePath);
//				if(!json.contains("bornDate") || !json.contains("detailedPerformances") || !json.contains("marketValueHistory")) {
//					Alert alert = new Alert(AlertType.ERROR, "File is not in the correct format", ButtonType.CLOSE);
//					alert.showAndWait();
//					return;
//				}
//				Player[] players = new Gson().fromJson(json, Player[].class);
//				for (Player player : players) {
//					if(App.getSharedInstance().getDaoPlayer().exists(player)) {
//						App.getSharedInstance().getDaoPlayer().updatePlayer(player.getFullName(), player);
//						System.out.println(player.getSurname() + " updated");
//						System.out.println("Player Image: " + player.getImagePlayer());
//					} else {
//						App.getSharedInstance().getDaoPlayer().createPlayer(player);
//						System.out.println(player.getSurname() + " created");
//					}
//				}
//			} else {
//				System.out.println("File is null");
//			}
//		} catch(DAOException e) {
//			Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
//			alert.showAndWait();
//		} catch(JsonSyntaxException jse) {
//			Alert alert = new Alert(AlertType.ERROR, "The file isn't in the correct format", ButtonType.CLOSE);
//			alert.showAndWait();
//		}
//		
//	}
	
//	public void ActionRetrieveRound(ActionEvent event) {
//		leagueSelectedToRound = comboBoxLeaguesMatches.getSelectionModel().getSelectedItem();
//		List<Match> matchesLeague = leagueSelectedToRound.getMatches();
//		Set<Integer> matchesRoundSet = new HashSet<>();
//		for (Match match: matchesLeague) {
//			String a = match.getRound().replaceAll("Round", "");
//			a = a.trim();
//			int numberRound = Integer.parseInt(a);
//			matchesRoundSet.add(numberRound);
//		}
//		List<Integer> matchesRound = new ArrayList<>(matchesRoundSet);
//		Collections.sort(matchesRound);
//		ObservableList<Integer> listRound = FXCollections.observableArrayList(matchesRound);
//		comboBoxRoundMatches.setItems(listRound);
//		
//	}
	
//	public void ActionRetrieveMatches(ActionEvent event) throws DAOException {
//		Integer round = comboBoxRoundMatches.getSelectionModel().getSelectedItem();
//		if (round == null) {
//			return;
//		} 
//		List<Match> roundMatches = App.getSharedInstance().getDaoMatch().retrieveMatchesbyRound(round,leagueSelectedToRound);
//		ObservableList<Match> listMatchesRound = FXCollections.observableArrayList(roundMatches);
//		listMatches.setItems(listMatchesRound);
//		listMatches.setOnMouseClicked(e -> {
//			try {
//				onClickEventOnMatches(e);
//			} catch (DAOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		});
		
		
//	}
	
//	public void onClickEventOnMatches(MouseEvent event) throws DAOException, IOException {
//		League league = comboBoxLeaguesMatches.getSelectionModel().getSelectedItem();
//		System.out.println("League selected: " + league.getFullname());
//		Match match = listMatches.getSelectionModel().getSelectedItem();
//		System.out.println("Match selected: " + match);
//		labelResultMatch.setText(match.toString().toUpperCase());
//		inizializeTableMatches(match);
//		Team teamHomeMatch = new Team();
//		Team teamAwayMatch = new Team();
//		teamHomeMatch.setName(match.getNameHome());
//		teamHomeMatch.setChampionshipCode(league.getChampionshipCode());
//		teamAwayMatch.setName(match.getNameAway());
//		teamAwayMatch.setChampionshipCode(league.getChampionshipCode());
//		System.out.println(teamHomeMatch.getName());
//		String shieldHome = App.getSharedInstance().getDaoTeam().retrieveShield(teamHomeMatch);
//		System.out.println("Shield: "+ shieldHome);
//		if (shieldHome.isEmpty()) {
//			Image imageStandard = new Image(getClass().getResourceAsStream("/resources/standard shard.png"));
//			imageTeamHome.setImage(imageStandard);
//		} else {
//			Utils.decode(shieldHome,"teamHomeImage.png" );
//			Image imageHome = new Image("file:teamHomeImage.png");
//			imageTeamHome.setImage(imageHome);
//		}
//		
//		String shieldAway = App.getSharedInstance().getDaoTeam().retrieveShield(teamAwayMatch);
//		if (shieldAway.isEmpty()) {
//			Image imageStandard = new Image(getClass().getResourceAsStream("/resources/standard shard.png"));
//			imageTeamAway.setImage(imageStandard);
//		} else {
//			Utils.decode(shieldAway,"teamAwayImage.png" );
//			Image imageAway = new Image("file:teamAwayImage.png");
//			imageTeamAway.setImage(imageAway);
//		}
//		
//		
//		
//	}
//	public void inizializeTableMatches(Match match) {
//		if (match.getListOfStatistics().isEmpty()) {
//			System.out.println("No statistics for this match");
//			return;
//		}
//		List<MatchPerformanceTable> matchPerformanceTables = match.getListOfStatistics();
//		columnHome.setCellValueFactory(cellData -> cellData.getValue().getHome());
//		columnStatistics.setCellValueFactory(cellData -> cellData.getValue().getStatistic());
//		columnAway.setCellValueFactory(cellData-> cellData.getValue().getAway());
//		ObservableList<MatchPerformanceTable> list = FXCollections.observableArrayList(matchPerformanceTables);
//		matchesResults.setItems(list);
//	
//		
//	}
	
	public void ActionToLogin(ActionEvent event) throws IOException {
		if (this.buttonLogin.getText().equalsIgnoreCase("LOGIN")) {
			Parent login = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene loginScene = new Scene(login);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app_stage.setScene(loginScene);
			app_stage.setMinHeight(739);
			app_stage.setMinWidth(866);
			app_stage.show();
		} else {
			this.buttonLogin.setText("LOGIN");
			this.buttonUpdateTeam.setVisible(false);
			this.buttonUpdateLeague.setVisible(false);
			this.buttoUpdatePlayer.setVisible(false);
			this.buttonDeleteLeague.setVisible(false);
			this.buttonDeletePlayer.setVisible(false);
			this.buttonDeleteTeam.setVisible(false);
			Alert alert = new Alert(AlertType.INFORMATION, "LOGOUT DONE", ButtonType.CLOSE);
			alert.showAndWait();
			
		}
	}
	
	public void ActionDeleteLeague(ActionEvent e) {
		League leagueSelected = listLeague.getSelectionModel().getSelectedItem();
		System.out.println(leagueSelected.getName());
		try {
			App.getSharedInstance().getDaoLeagueGraph().delete(leagueSelected);
			listLeague.getItems().remove(leagueSelected);
			System.out.println("DELETE OK");
		} catch (DAOException e1) {
			Alert alert = new Alert(AlertType.ERROR, e1.getMessage(), ButtonType.CLOSE);
			alert.showAndWait();
			return;
		}
//		//comboBoxLeaguesPlayer.getItems().clear();
//		//comboBoxLeaguesTeam.getItems().clear();
//		//comboBoxLeaguesMatches.getItems().clear();
//		//comboBoxLeaguesPlayer.getItems().addAll(retriveLeagueFromComboBoxPlayer());
//		//comboBoxLeaguesTeam.getItems().addAll(retriveLeagueFromComboBoxPlayer());
//		//comboBoxLeaguesMatches.getItems().addAll(retriveLeagueFromComboBoxPlayer());
//		System.out.println("DELETE OK");
	}
	
	public void ActionDeleteTeam(ActionEvent e) throws DAOException {
//		Team teamSelected = listTeams.getSelectionModel().getSelectedItem();
//		System.out.println(teamSelected.getName());
//		App.getSharedInstance().getDaoTeam().deleteTeam(teamSelected);
//		listTeams.getItems().remove(teamSelected);
//		System.out.println("DELETE OK");
	}
	
//	public void ActionDeletePlayer(ActionEvent e) throws DAOException {
//		Player playerSelected = listPlayer.getSelectionModel().getSelectedItem();
//		System.out.println(playerSelected.getName());
//		App.getSharedInstance().getDaoPlayer().deletePlayer(playerSelected);
//		listPlayer.getItems().remove(playerSelected);
//		System.out.println("DELETE OK");
//	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//comboBoxTeamsPlayer.setItems(comboDefault);
		//comboBoxLeaguesPlayer.setItems(retriveLeagueFromComboBoxPlayer());
		//comboBoxLeaguesTeam.setItems(retriveLeagueFromComboBoxPlayer());
		//comboBoxLeaguesMatches.setItems(retriveLeagueFromComboBoxPlayer());
		List<Team> teams = new ArrayList<>();
		try {
			teams = App.sharedInstance.getDaoTeamGraph().retrieveAllTeams();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObservableList<Team> list = FXCollections.observableArrayList(teams);
		listTeams.setItems(list);
		listLeague.setItems(retriveLeagueFromComboBoxPlayer());
		//barCharLeague.getData().add(emptyChart);
	}
	
	
}
