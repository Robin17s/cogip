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
		return rep.getById(id);
	}
	
	public String createNewContact(Contact contact) {
		rep.save(contact);
		return "New contact successfully created";
	}
	
	public String deleteContact(int id) {
		rep.deleteById(id);
		return String.format("Contact with id %d successfully removed", id);
	}
	
	public String updateContact(Contact contact) {
		rep.save(contact);
		return "Contact successfully updated";
	}
}
