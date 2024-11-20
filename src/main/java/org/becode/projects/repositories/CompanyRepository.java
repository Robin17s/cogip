package org.becode.projects.repositories;

import org.becode.projects.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer>{

}
