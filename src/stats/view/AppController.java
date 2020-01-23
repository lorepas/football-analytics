package stats.view;

import java.awt.Button;
import java.awt.TextField;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import stats.App;
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
				Team[] teams = new Gson().fromJson(json, Team[].class);
				for (Team team : teams) {
					if(App.getSharedInstance().getDaoTeam().exists(team)) {
						System.out.println(team.getFullName() + " already exists");
					} else {
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
				Player[] players = new Gson().fromJson(json, Player[].class);
				for (Player player : players) {
					if(App.getSharedInstance().getDaoPlayer().exists(player)) {
//						App.getSharedInstance().getDaoPlayer().updatePlayer(player.getFullName(), player);
						System.out.println(player.getSurname() + "updated");
					} else {
						App.getSharedInstance().getDaoPlayer().createPlayer(player);
						System.out.println(player.getSurname() + "created");
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

	
	

}
