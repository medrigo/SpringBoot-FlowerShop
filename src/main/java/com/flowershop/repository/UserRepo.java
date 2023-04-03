package com.flowershop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flowershop.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
	void deleteUserByEmail(String email);
	
	User findUserByEmail(String email);
	
	@Query(value = "SELECT * "
			+ "FROM users "
			+ "WHERE user_type = ?1",
			nativeQuery = true)
	List<User> findAllUsersByRole(String role);
	
	void deleteUserById(Long id);
}
