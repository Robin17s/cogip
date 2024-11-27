package org.becode.projects.controllers;

import java.util.List;

import org.becode.projects.domain.Company;
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
public class InvoiceController {

	@Autowired
	private InvoiceService service;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private CompanyService companyService;
	
	public InvoiceController() {
		
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ACCOUNTANT", "ROLE_INTERN"})
	@GetMapping("/invoices")
	public String getAllInvoices(){
		String string = "";
		for(Invoice invoice : service.getAllInvoices()) {
			string += invoice.toString() + "\n";
			string = printRelatedContact(string, invoice.getInvoice_contact_id());
			string = printRelatedCompany(string, invoice.getInvoice_company_id());			
		}
		return string;
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ACCOUNTANT", "ROLE_INTERN"})
	@GetMapping("/invoices/{id}")
	public String getSpecificInvoice(@PathVariable int id) {
		String string = "";
		string += service.getSpecificInvoice(id).toString() + "\n";
		string = printRelatedContact(string, id);
		string = printRelatedCompany(string, id);
		return string;
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ACCOUNTANT"})
	@PostMapping("/invoices")
	public String createNewInvoice(@RequestBody Invoice invoice) {
		return service.createNewInvoice(invoice);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ACCOUNTANT"})
	@DeleteMapping("/invoices/{id}")
	public String deleteSpecificInvoice(@PathVariable int id) {
		return service.deleteInvoice(id);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ACCOUNTANT"})
	@PutMapping("/invoices")
	public String updateInvoice(@RequestBody Invoice invoice) {
		return service.updateInvoice(invoice);
	}
	
	private String printRelatedContact(String string, int id) {
		string += contactService.getSpecificContact(id).toString() + "\n";
		return string;
	}
	
	private String printRelatedCompany(String string, int id) {
		Company company = companyService.getSpecificCompany(id);
		string += company.toString() + " type: " + company.getType() + "\n";
		return string;
	}
}
