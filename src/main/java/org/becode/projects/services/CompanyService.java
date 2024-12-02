package org.becode.projects.services;

import java.util.List;

import org.becode.projects.domain.Company;
import org.becode.projects.domain.User;
import org.becode.projects.repositories.CompanyRepository;
import org.becode.projects.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository rep;
	
	public CompanyService() {
		
	}
	
	public List<Company> getAllCompanies(){
		return rep.findAll();
	}
	
	public Company getSpecificCompany(int id) {
		if(controlIdExists(id)) {
			return rep.getById(id);			
		}
		throw new IllegalArgumentException(String.format("Company with id %d doesn't exist", id));
	}
	
	public String createNewCompany(Company company) {
		if(controlIdExists(company.getId())) {
			return "Company with this id already exists";
		}
		rep.save(company);
		return "New company successfully created";
	}
	
	public String deleteCompany(int id) {
		if(controlIdExists(id)) {
			rep.deleteById(id);
			return String.format("Company with id %d successfully removed", id);			
		}
		return "Company with this id doesn't exists";
	}
	
	public String updateCompany(Company company) {
		if(controlIdExists(company.getId())) {
			rep.save(company);
			return "Company successfully updated";			
		}
		return "Company with this id doesn't exists";
	}
	
	private boolean controlIdExists(int id) {
		return rep.existsById(id);
	}
}
