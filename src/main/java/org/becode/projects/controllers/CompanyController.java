package org.becode.projects.controllers;

import java.util.ArrayList;
import java.util.List;

import org.becode.projects.domain.Company;
import org.becode.projects.domain.Contact;
import org.becode.projects.domain.Invoice;
import org.becode.projects.domain.User;
import org.becode.projects.services.CompanyService;
import org.becode.projects.services.ContactService;
import org.becode.projects.services.InvoiceService;
import org.becode.projects.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

	@Autowired
	private CompanyService service;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	public CompanyController() {
		
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ACCOUNTANT", "ROLE_INTERN"})
	@GetMapping("/companies")
	public String getAllCompanies(@RequestParam(name="type", required = false) String type){
		String string = "";
		if(type != null) {
			for(Company com : service.getAllCompanies().stream().filter(company -> company.getType().equals(type)).toList()) {
				string += com.toString() + "\n\n";
				string = printAllRelatedContacts(string, com.getId());
			}
		}
		else {
			for(Company com : service.getAllCompanies()) {
				string += com.toString() + "\n\n";
				string = printAllRelatedContacts(string, com.getId());
			}
		}
		
		return string;
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ACCOUNTANT", "ROLE_INTERN"})
	@GetMapping("/companies/{id}")
	public String getSpecificCompany(@PathVariable int id) {
		String string = "";
		string += service.getSpecificCompany(id).toString() + "\n\n";
		string = printAllRelatedContacts(string, id);
		return string;
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ACCOUNTANT"})
	@PostMapping("/companies")
	public String createNewCompany(@RequestBody Company company) {
		return service.createNewCompany(company);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ACCOUNTANT"})
	@DeleteMapping("/companies/{id}")
	public String deleteSpecificCompany(@PathVariable int id) {
		return service.deleteCompany(id);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ACCOUNTANT"})
	@PutMapping("/companies")
	public String updateCompany(@RequestBody Company company) {
		return service.updateCompany(company);
	}
	
	private String printAllRelatedContacts(String string, int id){
		string += "Related contacts:\n";
		for(Contact contact : contactService.getAllContacts().stream().filter(contact -> contact.getContact_company_id() == id).toList()) {
			string += "\t" + contact.toString() + "\n";
			string = printAllRelatedInvoices(string, contact.getId(), id);
		}
		return string;
	}
	
	private String printAllRelatedInvoices(String string, int contactId, int companyId) {
		for(Invoice invoice : invoiceService.getAllInvoices().stream().filter(invoice -> invoice.getInvoice_contact_id() == contactId && invoice.getInvoice_company_id() == companyId).toList()) {
			string += "\t\t" + invoice.toString() + "\n";
		}
		
		return string;
	}
}
