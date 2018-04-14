package Objects;

import java.util.ArrayList;
import java.util.List;

public class CongressionalDistrict {
	private int id;
	private int totalVote;
	private String whichParty;
	private List<Precinct> precincts;
	private double republicanVote;
	private double democratVote;
	private List<CongressionalDistrict> neighborCD;
	private double compactnessRatio;
	private int totalPopulation;
	private List<Precinct> borderPrecincts;
	private int representativeAt;
	public State state; 
	
	public int getId() {
		return id;
	}
	
	public int getTotalVote() {
		return totalVote;
	}
	
	public String getParty() {
		return whichParty;
	}
	
	public List<Precinct> getPrecincts() {
		return precincts;
	}
	
	public double getRepublicanVote() {
		return republicanVote;
	}
	
	public double getDemocratVote() {
		return democratVote;
	}
	
	public List<CongressionalDistrict> getNegihborCD() {
		return neighborCD;
	}
	
	public double getCompactnessRatio() {
		return compactnessRatio;
	}
	
	public int getTotalPopulation() {
		return totalPopulation;
	}
	
	public List<Precinct> getBorderPrecincts() {
		return borderPrecincts;
	}
	
	public int getRepLocation() {
		return representativeAt;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTotalVote(int totalVote) {
		this.totalVote = totalVote;
	}
	
	public void setParty(String party) {
		this.whichParty = party;
	}
	
	public void setPrecincts(List<Precinct> precincts) {
		this.precincts = precincts;
	}
	
	public void setRepublicanVote(double vote) {
		this.republicanVote = vote;
	}
	
	public void setDemocratVote(double vote) {
		this.democratVote = vote;
	}
	
	public void setNegihborCD(List<CongressionalDistrict> neighborCD) {
		this.neighborCD = neighborCD;
	}
	
	public void setCompactnessRatio(double ratio) {
		this.compactnessRatio = ratio;
	}
	
	public void setTotalPopulation(int population) {
		this.totalPopulation = population;
	}
	
	public void setBorderPrecincts(List<Precinct> precincts) {
		this.borderPrecincts = precincts;
	}
	
	public void setRepLocation(int location) {
		this.representativeAt = location;
	}
	public List<Precinct> getBorderPrecinct(){
		List<Precinct> borders = new ArrayList();
		for(int i=0;i< precincts.size();i++) {
			if(precincts.get(i).getBorder() == 1) {
				borders.add(precincts.get(i));
			}
		}
		return borders;
	}
	public double getPopulationScore() {
		int avgPopulation = state.getTotalPopulation()/state.getCongressionalDistrict().size();
		int populationScore = 1 - Math.abs(avgPopulation - totalPopulation)/avgPopulation;
		return populationScore;
	}
	public double getCompactnessScore() {
		return 0.0;
	}
	public double getPartisanFairnessScore() {
		return 0.0;
	}
	public double getRacialFairnessScore() {
		return 0.0;
	}
}