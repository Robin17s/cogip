package org.becode.projects.services;

import java.util.List;

import org.becode.projects.domain.User;
import org.becode.projects.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository rep;
	
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
		rep.save(user);
		return "New user successfully created";
	}
	
	public String deleteUser(long id) {
		rep.deleteById(id);
		return String.format("User with id %d successfully removed", id);
	}
	
	public String updateUser(User user) {
		rep.save(user);
		return "User successfully updated";
	}
}
