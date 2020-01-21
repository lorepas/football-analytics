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
import stats.model.Team;
import stats.persistence.DAOException;

public class AppController {
	
	@FXML javafx.scene.control.Button searchButton;
	@FXML javafx.scene.control.TextField fieldTeam;
	@FXML ListView<Team> listTeams;
	
	
	
	public void ActionRetrieveTeam(ActionEvent event) throws DAOException {
		String text = fieldTeam.getText();
		List<Team> listSearchedTeams = App.sharedInstance.getDaoTeam().retrieveTeams(text);
		ObservableList list = FXCollections.observableArrayList(listSearchedTeams);
		listTeams.setItems(list);
		}

}
