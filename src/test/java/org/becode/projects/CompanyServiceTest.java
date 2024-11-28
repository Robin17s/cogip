package org.becode.projects;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.becode.projects.controllers.CompanyController;
import org.becode.projects.domain.Company;
import org.becode.projects.repositories.CompanyRepository;
import org.becode.projects.services.CompanyService;
import org.becode.projects.services.ContactService;
import org.becode.projects.services.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

class CompanyServiceTest {

	@Mock
	private CompanyRepository companyRepository;
	
	@InjectMocks
	private CompanyService companyService;
	
	private Company company;
	
	@BeforeEach
	public void setUp() throws Exception {
		company = new Company();
		company.setId(1);
		company.setName("Apple");
		company.setCountry("Belgium");
		company.setType("provider");
		company.setVat("12345");
		
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getAllCompanies() {
        List<Company> companies = Arrays.asList(company);
        when(companyRepository.findAll()).thenReturn(companies);

        // Act
        List<Company> result = companyService.getAllCompanies();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Apple", result.get(0).getName());
        assertEquals("Belgium", result.get(0).getCountry());
        assertEquals("provider", result.get(0).getType());
        assertEquals("12345", result.get(0).getVat());
	}
	
	@Test
	void getSpecificCompany() {
		when(companyRepository.getById(1)).thenReturn(company);
		
		Company result = companyService.getSpecificCompany(1);
		
		assertNotNull(result);
        assertEquals("Apple", result.getName());
        assertEquals("Belgium", result.getCountry());
        assertEquals("provider", result.getType());
        assertEquals("12345", result.getVat());
	}
	
	@Test
	void createNewCompany() {
        when(companyRepository.save(Mockito.any(Company.class))).thenReturn(company);

        String result = companyService.createNewCompany(company);

        assertEquals("New company successfully created", result);
        verify(companyRepository, times(1)).save(company);
	}
	
	@Test
	void deleteCompany() {
		int companyId = 1;
		doNothing().when(companyRepository).deleteById(companyId);

        String result = companyService.deleteCompany(companyId);

        assertEquals("Company with id 1 successfully removed", result);
        verify(companyRepository, times(1)).deleteById(companyId);
	}
	
	@Test 
	void updateCompany(){
		when(companyRepository.save(company)).thenReturn(company);

        String result = companyService.updateCompany(company);

        assertEquals("Company successfully updated", result);
        verify(companyRepository, times(1)).save(company);
	}

}
