package com.flowershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowershop.model.User;
import com.flowershop.repository.UserRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	@Autowired
	private final UserRepo userRepo;
	
	public UserService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	public User addUser(User user) {
		return userRepo.save(user);
	}
	
	public User updateUser(User user) {
		return userRepo.save(user);
	}
	
	public User findUserByEmail(String email) {
		return userRepo.findUserByEmail(email);
	}
	
	public List<User> findAllUsers() {
		return userRepo.findAll();
	}
	
	public void deleteUserByEmail(String email) {
		userRepo.deleteUserByEmail(email);
	}
	
	public void deleteUserById(Long id) {
		userRepo.deleteUserById(id);
	}
	
	public boolean login(String email, String password, HttpServletRequest request) {
		HttpSession session=request.getSession(true);
		for (User user:userRepo.findAll()) {
			String myEmail=user.getEmail();
			String myPassword=user.getPassword();
			if(myEmail.toString().equals(email) && myPassword.toString().equals(password)) {			
	            session.setAttribute("user_id",user.getId());
	            session.setAttribute("user_email",user.getEmail());
	            session.setAttribute("user_name",user.getName());
	            session.setAttribute("user_type",user.getUser_type());
				return true;
			}
				
		}
        session.setAttribute("error","Invalid email or password");
		return false;
	}
	
	public List<User> findAllUsersByRole(String role) {
		return userRepo.findAllUsersByRole(role);
	}
}
