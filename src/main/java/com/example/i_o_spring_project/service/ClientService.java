package com.example.i_o_spring_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.repository.CompanyRepository;
import com.example.i_o_spring_project.repository.CouponRepository;
import com.example.i_o_spring_project.repository.CustomerRepository;
import com.example.i_o_spring_project.validation.CompanyValidation;
import com.example.i_o_spring_project.validation.CouponValidation;
import com.example.i_o_spring_project.validation.CustomerValidation;

public abstract class ClientService {

	@Autowired
	protected CompanyRepository companyRepository;
	@Autowired
	protected CustomerRepository customerRepository;
	@Autowired
	protected CouponRepository couponRepository;
	@Autowired
	protected CompanyValidation companyValidation;
	@Autowired
	protected CustomerValidation customerValidation;
	@Autowired
	protected CouponValidation couponValidation;

	public ClientService(ConfigurableApplicationContext applicationContext) {
		companyRepository = applicationContext.getBean(CompanyRepository.class);
		customerRepository = applicationContext.getBean(CustomerRepository.class);
		couponRepository = applicationContext.getBean(CouponRepository.class);
		companyValidation = new CompanyValidation();
		customerValidation = new CustomerValidation();
		couponValidation = new CouponValidation();
	}

	public abstract boolean login(String email, String password) throws CouponsSystemExceptions;

}
