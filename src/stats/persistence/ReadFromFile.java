package stats.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadFromFile {
	
	public String readLocalJSON(String path) {
		StringBuilder json = new StringBuilder();
		try {
			FileReader in = new FileReader(path);
		    BufferedReader reader = new BufferedReader(in);
		    String line = null;
		    int i = 0;
		    while ((line = reader.readLine()) != null) {
//		    	i++;
//		    	if(i%10000 == 0) {
//		    		System.out.println(i);
//		    	}
		    	json.append(line);
		    }
		} catch (IOException e) {
		    new DAOException(e);
		}
		return json.toString();
	}
}
