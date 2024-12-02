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
		User user = new User(username, password);
		
		token =  restTemplate.postForObject(BASE_URL + "/login", user, String.class);
		
		return "Login successful";
	}
	
	@ShellMethod(key="getallusers", value="Get complete list of users from database")
	public String getAllUsers() {
		return getRequest(BASE_URL + "/users");
	}
	
	@ShellMethod(key="getspecificuser", value="get info of user of given id")
	public String getSpecificUser(@ShellOption long id) {
		return getRequest(BASE_URL + "/users/" + id);
	}
	
	private String getRequest(String url) {
		if(token == null) {
			return "You must login first to do this command";
		}
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "Bearer " + token);
		
		HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		 
		return response.getBody();
	}
	
	@ShellMethod(key="createnewuser", value="make a new user and save it in database")
	public String createNewUser(@ShellOption long id, @ShellOption String username, @ShellOption String password, @ShellOption String role) {
		if(token == null) {
			return "You must login first to do this command";
		}
		
		User user = new User(id, username, password, role);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "Bearer " + token);
//		httpHeaders.set("Content-Type", "application/json");
		
		HttpEntity<User> entity = new HttpEntity<>(user, httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/users", HttpMethod.POST, entity, String.class);
		 
		return response.getBody();
	}
	
}
