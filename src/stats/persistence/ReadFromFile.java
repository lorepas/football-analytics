package stats.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadFromFile {
	
	public String readLocalJSON(String path) {
		String json = "";
		try {
			FileReader in = new FileReader(path);
		    BufferedReader reader = new BufferedReader(in);
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		       	json.concat(line);
		       	System.out.println(line);
		    }
		} catch (IOException e) {
		    new DAOException(e);
		}
		return json;
	}
}
