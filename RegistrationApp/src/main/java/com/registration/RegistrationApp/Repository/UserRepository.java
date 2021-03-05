package com.registration.RegistrationApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.registration.RegistrationApp.Entity.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {

	public Users findByUserId(String userId);
	
	@Query("select u from Users u where u.name=:name and u.surname=:surname and u.pinCode=:pinCode")
	public List<Users> findByNameAndSurnameAndPincode(@Param("name") String name,@Param("surname") String surname,@Param("pinCode") Integer pinCode);
	
	@Query("select u from Users u ORDER BY u.dob ,u.joiningDate")
	public List<Users> findByDobAndJoiningDate();
	
	@Modifying
	@Query("delete from Users u where u.userId=:userId")
	public void deleteUserByUserId(@Param("userId") String userId);
	
	public List<Users> findByName(String name);
	
	public List<Users> findBySurname(String surname);
	
	public List<Users> findByPinCode(Integer pinCode);
	
	public List<Users> findByNameAndSurname(String name,String surname);
	
	public List<Users> findByNameAndPinCode(String name,Integer pinCode);
	
	public List<Users> findBySurnameAndPinCode(String name,Integer pinCode);
}
