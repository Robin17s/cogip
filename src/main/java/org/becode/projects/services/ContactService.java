package org.becode.projects.services;

import java.util.List;

import org.becode.projects.domain.Company;
import org.becode.projects.domain.Contact;
import org.becode.projects.repositories.CompanyRepository;
import org.becode.projects.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

	@Autowired
	private ContactRepository rep;
	
	public ContactService() {
		
	}
	
	public List<Contact> getAllContacts(){
		return rep.findAll();
	}
	
	public Contact getSpecificContact(int id) {
		if(controlIdExists(id)) {
			return rep.getById(id);			
		}
		throw new IllegalArgumentException(String.format("Contact with id %d doesn't exist", id));
	}
	
	public String createNewContact(Contact contact) {
		if(controlIdExists(contact.getId())) {
			return "Company with this id already exists";
		}
		rep.save(contact);
		return "New contact successfully created";
	}
	
	public String deleteContact(int id) {
		if(controlIdExists(id)) {
			rep.deleteById(id);
			return String.format("Contact with id %d successfully removed", id);			
		}
		return "Contact with this id doesn't exists";
	}
	
	public String updateContact(Contact contact) {
		if(controlIdExists(contact.getId())) {
			rep.save(contact);
			return "Contact successfully updated";			
		}
		return "Contact with this id doesn't exists";
	}
	
	private boolean controlIdExists(int id) {
		return rep.existsById(id);
	}
}
