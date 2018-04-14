package cse308.zsyj.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Objects.Precinct;

@Repository
public interface PrecinctRepository extends CrudRepository<Precinct, Integer>{
	List<Precinct> findAllById(int id);
}
