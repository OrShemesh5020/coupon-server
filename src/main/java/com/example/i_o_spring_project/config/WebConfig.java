package com.example.i_o_spring_project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.i_o_spring_project.exceptions.ErrorAttributesGetter23;
import com.example.i_o_spring_project.tokens.JWTFilter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableScheduling
@EnableSwagger2
public class WebConfig implements WebMvcConfigurer {

	private JWTFilter filter;

	@Autowired
	public WebConfig(JWTFilter filter) {
		this.filter = filter;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("*");
	}

	@Bean
	public FilterRegistrationBean<JWTFilter> loggingFilter() {
		FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(filter);
		registrationBean.addUrlPatterns("/admin/*");
		registrationBean.addUrlPatterns("/company/*");
		registrationBean.addUrlPatterns("/customer/*");
		return registrationBean;
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	@Bean
	public ErrorAttributes errorAttributes() {
		return new ErrorAttributesGetter23();
	}
}
