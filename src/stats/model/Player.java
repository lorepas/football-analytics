package stats.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private String name;
	private String surname;
	private String role;
	private String fullName;
	private String bornDate;
	private String marketValueString;
	private double link;
	private String team;
	private String nation;
	List<DetailedPerformance> detailedPerformances = new ArrayList<>();
	
	public Player() {
	}
	
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getRole() {
		return role;
	}
	public String getFullName() {
		return fullName;
	}
	public String getBornDate() {
		return bornDate;
	}
	public String getMarketValueString() {
		return marketValueString;
	}
	public double getLink() {
		return link;
	}
	public String getTeam() {
		return team;
	}
	public String getNation() {
		return nation;
	}
	public List<DetailedPerformance> getDetailedPerformances() {
		return detailedPerformances;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setBornDate(String bornDate) {
		this.bornDate = bornDate;
	}
	public void setMarketValueString(String marketValueString) {
		this.marketValueString = marketValueString;
	}
	public void setLink(double link) {
		this.link = link;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public void setDetailedPerformances(List<DetailedPerformance> detailedPerformances) {
		this.detailedPerformances = detailedPerformances;
	}
}
