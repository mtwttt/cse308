package Objects;

import java.util.List;

public class Precinct {
    public int id;
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
    public List<List<List<Double>>> coordinates; 
    
    public int getID() {
    		return id;
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
	public List<List<List<Double>>> getCoordinate() {
		return coordinates;
	}
	public int setID() {
		return id;
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
}
