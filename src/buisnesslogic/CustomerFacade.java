package buisnesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import annotations.NotNull;
import exceptions.CouponsSystemExceptions;
import exceptions.SystemExceptions;
import model.Category;
import model.Coupon;
import model.Customer;

public class CustomerFacade extends ClientFacade {
	private int customerId;
	private ObjectValidation objectValidation;

	public CustomerFacade() throws SQLException {
		super();
		objectValidation = new ObjectValidation();
	}

	/**
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CustomersDAO#isCustomerExists(String, String)}
	 *      {@link d.a.o.CustomersDAO#getIdByEmail(String)}
	 */
	@Override
	public boolean login(String email, String password) throws SQLException, InterruptedException {

		if (customersDAO.isCustomerExists(email, password)) {
			setCustomerID(customersDAO.getIdByEmail(email));
			return true;
		}
		return false;
	}

	/**
	 * This function receives a Coupon parameter and makes sure that it is
	 * Purchasable by the next criteria: <br>
	 * This coupon hasn't been purchased <strong> by this customer</Strong> <br>
	 * The coupon's amount is more than 0. <br>
	 * The coupon's starting date has passed. <br>
	 * The coupon's ending date hasn't arrived.
	 *
	 * @param coupon - Coupon-typed.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CouponsDAO#hasPurchasedCoupon(int, int)}
	 *      {@link d.a.o.CouponsDAO#addCouponPurchase(int, int)}
	 */
	public void purchaseCoupon(Coupon coupon) throws SQLException, CouponsSystemExceptions, InterruptedException {
		Calendar now = Calendar.getInstance();
		setTime(now);
		if (!couponsDAO.theCouponExist(coupon.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This coupon does not exist");
		}
		if (couponsDAO.hasPurchasedCoupon(customerId, coupon.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"You already have this coupon!");
		}
		if (coupon.getAmount() == 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "No more coupons left!");
		}
		if (coupon.getStartDate().getTimeInMillis() > now.getTimeInMillis()) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"This coupon has not been launched yet");
		}
		if (coupon.getEndDate().getTimeInMillis() < now.getTimeInMillis()) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This coupon has expired");
		}
		couponsDAO.addCouponPurchase(customerId, coupon.getId());
		System.out.println("\n--The coupon has been purchased--\n");
	}

	/**
	 * This function receives a Coupon parameter and makes sure it gets deleted
	 * along with it's purchased history.
	 * 
	 * @param coupon - Coupon-typed.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CouponsDAO#deleteCouponPurchase(int, int)}
	 *
	 */
	public void removeCouponPurchase(Coupon coupon) throws SQLException, CouponsSystemExceptions, InterruptedException {
		if (!couponsDAO.theCouponExist(coupon.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This coupon does not exist");
		}
		if (!isItOnTheCouponsList(coupon)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"You have not purchased this coupon yet!");
		}
		couponsDAO.deleteCouponPurchase(customerId, coupon.getId());
		System.out.println("\n--This coupon purchase has been removed--\n");

	}

	/**
	 * This function receives a Calendar parameter and sets it
	 * HOUR,MINUTE,SECOND,and MILLISECOND values to 0, and adds a month to the
	 * calendar.
	 * 
	 * @param now - Calendar-typed.
	 */

	private void setTime(Calendar now) {
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		now.add(Calendar.MONTH, 1);
	}

	/**
	 * This function gets a list of all coupons from the database and returns it,
	 * <strong>if its not empty</strong>.
	 * 
	 * @return all the coupons in the system - in a List form.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CouponsDAO#getAllCoupons()}
	 */
	public ArrayList<Coupon> getAllCoupons() throws SQLException, CouponsSystemExceptions, InterruptedException {
		ArrayList<Coupon> coupons = couponsDAO.getAllCoupons();
		if (coupons.size() == 0) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);
		}
		return coupons;
	}

	/**
	 * 
	 * This function <strong>receives</strong> a coupon id parameter,<br>
	 * then gets the id-specific coupon from the database.
	 * 
	 * @param couponId - int-typed.
	 * @return the coupon whose id equals the coupon id received in the
	 *         function,<strong> if it exists</strong>.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CouponsDAO#getOneCoupon(int)}
	 */
	public Coupon getOneCoupon(int couponId) throws SQLException, CouponsSystemExceptions, InterruptedException {
		Coupon coupon = couponsDAO.getOneCoupon(couponId);
		if (coupon != null) {
			return coupon;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND);

	}

	/**
	 * This function receives a Coupon parameter, then runs this coupon<br>
	 * against a list of all the Customer's coupon and returns true if it finds a
	 * match.
	 * 
	 * @param coupon - Coupon-typed.
	 * @return True - if it finds a match, false- if it doesn't.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link #getCustomerCoupons()}
	 */
	public boolean isItOnTheCouponsList(Coupon coupon) throws SQLException, CouponsSystemExceptions, InterruptedException {
		for (Coupon customerCoupon : getCustomerCoupons()) {
			if (coupon.equals(customerCoupon)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This function gets a list of all coupons purchased of a specific customer,
	 * from the database<br>
	 * and returns the list if its not empty.
	 * 
	 * @return All coupons purchased by a specific customer<Strong> in a List
	 *         form</Strong> , or null if the list is empty.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CouponsDAO#getAllcouponsPurchased(int)}
	 */
	public ArrayList<Coupon> getCustomerCoupons() throws SQLException, CouponsSystemExceptions, InterruptedException {
		ArrayList<Coupon> coupons = couponsDAO.getAllcouponsPurchased(customerId);
		if (coupons.size() == 0) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);
		}
		return coupons;
	}

	/**
	 * This function receives a Category parameter, and gets a list of all coupons
	 * purchased by a specific customer.<br>
	 * than, it returns a list of the customer's coupons that belong to the category
	 * received in the function.
	 * 
	 * @param category - Category-typed.
	 * 
	 * @return A list of all the customer's coupons that belong to the category
	 *         received in the function,<br>
	 *         <Strong>or null if the list is empty</Strong>.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link #getCustomerCoupons()}
	 */
	public ArrayList<Coupon> getCustomerCoupons(Category category) throws SQLException, CouponsSystemExceptions, InterruptedException {
		ArrayList<Coupon> coupons = new ArrayList<>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getCategory().equals(category)) {
				coupons.add(coupon);
			}
		}
		if (coupons.size() == 0) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);
		}
		return coupons;
	}

	/**
	 * This function receives a (double) price parameter and returns a list of all
	 * coupons that the specific customer have,<br>
	 * whose prices are equals or below the price parameter received in the
	 * function.
	 * 
	 * @param price - double-typed.
	 * @return All the coupons that the customer has whose prices are equals or
	 *         below the price received in the function in a list form <strong>or
	 *         null if the list is empty</strong>
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link #getCustomerCoupons()}
	 */
	public ArrayList<Coupon> getCustomerCoupons(double price) throws SQLException, CouponsSystemExceptions, InterruptedException {
		ArrayList<Coupon> coupons = new ArrayList<>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getPrice() <= price) {
				coupons.add(coupon);
			}
		}
		if (coupons.size() == 0) {
			throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);
		}
		return coupons;
	}

	/**
	 * This function gets a customer object from the getOneCustomer function, than
	 * returns it <strong> if its not null</strong> .
	 * 
	 * @return The customer that the function gets from the getOneCustomer , or null
	 *         if the customer is null.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CustomersDAO#getOneCustomer(int)}
	 */
	public Customer getCustomerDetails() throws SQLException, CouponsSystemExceptions, InterruptedException {
		Customer customer = customersDAO.getOneCustomer(customerId);
		if (customer == null) {
			throw new CouponsSystemExceptions(SystemExceptions.CUSTOMER_NOT_FOUND);
		}
		return customer;
	}

	/**
	 * This function receives a Customer parameter to be changed, checks its email
	 * to verify that its the customer's email then finally - updates the customer.
	 * 
	 * @param customer - Customer-typed.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException
	 * @see {@link d.a.o.CustomersDAO#isThisTheCustomersEmail(int, String)}
	 *      {@link d.a.o.CustomersDAO#isEmailTaken(String)} <br>
	 *      {@link d.a.o.CustomersDAO#updateCustomer(Customer)}
	 * 
	 */
	public void UpdateDetails(Customer customer) throws SQLException, CouponsSystemExceptions, InterruptedException {
		if (!customersDAO.theCustomerExist(customer.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"This customer does not exist");
		}
		objectValidation.isTheObjectEmpty(customer);
		if (objectValidation.charactersHasExceeded(customer)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"characters have exceeded the attribute's given capacity");
		}
		if (!customersDAO.isThisTheCustomersEmail(customerId, customer.getEmail())) {
			if (customersDAO.isEmailTaken(customer.getEmail())) {
				throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
			} else {
				customersDAO.updateCustomer(customer);
				System.out.println("\n--This customer has been updated--\n");
			}
		} else {
			customersDAO.updateCustomer(customer);
			System.out.println("\n--This customer has been updated--\n");
		}
	}

	public int getCustomerID() {
		return customerId;
	}

	@NotNull
	private void setCustomerID(int customerID) {
		if (customerID != 0) {
			this.customerId = customerID;
		}
	}

	/**
	 * This function receives a (Calendar) time parameter and returns its DAY, MONTH
	 * and YEAR parameters <br>
	 * <strong>in a String form</Strong>.
	 * 
	 * @param time - Calendar-typed.
	 * @return a String representation of the received calendar object's DAY,MONTH
	 *         and YEAR values.
	 * @see {@link #getFullNumber(int)}
	 */
	private String getTime(Calendar time) {
		return getFullNumber(time.get(Calendar.DAY_OF_MONTH)) + "/" + getFullNumber(time.get(Calendar.MONTH)) + "/"
				+ getFullNumber(time.get(Calendar.YEAR));
	}

	/**
	 * This function receives an (int) num parameter and returns it as a String,
	 * double-digited number.
	 * 
	 * @param num - int-typed.
	 * @return A string , double-digited representation of the number received in
	 *         the function.
	 */
	private String getFullNumber(int num) {
		return num > 9 ? num + "" : "0" + num;
	}
}
