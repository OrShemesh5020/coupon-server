package com.example.i_o_spring_project.validation;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;

public abstract class ObjectValidation<T> {

	public abstract void isTheObjectEmpty(T object) throws CouponsSystemExceptions;

	public abstract void charactersHasExceeded(T object) throws CouponsSystemExceptions;

	public void isTheParameterEmpty(Object value) throws CouponsSystemExceptions {
		if (value == null) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "value cannot be null!");
		}
	}

	public void isTheParameterEmpty(String value) throws CouponsSystemExceptions {
		if (value == null) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "value cannot be null!");
		}
		if (value.equals("")) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "value cannot be empty!");
		}
	}

	public void isTheParameterEmpty(int num) throws CouponsSystemExceptions {
		if (num == 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "value cannot be zero!");
		}
		if (num < 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "value cannot be negative!");
		}
	}

	public void charactersHasExceeded(String attribute, String name, int limit) throws CouponsSystemExceptions {
		if (attribute.length() > limit) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"Please enter maximum " + limit + " characters in '" + name + "' detailes");
		}
	}

}
