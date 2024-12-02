package org.becode.projects.cui;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.becode.projects.controllers.UserController;
import org.becode.projects.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class UserCommands {

	@Autowired
	private RestTemplate restTemplate;
	
	private static final String BASE_URL = "http://localhost:8080";
	
	private String token = null;
	
	public UserCommands() {
		
	}
	
	@ShellMethod(key = "login", value="Login to your account")
	public String login(@ShellOption String username, @ShellOption String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		token =  restTemplate.postForObject(BASE_URL + "/login", user, String.class);
		
		return "Login successful";
	}
	
	@ShellMethod(key="getallusers", value="Get complete list of users from database")
	public String getAllUsers() {
		if(token == null) {
			return "You must login first to do this command";
		}
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "Bearer " + token);
		
		HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/users", HttpMethod.GET, entity, String.class);
		 
		return response.getBody();
	}
	
	@ShellMethod(key="getspecificuser", value="get info of user of given id")
	public String getSpecificUser(@ShellOption long id) {
		if(token == null) {
			return "You must login first to do this command";
		}
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "Bearer " + token);
		
		HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/users/" + id, HttpMethod.GET, entity, String.class);
		 
		return response.getBody();
	}
	
//	private void controlIfTokenIsNull() {
//		if(token == null) {
//			return "You must login first to do this command";
//		}
//	}
	
}
