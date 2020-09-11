package com.example.i_o_spring_project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Customer;

@Service
public class AdminService extends ClientService {

	public AdminService() {
		super();
	}

	public boolean login(String email, String password) {

		if (!email.equals("admin@admin.com") && !password.equals("admin")) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"email and password are incorrect");
		}

		if (!email.equals("admin@admin.com")) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "inserted email is incorrect");
		}
		if (!password.equals("admin")) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"inserted password is incorrect");
		}
		return true;
	}

	@Transactional
	public Company addCompany(Company company) {
		companyValidation.isTheObjectEmpty(company);
		companyValidation.charactersHasExceeded(company);
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
		System.out.println("\n--This company has been added--\n");
		return companyRepository.save(company);
	}

	@Transactional
	public Company updateCompany(Company company) {
		if (!companyRepository.existsById(company.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This company does not exist");
		}
		companyValidation.isTheObjectEmpty(company);
		companyValidation.charactersHasExceeded(company);
		if (!companyRepository.findById(company.getId()).get().getName().equals(company.getName())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"You can't change the name of the company, because the company's name is an unchangeable attribute");
		}
		if (!companyRepository.findById(company.getId()).get().getEmail().equals(company.getEmail())) {
			if (companyRepository.findByEmail(company.getEmail()).isPresent()) {
				throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
			}
		}
		System.out.println("\n--This company has been updated--\n");
		return companyRepository.save(company);
	}

	@Transactional
	public void removeCompany(int companyId) {
		if (!companyRepository.existsById(companyId)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This company does not exist");
		}
		Company company = companyRepository.findById(companyId).get();
		couponRepository.deleteByCompanyAllPurchasedCoupon(companyId);
		couponRepository.deleteByCompany(company);
		companyRepository.delete(company);

		System.out.println("\n--This company along with all its' coupons have been deleted--\n");
	}

	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public Company getCompany(int companyId) {
		Optional<Company> company = companyRepository.findById(companyId);
		if (!company.isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.COMPANY_NOT_FOUND);
		}
		return company.get();
	}

	public Company getCompany(String name) {
		Optional<Company> company = companyRepository.findByName(name);
		if (!company.isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.COMPANY_NOT_FOUND);
		}
		return company.get();
	}

	@Transactional
	public Customer addCustomer(Customer customer) {
		customerValidation.isTheObjectEmpty(customer);
		customerValidation.charactersHasExceeded(customer);
		if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
		}
		System.out.println("\n--This customer has been added--\n");
		return customerRepository.save(customer);
	}

	@Transactional
	public Customer updateCustomer(Customer customer) {
		if (!customerRepository.existsById(customer.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"This customer does not exist");
		}
		customerValidation.isTheObjectEmpty(customer);
		customerValidation.charactersHasExceeded(customer);
		if (!customerRepository.findById(customer.getId()).get().getEmail().equals(customer.getEmail())) {
			if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
				throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
			}
		}
		System.out.println("\n--This customer has been updated--\n");
		return customerRepository.save(customer);
	}

	@Transactional
	public void removeCustomer(int customerId) {
		if (!customerRepository.existsById(customerId)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"This customer does not exist");
		}
		customerRepository.deleteById(customerId);
		System.out.println("\n--This customer has been deleted--\n");

	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer getCustomer(int customerId) {
		Optional<Customer> customer = customerRepository.findById(customerId);
		if (!customer.isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.CUSTOMER_NOT_FOUND);
		}
		return customer.get();
	}
}
