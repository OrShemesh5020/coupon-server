package com.example.i_o_spring_project.validation;

import org.springframework.stereotype.Component;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.model.Customer;

@Component
public class CustomerValidation extends ObjectValidation<Customer> {

	@Override
	public void isTheObjectEmpty(Customer customer) throws CouponsSystemExceptions {
		isTheParameterEmpty(customer.getFirstName(), "first name");
		isTheParameterEmpty(customer.getLastName(), "last name");
		isTheParameterEmpty(customer.getEmail(), "email");
		isTheParameterEmpty(customer.getPassword(), "password");
	}

	@Override
	public void charactersHasExceeded(Customer customer) throws CouponsSystemExceptions {
		charactersHasExceeded(customer.getFirstName(), "first name", 45);
		charactersHasExceeded(customer.getLastName(), "last name", 45);
		charactersHasExceeded(customer.getEmail(), "email", 45);
		charactersHasExceeded(customer.getPassword(), "password", 45);
	}

}
