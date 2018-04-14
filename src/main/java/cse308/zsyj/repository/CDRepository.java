package cse308.zsyj.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import Objects.CongressionalDistrict;

public interface CDRepository extends CrudRepository<CongressionalDistrict,Integer>{
	List<CongressionalDistrict> findById(int id);
}
