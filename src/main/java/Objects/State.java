package Objects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
@Entity
public class State {
	@Column
	private String name;
	private String overallPartyWin = "";
	private double republicanStat = 0;
	private double democraticSta = 0;
	@Transient
	private List<CongressionalDistrict> congressionalDistrict = new ArrayList<CongressionalDistrict>();
	private int overallStateVote = 0;
	private int year = 0;
	@Id
	private int id;
	private int totalPopulation = 0;
	public double totalAvgRace;
	public State() {
		
	}
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
	public RawCDData generateBorder(List<List<List<Double>>> coordinates, RawCDData precincts) {
		List<List<Double>> coordinate = coordinates.get(0);
		for(int i=0;i<precincts.features.size();i++) {
			List<List<Double>> checkLists = precincts.features.get(i).geometry.coordinates.get(0);
			for (int x = 0;x<checkLists.size();x++) {
				double checkX = checkLists.get(x).get(0);
				double checkY = checkLists.get(x).get(1);
				for(int y = 0;y<coordinate.size();y++) {
					double borderX = coordinate.get(x).get(0);
					double borderY = coordinate.get(x).get(1);
					if((borderX - checkX < 0.000001) && (borderY - checkY < 0.000001)) {
						precincts.features.get(i).properties.setBorder(1);
					}
				}
			}
		}
		return precincts;	
	}
	public State generateBorder(List<List<List<Double>>> cdBorder) {
		for(int i=0;i<congressionalDistrict.size();i++) {
			List<Precinct> precincts = congressionalDistrict.get(i).getPrecincts();
			for(int j=0;j<precincts.size();j++) {
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
		return this;
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
}
