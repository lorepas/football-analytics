package stats.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class League {

	private String fullname;
	private String name;
	private String year;
	private String link;
	private List<Match> matches = new ArrayList<Match>();
	
	public String getFullname() {
		return fullname;
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
	
	public void setFullname(String fullName) {
		this.fullname = fullName;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(fullname);
		return builder.toString();
	}
	
}
