package com.example.i_o_spring_project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;
import com.example.i_o_spring_project.model.Category;
import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.model.Customer;

@Service
public class CustomerService extends ClientService {

	private Customer customer;

	public CustomerService() {
		super();
	}

	@Transactional
	public boolean login(String email, String password) {
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
		if (optionalCustomer.isEmpty()) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "inserted email is incorrect");
		}
		Customer customer = optionalCustomer.get();
		if (!customer.getPassword().equals(password)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"inserted password is incorrect");
		}
		setCustomer(customer);
		return true;
	}

	@Transactional
	public Coupon purchaseCoupon(Coupon coupon) {
		Date now = new Date();
		if (!couponRepository.existsById(coupon.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND, "This coupon does not exist");
		}
		if (hasCouponPurchased(coupon.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"You already have this coupon!");
		}
		if (coupon.getAmount() == 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "No more coupons left!");
		}
		if (now.before(coupon.getStartDate())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"This coupon has not been launched yet");
		}
		if (coupon.getEndDate().before(now)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This coupon has expired");
		}
		customer.getCoupons().add(coupon);
		customerRepository.save(customer);
		System.out.println("\n--The coupon has been purchased--\n");
		return coupon;
	}

	@Transactional
	public void removeCouponPurchase(int couponId) {
		if (!couponRepository.existsById(couponId)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This coupon does not exist");
		}
		if (!hasCouponPurchased(couponId)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"You have not purchased this coupon yet!");
		}
		customer.getCoupons().remove(couponId);
		customerRepository.save(customer);
		System.out.println("\n--This coupon purchase has been removed--\n");
	}

	public List<Coupon> getAllCoupons() {
		return couponRepository.findAll();
	}

	public Coupon getOneCoupon(int couponId) {
		Optional<Coupon> coupon = couponRepository.findById(couponId);
		if (coupon.isPresent()) {
			return coupon.get();
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND);
	}

	public List<Coupon> getCustomerCoupons() {
		return customer.getCoupons();
	}

	public List<Coupon> getCustomerCoupons(Category category) {
		List<Coupon> coupons = new ArrayList<>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getCategory().equals(category)) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}

	public List<Coupon> getCustomerCoupons(Double price) {
		List<Coupon> coupons = new ArrayList<>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getPrice() <= price) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}

	public Customer getCustomerDetails() {
		if (customer == null) {
			throw new CouponsSystemExceptions(SystemExceptions.CUSTOMER_NOT_FOUND);
		}
		return customer;
	}

	@Transactional
	public Customer UpdateDetails(Customer givenCustomer) {
		if (!customerRepository.existsById(givenCustomer.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"This customer does not exist");
		}
		if (!customer.getId().equals(givenCustomer.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"This customer's id has been changed");
		}
		customerValidation.isTheObjectEmpty(givenCustomer);
		customerValidation.charactersHasExceeded(givenCustomer);
		if (!customer.getEmail().equals(givenCustomer.getEmail())) {
			if (customerRepository.findByEmail(givenCustomer.getEmail()).isPresent()) {
				throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
			}
		}
		setCustomer(givenCustomer);
		System.out.println("\n--This customer has been updated--\n");
		return customerRepository.save(givenCustomer);
	}

	private boolean hasCouponPurchased(int couponId) {
		List<Coupon> coupons = customer.getCoupons();
		for (Coupon purchasedCoupon : coupons) {
			if (purchasedCoupon.getId() == couponId) {
				return true;
			}
		}
		return false;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
