package stats.view;

import java.awt.Button;
import java.awt.TextField;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import stats.App;
import stats.model.Match;
import stats.model.Player;
import stats.model.Team;
import stats.persistence.DAOException;

public class AppController {
	
	@FXML javafx.scene.control.Button searchButton;
	@FXML javafx.scene.control.TextField fieldTeam;
	@FXML ListView<Team> listTeams;
	@FXML javafx.scene.control.Button searchPlayerButton;
	@FXML javafx.scene.control.TextField fieldPlayer;
	@FXML ListView<Team> listPlayer;
	@FXML javafx.scene.control.Button buttonUpdateTeam;
	@FXML javafx.scene.control.Button buttonLogin;
	@FXML javafx.scene.control.Button buttonUpdateLeague;
	@FXML javafx.scene.control.Button buttonDeleteLeague;
	@FXML javafx.scene.control.Button buttonDeleteTeam;
	@FXML javafx.scene.control.Button buttoUpdatePlayer;
	@FXML javafx.scene.control.Button buttonDeletePlayer;
	@FXML TableView<Match> tableMatches;
	@FXML BarChart<Team, Float> barCharLeague;
	@FXML Label labelYoungest;
	@FXML Label labelOldest;
	@FXML Label labelHigest;
	@FXML Label labelNameTeam;
	@FXML Label labelMarketValue;
	@FXML Label labelMostRepresentative;
	@FXML PieChart pieChartForeign;
	@FXML PieChart pieChartResults;
	
	
	
	
	
	
	
	public void ActionRetrieveTeam(ActionEvent event) {
		try {
			String text = fieldTeam.getText();
			List<Team> listSearchedTeams = App.sharedInstance.getDaoTeam().retrieveTeams(text);
			ObservableList list = FXCollections.observableArrayList(listSearchedTeams);
			listTeams.setItems(list);
		} catch(DAOException e) {
			Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
			alert.showAndWait();
		}
		
		}
	
	public void ActionRetrievePlayer(ActionEvent event) {
		try {
			String textPlayer = fieldPlayer.getText();
			List<Player> listSearchedPlayers = App.sharedInstance.getDaoPlayer().retrievePlayers(textPlayer);
			ObservableList listP = FXCollections.observableArrayList(listSearchedPlayers);
			listPlayer.setItems(listP);
		} catch(DAOException e) {
			Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
			alert.showAndWait();
		}
		
		
		}
	
	//TODO
	public void ActionRetrieveLeague(ActionEvent event) {
//		try {
//			
//			
//		} catch(DAOException e) {
//			Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
//			alert.showAndWait();
//		}
		
	}
	
	
	//TODO
	public void ActionUpdateLeague(ActionEvent event) {
//		try {
//			
//		} catch(DAOException e) {
//			Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
//			alert.showAndWait();
//		}
//		
		}
	//TODO
	public void ActionUpdateTeams(ActionEvent event) {
		try {
			System.out.println("UPDATE TEAM OK");
			FileChooser chooser = new FileChooser();
			File file = chooser.showOpenDialog(App.getSharedInstance().getPrimaryStage());
			if (file != null) {
				String filePath = file.getAbsolutePath();

				String json = App.getSharedInstance().getReadFromFile().readLocalJSON(filePath);
				if(!json.contains("rosterSize") && !json.contains("shield") && !json.contains("championshipCode")) {
					Alert alert = new Alert(AlertType.ERROR, "File is not in the correct format", ButtonType.CLOSE);
					alert.showAndWait();
					return;
				}
				Team[] teams = new Gson().fromJson(json, Team[].class);
				for (Team team : teams) {
					if(App.getSharedInstance().getDaoTeam().exists(team)) {
						App.getSharedInstance().getDaoTeam().updateTeam(team.getFullName(), team);
						System.out.println(team.getFullName() + " already exists");
					} else {
						App.getSharedInstance().getDaoTeam().createTeam(team);
						System.out.println(team.getFullName() + " doesn't exist");
					}
				}
			} else {
				System.out.println("File is null");
			}
		} catch(DAOException e) {
			Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
			alert.showAndWait();
		} catch(JsonSyntaxException jse) {
			Alert alert = new Alert(AlertType.ERROR, "The file isn't in the correct format", ButtonType.CLOSE);
			alert.showAndWait();
		}
		
	}
	//TODO
	public void ActionUpdatePlayers(ActionEvent event) {
		try {
			System.out.println("UPDATE TEAM OK");
			FileChooser chooser = new FileChooser();
			File file = chooser.showOpenDialog(App.getSharedInstance().getPrimaryStage());
			if (file != null) {
				String filePath = file.getAbsolutePath();

				String json = App.getSharedInstance().getReadFromFile().readLocalJSON(filePath);
				if(!json.contains("bornDate") || !json.contains("detailedPerformances") || !json.contains("marketValueHistory")) {
					Alert alert = new Alert(AlertType.ERROR, "File is not in the correct format", ButtonType.CLOSE);
					alert.showAndWait();
					return;
				}
				Player[] players = new Gson().fromJson(json, Player[].class);
				for (Player player : players) {
					if(App.getSharedInstance().getDaoPlayer().exists(player)) {
						App.getSharedInstance().getDaoPlayer().updatePlayer(player.getFullName(), player);
						System.out.println(player.getSurname() + " updated");
					} else {
						App.getSharedInstance().getDaoPlayer().createPlayer(player);
						System.out.println(player.getSurname() + " created");
					}
				}
			} else {
				System.out.println("File is null");
			}
		} catch(DAOException e) {
			Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
			alert.showAndWait();
		} catch(JsonSyntaxException jse) {
			Alert alert = new Alert(AlertType.ERROR, "The file isn't in the correct format", ButtonType.CLOSE);
			alert.showAndWait();
		}
		
	}
	
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
	
}
