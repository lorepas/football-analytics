package stats.utility;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

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
	
	private static final String CSV_SEPARATOR = ",";
	
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
	public static void decode(String s) throws IOException  {
		byte[] bytes = Base64.getDecoder().decode(s);
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
		ImageIO.write(img, "png", new File("shardImage.png") );
		
	}
	public static void decode(String s, String file) throws IOException  {
		byte[] bytes = Base64.getDecoder().decode(s);
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
		ImageIO.write(img, "png", new File(file) );
		
	}
	
	public static String prettyName(String name) {
		String[] args = name.split(" ");
		StringBuilder pretty = new StringBuilder();
		for (String string : args) {
			if(string.equalsIgnoreCase("Atl.")) {
				pretty.append("atletico");
				pretty.append(" ");
				continue;
			}
			if(!string.contains(".") && string.length() > 3) {
				pretty.append(string);
				pretty.append(" ");
			}
		}
		String result = pretty.toString();
		if(result.endsWith(" ")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
	
 }
