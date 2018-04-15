package cse308.zsyj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import Objects.Coordinate;
import Objects.CoordinateID;

public interface CoordinateRepository extends CrudRepository<Coordinate,CoordinateID>{
	@Query(value =  "Select x, y from Coordinate where precinctId = ?1", nativeQuery = true)
	List<List<Double>> findXandYbyPrecinctID(int PrecinctID);
}
