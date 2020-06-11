package com.example.i_o_spring_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.i_o_spring_project.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
