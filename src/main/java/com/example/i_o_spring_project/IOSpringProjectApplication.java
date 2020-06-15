package com.example.i_o_spring_project;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.model.Category;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.model.Customer;
import com.example.i_o_spring_project.repository.CategoryRepository;
import com.example.i_o_spring_project.repository.CompanyRepository;
import com.example.i_o_spring_project.repository.CouponRepository;
import com.example.i_o_spring_project.repository.CustomerRepository;
import com.example.i_o_spring_project.service.AdminService;
import com.example.i_o_spring_project.service.CustomerService;

@SpringBootApplication
public class IOSpringProjectApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(IOSpringProjectApplication.class,
				args);
		CompanyRepository companyRepository = applicationContext.getBean(CompanyRepository.class);
		CouponRepository couponRpository = applicationContext.getBean(CouponRepository.class);
		CustomerRepository customerRepository = applicationContext.getBean(CustomerRepository.class);
		CategoryRepository categoryRepository = applicationContext.getBean(CategoryRepository.class);
		Optional<Company> optionalCompany = companyRepository.findById(6);
		System.out.println(optionalCompany.toString());
		Company company = optionalCompany.get();
//		if(company!=null) {
//			couponRpository.findByCompanyAndPrice(company, 50);
//		}
//		List<Company> all = companyRepository.findAll();
//		for (Company company2 : all) {
//			System.out.println(company2.toString());
//		}
		List<Optional<Coupon>> companysCoupons = couponRpository.getCompanyCouponsByPrice(company, 2000);
		if (!companysCoupons.isEmpty()) {
			for (Optional<Coupon> coupon : companysCoupons) {
				System.out.println(coupon.toString());
			}
		} else {
			System.out.println("Coupon not found");
		}
		System.out.println("*************************************");
		Category category = categoryRepository.findByName("food");
		List<Optional<Coupon>> couponsByCategory = couponRpository.getCompanyCouponsByCategory(company, category);
		if (!couponsByCategory.isEmpty()) {
			for (Optional<Coupon> couponByCategory : couponsByCategory) {
				System.out.println(couponByCategory.toString());
			}
		} else {
			System.out.println("Coupon not found");
		}
		System.out.println("*************************************");
		Optional<Coupon> oneCoupon = couponRpository.getOneCoupon(company, "phone");
		System.out.println(oneCoupon.toString());

//		Optional<Customer> customr = customerRepository.findByEmail("orshemesh5020@gmail.com");
		List<Customer> all = customerRepository.findAll();
		for (Customer customer : all) {
			System.out.println(customer.toString());
		}
		Optional<Company> taken = companyRepository.getCompany("Orcompa", "or@gmail.com");
		System.out.println(taken.toString());

		AdminService adminService = new AdminService(applicationContext);
//		Company newCompany;
//		try {
//			newCompany = adminService.getCompany("Nahum3");
//			adminService.removeCompany(newCompany);
//		} catch (CouponsSystemExceptions e1) {
//			// TODO Auto-generated catch block
//			System.out.println(e1.toString());
//		}		
		CustomerService customerService = new CustomerService(applicationContext);
		Optional<Coupon> coupon = couponRpository.findById(51);
		customerService.setCustomer(customerRepository.findById(8).get());
		customerService.purchaseCoupon(coupon.get());
	}
}
