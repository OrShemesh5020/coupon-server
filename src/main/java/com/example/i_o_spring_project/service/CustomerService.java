package com.example.i_o_spring_project.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;
import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.model.Customer;
import com.example.i_o_spring_project.repository.CouponRepository;
import com.example.i_o_spring_project.repository.CustomerRepository;

public class CustomerService {

	@Autowired
	private Customer customer;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CouponRepository companyRepository;

	public CustomerService(ConfigurableApplicationContext applicationContext) {
		companyRepository = applicationContext.getBean(CouponRepository.class);
		customerRepository = applicationContext.getBean(CustomerRepository.class);
	}

	public boolean login(String email, String password) throws CouponsSystemExceptions {
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
		if (optionalCustomer.isEmpty()) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "inserted email is incorrect");
		}
		if (!optionalCustomer.get().getPassword().equals(password)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "inserted password is incorrect");
		}
		setCustomer(optionalCustomer.get());
		return true;
	}

	@Transactional
	public void purchaseCoupon(Coupon coupon) {
		System.out.println("customer id:" + customer.getId());
		customer.getCoupons().add(coupon);
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
