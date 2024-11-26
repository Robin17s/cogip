package org.becode.projects.controllers;

import java.util.List;

import org.becode.projects.domain.Company;
import org.becode.projects.domain.User;
import org.becode.projects.services.CompanyService;
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
	
	public CompanyController() {
		
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ACCOUNTANT", "ROLE_INTERN"})
	@GetMapping("/companies")
	public List<Company> getAllCompanies(){
		return service.getAllCompanies();
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ACCOUNTANT", "ROLE_INTERN"})
	@GetMapping("/companies/{id}")
	public Company getSpecificCompany(@PathVariable int id) {
		return service.getSpecificCompany(id);
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
}
