package com.example.i_o_spring_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;
import com.example.i_o_spring_project.model.ClientType;
import com.example.i_o_spring_project.repository.CompanyRepository;
import com.example.i_o_spring_project.repository.CustomerRepository;

@Service
public class UserService {
	private final String adminEmail = "admin@admin.com";
	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private CustomerRepository customerRepository;

	public ClientType getType(String email) {
		if (email.equals(adminEmail)) {
			return ClientType.ADMINISTRATOR;
		}
		if (companyRepository.findByEmail(email).isPresent()) {
			return ClientType.COMPANY;
		}
		return ClientType.CUSTOMER;
	}

	public void checkEmail(String email) {
		if (email.equals(adminEmail)) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "email is taken");
		}
		if (companyRepository.findByEmail(email).isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "email is taken");
		}
		if (customerRepository.findByEmail(email).isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "email is taken");
		}
	}
}
