package stats;
	
import java.util.ArrayList;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import java.util.List;

import javax.swing.JFileChooser;

import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import stats.model.DetailedPerformance;
import stats.model.League;
import stats.model.Match;
import stats.model.Team;
import stats.model.User;
import stats.persistence.DAOUserKV;
import stats.persistence.IDAOLeague;
import stats.persistence.IDAOMatch;
import stats.persistence.IDAOPlayer;
import stats.persistence.IDAOTeam;
import stats.persistence.IDAOUser;
import stats.persistence.ReadFromFile;
import stats.persistence.mongo.DAOLeagueMongo;
import stats.persistence.mongo.DAOMatchMongo;
import stats.persistence.mongo.DAOPlayerMongo;
import stats.persistence.mongo.DAOTeamMongo;
import stats.persistence.mongo.DAOUserMongo;
import stats.persistence.n4j.DAOLeagueN4J;
import stats.persistence.n4j.DAOMatchN4J;
import stats.persistence.n4j.DAOQuery;
import stats.persistence.n4j.DAOTeamN4J;
import stats.persistence.n4j.IDAOLeagueGraph;
import stats.persistence.n4j.IDAOMatchGraph;
import stats.persistence.n4j.IDAOQuery;
import stats.persistence.n4j.IDAOTeamGraph;
import stats.view.AppController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class App extends Application {
	//
	
	//
	public static App sharedInstance = new App();
	private AppController appController = new AppController();
//	private IDAOTeam daoTeam = new DAOTeamMongo();
//	private IDAOPlayer daoPlayer = new DAOPlayerMongo();
//	private IDAOLeague daoLeague = new DAOLeagueMongo();
//	private IDAOMatch daoMatch = new DAOMatchMongo();
	private IDAOUser daoUserKV = new DAOUserKV();
	private IDAOLeagueGraph daoLeagueGraph = new DAOLeagueN4J();
	private IDAOMatchGraph daoMatchGraph = new DAOMatchN4J();
	private IDAOTeamGraph daoTeamGraph = new DAOTeamN4J();
	private IDAOQuery daoQuery = new DAOQuery();
	private ReadFromFile readFromFile = new ReadFromFile();
	private Stage primaryStage;
	
	
	public static App getSharedInstance() {
		return sharedInstance;
	}

	public IDAOUser getDaoUserKV() {
		return daoUserKV;
	}
	
//	public IDAOTeam getDaoTeam() {
//		return daoTeam;
//	}
//	
//	public IDAOPlayer getDaoPlayer() {
//		return daoPlayer;
//	}
//
//	public IDAOLeague getDaoLeague() {
//		return daoLeague;
//	}
//
//	public IDAOMatch getDaoMatch() {
//		return daoMatch;
//	}
	
	public IDAOLeagueGraph getDaoLeagueGraph() {
		return daoLeagueGraph;
	}
	
	public IDAOMatchGraph getDaoMatchGraph() {
		return daoMatchGraph;
	}
	
	public IDAOTeamGraph getDaoTeamGraph() {
		return daoTeamGraph;
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
	
	public IDAOQuery getDaoQuery(){
		return daoQuery;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			//DON'T REMOVE THESE LINES
//			User user = new User();
//			user.setUsername("user");
//			user.setPwd("nalf10");
//			App.getSharedInstance().getDaoUserKV().putUser(user);
			LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			Logger mongoLogger = loggerContext.getLogger("org.mongodb.driver");
			mongoLogger.setLevel(Level.OFF);
			Logger neo4jLogger = loggerContext.getLogger("org.neo4j.driver");
			neo4jLogger.setLevel(Level.OFF);
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
