package org.becode.projects.services;

import java.util.List;

import org.becode.projects.domain.Invoice;
import org.becode.projects.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository rep;
	
	public InvoiceService() {
		
	}
	
	public List<Invoice> getAllInvoices(){
		return rep.findAll();
	}
	
	public Invoice getSpecificInvoice(int id) {
		if(controlIdExists(id)) {
			return rep.getById(id);			
		}
		throw new IllegalArgumentException(String.format("Invoice with id %d doesn't exist", id));
	}
	
	public String createNewInvoice(Invoice invoice) {
		if(controlIdExists(invoice.getId())) {
			return "Invoice with this id already exists";
		}
		rep.save(invoice);
		return "New invoice successfully created";
	}
	
	public String deleteInvoice(int id) {
		if(controlIdExists(id)) {
			rep.deleteById(id);
			return String.format("Invoice with id %d successfully removed", id);			
		}
		return "Invoice with this id doesn't exists";
	}
	
	public String updateInvoice(Invoice invoice) {
		if(controlIdExists(invoice.getId())) {
			rep.save(invoice);
			return "Invoice successfully updated";			
		}
		return "Invoice with this id doesn't exists";
	}
	
	private boolean controlIdExists(int id) {
		return rep.existsById(id);
	}
}
