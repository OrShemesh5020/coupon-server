package com.example.i_o_spring_project.validation;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.model.Customer;

public class CustomerValidation extends ObjectValidation<Customer> {

	@Override
	public void isTheObjectEmpty(Customer customer) throws CouponsSystemExceptions {
		isTheParameterEmpty(customer.getFirstName());
		isTheParameterEmpty(customer.getLastName());
		isTheParameterEmpty(customer.getEmail());
		isTheParameterEmpty(customer.getPassword());
	}

	@Override
	public void charactersHasExceeded(Customer customer) throws CouponsSystemExceptions {
		charactersHasExceeded(customer.getFirstName(), "first name", 45);
		charactersHasExceeded(customer.getLastName(), "last name", 45);
		charactersHasExceeded(customer.getEmail(), "email", 45);
		charactersHasExceeded(customer.getPassword(), "password", 45);
	}

}
