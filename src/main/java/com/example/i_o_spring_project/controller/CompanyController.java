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
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.model.User;
import com.example.i_o_spring_project.modelDTO.CompanyDTO;
import com.example.i_o_spring_project.modelDTO.CouponDTO;
import com.example.i_o_spring_project.modelDTO.ModelToDTOConverter;

@RestController
@RequestMapping("/company")
public class CompanyController extends ClientController {

	private final ClientType clienType = ClientType.COMPANY;

	@PostMapping("/coupon")
	public ResponseEntity<CouponDTO> addCoupon(@RequestBody CouponDTO couponDTO, HttpServletRequest request) {
		Coupon coupon = modelConverter.convertToCoupon(couponDTO);
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clienType);
		int companyId = (int) request.getAttribute(ID);
		return new ResponseEntity<CouponDTO>(dTOconverter.convertCoupon(companyService.addACoupon(coupon, companyId)),
				HttpStatus.OK);
	}

	@PutMapping("/coupon")
	public ResponseEntity<CouponDTO> updateCoupon(@RequestBody CouponDTO couponDTO, HttpServletRequest request) {
		Coupon coupon = modelConverter.convertToCoupon(couponDTO);
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clienType);
		int companyId = (int) request.getAttribute(ID);
		return new ResponseEntity<CouponDTO>(dTOconverter.convertCoupon(companyService.updateCoupon(coupon, companyId)),
				HttpStatus.OK);
	}

	@DeleteMapping("/coupon/{id}")
	public ResponseEntity<Void> deleteCoupon(@PathVariable int id, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clienType);
		int companyId = (int) request.getAttribute(ID);
		companyService.deleteCoupon(id, companyId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/coupons")
	public ResponseEntity<List<CouponDTO>> getAllCompanyCoupons(HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clienType);
		int companyId = (int) request.getAttribute(ID);
		return new ResponseEntity<List<CouponDTO>>(
				dTOconverter.convertCouponList(companyService.getAllCompanyCoupons(companyId)), HttpStatus.OK);
	}

	@GetMapping("/coupon/{id}")
	public ResponseEntity<CouponDTO> getCouponById(@PathVariable int id, HttpServletRequest request) {
		System.out.println(id);
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clienType);
		int companyId = (int) request.getAttribute(ID);
		return new ResponseEntity<CouponDTO>(dTOconverter.convertCoupon(companyService.getOneCoupon(id, companyId)),
				HttpStatus.OK);
	}

	@GetMapping("/coupon")
	public ResponseEntity<CouponDTO> getCouponByTitle(@RequestParam String title, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clienType);
		int companyId = (int) request.getAttribute(ID);
		return new ResponseEntity<CouponDTO>(dTOconverter.convertCoupon(companyService.getOneCoupon(title, companyId)),
				HttpStatus.OK);
	}

	@GetMapping("/coupons/price/{price}")
	public ResponseEntity<List<CouponDTO>> getCompanyCouponsByPrice(@PathVariable Double price,
			HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clienType);
		int companyId = (int) request.getAttribute(ID);
		return new ResponseEntity<List<CouponDTO>>(
				dTOconverter.convertCouponList(companyService.getCouponsByPrice(price, companyId)), HttpStatus.OK);
	}

	@GetMapping("/coupons/categoryId/{categoryId}")
	public ResponseEntity<List<CouponDTO>> getCompanyCouponsByCategory(@PathVariable Integer categoryId,
			HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clienType);
		int companyId = (int) request.getAttribute(ID);
		Category category = companyService.getCategory(categoryId);
		return new ResponseEntity<List<CouponDTO>>(
				dTOconverter.convertCouponList(companyService.getCouponsByCategory(category, companyId)),
				HttpStatus.OK);
	}

// The token type should be checked in all functions
	@GetMapping("/details")
	public ResponseEntity<CompanyDTO> getDetails(HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clienType);
		int companyId = (int) request.getAttribute(ID);
		return new ResponseEntity<CompanyDTO>(dTOconverter.convertCompany(companyService.getCompanyDetails(companyId)),
				HttpStatus.OK);
	}

	@PutMapping("/details")
	public ResponseEntity<CompanyDTO> updateDetails(@RequestBody Company company, HttpServletRequest request) {
		String tokenType = (String) request.getAttribute(TYPE);
		tokenFacade.doesTheTokenBelong(tokenType, clienType);
		int companyId = (int) request.getAttribute(ID);
		return new ResponseEntity<CompanyDTO>(
				dTOconverter.convertCompany(companyService.updateDetails(company, companyId)), HttpStatus.OK);
	}

}
