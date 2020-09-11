package com.example.i_o_spring_project.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.i_o_spring_project.service.AdminService;
import com.example.i_o_spring_project.service.CompanyService;
import com.example.i_o_spring_project.service.CustomerService;

public abstract class ClientController {

	@Autowired
	protected AdminService adminService;
	@Autowired
	protected CompanyService companyService;
	@Autowired
	protected CustomerService customerService;

	/**
	 * this is the father function that will be inhereted
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public abstract boolean login(String email, String password);
}
