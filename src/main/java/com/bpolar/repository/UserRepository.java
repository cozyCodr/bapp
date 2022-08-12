package com.bpolar.repository;

import com.bpolar.user.Role;
import com.bpolar.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	User findByEmail(String email);

	List<User> findByRoles_Name(String roles);
}
