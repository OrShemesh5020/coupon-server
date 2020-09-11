package com.example.i_o_spring_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.repository.CategoryRepository;
import com.example.i_o_spring_project.repository.CompanyRepository;
import com.example.i_o_spring_project.repository.CouponRepository;
import com.example.i_o_spring_project.repository.CustomerRepository;
import com.example.i_o_spring_project.validation.CompanyValidation;
import com.example.i_o_spring_project.validation.CouponValidation;
import com.example.i_o_spring_project.validation.CustomerValidation;

@Component
public abstract class ClientService {

	@Autowired
	protected CompanyRepository companyRepository;
	@Autowired
	protected CustomerRepository customerRepository;
	@Autowired	
	protected CouponRepository couponRepository;
	@Autowired
	protected CategoryRepository categoryRepository;
	@Autowired
	protected CompanyValidation companyValidation;
	@Autowired
	protected CustomerValidation customerValidation;
	@Autowired
	protected CouponValidation couponValidation;

	public ClientService() {
	}

	public abstract boolean login(String email, String password);

}
