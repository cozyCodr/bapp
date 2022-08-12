package com.bpolar.services;

import java.util.Collections;
import java.util.List;

import com.bpolar.repository.RoleRepository;
import com.bpolar.repository.UserRepository;
import com.bpolar.user.Role;
import com.bpolar.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


	private final UserRepository userRepo;
	private final RoleRepository roleRepo;

	public UserService(UserRepository userRepo, RoleRepository roleRepo) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
	}

	public void saveUserWithDefaultRole(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		Role userRole = roleRepo.findByName("User");
		user.addRole(userRole);
		
		userRepo.save(user);
	}
	
	public void saveUserWithAdminRole(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		Role userRole = roleRepo.findByName("Admin");
		user.addRole(userRole);

		userRepo.save(user);
	}

	public void saveEditedUser(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepo.save(user);
	}

	public void saveUser(User user){
		userRepo.save(user);
	}
	
	public List<User> findAllPatients(){
		return userRepo.findByRoles_Name("User");
	}
	
	public User get(Long id) {
		return userRepo.findById(id).get();
	}
	
	public List<Role> getRoles(){
		return roleRepo.findAll();
	}

	public User findPatient(String email) {
		return userRepo.findByEmail(email);
	}
}
