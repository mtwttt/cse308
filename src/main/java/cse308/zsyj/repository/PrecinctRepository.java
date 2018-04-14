package cse308.zsyj.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import Objects.Precinct;

public interface PrecinctRepository extends CrudRepository<Precinct, Integer>{
	List<Precinct> findAllById(int id);
}
