package com.example.i_o_spring_project.exceptions;

public enum SystemExceptions {
	/**
	 * This exception is displayed when the requested <code>Customer</code> object
	 * could not be found in the database.
	 */
	CUSTOMER_NOT_FOUND,

	/**
	 * This exception is displayed when the client is trying to put an
	 * already-existing value as it's set value.
	 */
	/**
	 * This exception is displayed when the requested <code>Coupon</code> object
	 * could not be found in the database.
	 */
	COUPON_NOT_FOUND,

	CATEGORY_NOT_FOUND,

	/**
	 * This exception is displayed when the client is trying to set illegal values.
	 */
	/**
	 * This exception is displayed when the requested <code>Company</code> object
	 * couldn't be found in the database.
	 */
	COMPANY_NOT_FOUND,
	/**
	 * This exception is displayed when the client is trying to perform an action
	 * that hasn't been approved by the system.
	 */
	ILLEGAL_ACTION_ATTEMPTED,

	ILLEGAL_VALUE_ENTERED,

	VALUE_UNAVAILABLE;
	/**
	 * This exception is displayed when the client is trying to put values that
	 * doesn't match the system's expectations.
	 */
//	INCORRECT_VALUE_ENTERED;
	/**
	 * This exception is displayed when the requested object has reached 0 with his
	 * <code>amount</code> value.
	 */
//	OUT_OF_STOCK,
	/**
	 * This exception is displayed when the requested <code>Coupon</code> object's
	 * launching date hasn't come yet.
	 */
//	COUPON_NOT_LAUNCHED,
	/**
	 * This exception is displayed when the requested <code>Coupon</code> object's
	 * expiration date has passed.
	 */
//	COUPON_EXIPRED,
	/**
	 * This exception is displayed when the requested <code>Coupon</code> object
	 * hasn't been bought by the requestiong party yet.
	 */

//	COUPON_NOT_PURCHASED,
	/**
	 * This exception is displayed when the requested <code>Coupon</code> object has
	 * already been purchased by the requesting party.
	 */
//	COUPON_ALREADY_PURCHASED,
	/**
	 * This exception is displayed when the requested <code>Companies</code> list
	 * couldn't be found in the database
	 */
//	COMPANIES_NOT_FOUND,
	/**
	 * This exception is displayed when the requested <code>Coupons</code> list
	 * could not be found in the database.
	 */
//	COUPONS_NOT_FOUND,
	/**
	 * This exception is displayed when the requested <code>Company</code> object
	 * could not be found in the database.
	 */
//	CUSTOMERS_NOT_FOUND,
	/**
	 * doesn't match the system's expectations.
	 */
//	INCORRECT_VALUE_ENTERED;
	/**
	 * This exception is displayed when the requested object has reached 0 with his
	 * <code>amount</code> value.
	 */
//	OUT_OF_STOCK,
	/**
	 * This exception is displayed when the requested <code>Coupon</code> object's
	 * launching date hasn't come yet.
	 */
//	COUPON_NOT_LAUNCHED,
	/**
	 * This exception is displayed when the requested <code>Coupon</code> object's
	 * expiration date has passed.
	 */
//	COUPON_EXIPRED,
	/**
	 * This exception is displayed when the requested <code>Coupon</code> object
	 * hasn't been bought by the requestiong party yet.
	 */
//	COUPON_NOT_PURCHASED,
//	/**This exception is displayed when the requested <code>Coupon</code> object has already been purchased by the requesting party.*/
//	COUPON_ALREADY_PURCHASED,

//	private SystemExceptions() {
//	}

}
