package cse308.zsyj.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Objects.Precinct;
import Objects.PrecinctID;

@Repository
public interface PrecinctRepository extends CrudRepository<Precinct, PrecinctID>{
	@Query(value =  "Select * from Precinct where cdNumber = ?1 and sid = ?2 and year = ?3", 
			nativeQuery = true)
	List<Precinct> findAllByCD(int cdNumber,int sid,int year);
}
