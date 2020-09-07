package com.example.i_o_spring_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/company")
	public void addCompany(@RequestBody Company company) {
		try {
			adminService.addCompany(company);
		} catch (CouponsSystemExceptions e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
}
