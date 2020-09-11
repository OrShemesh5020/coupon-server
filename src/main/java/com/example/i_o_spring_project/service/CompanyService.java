package com.example.i_o_spring_project.service;

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

	private Company company;

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
		setCompany(company);
		return true;
	}

	@Transactional
	public Coupon addACoupon(Coupon coupon) {
		Date now = new Date();
		couponValidation.isTheObjectEmpty(coupon);
		couponValidation.charactersHasExceeded(coupon);
		if (coupon.getEndDate().before(now)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"Coupon's end date cannot be prior to now.");
		}
		if (couponRepository.getOneCoupon(company, coupon.getTitle()).isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "this name is already taken!");
		}
		if (coupon.getStartDate().before(now)) {
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
	public Coupon updateCoupon(Coupon coupon) {
		Date now = new Date();

		if (!couponRepository.existsById(coupon.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND,
					"The coupon does not exist in the system");
		}
		if (!companyHasTheCoupon(coupon)) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND,
					"The company does not have this coupon ");
		}
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
		if (coupon.getStartDate().after(now)) {
			System.out.println("\n--The coupon was updated--\n");
			return couponRepository.save(coupon);
		} else {
			coupon.setStartDate(couponRepository.findById(coupon.getId()).get().getStartDate());
			System.out.println("\n--The coupon's details have been altered, not including it's start time--\n");
			return couponRepository.save(coupon);
		}
	}

	@Transactional
	public void deleteCoupon(int couponId) {
		if (!couponRepository.existsById(couponId)) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND,
					"The coupon does not exist in the system");
		}
		Coupon coupon = couponRepository.findById(couponId).get();
		if (!companyHasTheCoupon(coupon)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"The company does not have this coupon");
		}
		couponRepository.deleteAllPurchasedCoupon(couponId);
		couponRepository.delete(coupon);
		System.out.println("\n--The coupon was deleted--\n");
	}

	public Coupon getOneCoupon(int id) {
		Optional<Coupon> coupon = couponRepository.getOneCoupon(company, id);
		if (coupon.isPresent()) {
			return coupon.get();
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND);
	}

	public Coupon getOneCoupon(String title) {
		Optional<Coupon> coupon = couponRepository.getOneCoupon(company, title);
		if (coupon.isPresent()) {
			return coupon.get();
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND);
	}

	public List<Coupon> getAllCompanyCoupons() {
		return couponRepository.findByCompany(company);
	}

	public List<Coupon> getCouponsByCategory(Category category) {
		return couponRepository.getCompanyCouponsByCategory(company, category);
	}

	public List<Coupon> getCouponsByPrice(Double price) {
		return couponRepository.getCompanyCouponsByPrice(company, price);
	}

	public Company getCompanyDetails() {
		if (companyRepository.existsById(company.getId())) {
			return company;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COMPANY_NOT_FOUND);
	}

	@Transactional
	// idealy, in here, as all other validators, the validation itself - should sit
	// in a different class and what should be seen here is
	// if(company.isValid){ is what we should see here.
	public Company updateDetails(Company company) throws CouponsSystemExceptions {

		if (!companyRepository.existsById(company.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This company does not exist");
		}
		companyValidation.isTheObjectEmpty(company);

		companyValidation.charactersHasExceeded(company);

		if (!companyRepository.findById(company.getId()).get().getName().equals(company.getName())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"You can't change the name of the company");
		}
		if (!companyRepository.findById(company.getId()).get().getEmail().equals(company.getEmail())) {
			if (companyRepository.findByEmail(company.getEmail()).isPresent()) {
				throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
			}
		}
		setCompany(company);
		System.out.println("\n--This company has been updated--\n");
		return companyRepository.save(company);
	}

	@Transactional
	public Category getCategory(int categoryId) {
		if (!categoryRepository.existsById(categoryId)) {
			throw new CouponsSystemExceptions(SystemExceptions.CATEGORY_NOT_FOUND, "this category does not exist");
		}
		return categoryRepository.findById(categoryId).get();
	}
//
//	public java.sql.Date date(Calendar date) {
//		return java.sql.Date.valueOf(getDate(date));
//	}
//
//	private String getDate(Calendar date) {
//		return date.get(Calendar.YEAR) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.DAY_OF_MONTH);
//	}

	private boolean companyHasTheCoupon(Coupon coupon) {
		List<Coupon> couponsList = couponRepository.findByCompany(company);
		for (Coupon companyCoupon : couponsList) {
			if (companyCoupon.getId().equals(coupon.getId())) {
				return true;
			}
		}
		return false;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
