package com.example.i_o_spring_project.automatic_managment;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.i_o_spring_project.repository.CouponRepository;

@Component
public class CouponExpirationDaliyJob {
	@Autowired
	private CouponRepository couponRepository;

	//@Scheduled makes spring run this function automatically, so we don't have to call it in main
	@Transactional
	@Scheduled(fixedDelay = 86400000)
	public void manageCoupons() {
		couponRepository.deleteExpiredCouponsFromCustomers();
		couponRepository.deleteExpiredCoupons();
	}

}
