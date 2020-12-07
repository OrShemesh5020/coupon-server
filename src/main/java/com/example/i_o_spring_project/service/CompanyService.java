package com.example.i_o_spring_project.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;
import com.example.i_o_spring_project.model.Category;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Coupon;

@Service
public class CompanyService extends ClientService {

	public CompanyService() {
		super();
	}

	public boolean login(String email, String password) {
		Optional<Company> optionalCompany = companyRepository.findByEmail(email);
		if (optionalCompany.isEmpty()) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "Inserted email is incorrect");
		}
		Company company = optionalCompany.get();
		if (!company.getPassword().equals(password)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"Inserted password is incorrect");
		}
		return true;
	}

	@Transactional
	public Coupon addACoupon(Coupon coupon, int comapnyId) {
		Company company = getCompanyDetails(comapnyId);
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR, -11);
		couponValidation.isTheObjectEmpty(coupon);
		couponValidation.charactersHasExceeded(coupon);
		if (coupon.getEndDate().getTime() < now.getTimeInMillis()) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"Coupon's end date cannot be prior to now.");
		}
		if (couponRepository.getOneCoupon(company, coupon.getTitle()).isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "this name is already taken!");
		}
		if (coupon.getStartDate().getTime() < now.getTimeInMillis()) {
			System.out.println(coupon.getStartDate().getTime());
			System.out.println(now.getTimeInMillis());
			System.out.println(new Date().getTime());
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"Coupon launch date occurs before current date");
		}
		if (coupon.getEndDate().before(coupon.getStartDate())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"Coupon expired before coupon launch date");
		}
		if (coupon.getPrice() <= 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"Coupon's price cannot be equal to or less than 0");
		}
		if (coupon.getAmount() <= 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"Coupon's Amount cannot be equal to or less than 0");
		}
		System.out.println("\n--The coupon was added--\n");
		return couponRepository.save(coupon);
	}

	@Transactional
	public Coupon updateCoupon(Coupon coupon, int comapnyId) {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR, -11);
		if (!couponRepository.existsById(coupon.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND,
					"The coupon does not exist in the system");
		}
		doesCouponBelongToTheCoumpany(coupon.getId(), comapnyId);
		couponValidation.isTheObjectEmpty(coupon);

		couponValidation.charactersHasExceeded(coupon);

		if (coupon.getEndDate().before(coupon.getStartDate())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"Coupon expired before coupon launch date");
		}
		if (coupon.getAmount() <= 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"The coupon's amount cannot be less than or equal to zero");
		}
		if (coupon.getPrice() <= 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"The coupon's price cannot be less than or equal to zero");
		}
		if (coupon.getStartDate().getTime() > now.getTimeInMillis()) {
			System.out.println("\n--The coupon was updated--\n");
			return couponRepository.save(coupon);
		} else {
			coupon.setStartDate(couponRepository.findById(coupon.getId()).get().getStartDate());
			System.out.println("\n--The coupon's details have been altered, not including it's start time--\n");
			return couponRepository.save(coupon);
		}
	}

	@Transactional
	public void deleteCoupon(int couponId, int comapnyId) {
		if (!couponRepository.existsById(couponId)) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND,
					"The coupon does not exist in the system");
		}
		Coupon coupon = couponRepository.findById(couponId).get();
		doesCouponBelongToTheCoumpany(couponId, comapnyId);
		couponRepository.deleteAllPurchasedCoupon(couponId);
		couponRepository.delete(coupon);
		System.out.println("\n--The coupon was deleted--\n");
	}

	public Coupon getOneCoupon(int id, int comapnyId) {
		if (doesCouponBelongToTheCoumpany(id, comapnyId)) {
			return couponRepository.findById(id).get();
		}
		return null;
	}

	public Coupon getOneCoupon(String title, int comapnyId) {
		Company company = getCompanyDetails(comapnyId);
		Optional<Coupon> coupon = couponRepository.getOneCoupon(company, title);
		if (coupon.isPresent()) {
			return coupon.get();
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND);
	}

	public List<Coupon> getAllCompanyCoupons(int comapnyId) {
		Company company = getCompanyDetails(comapnyId);
		return couponRepository.findByCompany(company);
	}

	public List<Coupon> getCouponsByCategory(Category category, int comapnyId) {
		Company company = getCompanyDetails(comapnyId);
		return couponRepository.getCompanyCouponsByCategory(company, category);
	}

	public List<Coupon> getCouponsByPrice(Double price, int comapnyId) {
		Company company = getCompanyDetails(comapnyId);
		return couponRepository.getCompanyCouponsByPrice(company, price);
	}

	public Company getCompanyByEmail(String email) {
		Optional<Company> optionalCompany = companyRepository.findByEmail(email);
		if (optionalCompany.isPresent()) {
			return optionalCompany.get();
		}
		throw new CouponsSystemExceptions(SystemExceptions.COMPANY_NOT_FOUND);
	}

	public Company getCompanyDetails(int comapnyId) {
		Optional<Company> optionalCompany = companyRepository.findById(comapnyId);
		if (optionalCompany.isPresent()) {
			return optionalCompany.get();
		}
		throw new CouponsSystemExceptions(SystemExceptions.COMPANY_NOT_FOUND);
	}

	@Transactional
	// idealy, in here, as all other validators, the validation itself - should sit
	// in a different class and what should be seen here is
	// if(company.isValid){ is what we should see here.
	public Company updateDetails(Company givenCompany, int compantId) {
		Company company = getCompanyDetails(compantId);
		if (!companyRepository.existsById(givenCompany.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This company does not exist");
		}
		if (!company.getId().equals(givenCompany.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"This company's id has been changed");
		}
		companyValidation.isTheObjectEmpty(givenCompany);

		companyValidation.charactersHasExceeded(givenCompany);
		if (!companyRepository.findById(company.getId()).get().getName().equals(givenCompany.getName())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"You can't change the name of the company");
		}
		if (!companyRepository.findById(company.getId()).get().getEmail().equals(givenCompany.getEmail())) {
			userService.checkEmail(givenCompany.getEmail());
//			if (companyRepository.findByEmail(givenCompany.getEmail()).isPresent()) {
//				throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
//			}
		}
		System.out.println("\n--This company has been updated--\n");
		return companyRepository.save(givenCompany);
	}

//	@Transactional
//	public Category getCategory(String categoryName) {
//		if (!categoryRepository.existsById(categoryId)) {
//			throw new CouponsSystemExceptions(SystemExceptions.CATEGORY_NOT_FOUND, "this category does not exist");
//		}
//		return categoryRepository.findById(categoryId).get();
//	}

	@Transactional
	public Category getCategory(String categoryName) {
		if (!categoryRepository.findByName(categoryName).isPresent()) {
			return categoryRepository.save(new Category(categoryName));
		}
		return categoryRepository.findByName(categoryName).get();
	}

	private boolean doesCouponBelongToTheCoumpany(int couponId, int companyId) {
		Company company = getCompanyDetails(companyId);
		Optional<Coupon> coupon = couponRepository.getOneCoupon(company, couponId);
		if (coupon.isPresent()) {
			return true;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND, "The company does not have this coupon");
	}

	public Integer getTheSalesNumber(int couponId, int companyId) {
		if (doesCouponBelongToTheCoumpany(couponId, companyId)) {
			return couponRepository.howManyCouponsWereSold(couponId);
		}
		return null;
	}
	
	public Integer getTheTotalSalesNumber(int companyId) {
		return couponRepository.howManyCompanyCouponsWereSold(companyId);
	}
	
	public Double getTheSumOfSales(int companyId) {
		return couponRepository.getTheCouponsSumOfSales(companyId);
	}

}
