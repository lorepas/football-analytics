package stats.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.MongoClient;


public class Utils {
		
	public static MongoClient getMongoClient() {
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		builder.maxConnectionIdleTime(60000);
//		builder.sslEnabled(true);
		List<ServerAddress> servers = new ArrayList<ServerAddress>();
		servers.add(new ServerAddress("172.16.0.132", 27018));
		servers.add(new ServerAddress("172.16.0.136", 27018));
		servers.add(new ServerAddress("172.16.0.138", 27018));
		MongoClient mongoClient = new MongoClient(servers, builder.build());
		return mongoClient;
	}
	
 }
