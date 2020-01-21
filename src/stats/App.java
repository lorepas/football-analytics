package stats;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import stats.persistence.DAOPlayerMongo;
import stats.persistence.DAOTeamMongo;
import stats.persistence.IDAOPlayer;
import stats.persistence.IDAOTeam;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class App extends Application {
	public static App sharedInstance = new App();
	private IDAOTeam daoTeam = new DAOTeamMongo();
	private IDAOPlayer daoPlayer = new DAOPlayerMongo();
	
	public static App getSharedInstance() {
		return sharedInstance;
	}

	
	public IDAOTeam getDaoTeam() {
		return daoTeam;
	}
	
	public IDAOPlayer getDaoPlayer() {
		return daoPlayer;
	}


	


	@Override
	public void start(Stage primaryStage) {
		try {
			
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
