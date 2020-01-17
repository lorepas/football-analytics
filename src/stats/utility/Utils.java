package stats.utility;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import stats.model.Player;

public class Utils {
	
	public static Player playerFromJson(String jsonString) {
		Gson g = new Gson();
		return g.fromJson(jsonString, Player.class);
	}
	
 }
