package com.example.i_o_spring_project.validation;

import org.springframework.stereotype.Component;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.model.Coupon;

@Component
public class CouponValidation extends ObjectValidation<Coupon> {

	@Override
	public void isTheObjectEmpty(Coupon coupon) throws CouponsSystemExceptions {
		isTheParameterEmpty(coupon.getCompany(), "coupon company");
		isTheParameterEmpty(coupon.getCategory(), "coupon category");
		isTheParameterEmpty(coupon.getTitle(), "title");
		isTheParameterEmpty(coupon.getDescription(), "description");
		isTheParameterEmpty(coupon.getStartDate(), "start date");
		isTheParameterEmpty(coupon.getEndDate(), "end date");
	}

	@Override
	public void charactersHasExceeded(Coupon coupon) throws CouponsSystemExceptions {
		charactersHasExceeded(coupon.getTitle(), "title", 45);
		charactersHasExceeded(coupon.getDescription(), "description", 200);
		charactersHasExceeded(coupon.getImage(), "image", 45);
	}

}
