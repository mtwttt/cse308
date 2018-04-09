package Objects;

import java.util.ArrayList;
import java.util.List;

public class State {
	private String name = "";
	private String overallPartyWin = "";
	private double republicanStat = 0;
	private double democraticSta = 0;
	private List<CongressionalDistrict> congressionalDistrict = new ArrayList<CongressionalDistrict>();
	private int overallStateVote = 0;
	private int year = 0;
	private int id = 0;
	private int totalPopulation = 0;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOverallPartyWin() {
		return overallPartyWin;
	}
	public void setOverallPartyWin(String overallPartyWin) {
		this.overallPartyWin = overallPartyWin;
	}
	public double getRepublicanStat() {
		return republicanStat;
	}
	public void setRepublicanStat(double republicanStat) {
		this.republicanStat = republicanStat;
	}
	public double getDemocraticSta() {
		return democraticSta;
	}
	public void setDemocraticSta(double democraticSta) {
		this.democraticSta = democraticSta;
	}
	public List<CongressionalDistrict> getCongressionalDistrict() {
		return congressionalDistrict;
	}
	public void setCongressionalDistrict(List<CongressionalDistrict> congressionalDistrict) {
		this.congressionalDistrict = congressionalDistrict;
	}
	public int getOverallStateVote() {
		return overallStateVote;
	}
	public void setOverallStateVote(int overallStateVote) {
		this.overallStateVote = overallStateVote;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalPopulation() {
		return totalPopulation;
	}
	public void setTotalPopulation(int totalPopulation) {
		this.totalPopulation = totalPopulation;
	}
	
}
