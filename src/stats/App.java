package stats;
	
import java.util.ArrayList;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import java.util.List;

import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import stats.model.User;
import stats.persistence.DAOLeagueMongo;
import stats.persistence.DAOMatchMongo;
import stats.persistence.DAOPlayerMongo;
import stats.persistence.DAOTeamMongo;
import stats.persistence.DAOUserKV;
import stats.persistence.DAOUserMongo;
import stats.persistence.IDAOLeague;
import stats.persistence.IDAOMatch;
import stats.persistence.IDAOPlayer;
import stats.persistence.IDAOTeam;
import stats.persistence.IDAOUser;
import stats.persistence.ReadFromFile;
import stats.view.AppController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class App extends Application {
	//
	
	//
	public static App sharedInstance = new App();
	private AppController appController = new AppController();
	private IDAOTeam daoTeam = new DAOTeamMongo();
	private IDAOPlayer daoPlayer = new DAOPlayerMongo();
	private IDAOLeague daoLeague = new DAOLeagueMongo();
	private IDAOMatch daoMatch = new DAOMatchMongo();
	private IDAOUser daoUser = new DAOUserMongo();
	private IDAOUser daoUserKV = new DAOUserKV();
	private ReadFromFile readFromFile = new ReadFromFile();
	private Stage primaryStage;
	
	
	public static App getSharedInstance() {
		return sharedInstance;
	}

	public IDAOUser getDaoUserKV() {
		return daoUserKV;
	}
	
	public IDAOTeam getDaoTeam() {
		return daoTeam;
	}
	
	public IDAOPlayer getDaoPlayer() {
		return daoPlayer;
	}

	public IDAOLeague getDaoLeague() {
		return daoLeague;
	}

	public IDAOMatch getDaoMatch() {
		return daoMatch;
	}
	public IDAOUser getDaoUser() {
		return daoUser;
	}


	public AppController getAppController() {
		return appController;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public ReadFromFile getReadFromFile() {
		return readFromFile;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
//			User user = new User();
//			user.setUsername("user");
//			user.setPwd("nalf10");
//			App.getSharedInstance().getDaoUserKV().putUser(user);
			LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
			rootLogger.setLevel(Level.OFF);
			this.primaryStage = primaryStage;
			Parent root = FXMLLoader.load(getClass().getResource("/stats/view/Main.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/stats/view/Main.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/stats/view/application.css").toExternalForm());
			primaryStage.setMinHeight(739);
			primaryStage.setMinWidth(866);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
