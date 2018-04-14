package Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(Coordinate.class)
public class Coordinate {
	@Id
	double x;
	@Id
	double y;
	@Id
	int precinctID;
}
