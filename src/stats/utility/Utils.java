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
import java.net.InetAddress;
import java.net.UnknownHostException;
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
		try {
			boolean check = InetAddress.getByName("172.16.0.132").isReachable(10000);
			if(!check) {
				System.out.println("Not connected to VPN!");
				throw new MongoException("Mongo is down: you should connect with a VPN");
			}
		} catch (UnknownHostException e1) {
			throw new MongoException(e1.getMessage());
		} catch (IOException e1) {
			throw new MongoException(e1.getMessage());
		}
		
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
	
	/*public static void writeToCSV(List<PerformanceLastSeason> performances){
        try
        {
        	String header = "SEASON,TEAM,NATION,ROLE,BORN DATE,CALLS,PRESENCES,GOALS,ASSISTS"
        			+ "PENALTY GOALS,OWN GOALS,YELLOW CARDS,DOUBLE YELLOW CARDS,RED CARDS,MINUTES PLAYED,MARKET VALUE";
        	String defaultDirectoryPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(defaultDirectoryPath+"/statistics.csv"), "UTF-8"));
            bw.append(header);
            bw.newLine();
            for (PerformanceLastSeason performance : performances)
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(performance.getSeason());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getTeam());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getNation());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getRole());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getBornDate());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getCalls());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getPresences());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getGoals());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getAssists());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getPenalityGoals());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getOwnGoals());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getYellowCards());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getDoubleYellowCards());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getRedCards());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getBornDate());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getMarketValue());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        }
        catch (FileNotFoundException e){
        	e.printStackTrace();
        }
        catch (IOException e){
        	e.printStackTrace();
        }
    }*/
 }
