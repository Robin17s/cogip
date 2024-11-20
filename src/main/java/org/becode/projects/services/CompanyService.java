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
		return rep.getById(id);
	}
	
	public String createNewCompany(Company company) {
		rep.save(company);
		return "New company successfully created";
	}
	
	public String deleteCompany(int id) {
		rep.deleteById(id);
		return String.format("Company with id %d successfully removed", id);
	}
	
	public String updateCompany(Company company) {
		rep.save(company);
		return "Company successfully updated";
	}
}
