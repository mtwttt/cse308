package cse308.zsyj.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Objects.State;

@Repository
public interface StateRepository extends CrudRepository<State,Integer>{
	int findByNameAndYear(String Name, int year);
}
