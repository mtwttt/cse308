package cse308.zsyj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Objects.CongressionalDistrict;
@Repository
public interface CDRepository extends CrudRepository<CongressionalDistrict,Integer>{
	@Query(value =  "Select * from CongressionalDistrict where sid = ?1", nativeQuery = true)
	List<CongressionalDistrict> findAllById(int id);
}
