package org.becode.projects.controllers;

import java.util.List;

import org.becode.projects.domain.User;
import org.becode.projects.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {

	@Autowired
	private UserService service;
	
	public UserController() {
		
	}
	
	@GetMapping("/csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		return service.verify(user);
	}
	
	@GetMapping("/")
	public String test() {
		return "Hello world";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/users")
	public String getAllUsers(){
//		return service.getAllUsers();
		String string = "";
		for(User user : service.getAllUsers()) {
			string += user.toString() + "\n";
		}
		return string;
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/users/{id}")
	public User getSpecificUser(@PathVariable long id) {
		return service.getSpecificUser(id);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/users")
	public String createNewUser(@RequestBody User user) {
		return service.createNewUser(user);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/users/{id}")
	public String deleteSpecificUser(@PathVariable long id) {
		return service.deleteUser(id);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/users")
	public String updateUser(@RequestBody User user) {
		return service.updateUser(user);
	}
}
