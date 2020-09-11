package com.example.i_o_spring_project.validation;

import org.springframework.stereotype.Component;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;

@Component
public abstract class ObjectValidation<T> {

	public abstract void isTheObjectEmpty(T object) throws CouponsSystemExceptions;

	public abstract void charactersHasExceeded(T object) throws CouponsSystemExceptions;

	public void isTheParameterEmpty(Object value, String name) throws CouponsSystemExceptions {
		if (value == null) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"The " + name + " cannot be null!");
		}
	}

	public void isTheParameterEmpty(String value, String name) throws CouponsSystemExceptions {
		if (value == null) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"The " + name + " cannot be null!");
		}
		if (value.equals("") || value.equals(" ")) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"The " + name + " cannot be empty!");
		}
	}

	public void isTheParameterEmpty(int num, String name) throws CouponsSystemExceptions {
		if (num == 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"The " + name + " cannot be zero!");
		}
		if (num < 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"The " + name + " cannot be negative!");
		}
	}

	public void charactersHasExceeded(String attribute, String name, int limit) throws CouponsSystemExceptions {
		if (attribute.length() > limit) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"Please enter a maximum of " + limit + " characters in the '" + name + "' value");
		}
	}

}
