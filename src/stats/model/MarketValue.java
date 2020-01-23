package stats.model;

import java.util.Date;

public class MarketValue {

	String team;
	int age;
	String marketValueString;
	String dateString;
	Date date;
	double marketValue;
	
	public String getTeam() {
		return team;
	}
	public int getAge() {
		return age;
	}
	public String getMarketValueString() {
		return marketValueString;
	}
	public String getDateString() {
		return dateString;
	}
	public Date getDate() {
		return date;
	}
	public double getMarketValue() {
		return marketValue;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setMarketValueString(String marketValueString) {
		this.marketValueString = marketValueString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}
	
	
}