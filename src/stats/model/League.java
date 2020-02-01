package stats.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class League {

	private String fullName;
	private String name;
	private String year;
	private List<Match> matches = new ArrayList<Match>();
	
	public String getFullName() {
		return fullName;
	}
	
	public String getName() {
		return name;
	}
	
	public String getYear() {
		return year;
	}
	
	public List<Match> getMatches() {
		return matches;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}
	
	public String toJSON() {
		Gson g = new Gson();
		return g.toJson(this);
	}
	
	public static League leagueFromJson(String jsonString) {
		Gson g = new Gson();
		return g.fromJson(jsonString, League.class);
	}
	
}
