package com.example.i_o_spring_project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.i_o_spring_project.model.Category;
import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.model.Customer;

@RestController
@RequestMapping("/customer")
public class CustomerController extends ClientController {

	@Override
	/**
	 * this function should be mapped
	 */
	@GetMapping("/login")
	public ResponseEntity<Boolean> login(@RequestParam String email, @RequestParam String password) {
		return new ResponseEntity<Boolean>(customerService.login(email, password), HttpStatus.OK);
	}

	@PostMapping("/coupon")
	public ResponseEntity<Coupon> purchaseCoupon(@RequestBody Coupon coupon) {
		return new ResponseEntity<Coupon>(customerService.purchaseCoupon(coupon), HttpStatus.OK);
	}

	@DeleteMapping("/coupon/{id}")
	public ResponseEntity<Void> removePurchasedCoupon(@PathVariable int id) {
		customerService.removeCouponPurchase(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/coupon/{id}")
	public ResponseEntity<Coupon> getCoupon(int id) {
		return new ResponseEntity<Coupon>(customerService.getOneCoupon(id), HttpStatus.OK);
	}
	
	@GetMapping("/coupons")
	public ResponseEntity<List<Coupon>> getCustomerCoupons() {
		return new ResponseEntity<List<Coupon>>(customerService.getCustomerCoupons(), HttpStatus.OK);
	}

	@GetMapping("/coupons/categoryId/{categoryId}")
	public ResponseEntity<List<Coupon>> getCustomerCoupons(@PathVariable Integer categoryId) {
		Category category = companyService.getCategory(categoryId);
		return new ResponseEntity<List<Coupon>>(customerService.getCustomerCoupons(category), HttpStatus.OK);
	}

	@GetMapping("/coupons/price/{price}")
	public ResponseEntity<List<Coupon>> getCustomerCoupons(@PathVariable Double price) {
		return new ResponseEntity<List<Coupon>>(customerService.getCustomerCoupons(price), HttpStatus.OK);
	}

	@GetMapping("/details")
	public ResponseEntity<Customer> getCustomerDetails() {
		return new ResponseEntity<Customer>(customerService.getCustomerDetails(), HttpStatus.OK);
	}

	@PutMapping("/details")
	public ResponseEntity<Customer> updateCustomerDetails(@RequestBody Customer customer) {
		return new ResponseEntity<Customer>(customerService.UpdateDetails(customer), HttpStatus.OK);
	}
}
