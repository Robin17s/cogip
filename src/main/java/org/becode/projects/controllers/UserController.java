package org.becode.projects.controllers;

import java.util.List;

import org.becode.projects.domain.User;
import org.becode.projects.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserService service;
	
	public UserController() {
		
	}
	
	@GetMapping("/")
	public String test() {
		return "Hello world";
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return service.getAllUsers();
	}
	
	@GetMapping("/users/{id}")
	public User getSpecificUser(@PathVariable long id) {
		return service.getSpecificUser(id);
	}
	
	@PostMapping("/users")
	public String createNewUser(@RequestBody User user) {
		return service.createNewUser(user);
	}
}
