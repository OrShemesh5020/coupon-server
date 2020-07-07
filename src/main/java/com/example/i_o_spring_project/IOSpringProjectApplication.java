package com.example.i_o_spring_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.i_o_spring_project.model.Company;

@SpringBootApplication
public class IOSpringProjectApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(IOSpringProjectApplication.class,
				args);
		Test test = applicationContext.getBean(Test.class);
//		test.testAll();
		Company company = applicationContext.getBean(Company.class);
		company.setEmail("email");
		System.out.println(company.toString());
//		ClientService companyService = applicationContext.getBean(CompanyService.class);
//		try {
//			boolean login = companyService.login("or@gmail.com", "123456");
//			System.out.println(login);
//		} catch (CouponsSystemExceptions e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
