package com.example.i_o_spring_project.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.SystemException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.repository.CompanyRepository;
import com.example.i_o_spring_project.repository.CouponRepository;

@Service
public class CompanyService extends ClientService{

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
		return true;
	}

	public void addACoupon(Coupon coupon) throws SQLException, CouponsSystemExceptions, InterruptedException {
		// object validation if. PROBABLY UNNCESSARY.
		Optional<Coupon> optionalCoupon = couponRepository.findById(coupon.getId());
		Date now = new Date();

		if (optionalCoupon.isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE,
					"this coupon already exists in the system.");
		}
		couponValidation.charactersHasExceeded(coupon);
		couponValidation.isTheObjectEmpty(coupon);
		
		
		
		if (optionalCoupon.get().getEndDate().before(now)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"coupon's end date cannot be prior to now.");

		}
		if (optionalCoupon.get().getEndDate().before(coupon.getStartDate())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"coupon's startDate cannot surpass its endDate ");
		}
		if (optionalCoupon.get().getPrice() <= 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"coupon's price cannot be equal to or less than 0");
		}
		if (optionalCoupon.get().getAmount() <= 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"coupon's Amount cannot be equal to or less than 0");
		}
		couponRepository.addCoupon(optionalCoupon);
		System.out.println("\n--The coupon was added--\n");

	}
	
	public void updateCoupon(Coupon coupon) throws SQLException, CouponsSystemExceptions, InterruptedException {
		
		Date now = new Date(0L);
		// notice that existsById doesn't check if it belongs to the company but that it Exists in the system.
		if(!couponRepository.existsById(coupon.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND,"this coupon does not exist in the system.");
		}
		if(!companyHasCouponPurchased(coupon)) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND,"the company does not have this coupon ");
		}
		
	}
	
	public void deleteCoupon(Coupon coupon) throws SQLException, CouponsSystemExceptions, InterruptedException{
		if(!companyHasCouponPurchased(coupon)) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND,"the company does not have this coupon");
		}
		couponRepository.delete(coupon);
		System.out.println("\n--The coupon was deleted--\n");
		
	}
		
		public List<Coupon> getAllCompanyCoupons() throws CouponsSystemExceptions{
			List<Coupon> companyCoupons = company.getCoupons();
			if(companyCoupons!= null) {
				return companyCoupons;
			}
			throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);
		}
		
		public Coupon getOneCoupon(String title) throws CouponsSystemExceptions {
			for (Coupon coupon : getAllCompanyCoupons() ) {
				if(coupon.getTitle().equals(title)) {
					return coupon;
				
			}
			
		}	
		
			throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND);
	}
		
		
			
		
		
		
		private boolean companyHasCouponPurchased(Coupon coupon) {
			List<Coupon> coupons = company.getCoupons();
			for (Coupon purchasedCoupon : coupons) {
				if (purchasedCoupon.equals(coupon)) {
					return true;
				}
			}
			return false;
		}
		
		
	}


