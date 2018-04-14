package cse308.zsyj.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Objects.CongressionalDistrict;
@Repository
public interface CDRepository extends CrudRepository<CongressionalDistrict,Integer>{
	List<CongressionalDistrict> findById(int id);
}
