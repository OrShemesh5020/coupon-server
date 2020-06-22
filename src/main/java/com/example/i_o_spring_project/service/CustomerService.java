package com.example.i_o_spring_project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;
import com.example.i_o_spring_project.model.Category;
import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.model.Customer;

public class CustomerService extends ClientService {

	@Autowired
	private Customer customer;

	public CustomerService(ConfigurableApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Transactional
	public boolean login(String email, String password) throws CouponsSystemExceptions {
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
		if (optionalCustomer.isEmpty()) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "inserted email is incorrect");
		}
		Customer customer = optionalCustomer.get();
		if (!customer.getPassword().equals(password)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "inserted password is incorrect");
		}
		setCustomer(customer);
		return true;
	}

	@Transactional
	public void purchaseCoupon(Coupon coupon) throws CouponsSystemExceptions {
		Date now = new Date();
		if (!couponRepository.existsById(coupon.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This coupon does not exist");
		}
		if (hasCouponPurchased(coupon)) {
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
	}

	@Transactional
	public void removeCouponPurchase(Coupon coupon) throws CouponsSystemExceptions {
		if (!couponRepository.existsById(coupon.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This coupon does not exist");
		}
		if (!hasCouponPurchased(coupon)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"You have not purchased this coupon yet!");
		}
		customer.getCoupons().remove(coupon);
		customerRepository.save(customer);
		System.out.println("\n--This coupon purchase has been removed--\n");
	}

	public List<Coupon> getAllCoupons() throws CouponsSystemExceptions {
		List<Coupon> coupons = couponRepository.findAll();
		if (coupons != null) {
			return coupons;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);
	}

	public Coupon getOneCoupon(int couponId) throws CouponsSystemExceptions {
		Optional<Coupon> coupon = couponRepository.findById(couponId);
		if (coupon.isPresent()) {
			return coupon.get();
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND);
	}

	public List<Coupon> getCustomerCoupons() throws CouponsSystemExceptions {
		List<Coupon> coupons = customer.getCoupons();
		if (coupons != null) {
			return coupons;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);
	}

	public List<Coupon> getCustomerCoupons(Category category) throws CouponsSystemExceptions {
		List<Coupon> coupons = new ArrayList<>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getCategory().equals(category)) {
				coupons.add(coupon);
			}
		}
		if (coupons.size() == 0) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);
		}
		return coupons;
	}

	public List<Coupon> getCustomerCoupons(Double price) throws CouponsSystemExceptions {
		List<Coupon> coupons = new ArrayList<>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getPrice() <= price) {
				coupons.add(coupon);
			}
		}
		if (coupons.size() == 0) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);
		}
		return coupons;
	}

	public Customer getCustomerDetails() throws CouponsSystemExceptions {
		if (customer == null) {
			throw new CouponsSystemExceptions(SystemExceptions.CUSTOMER_NOT_FOUND);
		}
		return customer;
	}

	@Transactional
	public void UpdateDetails(Customer givenCustomer) throws CouponsSystemExceptions {
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
		customerRepository.save(givenCustomer);
		setCustomer(givenCustomer);
		System.out.println("\n--This customer has been updated--\n");
	}

	private boolean hasCouponPurchased(Coupon coupon) {
		List<Coupon> coupons = customer.getCoupons();
		for (Coupon purchasedCoupon : coupons) {
			if (purchasedCoupon.equals(coupon)) {
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
