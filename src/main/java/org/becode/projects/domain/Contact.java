package org.becode.projects.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="contact")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Contact {

	@Id
	private int id;
	private String firstname;
	private String lastname;
	private String phone;
	private String email;
	private LocalDateTime timestamp;
	private int contact_company_id;
	
	public Contact() {
		
	}

	public Contact(int id, String firstname, String lastname, String phone, String email, LocalDateTime timestamp,
			int contact_company_id) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.email = email;
		this.timestamp = timestamp;
		this.contact_company_id = contact_company_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getContact_company_id() {
		return contact_company_id;
	}

	public void setContact_company_id(int contact_company_id) {
		this.contact_company_id = contact_company_id;
	}
	
	public String toString() {
		return String.format("%s %s, email: %s", firstname, lastname, email);
	}
}
