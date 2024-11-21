package org.becode.projects.controllers;

import java.util.List;

import org.becode.projects.domain.Invoice;
import org.becode.projects.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	public InvoiceController() {
		
	}
	
	@GetMapping("/invoices")
	public List<Invoice> getAllInvoices(){
		return service.getAllInvoices();
	}
	
	@GetMapping("/invoices/{id}")
	public Invoice getSpecificInvoice(@PathVariable int id) {
		return service.getSpecificInvoice(id);
	}
	
	@PostMapping("/invoices")
	public String createNewInvoice(@RequestBody Invoice invoice) {
		return service.createNewInvoice(invoice);
	}
	
	@DeleteMapping("/invoices/{id}")
	public String deleteSpecificInvoice(@PathVariable int id) {
		return service.deleteInvoice(id);
	}
	
	@PutMapping("/invoices")
	public String updateInvoice(@RequestBody Invoice invoice) {
		return service.updateInvoice(invoice);
	}
}
