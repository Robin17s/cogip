package org.becode.projects.cui;

import java.time.LocalDateTime;

import org.becode.projects.domain.Company;
import org.becode.projects.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class ContactCommands {

	@Autowired
	private RestTemplate restTemplate;
	
	private static final String BASE_URL = "http://localhost:8080";
	
	@Autowired
	private UserCommands userCommands;
	
	public ContactCommands() {
		
	}
	
	@ShellMethod(key="getallcontacts", value="Get complete list of contacts from database")
	public String getAllContacts() {
		return getAndDeleteRequest(BASE_URL + "/contacts", "get");
	}
	
	@ShellMethod(key="getcontact", value="get info of contact of given id")
	public String getSpecificContact(@ShellOption int id) {
		return getAndDeleteRequest(BASE_URL + "/contacts/" + id, "get");
	}
	
	private String getAndDeleteRequest(String url, String request) {
		if(userCommands.getToken() == null) {
			return "You must login first to do this command";
		}
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "Bearer " + userCommands.getToken());
		
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		ResponseEntity<String> response;
		
		if(request.equals("get")) {
			response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		}
		else {
			response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);			
		}
		 
		return response.getBody();
	}
	
	@ShellMethod(key="createcontact", value="make a new contact and save it in database")
	public String createNewContact(@ShellOption int id,@ShellOption(value= {"--firstname", "-fn"}) String firstname,@ShellOption(value= {"--lastname", "-ln"}) String lastname, @ShellOption(value= {"--phone", "-p"}) String phone, @ShellOption(value= {"--email", "-e"}) String email, @ShellOption(value= {"--companyid", "-cid"}) int companyid) {
		Contact contact = new Contact(id, firstname, lastname, phone, email, companyid, LocalDateTime.now());
		return createAndUpdateRequest(contact, BASE_URL + "/contacts", "post");
	}
	
	@ShellMethod(key="deletecontact", value="Change data of an contact and save it in database")
	public String deleteSpecificContact(@ShellOption int id) {
		return getAndDeleteRequest(BASE_URL + "/contacts/" + id, "delete");
	}
	
	@ShellMethod(key="updatecontact", value="Change data of an contact and save it in database")
	public String updateContact(@ShellOption int id,@ShellOption(value= {"--firstname", "-fn"}) String firstname,@ShellOption(value= {"--lastname", "-ln"}) String lastname, @ShellOption(value= {"--phone", "-p"}) String phone, @ShellOption(value= {"--email", "-e"}) String email, @ShellOption(value= {"--companyid", "-cid"}) int companyid) {
		Contact contact = new Contact(id, firstname, lastname, phone, email, companyid, LocalDateTime.now());
		return createAndUpdateRequest(contact, BASE_URL + "/contacts", "put");
	}
	
	private String createAndUpdateRequest(Contact contact, String url, String request) {
		if(userCommands.getToken() == null) {
			return "You must login first to do this command";
		}
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "Bearer " + userCommands.getToken());
		
		HttpEntity<Contact> entity = new HttpEntity<>(contact, httpHeaders);
		ResponseEntity<String> response;
		if(request.equals("post")) {
			response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);						
		}
		else {
			response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);			
		}
		 
		return response.getBody();
	}
}
