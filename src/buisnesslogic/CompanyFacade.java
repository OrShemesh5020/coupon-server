package buisnesslogic;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import exceptions.CouponsSystemExceptions;
import exceptions.SystemExceptions;
import model.Category;
import model.Company;
import model.Coupon;

public class CompanyFacade extends ClientFacade {
	private int companyId;
	private ObjectValidation objectValidation;

	public CompanyFacade() throws SQLException {
		super();
		objectValidation = new ObjectValidation();
	}

	/**
	 * @throws InterruptedException 
	 * @See {@link d.a.o.CompaniesDAO#doesCompanyExists(String, String)}
	 *      {@link d.a.o.CompaniesDAO#getIdByEmail(String)}
	 */
	@Override
	public boolean login(String email, String password) throws SQLException, InterruptedException {
		if (companiesDAO.doesCompanyExists(email, password)) {
			setCompanyId(companiesDAO.getIdByEmail(email));
			return true;
		}
		return false;
	}

	/**
	 * this function receives a Coupon parameter, checks the object's details and
	 * only allows a new coupon addition only if : the coupon's entered name
	 * parameter doesn't exist in the system. the start time hasn't passed. the end
	 * date parameter is not set before the start date parameter. the coupon's
	 * amount parameters are not set as less than or equals 0.
	 * 
	 * @param coupon - Coupon-typed.
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CouponsDAO#nameAlreadyTaken(int, String)}
	 *      {@link d.a.o.CouponsDAO#addACoupon(Coupon)}
	 *
	 */
	public void addACoupon(Coupon coupon) throws SQLException, CouponsSystemExceptions, InterruptedException {
		Calendar now = Calendar.getInstance();
		setTime(now);
		objectValidation.isTheObjectEmpty(coupon);
		if (objectValidation.charactersHasExceeded(coupon)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"characters have exceeded the attribute's given capacity");
		}
		if (couponsDAO.nameAlreadyTaken(companyId, coupon.getTitle())) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE,
					"This coupon title is already taken!");
		}
		if (coupon.getStartDate().getTimeInMillis() < now.getTimeInMillis()) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"Coupon launch date occurs before current date");
		}
		if (coupon.getEndDate().getTimeInMillis() < coupon.getStartDate().getTimeInMillis()) {
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
		coupon.getStartDate().add(Calendar.DAY_OF_MONTH, 1);
		coupon.getEndDate().add(Calendar.DAY_OF_MONTH, 1);
		couponsDAO.addACoupon(coupon);
		System.out.println("\n--The coupon was added--\n");
	}

	public void updateCoupon(Coupon coupon) throws SQLException, CouponsSystemExceptions, InterruptedException {
		Calendar now = Calendar.getInstance();
		setTime(now);
//		System.out.println("CompanyFacade.updateCoupon");
//		System.out.println("now: " + getTime(now));
//		System.out.println("now: " + now.getTimeInMillis());
//		System.out.println("start date: " + getTime(coupon.getStartDate()));
//		System.out.println("start date: " + coupon.getStartDate().getTimeInMillis());
//		System.out.println("end date: " + getTime(coupon.getEndDate()));
//		System.out.println("end date: " + coupon.getEndDate().getTimeInMillis());
		if (!couponsDAO.companyHasTheCoupon(companyId, coupon.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"This coupon does not belong to this company");
		}
		objectValidation.isTheObjectEmpty(coupon);
		if (objectValidation.charactersHasExceeded(coupon)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"characters have exceeded the attribute's given capacity");
		}
		if (coupon.getEndDate().getTimeInMillis() < coupon.getStartDate().getTimeInMillis()) {
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
		if (coupon.getStartDate().getTimeInMillis() > now.getTimeInMillis()) {
			coupon.getStartDate().add(Calendar.DAY_OF_MONTH, 1);
			coupon.getEndDate().add(Calendar.DAY_OF_MONTH, 1);
			couponsDAO.updateACoupon(coupon);
			System.out.println("\n--The coupon was updated--\n");
		} else {
			coupon.getStartDate().add(Calendar.DAY_OF_MONTH, 1);
			coupon.getEndDate().add(Calendar.DAY_OF_MONTH, 1);
			couponsDAO.updateACouponWithoutStartTime(coupon);
			System.out.println("\n--The Coupon's details have been altered, not including it's start time--\n");
		}
	}

	/**
	 * this function receives a Calendar-typed object 'now' and sets the time's
	 * HOUR,MINUTE,SECOND,and MILLISECOND to 0 and also adds +1 to the MONTH value
	 * because the Calendar's default value of January is 0;
	 * 
	 * @param now =Calendar-type object = {@link Calendar}
	 */
	private void setTime(Calendar now) {
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		now.add(Calendar.MONTH, 1);
	}

	public void deleteCoupon(Coupon coupon) throws SQLException, CouponsSystemExceptions, InterruptedException {
		if (!couponsDAO.companyHasTheCoupon(companyId, coupon.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"This coupon does not belong to this company");
		}
		couponsDAO.deleteCouponByCoupon(coupon.getId());
		System.out.println("\n--The coupon was deleted--\n");
	}

	/**
	 * this function receives a coupon's Id and checks it against the Database.
	 * 
	 * @param couponId
	 * @return
	 * @throws InterruptedException 
	 */
	public Coupon getOneCoupon(int couponId) throws SQLException, CouponsSystemExceptions, InterruptedException {
		Coupon coupon = couponsDAO.getOneCoupon(couponId);
		if (coupon != null) {
			return coupon;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND);
	}

	/**
	 * this function receives a title, and runs it against AllCouponList, if its
	 * finds a coupon it returns it, if not it shows a message.
	 * 
	 * @param title
	 * @return a Coupon-typed object, OR null if the coupon's title doesn't exist in
	 *         the Database.
	 * @throws InterruptedException 
	 */
	public Coupon getOneCoupon(String title) throws SQLException, CouponsSystemExceptions, InterruptedException {
		Coupon coupon = couponsDAO.getOneCoupon(companyId, title);
		if (coupon != null) {
			return coupon;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPON_NOT_FOUND);
	}

	/**
	 * this function accesses the coupons' Dao division's getAllCompanyCoupons
	 * function and validates by checking if the list has more than 1 element
	 * (size()>0).
	 * 
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * 
	 * @returns a list of all the current company's coupons
	 * @see getAllCompanyCoupons.couponsDBDAO
	 */
	public List<Coupon> getAllCompanyCoupons() throws SQLException, CouponsSystemExceptions, InterruptedException {
		List<Coupon> coupons = couponsDAO.getAllCompanyCoupons(companyId);
		if (coupons.size() > 0) {
			return coupons;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);
	}

	/**
	 * this function returns a list of all company's coupons, by accessing the
	 * DAO-related class and then uses the function that exists in the DAO class to
	 * get a list of coupons by companyID, and category, than it validates by
	 * checking if the size of the accepted list is >0;
	 * 
	 * @param category
	 * @return
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 */
	public List<Coupon> getCouponsByCategory(Category category) throws SQLException, CouponsSystemExceptions, InterruptedException {
		List<Coupon> coupons = couponsDAO.getCompanyCouponsByCategory(companyId, category.ordinal() + 1);
		if (coupons.size() > 0) {
			return coupons;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);

	}

	/**
	 * this function creates a list of coupons by sending the local company's Id and
	 * the maxPrice that the function receives into the DAO's class function than
	 * validates by checking that the list has more than one element (list's
	 * size()>0) and finally returns the list.
	 * 
	 * @param maxPrice
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @returns a list of the company's coupons that are under maxPrice's value
	 */
	public List<Coupon> getCouponsByPrice(double maxPrice) throws SQLException, CouponsSystemExceptions, InterruptedException {
		List<Coupon> coupons = couponsDAO.getCompanyCouponsByPrice(companyId, maxPrice);
		if (coupons.size() > 0) {
			return coupons;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COUPONS_NOT_FOUND);
	}

	public Company getCompanyDetails() throws SQLException, CouponsSystemExceptions, InterruptedException {
		Company company = companiesDAO.getOneCompany(companyId);
		if (company != null) {
			return company;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COMPANY_NOT_FOUND);
	}

	public void UpdateDetails(Company company) throws SQLException, CouponsSystemExceptions, InterruptedException {
		if (!companiesDAO.theCompanyExist(company.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This company does not exist");
		}
		objectValidation.isTheObjectEmpty(company);
		if (objectValidation.charactersHasExceeded(company)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"characters have exceeded the attribute's given capacity");
		}
		if (!companiesDAO.isThisTheCompanysName(companyId, company.getName())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"You can't change the name of the company");
		} else if (!companiesDAO.isThisTheCompanysEmail(companyId, company.getEmail())) {
			if (companiesDAO.areElementsTaken(company.getEmail())) {
				throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
			} else {
				System.out.println("\n--This company has been updated--\n");
				companiesDAO.updateCompany(company);
			}
		} else {
			System.out.println("\n--This company has been updated--\n");
			companiesDAO.updateCompany(company);
		}
	}

	public int getCompanyId() {
		return companyId;
	}

	/**
	 * this function makes sure that it doesn't return 0 as a companyId
	 * 
	 * @param companyId
	 */
	private void setCompanyId(int companyId) {
		if (companyId != 0) {
			this.companyId = companyId;
		}
	}

}
