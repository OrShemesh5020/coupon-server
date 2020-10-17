package com.example.i_o_spring_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;

@ControllerAdvice
public class ExceptionHandlerApi {

	@ExceptionHandler(CouponsSystemExceptions.class)
	public ResponseEntity<String> exceptionHandler(CouponsSystemExceptions ex) {
		if (ex.getErrorCode().equals(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED)
				|| ex.getErrorCode().equals(SystemExceptions.ILLEGAL_VALUE_ENTERED)
				|| ex.getErrorCode().equals(SystemExceptions.VALUE_UNAVAILABLE)
				|| ex.getErrorCode().equals(SystemExceptions.MISSING_VALUE)
				|| ex.getErrorCode().equals(SystemExceptions.INCORRECT_VALUE_ENTERED)) {
			System.err.println("ApiExceptionHandler: " + ex.getErrorCode());
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} else if (ex.getErrorCode().equals(SystemExceptions.INVALID_TOKEN)) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
