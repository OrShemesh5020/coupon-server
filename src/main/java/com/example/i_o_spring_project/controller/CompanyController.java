package com.example.i_o_spring_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.repository.CategoryRepository;
import com.example.i_o_spring_project.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@PostMapping("/coupon")
	public ResponseEntity<Coupon> addCoupon(@RequestBody Coupon coupon) {
		return new ResponseEntity<Coupon>(companyService.addACoupon(coupon), HttpStatus.OK);
	}

	@PutMapping("/coupon")
	public ResponseEntity<Coupon> updateCoupon(@RequestBody Coupon coupon) {
		return new ResponseEntity<Coupon>(companyService.updateCoupon(coupon), HttpStatus.OK);
	}

	@DeleteMapping("/coupon/{id}")
	public ResponseEntity<Void> deleteCoupon(@PathVariable int id) {
		companyService.deleteCoupon(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/coupons")
	public ResponseEntity<List<Coupon>> getAllCompanyCoupons() {
		return new ResponseEntity<List<Coupon>>(companyService.getAllCompanyCoupons(), HttpStatus.OK);
	}

	@GetMapping("/coupon/{id}")
	public ResponseEntity<Coupon> getCouponById(@PathVariable int id) {
		return new ResponseEntity<Coupon>(companyService.getOneCoupon(id), HttpStatus.OK);
	}

	@GetMapping("/coupon/{title}")
	public ResponseEntity<Coupon> getCouponByTitle(@PathVariable String title) {
		return new ResponseEntity<Coupon>(companyService.getOneCoupon(title), HttpStatus.OK);
	}

	@GetMapping("/coupons/{price}")
	public ResponseEntity<List<Coupon>> getCompanyCouponsByPrice(@PathVariable Double price) {
		return new ResponseEntity<List<Coupon>>(companyService.getCouponsByPrice(price), HttpStatus.OK);
	}

	@GetMapping("/coupons/{categoryId}")
	public ResponseEntity<List<Coupon>> getCompanyCouponsByCategory(@PathVariable Integer categoryId) {
		Category category = companyService.getCategory(categoryId);
		return new ResponseEntity<List<Coupon>>(companyService.getCouponsByCategory(category), HttpStatus.OK);
	}

	@GetMapping("/details")
	public ResponseEntity<Company> getDetails() {
		return new ResponseEntity<Company>(companyService.getCompanyDetails(), HttpStatus.OK);
	}

	@PutMapping("/details")
	public ResponseEntity<Company> updateDetails(@RequestBody Company company) {
		return new ResponseEntity<Company>(companyService.updateDetails(company), HttpStatus.OK);
	}
}
