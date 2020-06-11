package com.example.i_o_spring_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.i_o_spring_project.repository.CompanyRepository;

@SpringBootApplication
public class IOSpringProjectApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(IOSpringProjectApplication.class,
				args);
		CompanyRepository companyRepository = applicationContext.getBean(CompanyRepository.class);
		boolean f = companyRepository.doesCompanyExists("dfsv", "sdv");
		System.out.println(f);

	}

}
