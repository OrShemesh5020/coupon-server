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
	protected CompanyValidation companyValidation;
	protected CustomerValidation customerValidation;
	protected CouponValidation couponValidation;

	public ClientService() {
//		companyRepository = new CompanyRepository();
//		customerRepository = new CustomerRepository();
//		couponRepository = new CouponRepository();
		companyValidation = new CompanyValidation();
		customerValidation = new CustomerValidation();
		couponValidation = new CouponValidation();
	}

	public abstract boolean login(String email, String password);

}
