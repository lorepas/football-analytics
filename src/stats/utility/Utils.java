package stats.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;

import stats.persistence.DAOException;

import com.mongodb.MongoClient;


public class Utils {
		
	public static MongoClient getMongoClient() throws MongoException {
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		builder.maxConnectionIdleTime(60000);
//		builder.sslEnabled(true);
		List<ServerAddress> servers = new ArrayList<ServerAddress>();
		servers.add(new ServerAddress("172.16.0.132", 27018));
		servers.add(new ServerAddress("172.16.0.136", 27018));
		servers.add(new ServerAddress("172.16.0.138", 27018));
		MongoClient mongoClient = new MongoClient(servers, builder.build());
		try {
			mongoClient.getAddress();
		} catch (Exception e) {
			System.out.println("Mongo is down");
//			mongoClient.close();
			throw new MongoException("Mongo is down: you should connect with a VPN");
		}
		return mongoClient;
	}
	
	public static Driver getNEO4JDriver() {
		AuthToken authToken = AuthTokens.basic("neo4j", "huDfac-hyrmo4-fomviw");
        Driver driver = GraphDatabase.driver("bolt://172.16.0.140:7687", authToken);
        return driver;
	}
	
 }
