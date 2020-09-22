package com.example.i_o_spring_project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Customer;

@RestController
@RequestMapping("/admin")
public class AdminController extends ClientController {

	@PostMapping("/company")
	public ResponseEntity<Company> addCompany(@RequestBody Company company) {
		return new ResponseEntity<Company>(adminService.addCompany(company), HttpStatus.OK);
	}

	@PutMapping("/company")
	public ResponseEntity<Company> updateCompany(@RequestBody Company company) {
		return new ResponseEntity<Company>(adminService.updateCompany(company), HttpStatus.OK);
	}

	@DeleteMapping("/company/{id}")
	public ResponseEntity<Void> deleteCompany(@PathVariable int id) {
		adminService.removeCompany(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/companies")
	public ResponseEntity<List<Company>> getAllCompanies() {
		return new ResponseEntity<List<Company>>(adminService.getAllCompanies(), HttpStatus.OK);
	}

	@GetMapping("/company/{id}")
	public ResponseEntity<Company> getCompanyById(@PathVariable int id) {
		return new ResponseEntity<Company>(adminService.getCompany(id), HttpStatus.OK);
	}

	/*
	 * when using @PathVariable mappimg here the spring app falls on illegal state
	 * exception
	 */
	@GetMapping("/company")
	public ResponseEntity<Company> getCompanyByName(@RequestParam String name) {
		return new ResponseEntity<Company>(adminService.getCompany(name), HttpStatus.OK);
	}

	@PostMapping("/customer")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		return new ResponseEntity<Customer>(adminService.addCustomer(customer), HttpStatus.OK);
	}

	@PutMapping("/customer")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
		return new ResponseEntity<Customer>(adminService.updateCustomer(customer), HttpStatus.OK);
	}

	@DeleteMapping("/customer/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
		adminService.removeCustomer(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return new ResponseEntity<List<Customer>>(adminService.getAllCustomers(), HttpStatus.OK);
	}

	@GetMapping("/customer")
	public ResponseEntity<Customer> getCustomer(@RequestParam int id) {
		return new ResponseEntity<Customer>(adminService.getCustomer(id), HttpStatus.OK);
	}

	@Override
	/**
	 * this function should be mapped
	 */
	@GetMapping("/login")
	public ResponseEntity<Boolean> login(@RequestParam String email, @RequestParam String password) {
		return new ResponseEntity<Boolean>(adminService.login(email, password), HttpStatus.OK);
	}
}
