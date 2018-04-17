package cse308.zsyj.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Objects.State;

@Repository
public interface StateRepository extends CrudRepository<State,Integer>{
	@Query(value =  "Select sid from State where name = ?1 and year =?2",
			nativeQuery = true)
	int findByNameAndYear(String Name, int year);
}
