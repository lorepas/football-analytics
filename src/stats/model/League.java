package stats.model;

import java.util.ArrayList;
import java.util.List;

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
	
}
