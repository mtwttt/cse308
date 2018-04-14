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
	public double totalAvgRace;
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
	public double getTargetRacial() {
		return totalAvgRace / congressionalDistrict.size();
	}
}
