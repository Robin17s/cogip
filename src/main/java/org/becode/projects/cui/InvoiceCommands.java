package org.becode.projects.cui;

import java.time.LocalDateTime;

import org.becode.projects.domain.Invoice;
import org.becode.projects.domain.User;
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
public class InvoiceCommands {

	@Autowired
	private RestTemplate restTemplate;
	
	private static final String BASE_URL = "http://localhost:8080";
	
	@Autowired
	private UserCommands userCommands;
	
	public InvoiceCommands() {
		
	}
	
	@ShellMethod(key="getallinvoices", value="Get complete list of invoices from database")
	public String getAllInvoices() {
		return getAndDeleteRequest(BASE_URL + "/invoices", "get");
	}
	
	@ShellMethod(key="getspecificinvoice", value="get info of invoice of given id")
	public String getSpecificInvoice(@ShellOption int id) {
		return getAndDeleteRequest(BASE_URL + "/invoices/" + id, "get");
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
	
	@ShellMethod(key="createnewinvoice", value="make a new invoice and save it in database")
	public String createNewInvoice(@ShellOption int id,@ShellOption int companyId,@ShellOption int contactId) {
		Invoice invoice = new Invoice(id, companyId, contactId, LocalDateTime.now());
		return createAndUpdateRequest(invoice, BASE_URL + "/invoices", "post");
	}
	
	@ShellMethod(key="deletespecificinvoice", value="Change data of an invoice and save it in database")
	public String deleteSpecificInvoice(@ShellOption int id) {
		return getAndDeleteRequest(BASE_URL + "/invoices/" + id, "delete");
	}
	
	@ShellMethod(key="updateinvoice", value="Change data of an invoice and save it in database")
	public String updateInvoice(@ShellOption int id, @ShellOption int companyId, @ShellOption int contactId) {
		Invoice invoice = new Invoice(id, companyId, contactId, LocalDateTime.now());
		return createAndUpdateRequest(invoice, BASE_URL + "/invoices", "put");
	}
	
	private String createAndUpdateRequest(Invoice invoice, String url, String request) {
		if(userCommands.getToken() == null) {
			return "You must login first to do this command";
		}
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "Bearer " + userCommands.getToken());
		
		HttpEntity<Invoice> entity = new HttpEntity<>(invoice, httpHeaders);
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
