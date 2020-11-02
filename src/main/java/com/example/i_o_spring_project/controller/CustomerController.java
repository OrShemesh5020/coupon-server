package com.example.i_o_spring_project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.example.i_o_spring_project.model.ClientType;
import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.model.Customer;
import com.example.i_o_spring_project.model.User;
import com.example.i_o_spring_project.modelDTO.CouponDTO;
import com.example.i_o_spring_project.modelDTO.CustomerDTO;

@RestController
@RequestMapping("/customer")
public class CustomerController extends ClientController {

	private final ClientType clientType = ClientType.CUSTOMER;

	@PostMapping("/coupon")
	public ResponseEntity<CouponDTO> purchaseCoupon(@RequestBody CouponDTO couponDTO, HttpServletRequest request) {
		Coupon coupon = modelConverter.convertToCoupon(couponDTO);
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		int customerId = (int) request.getAttribute(ID);
		return new ResponseEntity<CouponDTO>(
				dTOconverter.convertCoupon(customerService.purchaseCoupon(coupon, customerId)), HttpStatus.OK);
	}

	@DeleteMapping("/coupon/{id}")
	public ResponseEntity<Void> removePurchasedCoupon(@PathVariable int id, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		int customerId = (int) request.getAttribute(ID);
		customerService.removeCouponPurchase(id, customerId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/coupon/{id}")
	public ResponseEntity<CouponDTO> getCoupon(int id, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		return new ResponseEntity<CouponDTO>(dTOconverter.convertCoupon(customerService.getOneCoupon(id)),
				HttpStatus.OK);
	}

	@GetMapping("/coupons")
	public ResponseEntity<List<CouponDTO>> getCustomerCoupons(HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		int customerId = (int) request.getAttribute(ID);
		return new ResponseEntity<List<CouponDTO>>(
				dTOconverter.convertCouponList(customerService.getCustomerCoupons(customerId)), HttpStatus.OK);
	}

	@GetMapping("/coupons/categoryId/{categoryId}")
	public ResponseEntity<List<CouponDTO>> getCustomerCoupons(@PathVariable Integer categoryId,
			HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		int customerId = (int) request.getAttribute(ID);
		Category category = companyService.getCategory(categoryId);
		return new ResponseEntity<List<CouponDTO>>(
				dTOconverter.convertCouponList(customerService.getCustomerCoupons(category, customerId)),
				HttpStatus.OK);
	}

	@GetMapping("/coupons/price/{price}")
	public ResponseEntity<List<CouponDTO>> getCustomerCoupons(@PathVariable Double price, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		int customerId = (int) request.getAttribute(ID);
		return new ResponseEntity<List<CouponDTO>>(
				dTOconverter.convertCouponList(customerService.getCustomerCoupons(price, customerId)), HttpStatus.OK);
	}

	@GetMapping("/details")
	public ResponseEntity<CustomerDTO> getCustomerDetails(HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		int customerId = (int) request.getAttribute(ID);
		return new ResponseEntity<CustomerDTO>(
				dTOconverter.convertCustomer(customerService.getCustomerDetails(customerId)), HttpStatus.OK);
	}

	@PutMapping("/details")
	public ResponseEntity<CustomerDTO> updateCustomerDetails(@RequestBody Customer customer,
			HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clientType);
		int customerId = (int) request.getAttribute(ID);
		return new ResponseEntity<CustomerDTO>(
				dTOconverter.convertCustomer(customerService.UpdateDetails(customer, customerId)), HttpStatus.OK);
	}

}
