package Objects;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;

@Entity
@IdClass(PrecinctID.class)
public class Precinct {
	@Id
    public int pid;
	@Id 
	public int sid;
	@Id 
	public int year;
    @Column
    public int population;
    public int cdNumber;
    public int aLand;
    public int aWater;
    public int totalVote;
    public double latitude;
    public double longtitude;
    public int rVote;
    public int dVote;
    public int oVote; 
    @Transient
    public List<List<List<Double>>> coordinates;
    public int isBorder;
    public double avgRace;
    @Transient
    public int isUsed;
    @Transient
    public int isChecked;
    @Transient
    public int isCheckedWithP;
    
    public Precinct() {}
    public int getSid() {
    	return sid;
    }
    public void setSid(int s) {
    	this.sid = s;
    }
    public int getIsUsed() {
    	return isUsed;
    }
    
    public void setIsUsed(int used) {
    	this.isUsed = used;
    }
    
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
	
	public List<List<List<Double>>> getCoordinate() {
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
	
	public void setCoordinate(List<List<List<Double>>> list) {
		coordinates = list;
	}
	
}
