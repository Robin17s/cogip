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
		return rep.getById(id);
	}
	
	public String createNewInvoice(Invoice invoice) {
		rep.save(invoice);
		return "New invoice successfully created";
	}
	
	public String deleteInvoice(int id) {
		rep.deleteById(id);
		return String.format("Invoice with id %d successfully removed", id);
	}
	
	public String updateInvoice(Invoice invoice) {
		rep.save(invoice);
		return "Invoice successfully updated";
	}
}
