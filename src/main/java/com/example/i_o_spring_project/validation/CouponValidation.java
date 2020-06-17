package com.example.i_o_spring_project.validation;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.model.Coupon;

public class CouponValidation extends ObjectValidation<Coupon> {

	@Override
	public void isTheObjectEmpty(Coupon coupon) throws CouponsSystemExceptions {
		isTheParameterEmpty(coupon.getCompany());
		isTheParameterEmpty(coupon.getCategory());
		isTheParameterEmpty(coupon.getTitle());
		isTheParameterEmpty(coupon.getDescription());
		isTheParameterEmpty(coupon.getStartDate());
		isTheParameterEmpty(coupon.getEndDate());
	}

	@Override
	public void charactersHasExceeded(Coupon coupon) throws CouponsSystemExceptions {
		charactersHasExceeded(coupon.getTitle(),"title", 45);
		charactersHasExceeded(coupon.getDescription(),"description", 200);
		charactersHasExceeded(coupon.getImage(),"image", 45);
	}

}
