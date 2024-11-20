package org.becode.projects.repositories;

import org.becode.projects.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer>{

}
