package org.becode.projects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.becode.projects.domain.Invoice;
import org.becode.projects.repositories.InvoiceRepository;
import org.becode.projects.services.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class InvoiceServiceTest {

	@Mock
	private InvoiceRepository invoiceRepository;
	
	@InjectMocks
	private InvoiceService invoiceService;
	
	private Invoice invoice;
	
	@BeforeEach
	public void setUp() throws Exception {
		invoice = new Invoice();
		invoice.setId(1);
		invoice.setInvoice_company_id(1);
		invoice.setInvoice_contact_id(1);
		
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getAllInvoices() {
        List<Invoice> invoices = Arrays.asList(invoice);
        when(invoiceRepository.findAll()).thenReturn(invoices);

        List<Invoice> result = invoiceService.getAllInvoices();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getInvoice_company_id());
        assertEquals(1, result.get(0).getInvoice_contact_id());
	}
	
	@Test
	void getSpecificInvoice() {
		when(invoiceRepository.getById(1)).thenReturn(invoice);
		
		Invoice result = invoiceService.getSpecificInvoice(1);
		
		assertNotNull(result);
        assertEquals(1, result.getInvoice_company_id());
        assertEquals(1, result.getInvoice_contact_id());
	}
	
	@Test
	void createNewInvoice() {
        when(invoiceRepository.save(Mockito.any(Invoice.class))).thenReturn(invoice);

        String result = invoiceService.createNewInvoice(invoice);

        assertEquals("New invoice successfully created", result);
        verify(invoiceRepository, times(1)).save(invoice);
	}
	
	@Test
	void deleteInvoice() {
		int invoiceId = 1;
		doNothing().when(invoiceRepository).deleteById(invoiceId);

        String result = invoiceService.deleteInvoice(invoiceId);

        assertEquals("Invoice with id 1 successfully removed", result);
        verify(invoiceRepository, times(1)).deleteById(invoiceId);
	}
	
	@Test 
	void updateInvoice(){
		when(invoiceRepository.save(invoice)).thenReturn(invoice);

        String result = invoiceService.updateInvoice(invoice);

        assertEquals("Invoice successfully updated", result);
        verify(invoiceRepository, times(1)).save(invoice);
	}

}
