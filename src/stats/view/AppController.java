package stats.view;

import java.awt.Button;
import java.awt.TextField;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
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
//		try {
		System.out.println("UPDATE TEAM OK");
		
		
//			
//		} catch(DAOException e) {
//			Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
//			alert.showAndWait();
//		}
		
	}
	//TODO
	public void ActionUpdatePlayers(ActionEvent event) {
//		try {
			System.out.println("UPDATE PLAYER OK");
//		} catch(DAOException e) {
//			Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
//			alert.showAndWait();
//			
//		}
		
	}

	
	

}
