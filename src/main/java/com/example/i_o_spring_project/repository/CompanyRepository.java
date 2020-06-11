package com.example.i_o_spring_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.i_o_spring_project.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Query("select CASE WHEN EXISTS (SELECT * FROM companies WHERE id = 6) THEN TRUE ELSE FALSE END AS bool;")
	public boolean doesCompanyExists(String email, String password);

//	@Query("select all from companise")
//	public ArrayList<Company> getAllCompanies();

}
