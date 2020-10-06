package com.example.i_o_spring_project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.i_o_spring_project.tokens.JWTFilter;
import com.example.i_o_spring_project.tokens.TokenServiceImpl;

@Configuration
public class WebConfig {

	@Autowired
	private JWTFilter filter;

	@Bean
	public FilterRegistrationBean<JWTFilter> loggingFilter() {
		FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(filter);
//		registrationBean.addUrlPatterns("[^login]");
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}
}
