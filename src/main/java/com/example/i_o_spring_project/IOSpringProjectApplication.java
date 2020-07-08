package com.example.i_o_spring_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IOSpringProjectApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(IOSpringProjectApplication.class,
				args);
		Test test = applicationContext.getBean(Test.class);
		test.testAll();
//		LoginManager loginManager = applicationContext.getBean(LoginManager.class);
//		try {
//			CustomerService customerService = (CustomerService) loginManager.login("idocohen@gmail.com", "12345678910",
//					ClientType.CUSTOMER);
//			Coupon coupon = customerService.getOneCoupon(17);
//			System.out.println(coupon.toString());
//			customerService.purchaseCoupon(coupon);
//		} catch (CouponsSystemExceptions couponException) {
//			System.err.println(couponException.toString());
//		}
	}

//	@PostConstruct
//	public void setCompanyValue() {
//		com
//	}
}
