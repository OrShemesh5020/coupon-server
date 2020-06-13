package com.example.i_o_spring_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.i_o_spring_project.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	public Optional<Company> findByEmail(String email);

	public Optional<Company> findByName(String name);

	public Optional<Company> findById(int id);

}
