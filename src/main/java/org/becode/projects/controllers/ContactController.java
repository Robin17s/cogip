package org.becode.projects.controllers;

import java.util.List;

import org.becode.projects.domain.Company;
import org.becode.projects.domain.Contact;
import org.becode.projects.domain.Invoice;
import org.becode.projects.services.CompanyService;
import org.becode.projects.services.ContactService;
import org.becode.projects.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	public ContactController() {
		
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ACCOUNTANT", "ROLE_INTERN"})
	@GetMapping("/contacts")
	public String getAllContact(){
		String string = "";
		
		for(Contact contact : service.getAllContacts()) {
			string += contact.toString() + "\n\n";
			string = printRelatedCompany(string, contact.getId());
			string = printAllRelatedInvoices(string, contact.getId());
		}
		
		return string;
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ACCOUNTANT", "ROLE_INTERN"})
	@GetMapping("/contacts/{id}")
	public String getSpecificContact(@PathVariable int id) {
		String string = "";
		string += service.getSpecificContact(id).toString() + "\n\n";
		string = printRelatedCompany(string, id);
		string = printAllRelatedInvoices(string, id);
		return string;
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/contacts")
	public String createNewContact(@RequestBody Contact contact) {
		return service.createNewContact(contact);
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/contacts/{id}")
	public String deleteSpecificContact(@PathVariable int id) {
		return service.deleteContact(id);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/contacts")
	public String updateContact(@RequestBody Contact contact) {
		return service.updateContact(contact);
	}
	
	private String printRelatedCompany(String string, int id) {
		Contact contact = service.getSpecificContact(id);
		string += companyService.getSpecificCompany(contact.getContact_company_id()).toString() + "\n";
		return string;
	}
	
	private String printAllRelatedInvoices(String string, int contactId) {
		for(Invoice invoice : invoiceService.getAllInvoices().stream().filter(invoice -> invoice.getInvoice_contact_id() == contactId).toList()) {
			string += "\t" + invoice.toString() + "\n";
		}
		
		return string;
	}
}
