package stats;
	
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import stats.persistence.DAOLeagueMongo;
import stats.persistence.DAOMatchMongo;
import stats.persistence.DAOPlayerMongo;
import stats.persistence.DAOTeamMongo;
import stats.persistence.IDAOLeague;
import stats.persistence.IDAOMatch;
import stats.persistence.IDAOPlayer;
import stats.persistence.IDAOTeam;
import stats.persistence.ReadFromFile;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class App extends Application {
	
	public static App sharedInstance = new App();
	private ReadFromFile readFromFile = new ReadFromFile();
	private IDAOTeam daoTeam = new DAOTeamMongo();
	private IDAOPlayer daoPlayer = new DAOPlayerMongo();
	private IDAOLeague daoLeague = new DAOLeagueMongo();
	private IDAOMatch daoMatch = new DAOMatchMongo();
	private Stage primaryStage;
	
	public static App getSharedInstance() {
		return sharedInstance;
	}

	public ReadFromFile getReadFromFile() {
		return readFromFile;
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

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
			rootLogger.setLevel(Level.OFF);
			
			this.primaryStage = primaryStage;
			Parent root = FXMLLoader.load(getClass().getResource("/stats/view/Main.fxml"));
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
