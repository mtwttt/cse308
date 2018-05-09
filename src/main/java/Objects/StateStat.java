package Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StateStat {
	@Id
	public int sid;
	public int count; 
	
	public int getSid() {
		return sid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int s) {
		this.count = s;
	}
}
