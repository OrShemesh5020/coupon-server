package com.example.i_o_spring_project.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;
import com.example.i_o_spring_project.model.Category;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.repository.CompanyRepository;
import com.example.i_o_spring_project.repository.CouponRepository;

@Service
public class CompanyService extends ClientService {

	@Autowired
	private Company company;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CouponRepository couponRepository;

	public CompanyService(ConfigurableApplicationContext applicationContext) {
		super(applicationContext);
		companyRepository = applicationContext.getBean(CompanyRepository.class);
		couponRepository = applicationContext.getBean(CouponRepository.class);
	}

	public boolean login(String email, String password) throws CouponsSystemExceptions {
		Optional<Company> optionalCompany = companyRepository.findByEmail(email);
		if (optionalCompany.isEmpty()) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "inserted email is incorrect");
		}
		Company company = optionalCompany.get();
		if (!company.getPassword().equals(password)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "inserted password is incorrect");
		}
		setCompany(company);
		return true;
	}

	@Transactional
	public void addACoupon(Coupon coupon) throws SQLException, CouponsSystemExceptions, InterruptedException {

		couponValidation.isTheObjectEmpty(coupon);

		couponValidation.charactersHasExceeded(coupon);

		Date now = new Date();

		if (coupon.getEndDate().before(now)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"coupon's end date cannot be prior to now.");

		}
		if (couponRepository.findByTitle(coupon.getTitle()).get().getId().equals(coupon.getId())) {
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
					"coupon's price cannot be equal to or less than 0");
		}
		if (coupon.getAmount() <= 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"coupon's Amount cannot be equal to or less than 0");
		}
		couponRepository.save(coupon);
		System.out.println("\n--The coupon was added--\n");

	}

	@Transactional
	public void updateCoupon(Coupon coupon) throws SQLException, CouponsSystemExceptions, InterruptedException {
		Date now = new Date(0L);

		if (!couponRepository.existsById(coupon.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND);
		}
		if (!companyHasCouponPurchased(coupon)) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND,
					"the company does not have this coupon ");
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
			couponRepository.save(coupon);
			System.out.println("\n--The coupon was updated--\n");
		} else {
			coupon.setStartDate(couponRepository.findById(coupon.getId()).get().getStartDate());
			couponRepository.save(coupon);
			System.out.println("\n--The Coupon's details have been altered, not including it's start time--\n");
		}

	}

	public void deleteCoupon(Coupon coupon) throws SQLException, CouponsSystemExceptions, InterruptedException {
		if (!companyHasCouponPurchased(coupon)) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND,
					"the company does not have this coupon");
		}
		couponRepository.delete(coupon);
		System.out.println("\n--The coupon was deleted--\n");

	}

	public Coupon getOneCoupon(String title) throws CouponsSystemExceptions {
		Optional<Coupon> oneCoupon = couponRepository.getOneCoupon(company, title);
		if (oneCoupon.isPresent()) {
			return oneCoupon.get();

		}

		throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND);
	}

	public List<Coupon> getAllCompanyCoupons() throws CouponsSystemExceptions {
		List<Coupon> couponsList = company.getCoupons();
		if (!couponsList.isEmpty()) {
			return couponsList;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);
	}

	public List<Coupon> getCouponsByCategory(Category category) throws CouponsSystemExceptions {
		List<Optional<Coupon>> companyCouponsByCategory = couponRepository.getCompanyCouponsByCategory(company,
				category);
		if (!companyCouponsByCategory.isEmpty()) {
			return couponsListConverter(companyCouponsByCategory);
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);
	}

	public List<Coupon> getCouponsByPrice(Double price) throws CouponsSystemExceptions {

		List<Optional<Coupon>> companyCouponsByPrice = couponRepository.getCompanyCouponsByPrice(company, price);
		if (!companyCouponsByPrice.isEmpty()) {
			return couponsListConverter(companyCouponsByPrice);
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);
	}

	public Company getCompanyDetails(int id) throws CouponsSystemExceptions {
		Optional<Company> optionalCompany = companyRepository.findById(id);
		if (optionalCompany.isPresent()) {
			return optionalCompany.get();

		}
		throw new CouponsSystemExceptions(SystemExceptions.COMPANY_NOT_FOUND);
	}

	@Transactional
	public void updateDetails(Company company) throws CouponsSystemExceptions {

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
		System.out.println("\n--This company has been updated--\n");
		companyRepository.save(company);
	}

	private boolean companyHasCouponPurchased(Coupon coupon) {
		List<Optional<Coupon>> optionalCouponsList = couponRepository.findByCompany(company);
		for (Optional<Coupon> optional : optionalCouponsList) {
			if (optional.get().equals(coupon)) {
				return true;
			}
		}
		return false;
	}

	private List<Coupon> couponsListConverter(List<Optional<Coupon>> optionalCoupons) {

		List<Coupon> couponsList = new ArrayList<Coupon>();
		for (Optional<Coupon> optionalCoupon : optionalCoupons) {
			couponsList.add(optionalCoupon.get());
		}
		return couponsList;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
