package stats.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Team {

	private String name;
	private String fullName;
	private int rosterSize;
//	private String link;
	private String shield;
	private List<String> players = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public String getFullName() {
		return fullName;
	}
	public int getRosterSize() {
		return rosterSize;
	}
	public String getShield() {
		return shield;
	}
	public List<String> getPlayers() {
		return players;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setRosterSize(int rosterSize) {
		this.rosterSize = rosterSize;
	}
	public void setShield(String shield) {
		this.shield = shield;
	}
	public void setPlayers(List<String> players) {
		this.players = players;
	}
	
	public String toJSON() {
		Gson g = new Gson();
		return g.toJson(this);
	}
	
	public static Team teamFromJson(String jsonString) {
		Gson g = new Gson();
		return g.fromJson(jsonString, Team.class);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		return builder.toString();
	}
	
	
	
}
