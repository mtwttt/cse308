package Objects;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Precinct {
	@Id
    public int pid;
    @Column
    public int population;
    public int cdNumber;
    public int aLand;
    public int aWater;
    public int totalVote;
    public int year; 
    public double latitude;
    public double longtitude;
    public int rVote;
    public int dVote;
    public int oVote; 
    @Transient
    public List<ArrayList<ArrayList<Double>>> coordinates;
    public int isBorder;
    public double avgRace;
    
    public Precinct() {}
    
    public int getID() {
    		return pid;
    }
    
    public int getPopulation() {
    		return population;
    }
    
    public int getcdNumber() {
    		return cdNumber;
    }
    
    public int getALand() {
    		return aLand;
    }
    
    public int getAWater() {
    		return aWater;
    }
    
	public int getTotalVote() {
		return totalVote;
	}
	
	public int getYear() {
		return year;
	}
	
	public double getLat() {
		return latitude;
	}
	
	public double getLong() {
		return longtitude;
	}
	
	public double getrVote() {
		return rVote;
	}
	
	public double getdVote() {
		return dVote;
	}
	
	public double getoVote() {
		return oVote; 
	}
	
	public List<ArrayList<ArrayList<Double>>> getCoordinate() {
		return coordinates;
	}
	
	public int getBorder() {
		return isBorder;
	}
	
	public void setID(int id) {
		pid = id;
    }
	
	public double getRaceAvg() {
		return avgRace;
	}
	
    public void setPopulation(int population) {
    		this.population = population;
    }
    
    public void setcdNumber(int cdNumber) {
    		this.cdNumber = cdNumber;
    }
    
    public void setALand(int aLand) {
    		this.aLand = aLand;
    }
    
    public void setAWater(int aWater) {
    		this.aWater = aWater;
    }
    
	public void setTotalVote(int totalVote) {
		this. totalVote = totalVote;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public void setLat(double lat) {
		latitude = lat;
	}
	
	public void setLong(double lon) {
		longtitude = lon;
	}
	
	public void setrVote(int v) {
		rVote = v;
	}
	
	public void setdVote(int v) {
		dVote = v;
	}
	
	public void setoVote(int v) {
		oVote = v; 
	}
	
	public void setBorder(int b) {
		isBorder = b;
	}
	
	public void setAvgRace(int a) {
		avgRace = a;
	}
	
	public void setCoordinate(List<Coordinate> list) {
		if(coordinates == null) {
			coordinates = new ArrayList<ArrayList<ArrayList<Double>>>();
		}
		if(list.size()!=0) {
			ArrayList<ArrayList<Double>> addList = new ArrayList<ArrayList<Double>>();
			for(int i=0;i<list.size();i++) {
				ArrayList<Double> sublist = new ArrayList<Double>();
				sublist.add(list.get(i).x);
				sublist.add(list.get(i).y);
				addList.add(sublist);
			}
			coordinates.add(addList);
		}
	}
	
}
