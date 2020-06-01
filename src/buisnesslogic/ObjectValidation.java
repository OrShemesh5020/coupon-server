package buisnesslogic;

import exceptions.CouponsSystemExceptions;
import exceptions.SystemExceptions;
import model.Company;
import model.Coupon;
import model.Customer;

public class ObjectValidation {
	/**
	 * This function receives a <code>Company</code> parameter,<br>
	 * then, checks if the parameter's Name, email and password parameters are null, or empty
	 * and throws the exception that is thrown by the other function.
	 * @param company - Company-typed.
	 * @throws CouponsSystemExceptions 
	 * @see
	 * {@link #isTheValueEmpty(String)}<br>
	 * {@link SystemExceptions#ILLEGAL_VALUE_ENTERED}
	 */
	public void isTheObjectEmpty(Company company) throws CouponsSystemExceptions {
		isTheValueEmpty(company.getName());
		isTheValueEmpty(company.getEmail());
		isTheValueEmpty(company.getPassword());
	}
	/**
	 * This function receives a <code>Customer</code> parameter,<br>
	 * then, checks if the parameter's firstName, lastName , email and password parameters are null, or empty
	 * and throws the exception that is thrown by the other function.
	 * @param customer - Customer-typed.
	 * @throws CouponsSystemExceptions
	 * @see
 	 * {@link #isTheValueEmpty(String)}<br>
	 * {@link exceptions.SystemExceptions#ILLEGAL_VALUE_ENTERED}
 	 */
	public void isTheObjectEmpty(Customer customer) throws CouponsSystemExceptions {
		isTheValueEmpty(customer.getFirstName());
		isTheValueEmpty(customer.getLastName());
		isTheValueEmpty(customer.getEmail());
		isTheValueEmpty(customer.getPassword());
	}
	/**
	 * This function receives a <code>Coupon</code> parameter,<br>
	 * then, checks if the parameter's companyId, category , title , description, startDate, and endDate parameters<br>
	 * are null, or empty, and throws the exception that is thrown by the other function.
	 * @param coupon - Coupon-typed
	 * @throws CouponsSystemExceptions
	 * @see
	 * {@link #isTheValueEmpty(int)}<br>
 	 * {@link #isTheValueEmpty(String)}<br>
 	 * {@link #isTheObjectEmpty(Object)}<br>
	 * {@link exceptions.SystemExceptions#ILLEGAL_VALUE_ENTERED}
	 */
	public void isTheObjectEmpty(Coupon coupon) throws CouponsSystemExceptions {
		isTheValueEmpty(coupon.getCompanyId());
		isTheObjectEmpty(coupon.getCategory());
		isTheValueEmpty(coupon.getTitle());
		isTheValueEmpty(coupon.getDescription());
		isTheObjectEmpty(coupon.getStartDate());
		isTheObjectEmpty(coupon.getEndDate());
	}
	/**
	 * This function receives an <code>object</code> as a parameter, then, it checks if that object is null.
	 * @param value - Any-type.
	 * @throws CouponsSystemExceptions - if the object is null.
	 * @see
	 * {@link exceptions.SystemExceptions#ILLEGAL_VALUE_ENTERED}
	 */
	private void isTheObjectEmpty(Object value) throws CouponsSystemExceptions {
		if (value == null) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "value cannot be null!");
		}
	}
	/**
	 * This function receives a <code>number</code> parameter, <br>
	 * and throws an appropriate exception, if its 0 or beneath it. 
	 * @param num - int-typed.
	 * @throws CouponsSystemExceptions - if the parameter is equivalant or below 0.
	 * @see
	 * {@link exceptions.SystemExceptions#ILLEGAL_VALUE_ENTERED}
	 */
	private void isTheValueEmpty(int num) throws CouponsSystemExceptions {
		if (num == 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "value cannot be zero!");
		}
		if (num < 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "value cannot be negative!");
		}
	}
	/**
	 * This function receives a <code>value</code> parameter,<br>
	 * and throws the appropriate exception if its null, or empty.
	 * @param value - String-typed.
	 * @throws CouponsSystemExceptions - if the value is null or empty.
	 * @see
	 * {@link exceptions.SystemExceptions#ILLEGAL_VALUE_ENTERED}
	 */
	private void isTheValueEmpty(String value) throws CouponsSystemExceptions {
		if (value == null) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "value cannot be null!");
		}
		if (value.equals("")) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "value cannot be empty!");
		}
	}
	/**
	 * This function receives a <code>company</code> parameter,<br>
	 * and checks if the company's name, email or password characters' count has exceeded 45.
	 * @param company - Company-typed.
	 * @return true if at least one parameter have exceeded, false if they none of the parameters have exceeded.
	 * @See
	 * {@link #charactersHasExceeded(String, int )}
	 */
	public boolean charactersHasExceeded(Company company) {
		if (charactersHasExceeded(company.getName(), 45)) {
			return true;
		}
		if (charactersHasExceeded(company.getEmail(), 45)) {
			return true;
		}
		if (charactersHasExceeded(company.getPassword(), 45)) {
			return true;
		}
		return false;
	}
	/**
	 * This function receives a <code>customer</code> parameter,<br>
	 * and checks if the customer's firstName, lastName, email or password characters' count has exceeded 45.
	 * @param customer - Customer-typed.
	 * @return true if at least one parameter have exceeded, false if they none of the parameters have exceeded.
	 * @See
	 * {@link #charactersHasExceeded(String, int )}
	 */
	public boolean charactersHasExceeded(Customer customer) {
		if (charactersHasExceeded(customer.getFirstName(), 45)) {
			return true;
		}
		if (charactersHasExceeded(customer.getLastName(), 45)) {
			return true;
		}
		if (charactersHasExceeded(customer.getEmail(), 45)) {
			return true;
		}
		if (charactersHasExceeded(customer.getPassword(), 45)) {
			return true;
		}
		return false;
	}
	/**
	 * This function receives a <code>coupon</code> parameter,<br>
	 * and checks if the company's title or image characters' count has exceeded 45,<br>
	 * and, if  description's characters' ount has exceeded 200.
	 * @param coupon Coupon - typed.
	 * @return true if at least one parameter have exceeded, false if they none of the parameters have exceeded.
	 * @See
	 * {@link #charactersHasExceeded(String, int)} 
	 */
	public boolean charactersHasExceeded(Coupon coupon) {
		if (charactersHasExceeded(coupon.getTitle(), 45)) {
			return true;
		}
		if (charactersHasExceeded(coupon.getDescription(), 200)) {
			return true;
		}
		if (charactersHasExceeded(coupon.getImage(), 45)) {
			return true;
		}
		return false;
	}
	/**
	 * This function receives an <code>attribute</code> and a <code>limit</code> parameters,<br>
	 * and checks if the attribute exceedes the limit.
	 * @param attribute - String-typed.
	 * @param limit - int-typed.
	 * @return true - if the attribute has exceeded the limit, false if it didn't.
	 */
	private boolean charactersHasExceeded(String attribute, int limit) {
		if (attribute.length() > limit) {
			return true;
		}
		return false;
	}
}
