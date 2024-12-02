package org.becode.projects.services;

import java.util.List;

import org.becode.projects.domain.User;
import org.becode.projects.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

@Service
public class UserService {

	@Autowired
	private UserRepository rep;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public UserService() {
		
	}
	
	public List<User> getAllUsers(){
		return rep.findAll();
	}
	
	public User getSpecificUser(long id) {
//		User user = rep.getById(id);
//		try {
//			if(user == null) {
//				throw new IllegalArgumentException(String.format("User with id %d doesn't exist", id));
//			}
//		} catch (Exception e) {
//			System.out.println("error: " + e.getMessage());
//		}
//		return user;
		return rep.getById(id);
	}
	
	public String createNewUser(User user) {
		if(controlIdExists(user.getId())) {
			return "User with this id already exists";
		}
		user.setPassword(encoder.encode(user.getPassword()));
		rep.save(user);
		return "New user successfully created";
	}
	
	public String deleteUser(long id) {
		if(controlIdExists(id)) {
			rep.deleteById(id);
			return String.format("User with id %d successfully removed", id);
		}
		return "User with this id doesn't exists";
	}
	
	public String updateUser(User user) {
		if(controlIdExists(user.getId())) {
			user.setPassword(encoder.encode(user.getPassword()));
			rep.save(user);
			return "User successfully updated";
		}
		return "User with this id doesn't exists";
	}

	public String verify(User user) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getUsername());
		}
		
		return "fail";
	}
	
	private boolean controlIdExists(long id) {
		return rep.existsById(id);
	}
}
