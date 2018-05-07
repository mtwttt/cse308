package Objects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Hashtable;
@Entity
public class State {
	@Column
	private String name;
	private String overallPartyWin;
	private double republicanStat;
	private double democraticSta;
	@Transient
	private List<CongressionalDistrict> congressionalDistrict = new ArrayList<CongressionalDistrict>();
	private int overallStateVote;
	private int year;
	@Id
	private int sid;
	private int totalPopulation;
	public double totalAvgRace;
	@Transient
	public List<Integer> selectedPids;
	public State() {	}
	
	public String getName() {
		return name;
	}
	public List<Integer> getSelectedPids(){
		return selectedPids;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSeletedPids(List<Integer> pids) {
		this.selectedPids = pids;
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
		return sid;
	}
	
	public void setId(int id) {
		this.sid = id;
	}
	
	public int getTotalPopulation() {
		return totalPopulation;
	}
	
	public void setTotalPopulation(int totalPopulation) {
		this.totalPopulation = totalPopulation;
	}
	
	
	
	public void generateBorder(List<List<List<Double>>> cdBorder) {
		for(int i=0;i<congressionalDistrict.size();i++) {
			List<Precinct> precincts = congressionalDistrict.get(i).getPrecincts();
			for(int j=0;j<precincts.size();j++) {
				if(precincts.get(j).getCoordinate().size()!=0) {
				List<List<Double>> coordinate = precincts.get(j).getCoordinate().get(0);
					for(int k = 0;k<coordinate.size();k++) {
						double pX = coordinate.get(k).get(0);
						double pY = coordinate.get(k).get(1);
						for(int x = 0;x<cdBorder.get(0).size();x++) {
							double bX = cdBorder.get(0).get(x).get(0);
							double bY = cdBorder.get(0).get(x).get(1);
							if((Math.abs(bX - pX)< 0.000001) && (Math.abs(bY - pY) < 0.000001)) {
								precincts.get(j).setBorder(1);
							}
						}
					}
				}
			}
		}
	}
	
	public double getTargetRacial() {
		return totalAvgRace / congressionalDistrict.size();
	}
	
	public int getCurrentPopulation() {
		int total =0;
		for (int i=0;i<congressionalDistrict.size();i++) {
			total+=congressionalDistrict.get(i).getCurrentPopulation();
		}
		return total;
	}
	
	public ArrayList<Integer> getBorderPrecinctIDs(){
		ArrayList<Integer> pids = new ArrayList<Integer>();
		for(int i=0;i<congressionalDistrict.size();i++) {
			CongressionalDistrict cd = congressionalDistrict.get(i);
			for(int j=0;j<cd.getPrecincts().size();j++) {
				Precinct p = cd.getPrecincts().get(j);
				if(p.isBorder==1) {
					pids.add(p.getID());
				}
			}
		}
		return pids;
	}
	
	public ArrayList<Precinct> getBorderPrecincts(){
		ArrayList<Precinct> ps = new ArrayList<Precinct>();
		for(int i=0;i<congressionalDistrict.size();i++) {
			CongressionalDistrict cd = congressionalDistrict.get(i);
			for(int j=0;j<cd.getPrecincts().size();j++) {
				Precinct p = cd.getPrecincts().get(j);
				if(p.isBorder==1) {
					ps.add(p);
				}
			}
		}
		return ps;
	}
	
	public Hashtable<Integer,Integer> getBorderDict(){
		Hashtable<Integer,Integer> ps = new Hashtable<Integer,Integer>();
		for(int i=0;i<congressionalDistrict.size();i++) {
			CongressionalDistrict cd = congressionalDistrict.get(i);
			for(int j=0;j<cd.getPrecincts().size();j++) {
				Precinct p = cd.getPrecincts().get(j);
				if(p.isBorder==1) {
					ps.put(p.getID(),cd.getId());
				}
			}
		}
		return ps;
	}
	
}
