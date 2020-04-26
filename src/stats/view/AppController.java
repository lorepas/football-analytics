package stats.view;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import stats.App;
import stats.model.League;
import stats.model.Team;
import stats.persistence.DAOException;

public class AppController implements Initializable{
	
	@FXML javafx.scene.control.Button searchButton;
	@FXML javafx.scene.control.TextField fieldTeam;
	@FXML ListView<Team> listTeams; 
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
	@FXML Label labelNameTeam;
	@FXML Label labelNumberMatchesWin;
	@FXML Label labelNumberMatchesLost;
	@FXML Label labelNumberMatchesDrawn;
	@FXML Label labelLeague; 
	ObservableList comboDefault = FXCollections.observableArrayList();
	private League leagueSelectedToRound = null;
	private League leagueSelectedCombo = null;
	@FXML ImageView backLeague;
	@FXML ImageView backTeam;
	
	public ObservableList<League> refreshListLeague(){
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
	
	public ObservableList<Team> refreshListTeam(){
		List<Team> teams = new ArrayList<>();
		try {
			teams = App.sharedInstance.getDaoTeamGraph().retrieveAllTeams();
		} catch (DAOException ec) {
			// TODO Auto-generated catch block
			ec.printStackTrace();
		}
		ObservableList<Team> list = FXCollections.observableArrayList(teams);
		return list;
	}
	
	public void ActionRetrieveTeam(ActionEvent event) {
		try {
			String text = fieldTeam.getText();
			if (text.isEmpty()) {
				Alert alert = new Alert(AlertType.WARNING, "Empty field", ButtonType.CLOSE);
				alert.showAndWait();
			} else {
				List<Team> listSearchedTeams = App.sharedInstance.getDaoTeamGraph().retrieveTeams(text);
				if (listSearchedTeams.isEmpty()) {
					Alert alert = new Alert(AlertType.WARNING, "No team selected", ButtonType.CLOSE);
					alert.showAndWait();
					return;
				}
				ObservableList<Team> list = FXCollections.observableArrayList(listSearchedTeams);
				listTeams.getItems().clear();
				listTeams.setItems(list);
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
	}
	
	
	public void onClickEventOnLeague(MouseEvent event) throws DAOException{
		League leagueSelected = listLeague.getSelectionModel().getSelectedItem();
		labelLeague.setText(leagueSelected.getName().toUpperCase()+" "+leagueSelected.getYear());
		List<Team> teams = App.sharedInstance.getDaoQuery().countTeams(leagueSelected);
		ObservableList<Team> listTeams = FXCollections.observableArrayList(teams);
		listTeamsOfALeague.setItems(listTeams);
	}

	public void ActionRetrieveLeague(ActionEvent event) {
		try {
			String text = fieldLeague.getText();
			if (text.isEmpty()) {
				Alert alert = new Alert(AlertType.WARNING, "Empty field", ButtonType.CLOSE);
				alert.showAndWait();
			} else {
				List<League> listSearchedLeagues = App.sharedInstance.getDaoLeagueGraph().retrieveLeagues(text);
				if (listSearchedLeagues.isEmpty()) {
					Alert alert = new Alert(AlertType.WARNING, "No league selected", ButtonType.CLOSE);
					alert.showAndWait();
					return;
				}
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
					System.out.println(league.getFullname() + " already exists");
					App.getSharedInstance().getDaoLeagueGraph().updateLeague(league.getFullname(), league);
					listLeague.setItems(refreshListLeague());
					listTeams.getItems().clear();
					listTeams.setItems(refreshListTeam());
					System.out.println(league.getName() + " already exists");
				} else {
					App.getSharedInstance().getDaoLeagueGraph().createLeague(league);
					listLeague.getItems().add(league);
					listTeams.getItems().clear();
					listTeams.setItems(refreshListTeam());
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
	
	public void ActionDeleteTeam(ActionEvent e) throws DAOException {
//		Team teamSelected = listTeams.getSelectionModel().getSelectedItem();
//		System.out.println(teamSelected.getName());
//		App.getSharedInstance().getDaoTeam().deleteTeam(teamSelected);
//		listTeams.getItems().remove(teamSelected);
//		System.out.println("DELETE OK");
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
			buttonLogin.setText("LOGIN");
			buttonUpdateTeam.setVisible(false);
			buttonUpdateLeague.setVisible(false);
			buttonDeleteLeague.setVisible(false);
			buttonDeleteTeam.setVisible(false);
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
			listTeams.getItems().clear();
			listTeams.setItems(refreshListTeam());
			System.out.println("DELETE OK");
		} catch (DAOException e1) {
			Alert alert = new Alert(AlertType.ERROR, e1.getMessage(), ButtonType.CLOSE);
			alert.showAndWait();
			return;
		}
		System.out.println("DELETE OK");
	}
	
	public void ActionBackListLeague(MouseEvent e) {
		fieldLeague.setText("");
		listLeague.getItems().clear();
		listLeague.setItems(refreshListLeague());
	}
	
	public void ActionBackListTeam(MouseEvent e) {
		fieldTeam.setText("");
		listTeams.getItems().clear();
		listTeams.setItems(refreshListTeam());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listTeams.setItems(refreshListTeam());
		listLeague.setItems(refreshListLeague());
	}
	
}
