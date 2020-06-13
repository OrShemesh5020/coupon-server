package com.example.i_o_spring_project.servise;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.repository.CompanyRepository;
import com.example.i_o_spring_project.repository.CustomerRepository;

public class AdminServise {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CustomerRepository customerRepository;

	public boolean login(String email, String password) {

		if (!email.equals("admin@admin.com") && !password.equals("admin")) {
			System.err.println("email and password are incorrect ");
			return false;
		}

		if (!email.equals("admin@admin.com")) {
			System.err.println("email is incorrect");
			return false;
		}
		if (!password.equals("admin")) {
			System.err.println("password is incorrect");
			return false;
		}
		return true;

	}

	public void addCompany(Company company) throws CouponsSystemExceptions {
		if (companyRepository.findByName(company.getName()).isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This name is already taken!");
		}
		if (companyRepository.findByEmail(company.getEmail()).isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
		}
		companyRepository.save(company);
	}

	public void updateCompany(Company company) throws CouponsSystemExceptions {
		if (!companyRepository.findById(company.getId()).isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This company does not exist");
		}
		if (!companyRepository.findById(company.getId()).get().getName().equals(company.getName())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"You can't change the name of the company");
		}
		if (!companyRepository.findById(company.getId()).get().getEmail().equals(company.getEmail())) {
			if (companyRepository.findByEmail(company.getEmail()).isPresent()) {
				throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
			}
			System.out.println("\n--This company has been updated--\n");
			companyRepository.save(company);
		} else {
			System.out.println("\n--This company has been updated--\n");
			companyRepository.save(company);
		}
	}
}
