package org.becode.projects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.becode.projects.domain.Company;
import org.becode.projects.domain.Contact;
import org.becode.projects.repositories.CompanyRepository;
import org.becode.projects.repositories.ContactRepository;
import org.becode.projects.services.CompanyService;
import org.becode.projects.services.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ContactServiceTest {

	@Mock
	private ContactRepository contactRepository;
	
	@InjectMocks
	private ContactService contactService;
	
	private Contact contact;
	
	@BeforeEach
	public void setUp() throws Exception {
		contact = new Contact();
		contact.setId(1);
		contact.setFirstname("robin");
		contact.setLastname("sanders");
		contact.setContact_company_id(1);
		contact.setEmail("robin@telenet.be");
		contact.setPhone("12345");
		
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getAllContacts() {
        List<Contact> contacts = Arrays.asList(contact);
        when(contactRepository.findAll()).thenReturn(contacts);

        // Act
        List<Contact> result = contactService.getAllContacts();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("robin", result.get(0).getFirstname());
        assertEquals("sanders", result.get(0).getLastname());
        assertEquals("robin@telenet.be", result.get(0).getEmail());
        assertEquals("12345", result.get(0).getPhone());
        assertEquals(1, result.get(0).getContact_company_id());
	}
	
	@Test
	void getSpecificContact() {
		when(contactRepository.getById(1)).thenReturn(contact);
		
		Contact result = contactService.getSpecificContact(1);
		
		assertNotNull(result);
        assertEquals("robin", result.getFirstname());
        assertEquals("sanders", result.getLastname());
        assertEquals("robin@telenet.be", result.getEmail());
        assertEquals("12345", result.getPhone());
        assertEquals(1, result.getContact_company_id());
	}
	
	@Test
	void createNewContact() {
        when(contactRepository.save(Mockito.any(Contact.class))).thenReturn(contact);

        String result = contactService.createNewContact(contact);

        assertEquals("New contact successfully created", result);
        verify(contactRepository, times(1)).save(contact);
	}
	
	@Test
	void deleteContact() {
		int contactId = 1;
		doNothing().when(contactRepository).deleteById(contactId);

        String result = contactService.deleteContact(contactId);

        assertEquals("Contact with id 1 successfully removed", result);
        verify(contactRepository, times(1)).deleteById(contactId);
	}
	
	@Test 
	void updateContact(){
		when(contactRepository.save(contact)).thenReturn(contact);

        String result = contactService.updateContact(contact);

        assertEquals("Contact successfully updated", result);
        verify(contactRepository, times(1)).save(contact);
	}

}
