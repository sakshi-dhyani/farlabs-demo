package com.farelabs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.farelabs.entity.Role;
import com.farelabs.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	User findByEmail(String email);

//	Object findByEmailAndActive(String email, byte b);
	
	Optional<User> findByEmailAndActive(String username, byte b);

	User findByMobileNumber(String mobileNumber);


	User findByUsernames(String id);

	User findByEmailAndRole(String id, Role getRole);

	User findByIdAndActive(int user , byte b);

	List<User> findByRoleId(int inchargeID);



}
