package cse308.zsyj.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import Objects.Coordinate;
import Objects.CoordinateID;

public interface CoordinateRepository extends CrudRepository<Coordinate,CoordinateID>{
	@Query(value =  "Select * from Coordinate where precinctid = ?1",
			nativeQuery = true)
	List<Coordinate> findXandYbyPrecinctID(int PrecinctID);
}
