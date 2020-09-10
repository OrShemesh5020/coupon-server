package com.example.i_o_spring_project.validation;

import org.springframework.stereotype.Component;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.model.Company;

@Component
public class CompanyValidation extends ObjectValidation<Company> {

	@Override
	public void isTheObjectEmpty(Company company) throws CouponsSystemExceptions {

		isTheParameterEmpty(company.getName(), "name");
		isTheParameterEmpty(company.getEmail(), "email");
		isTheParameterEmpty(company.getPassword(),"password");
	}

	@Override
	public void charactersHasExceeded(Company company) throws CouponsSystemExceptions {
		charactersHasExceeded(company.getName(), "name", 45);
		charactersHasExceeded(company.getEmail(), "email", 45);
		charactersHasExceeded(company.getPassword(), "password", 45);
	}

}
