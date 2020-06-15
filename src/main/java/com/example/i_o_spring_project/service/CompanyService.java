package com.example.i_o_spring_project.service;

import java.sql.SQLException;
import java.util.Optional;

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
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CouponRepository couponRepository;

	public CompanyService(ConfigurableApplicationContext applicationContext) {
		companyRepository = applicationContext.getBean(CompanyRepository.class);
		couponRepository = applicationContext.getBean(CouponRepository.class);
	}

	public boolean login(String email, String password) throws SQLException, CouponsSystemExceptions {
		Optional<Company> optionalCompany = companyRepository.findByEmail(email);
		if (optionalCompany.isEmpty()) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "inserted email is incorrect");	
		}
		Company company = optionalCompany.get();
		if(!company.getPassword().equals(password)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "inserted password is incorrect");
		}
		return true;
	}

	public void addACoupon(Coupon coupon) throws CouponsSystemExceptions {
			//object validation if.
		Optional<Coupon> optionalCoupon = couponRepository.findById(coupon.getId());

	if(optionalCoupon.isPresent()) {
		throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "this coupon already exists in the system.");		
		}
	//if(optionalCoupon.get().getEndDate().before(when) {
	// the function .before works here, but the problem is to get an instance of NOW to compare to it.
	//}
	if(optionalCoupon.get().getEndDate().before(coupon.getStartDate())) {
		throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "coupon's startDate cannot surpass its endDate ");
		}
	//if()
	}

}
