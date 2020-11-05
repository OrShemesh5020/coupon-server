package com.example.i_o_spring_project.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;
import com.example.i_o_spring_project.model.ClientType;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Customer;
import com.example.i_o_spring_project.model.User;
import com.example.i_o_spring_project.modelDTO.CompanyDTO;
import com.example.i_o_spring_project.modelDTO.CouponDTO;
import com.example.i_o_spring_project.modelDTO.CustomerDTO;

@RestController
@RequestMapping("/general")
public class PublicController extends ClientController {

	@PostMapping("/company")
	public ResponseEntity<CompanyDTO> addCompany(@RequestBody Company company) {
		System.err.println("PublicController/addCompany: " + company);
		return new ResponseEntity<CompanyDTO>(dTOconverter.convertCompany(adminService.addCompany(company)),
				HttpStatus.OK);
	}

	@PostMapping("/customer")
	public ResponseEntity<CustomerDTO> addCustomer(@RequestBody Customer customer) {
		System.err.println("PublicController/addCompany: " + customer);
		return new ResponseEntity<CustomerDTO>(dTOconverter.convertCustomer(adminService.addCustomer(customer)),
				HttpStatus.OK);
	}

	@GetMapping("/coupons")
	public ResponseEntity<List<CouponDTO>> viewAllCoupons() {
		return new ResponseEntity<List<CouponDTO>>(dTOconverter.convertCouponList(customerService.viewAllCoupons()),
				HttpStatus.OK);
	}

	@GetMapping("/login")
	public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password,
			HttpServletResponse response) {
		ClientType type = userService.getType(email);
		System.err.println("Type: " + type);
		switch (type) {
		case ADMINISTRATOR:
			return adminLogin(email, password, response);
			
		case COMPANY:
			return companyLogin(email, password, response);

		case CUSTOMER:
			return customerLogin(email, password, response);

		default:
			System.err.println("default");
			throw new CouponsSystemExceptions(SystemExceptions.MISSING_VALUE, "no type!");
		}
	}

	private ResponseEntity<User> login(int id, String email, String password, ClientType type,
			HttpServletResponse response) {
		User generatedUser = tokenFacade.generateUser(id, email, password, type);
		response.setHeader("token", generatedUser.getToken());
		return new ResponseEntity<User>(generatedUser, HttpStatus.OK);
	}

	private ResponseEntity<User> adminLogin(String email, String password, HttpServletResponse response) {
		if (adminService.login(email, password)) {
			return login(0, email, password, ClientType.ADMINISTRATOR, response);
		}
		return null;
	}

	private ResponseEntity<User> companyLogin(String email, String password, HttpServletResponse response) {
		if (companyService.login(email, password)) {
			Company company = companyService.getCompanyByEmail(email);
			return login(company.getId(), email, password, ClientType.COMPANY, response);
		}
		return null;
	}

	private ResponseEntity<User> customerLogin(String email, String password, HttpServletResponse response) {
		if (customerService.login(email, password)) {
			Customer customer = customerService.getCustomerByEmail(email);
			return login(customer.getId(), email, password, ClientType.CUSTOMER, response);
		}
		return null;
	}
}
