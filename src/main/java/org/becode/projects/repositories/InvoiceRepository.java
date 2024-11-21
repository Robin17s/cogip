package org.becode.projects.repositories;

import org.becode.projects.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{

}
