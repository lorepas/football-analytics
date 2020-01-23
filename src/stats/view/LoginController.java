package stats.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import stats.App;

public class LoginController {
	
	@FXML javafx.scene.control.Button buttonToHome;
	
public void ActionToHomePage(ActionEvent event) throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
	Parent homePage = loader.load();
	Scene homeScene = new Scene(homePage);
	Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	AppController controller = loader.getController();
	app_stage.setScene(homeScene);
	controller.buttonLogin.setText("LOGOUT");
	controller.buttonUpdateTeam.setVisible(true);
	app_stage.show();
	}


}
