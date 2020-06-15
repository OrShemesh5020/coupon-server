package com.example.i_o_spring_project.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Customer;
import com.example.i_o_spring_project.repository.CompanyRepository;
import com.example.i_o_spring_project.repository.CustomerRepository;

@Service
public class AdminService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CustomerRepository customerRepository;

	public AdminService(ConfigurableApplicationContext applicationContext) {
		companyRepository = applicationContext.getBean(CompanyRepository.class);
		customerRepository = applicationContext.getBean(CustomerRepository.class);
	}

	public boolean login(String email, String password) throws CouponsSystemExceptions {

		if (!email.equals("admin@admin.com") && !password.equals("admin")) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"email and password are incorrect");
		}

		if (!email.equals("admin@admin.com")) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "inserted email is incorrect");
		}
		if (!password.equals("admin")) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "inserted password is incorrect");
		}
		return true;

	}

	@Transactional
	public void addCompany(Company company) throws CouponsSystemExceptions {
		if (companyRepository.getCompany(company.getName(), company.getEmail()).isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE,
					"The name and the email are already taken");
		}
		if (companyRepository.findByName(company.getName()).isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This name is already taken!");
		}
		if (companyRepository.findByEmail(company.getEmail()).isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
		}
		companyRepository.save(company);
	}

	@Transactional
	public void updateCompany(Company company) throws CouponsSystemExceptions {
		if (!companyRepository.existsById(company.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This company does not exist");
		}
		if (!companyRepository.findById(company.getId()).get().getName().equals(company.getName())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"You can't change the name of the company, because the company's name is an unchangeable attribute");
		}
		if (!companyRepository.findById(company.getId()).get().getEmail().equals(company.getEmail())) {
			if (companyRepository.findByEmail(company.getEmail()).isPresent()) {
				throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
			}
			companyRepository.save(company);
			System.out.println("\n--This company has been updated--\n");
		} else {
			companyRepository.save(company);
			System.out.println("\n--This company has been updated--\n");
		}
	}

	@Transactional
	public void removeCompany(Company company) throws CouponsSystemExceptions {
		if (!companyRepository.existsById(company.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This company does not exist");
		}
		companyRepository.delete(company);
		System.out.println("\n--This company has been deleted--\n");
	}

	public List<Company> getAllCompanies() throws CouponsSystemExceptions {
		List<Company> companies = companyRepository.findAll();
		if (companies != null) {
			return companies;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COMPANYS_NOT_FOUND);
	}

	public Company getCompany(int companyId) throws CouponsSystemExceptions {
		Optional<Company> company = companyRepository.findById(companyId);
		if (!company.isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.COMPANY_NOT_FOUND);
		}
		return company.get();
	}

	public Company getCompany(String name) throws CouponsSystemExceptions {
		Optional<Company> company = companyRepository.findByName(name);
		if (!company.isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.COMPANY_NOT_FOUND);
		}
		return company.get();
	}

	@Transactional
	public void addCustomer(Customer customer) throws CouponsSystemExceptions {
		if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
		}
		customerRepository.save(customer);
		System.out.println("\n--This customer has been added--\n");
	}

	@Transactional
	public void updateCustomer(Customer customer) throws CouponsSystemExceptions {
		if (!customerRepository.existsById(customer.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"This customer does not exist");
		}
		if (!customerRepository.findById(customer.getId()).get().getEmail().equals(customer.getEmail())) {
			if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
				throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
			}
			customerRepository.save(customer);
			System.out.println("\n--This customer has been updated--\n");
		} else {
			customerRepository.save(customer);
			System.out.println("\n--This customer has been updated--\n");
		}
	}

	@Transactional
	public void removeCustomer(Customer customer) throws CouponsSystemExceptions {
		if (!customerRepository.existsById(customer.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"This customer does not exist");
		}
		customerRepository.delete(customer);
		System.out.println("\n--This customer has been deleted--\n");
	}

	public List<Customer> getAllCustomers() throws CouponsSystemExceptions {
		List<Customer> customers = customerRepository.findAll();
		if (customers != null) {
			return customers;
		}
		throw new CouponsSystemExceptions(SystemExceptions.CUSTOMERS_NOT_FOUND);
	}

	public Customer getCustomer(int customerId) throws CouponsSystemExceptions {
		Optional<Customer> customer = customerRepository.findById(customerId);
		if (!customer.isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.CUSTOMER_NOT_FOUND);
		}
		return customer.get();
	}
}
