package Objects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
@Entity
public class CongressionalDistrict {
	@Id
	private int cdid;
	private int sid;
	@Column
	private int totalVote;
	private String whichParty;
	@Transient
	private List<Precinct> precincts;
	private double republicanVote;
	private double democratVote;
	@Transient
	private List<CongressionalDistrict> neighborCD;
	private double compactnessRatio;
	private int totalPopulation;
	@Transient
	private List<Precinct> borderPrecincts;
	private int representativeAt;
	@Transient
	public State state; 
	private double totalRacial;
	
	public CongressionalDistrict() {	}
	
	public int getId() {
		return cdid;
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
		this.cdid = id;
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
		double avgPopulation = state.getTotalPopulation() /state.getCongressionalDistrict().size();
		double populationScore = (1 - (Math.abs(avgPopulation - totalPopulation)/avgPopulation));
		return populationScore;
	}
	
	public double getCompactnessScore() {
		return 0.0;
	}
	
	public double getPartisanFairnessScore() {
		double partisanScore = 0.0;
		double democWastedVotes = 0.0;
		double repubWastedVotes = 0.0;
		for (Precinct p : this.precincts) {
			int precinctTotal = p.getTotalVote();
			double precinctDemoc = p.getdVote();
			double precinctRepub = p.getrVote();
			int voteToWin = precinctTotal/2+1;
			if (precinctDemoc>=precinctRepub) {
				democWastedVotes+= precinctDemoc - voteToWin;
				if (democWastedVotes<=0)
					democWastedVotes = 0;
				repubWastedVotes+= precinctRepub;
			}
			else {
				repubWastedVotes+= precinctRepub - voteToWin;
				if (repubWastedVotes<=0)
					repubWastedVotes = 0;
				democWastedVotes+= precinctDemoc;
			}
		}
		partisanScore = 1 - (repubWastedVotes + democWastedVotes)/this.totalVote;
		return 0;
	}
	
	public double getRacialFairnessScore() {
		double goal = state.getTargetRacial();
		double temp = 1-(Math.abs(totalRacial - goal)/state.totalAvgRace);
		return temp;
	}
	
	public int getCurrentPopulation() {
		int total =0;
		for (int i=0;i<precincts.size();i++) {
			total += precincts.get(0).getPopulation();
		}
		return total;
	}
	
	public int getCurrentTotalVote() {
		int total =0;
		for (int i=0;i<precincts.size();i++) {
			total += precincts.get(0).getTotalVote();
		}
		return total;
	}
	
	public void updateCDInfo() {
		int totalP =0;
		int totalV =0;
		double totalR =0;
		for (int i=0;i<precincts.size();i++) {
			totalP += precincts.get(i).getPopulation();
			totalV += precincts.get(i).getTotalVote();
			totalR += precincts.get(i).getRaceAvg();
		}
		totalPopulation = totalP;
		totalVote = totalV;
		totalRacial = totalR;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	public double getTotalArea() {
		double totalArea = 0;
		for(int i=0;i<precincts.size();i++) {
			totalArea += precincts.get(i).getALand();
			totalArea += precincts.get(i).getAWater();
		}
		return totalArea;
	}
}
