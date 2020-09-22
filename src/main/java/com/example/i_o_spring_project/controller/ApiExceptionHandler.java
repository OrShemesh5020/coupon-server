package com.example.i_o_spring_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CouponsSystemExceptions.class)
	public ResponseEntity<String> NotFoundHandler(CouponsSystemExceptions ex) {
		if (ex.getErrorCode().equals(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED)
				|| ex.getErrorCode().equals(SystemExceptions.ILLEGAL_VALUE_ENTERED)
				|| ex.getErrorCode().equals(SystemExceptions.VALUE_UNAVAILABLE)) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
