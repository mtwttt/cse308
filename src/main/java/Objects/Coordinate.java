package Objects;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(CoordinateID.class)
public class Coordinate {
	@Id
	double x;
	@Id
	double y;
	@Id
	int precinctID;
	public Coordinate(double x, double y, int precinctID) {
		this.x = x;
		this.y = y;
		this.precinctID = precinctID;
	}
}
