package com.example.i_o_spring_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.i_o_spring_project.modelDTO.DTOToModelConverter;
import com.example.i_o_spring_project.modelDTO.ModelToDTOConverter;
import com.example.i_o_spring_project.service.AdminService;
import com.example.i_o_spring_project.service.CompanyService;
import com.example.i_o_spring_project.service.CustomerService;
import com.example.i_o_spring_project.service.TokenFacade;

@Component
public abstract class ClientController {

	protected final String TYPE = "type";
	protected final String ID = "id";

	@Autowired
	protected AdminService adminService;

	@Autowired
	protected CompanyService companyService;

	@Autowired
	protected CustomerService customerService;

	@Autowired
	protected TokenFacade tokenFacade;
	
	@Autowired
	protected ModelToDTOConverter dTOconverter;
	
	@Autowired
	protected DTOToModelConverter modelConverter;

}
