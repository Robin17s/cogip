package org.becode.projects.cui;

import java.time.LocalDateTime;

import org.becode.projects.domain.Company;
import org.becode.projects.domain.Invoice;
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
public class CompanyCommands {

	@Autowired
	private RestTemplate restTemplate;
	
	private static final String BASE_URL = "http://localhost:8080";
	
	@Autowired
	private UserCommands userCommands;
	
	public CompanyCommands() {
		
	}
	
	@ShellMethod(key="getallcompanies", value="Get complete list of companies from database")
	public String getAllCompanies() {
		return getAndDeleteRequest(BASE_URL + "/companies", "get");
	}
	
	@ShellMethod(key="getspecificcompany", value="get info of company of given id")
	public String getSpecificCompany(@ShellOption int id) {
		return getAndDeleteRequest(BASE_URL + "/companies/" + id, "get");
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
	
	@ShellMethod(key="createnewcompany", value="make a new company and save it in database")
	public String createNewCompany(@ShellOption int id,@ShellOption String name,@ShellOption String country, @ShellOption String vat, @ShellOption String type) {
		Company company = new Company(id, name, country, vat, type, LocalDateTime.now());
		return createAndUpdateRequest(company, BASE_URL + "/companies", "post");
	}
	
	@ShellMethod(key="deletespecificcompany", value="Change data of an company and save it in database")
	public String deleteSpecificCompany(@ShellOption int id) {
		return getAndDeleteRequest(BASE_URL + "/companies/" + id, "delete");
	}
	
	@ShellMethod(key="updatecompany", value="Change data of an company and save it in database")
	public String updateCompany(@ShellOption int id,@ShellOption String name,@ShellOption String country, @ShellOption String vat, @ShellOption String type) {
		Company company = new Company(id, name, country, vat, type, LocalDateTime.now());
		return createAndUpdateRequest(company, BASE_URL + "/companies", "put");
	}
	
	private String createAndUpdateRequest(Company company, String url, String request) {
		if(userCommands.getToken() == null) {
			return "You must login first to do this command";
		}
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "Bearer " + userCommands.getToken());
		
		HttpEntity<Company> entity = new HttpEntity<>(company, httpHeaders);
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
