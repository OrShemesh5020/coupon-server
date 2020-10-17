package com.example.i_o_spring_project.exceptions;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

public class ErrorAttributesGetter23 extends DefaultErrorAttributes {

	/**
	 * Extract error information.
	 *
	 * @param req request information
	 * @return error information
	 */
	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		// Get detailed error information with DefaultErrorAttributes class
//		ServletWebRequest swr = new ServletWebRequest(req);
		DefaultErrorAttributes dea = new DefaultErrorAttributes();
		ErrorAttributeOptions eao = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.BINDING_ERRORS,
				ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.MESSAGE,
				ErrorAttributeOptions.Include.STACK_TRACE);
		return dea.getErrorAttributes(webRequest, eao);
	}
}
