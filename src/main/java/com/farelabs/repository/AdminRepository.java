package com.farelabs.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.farelabs.entity.Admin;
import com.farelabs.entity.User;

public interface AdminRepository extends CrudRepository<Admin, Integer> {

	Admin findByMobileNumber(String mobileNumber);

	Admin findByEmail(String email);

	Admin findByIdAndActive(int adminId, byte b);

	Optional<Admin> findByEmailAndActive(String username, byte b);

}
