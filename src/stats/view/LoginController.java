package stats.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import stats.App;
import stats.persistence.DAOException;

public class LoginController {
	private Stage primaryStage;
	
	@FXML javafx.scene.control.Button buttonToHome;
	@FXML javafx.scene.control.Button buttonGuest;
	@FXML javafx.scene.control.TextField fieldUsername;
	@FXML javafx.scene.control.TextField fieldPassword;
	
public void ActionToHomePage(ActionEvent event) throws IOException {
	
	String username = fieldUsername.getText();
	String pwd = fieldPassword.getText();
	try {
		if (App.getSharedInstance().getDaoUser().Login(username, pwd)) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		Parent homePage = loader.load();
		Scene homeScene = new Scene(homePage);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AppController controller = loader.getController();
		app_stage.setScene(homeScene);
		controller.buttonLogin.setText("LOGOUT");
		controller.buttonUpdateTeam.setVisible(true);
		controller.buttonUpdateLeague.setVisible(true);
		controller.buttonDeleteLeague.setVisible(true);
		controller.buttonDeleteTeam.setVisible(true);
		controller.buttonDeletePlayer.setVisible(true);
		controller.buttoUpdatePlayer.setVisible(true);
		app_stage.show();
		
		} else {
			Alert alert = new Alert(AlertType.INFORMATION, "Wrong insert! Try again or if you want you can login as a guest.", ButtonType.CLOSE);
			alert.showAndWait();
		}
	} catch (DAOException e) {
		Alert alert = new Alert(AlertType.ERROR, "Delete " + e.getMessage(), ButtonType.CLOSE);
		alert.showAndWait();
	}
	
	
}
	public void ActionAsGuest(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		Parent homePage = loader.load();
		Scene homeScene = new Scene(homePage);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AppController controller = loader.getController();
		app_stage.setScene(homeScene);
		//controller.buttonLogin.setText("LOGOUT");
		controller.buttonUpdateTeam.setVisible(false);
		controller.buttonUpdateLeague.setVisible(false);
		controller.buttonDeleteLeague.setVisible(false);
		controller.buttonDeleteTeam.setVisible(false);
		controller.buttonDeletePlayer.setVisible(false);
		controller.buttoUpdatePlayer.setVisible(false);
		app_stage.show();
	}


}
