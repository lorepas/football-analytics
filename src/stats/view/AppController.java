package stats.view;

import java.awt.Button;
import java.awt.TextField;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
	
	
	
	public void ActionRetrieveTeam(ActionEvent event) throws DAOException {
		String text = fieldTeam.getText();
		List<Team> listSearchedTeams = App.sharedInstance.getDaoTeam().retrieveTeams(text);
		ObservableList list = FXCollections.observableArrayList(listSearchedTeams);
		listTeams.setItems(list);
		}
	
	public void ActionRetrievePlayer(ActionEvent event) throws DAOException {
		String textPlayer = fieldPlayer.getText();
		List<Player> listSearchedPlayers = App.sharedInstance.getDaoPlayer().retrievePlayers(textPlayer);
		//CONTROL OF LIST
		if (listSearchedPlayers.isEmpty()) {
			System.out.println("LISTA VUOTA");
		}
		//
		ObservableList listP = FXCollections.observableArrayList(listSearchedPlayers);
		listTeams.setItems(listP);
		}

}
