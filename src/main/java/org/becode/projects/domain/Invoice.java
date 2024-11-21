package org.becode.projects.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="invoice")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Invoice {

	@Id
	private int id;
	private LocalDateTime timestamp;
	private int invoice_company_id;
	private int invoice_contact_id;
	
	public Invoice() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getInvoice_company_id() {
		return invoice_company_id;
	}

	public void setInvoice_company_id(int invoice_company_id) {
		this.invoice_company_id = invoice_company_id;
	}

	public int getInvoice_contact_id() {
		return invoice_contact_id;
	}

	public void setInvoice_contact_id(int invoice_contact_id) {
		this.invoice_contact_id = invoice_contact_id;
	}
	
}
