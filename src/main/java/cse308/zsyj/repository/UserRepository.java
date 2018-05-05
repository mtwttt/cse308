package cse308.zsyj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import Objects.Account;
import Objects.State;

public interface UserRepository extends CrudRepository<Account,String>{
	@Query(value =  "Select email from Account where username = ?1 and password =?2",
			nativeQuery = true)
	String findAccount(String username, String password);
	
	@Query(value =  "Select CASE WHEN count(*) > 0 THEN true ELSE false END from Account where username = ?1 and password =?2 and verified=true",
			nativeQuery = true)
	int verified(String username, String password);
	
	@Query(value =  "Select vkey from Account where username = ?1",
			nativeQuery = true)
	String getVkey(String username);
	
	@Query(value =  "Select * from Account where username = ?1",
			nativeQuery = true)
	Account getAccount(String username);
	
	@Query(value =  "Select * from Account where isAdmin = 0",
			nativeQuery = true)
	List<Account> getUsers();
}
