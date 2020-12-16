package com.example.i_o_spring_project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.example.i_o_spring_project.model.ClientType;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Customer;
import com.example.i_o_spring_project.modelDTO.CompanyDTO;
import com.example.i_o_spring_project.modelDTO.CustomerDTO;

@RestController
@RequestMapping("/admin")
public class AdminController extends ClientController {

	private final ClientType clientType = ClientType.ADMINISTRATOR;
	
	@PostMapping("/company")
	public ResponseEntity<CompanyDTO> addCompany(@RequestBody Company company, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		return new ResponseEntity<CompanyDTO>(dTOconverter.convertCompany(adminService.addCompany(company)),
				HttpStatus.OK);
	}

	@PutMapping("/company")
	public ResponseEntity<CompanyDTO> updateCompany(@RequestBody Company company, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		return new ResponseEntity<CompanyDTO>(dTOconverter.convertCompany(adminService.updateCompany(company)),
				HttpStatus.OK);
	}

	@DeleteMapping("/company/{id}")
	public ResponseEntity<Void> deleteCompany(@PathVariable int id, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		adminService.removeCompany(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/companies")
	public ResponseEntity<List<CompanyDTO>> getAllCompanies(HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		return new ResponseEntity<List<CompanyDTO>>(dTOconverter.convertCompanyList(adminService.getAllCompanies()),
				HttpStatus.OK);
	}

	@GetMapping("/company/{id}")
	public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable int id, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		return new ResponseEntity<CompanyDTO>(dTOconverter.convertCompany(adminService.getCompany(id)), HttpStatus.OK);
	}

	@GetMapping("/company")
	public ResponseEntity<CompanyDTO> getCompanyByName(@RequestParam String name, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		return new ResponseEntity<CompanyDTO>(dTOconverter.convertCompany(adminService.getCompany(name)),
				HttpStatus.OK);
	}

	@PostMapping("/customer")
	public ResponseEntity<CustomerDTO> addCustomer(@RequestBody Customer customer, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		return new ResponseEntity<CustomerDTO>(dTOconverter.convertCustomer(adminService.addCustomer(customer)),
				HttpStatus.OK);
	}

	@PutMapping("/customer")
	public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody Customer customer, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		return new ResponseEntity<CustomerDTO>(dTOconverter.convertCustomer(adminService.updateCustomer(customer)),
				HttpStatus.OK);
	}

	@DeleteMapping("/customer/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable int id, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		adminService.removeCustomer(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/customers")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers(HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		return new ResponseEntity<List<CustomerDTO>>(dTOconverter.convertCustomerList(adminService.getAllCustomers()),
				HttpStatus.OK);
	}

	@GetMapping("/customer")
	public ResponseEntity<CustomerDTO> getCustomer(@RequestParam int id, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		return new ResponseEntity<CustomerDTO>(dTOconverter.convertCustomer(adminService.getCustomer(id)),
				HttpStatus.OK);
	}
	
	@GetMapping("/salesNumber")
	public ResponseEntity<Integer> getTotalCouponsSalesNumber(HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		return new ResponseEntity<Integer>(adminService.getTheTotalSalesNumber(), HttpStatus.OK);
	}
}
