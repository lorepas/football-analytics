package stats;
	
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.MongoSocketOpenException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import stats.model.Player;
import stats.persistence.DAOException;
import stats.persistence.DAOPlayerMongo;
import stats.persistence.ReadFromFile;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class App extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/stats/view/Main.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/stats/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			String json = new ReadFromFile().readLocalJSON("/Users/kenobi_gen/Serie A 2019-20- Players.json");
			Gson gson = new Gson();
			Player[] players = gson.fromJson(json, Player[].class);
			System.out.println("Number: " + players.length);
			List<Player> playerList = Arrays.asList(players);
			new DAOPlayerMongo().createListOfPlayers(playerList);
		} catch(DAOException msoe) {
			System.out.println("Non sei collegato alla VPN");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
