package org.becode.projects.controllers;

import java.util.List;

import org.becode.projects.domain.Company;
import org.becode.projects.domain.Contact;
import org.becode.projects.services.CompanyService;
import org.becode.projects.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

	@Autowired
	private ContactService service;
	
	public ContactController() {
		
	}
	
	@GetMapping("/contacts")
	public List<Contact> getAllContact(){
		return service.getAllContacts();
	}
	
	@GetMapping("/contacts/{id}")
	public Contact getSpecificContact(@PathVariable int id) {
		return service.getSpecificContact(id);
	}
	
	@PostMapping("/contacts")
	public String createNewContact(@RequestBody Contact contact) {
		return service.createNewContact(contact);
	}
	
	@DeleteMapping("/contacts/{id}")
	public String deleteSpecificContact(@PathVariable int id) {
		return service.deleteContact(id);
	}
	
	@PutMapping("/contacts")
	public String updateContact(@RequestBody Contact contact) {
		return service.updateContact(contact);
	}
}
