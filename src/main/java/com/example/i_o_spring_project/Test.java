package com.example.i_o_spring_project;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.example.i_o_spring_project.automatic_managment.CouponExpirationDaliyJob;
import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.model.Category;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.model.Customer;
import com.example.i_o_spring_project.repository.CategoryRepository;
import com.example.i_o_spring_project.service.AdminService;
import com.example.i_o_spring_project.service.ClientType;
import com.example.i_o_spring_project.service.CompanyService;
import com.example.i_o_spring_project.service.CustomerService;
import com.example.i_o_spring_project.service.LoginManager;

/**
 * This class is designed to test all of the system's functions in a hard-coded
 * way.
 * 
 * @author i.o
 *
 */
@Component
public class Test {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CouponExpirationDaliyJob daliyJob;

	/**
	 * This function activates the dailyJob. It instantiates a LoginManager-typed
	 * object, than it activates each of the the client's types functions to test
	 * all the program's performances. It closes the dailyJob when it stop running.
	 * 
	 * @ @see {@link automatic.managment.CouponExpirationDailyJob}<br>
	 *   {@link buisnesslogic.LoginManager#login(String, String, ClientType)} <br>
	 *   {@link #adminsOptionsProperly(AdminFacade)}<br>
	 *   {@link #companysOptionsProperly(CompanyFacade)}<br>
	 *   {@link #customersOptionsProperly(CustomerFacade)}<br>
	 *   {@link #adminsOptionsImproperly(AdminFacade)}<br>
	 *   {@link #companysOptionsImproperly(CompanyFacade)}<br>
	 *   {@link #customersOptionsImproperly(CustomerFacade)}<br>
	 * 
	 * 
	 */
	public void testAll() {
		try {
			daliyJob.run();
			AdminService admin = (AdminService) loginManager.login("admin@admin.com", "admin",
					ClientType.ADMINISTRATOR);
			CompanyService company = (CompanyService) loginManager.login("or@gmail.com", "123456", ClientType.COMPANY);
			CustomerService customer = (CustomerService) loginManager.login("orshemesh5020@gmail.com", "1q2w3e",
					ClientType.CUSTOMER);
			System.out.println("Displaying the test properly...");
			System.out.println("Admin:");
			adminsOptionsProperly(admin);
			System.out.println("Company:");
			companysOptionsProperly(company);
			System.out.println("Customer:");
			customersOptionsProperly(customer);
			System.out.println("Displaying the test improperly...");
			System.out.println("Admin:");
			adminsOptionsImproperly(admin);
			System.out.println("****************************************************************");
			System.out.println("Company:");
			companysOptionsImproperly(company);
			System.out.println("****************************************************************");
			System.out.println("Customer:");
			customersOptionsImproperly(customer);
			System.out.println("****************************************************************");
		} catch (CouponsSystemExceptions couponException) {
			System.err.println(couponException.toString());
		}
	}

	/**
	 * This function receives a <code>customer</code> parameter,<br>
	 * and inserts incorrect values to the customer class functions.
	 * 
	 * @param customer - CustomerFacade-typed.
	 * @ @throws InterruptedException
	 * @see {@link CustomerFacade#getOneCoupon(int)}<br>
	 *      {@link #purchaseACouponImproperly(CustomerFacade, Coupon, Coupon)}<br>
	 *      {@link #removeCouponPurchaseImproperly(CustomerFacade, Coupon)}<br>
	 *      {@link #updateCustomerDetailsImproperly(CustomerFacade, Customer) }<br>
	 *      {@link CustomerFacade#getCustomerDetails()}
	 */
	private void customersOptionsImproperly(CustomerService customer) {
		Coupon purchasedCoupon = customer.getOneCoupon(197);
		Coupon unpurchasedCoupon = customer.getOneCoupon(15);
		purchaseACouponImproperly(customer, purchasedCoupon, unpurchasedCoupon);
		System.out.println("****************************************************************");
		removeCouponPurchaseImproperly(customer, unpurchasedCoupon);
		System.out.println("****************************************************************");
		updateCustomerDetailsImproperly(customer, customer.getCustomerDetails());
		System.out.println("****************************************************************");
		System.out.println("****************************************************************");
	}

	/**
	 * This function receives a <code>customerFacade</code> , and a
	 * <code>customer </code>parameters,<br>
	 * and attempts to update the customer's different attributes with incorrect
	 * values.
	 * 
	 * @param customerFacade - CustomerFacade-typed.
	 * @param customer       - Customer-typed.
	 * @throws InterruptedException
	 * @Catch {@link CouponsSystemExceptions}
	 * @see {@link #cloneCustomer(Customer)} <br>
	 *      {@link #createARandomNumber(int, int)}<br>
	 *      {@link CustomerFacade#UpdateDetails(Customer)}<br>
	 *      {@link #createALongString(int)}
	 */
	private void updateCustomerDetailsImproperly(CustomerService customerFacade, Customer customer) {
		System.out.println("Update a customer details improperly:\n");
		Customer customer1 = cloneCustomer(customer);
		int randomId = createARandomNumber(100, 500);
		System.out.println("Example of entering an incorrect id (" + randomId + "):\n");
		customer1.setId(randomId);
		System.out.println(customer1.toString());
		try {
			customerFacade.UpdateDetails(customer1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering an empty value:\n");
		customer1.setId(customer.getId());
		customer1.setEmail(null);
		System.out.println(customer1.toString());
		try {
			customerFacade.UpdateDetails(customer1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering a value whose characters have exceeded the given capacity:\n");
		customer1.setEmail(createALongString(46));
		System.out.println(customer1.toString());
		try {
			customerFacade.UpdateDetails(customer1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering an already taken value (email):\n");
		customer1.setEmail("idocohen@gmail.com");
		System.out.println(customer1.toString());
		try {
			customerFacade.UpdateDetails(customer1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	/**
	 * This function receives a <code>customer</code> and a <code>coupon</code>
	 * parameters,<br>
	 * and attempts to remove a coupon that hasn't been purchased, and then - a
	 * non-existing coupon.
	 * 
	 * @param customer          - CustomerFacade-typed.
	 * @param unpurchasedCoupon - Coupon-typed.
	 * @throws InterruptedException
	 * @catch {@link CouponsSystemExceptions}
	 * @see {@link #createARandomNumber(int, int) }<br>
	 *      {@link CustomerFacade#removeCouponPurchase(Coupon) }
	 * 
	 */
	private void removeCouponPurchaseImproperly(CustomerService customer, Coupon unpurchasedCoupon) {
		System.out.println("Removing a coupon purchase improperly:\n");
		int randomId = createARandomNumber(100, 500);
		System.out.println("Example of an id entry (" + unpurchasedCoupon.getId()
				+ ") that exists in the system but not yet purchased:\n");
		System.out.println(unpurchasedCoupon.toString());
		try {
			customer.removeCouponPurchase(unpurchasedCoupon.getId());
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of an id entry (" + randomId + ") that doesn't exist in the system:\n");
		unpurchasedCoupon.setId(randomId);
		System.out.println(unpurchasedCoupon.toString());
		try {
			customer.removeCouponPurchase(unpurchasedCoupon.getId());
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	/**
	 * This function receives a <code>customer</code> and a <code>coupon</code>
	 * parameters,<br>
	 * and attempts to purchase a coupon using incorrect data, or methods.
	 * 
	 * @param customer          - CustomerFacade-typed.
	 * @param purchasedCoupon   - Coupon-typed.
	 * @param unpurchasedCoupon - Coupon-typed.
	 * @throws InterruptedException
	 * 
	 * @catch {@link CouponsSystemExceptions}
	 * @see {@link #cloneCoupon(Coupon) }
	 *      {@link #createARandomNumber(int, int) }<br>
	 *      {@link CustomerFacade#purchaseCoupon(Coupon)) }<br>
	 *      {@link #createARandomNumber(int, int)}
	 */
	private void purchaseACouponImproperly(CustomerService customer, Coupon purchasedCoupon, Coupon unpurchasedCoupon) {
		System.out.println("Purchase a coupon improperly:");
		Coupon coupon = cloneCoupon(purchasedCoupon);
		int randomId = createARandomNumber(100, 500);
		System.out.println("\nExample of entering an incorrect id (" + randomId + "):\n");
		coupon.setId(randomId);
		System.out.println(coupon.toString());
		try {
			customer.purchaseCoupon(coupon);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of an existing coupon purchase:\n");
		coupon.setId(purchasedCoupon.getId());
		System.out.println(coupon.toString());
		try {
			customer.purchaseCoupon(coupon);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of purchasing a coupon whose out of stock:\n");
		coupon = cloneCoupon(unpurchasedCoupon);
		coupon.setAmount(0);
		System.out.println(coupon.toString());
		try {
			customer.purchaseCoupon(coupon);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of purchasing a coupon that has not yet been launched:\n");
		coupon.setAmount(unpurchasedCoupon.getAmount());
		Calendar startDate = Calendar.getInstance();
		startDate.set(Calendar.YEAR, createARandomNumber(2021, 2025));
		coupon.setStartDate(startDate.getTime());
		Calendar endDate = Calendar.getInstance();
		endDate.set(Calendar.YEAR, createARandomNumber(2026, 2030));
		coupon.setEndDate(endDate.getTime());
		System.out.println(coupon.toString());
		try {
			customer.purchaseCoupon(coupon);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of purchasing a coupon that has been expired:\n");
		coupon = cloneCoupon(unpurchasedCoupon);
		startDate.set(Calendar.YEAR, createARandomNumber(2000, 2014));
		coupon.setStartDate(startDate.getTime());
		endDate.set(Calendar.YEAR, createARandomNumber(2015, 2019));
		coupon.setEndDate(endDate.getTime());
		System.out.println(coupon.toString());
		try {
			customer.purchaseCoupon(coupon);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	/**
	 * This function receives a <code>company</code> parameter,<br>
	 * and inserts incorrect values to the company class functions.
	 * 
	 * @param company - CompanyFacade-typed.
	 * @ @throws InterruptedException
	 * @see {@link #addCouponImproperly(CompanyFacade)}<br>
	 *      {@link #getOneCouponImproperly(CompanyFacade)}<br>
	 *      {@link #updateOneCouponImproperly(CompanyFacade, Coupon)}<br>
	 *      {@link #deleteOneCouponImproperly(CompanyFacade, Coupon)}<br>
	 *      {@link #updateCompanyDetailsImproperly(CompanyFacade, Company)}<br>
	 *      {@link CompanyFacade#getCompanyDetails()}
	 */
	private void companysOptionsImproperly(CompanyService company) {
		addCouponImproperly(company);
		System.out.println("****************************************************************");
		getOneCouponImproperly(company);
		System.out.println("****************************************************************");
		Coupon coupon = company.getOneCoupon(15);
		updateOneCouponImproperly(company, coupon);
		System.out.println("****************************************************************");
		deleteOneCouponImproperly(company, coupon);
		System.out.println("****************************************************************");
		updateCompanyDetailsImproperly(company, company.getCompanyDetails());
		System.out.println("****************************************************************");
	}

	/**
	 * This function receieves a <code>companyFacade </code> and a
	 * <code>company</code> parameters,<br>
	 * and attempts to update the company's different attributes using incorrect
	 * data or methods.
	 * 
	 * @param companyService - CompanyFacade-typed.
	 * @param company        - Company-typed.
	 * @throws InterruptedException
	 * @see {@link #cloneCompany(Company)}<br>
	 *      {@link #createARandomNumber(int, int)}<br>
	 *      {@link CompanyFacade#UpdateDetails(Company)}<br>
	 *      {@link #createALongString(int)}<br>
	 * @catch {@link CouponsSystemExceptions}
	 */
	private void updateCompanyDetailsImproperly(CompanyService companyService, Company company) {
		System.out.println("Update a company datails improperly:\n");
		Company company1 = cloneCompany(company);
		int randomId = createARandomNumber(100, 500);
		System.out.println("Example of entering an incorrect id (" + randomId + "):\n");
		company1.setId(randomId);
		System.out.println(company1.toString());
		try {
			companyService.updateDetails(company1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering an empty value:\n");
		company1.setId(company.getId());
		company1.setEmail(null);
		System.out.println(company1.toString());
		try {
			companyService.updateDetails(company1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering a value whose characters have exceeded the given capacity:\n");
		company1.setEmail(createALongString(46));
		System.out.println(company1.toString());
		try {
			companyService.updateDetails(company1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering a different name:\n");
		company1.setEmail(company.getEmail());
		company1.setName("name" + createARandomNumber(100, 500));
		System.out.println(company1.toString());
		try {
			companyService.updateDetails(company1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering an already taken value (email):\n");
		company1.setName(company.getName());
		company1.setEmail("drink123@gmail.com");
		System.out.println(company1.toString());
		try {
			companyService.updateDetails(company1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	/**
	 * This function receives a <code>company</code> and a <code>coupon</code>
	 * parameters,<br>
	 * and attempts to delete the received coupons using incorrect data or methods.
	 * 
	 * @param company - CompanyFacade-typed.
	 * @param coupon  - Coupon-typed.
	 * @throws InterruptedException
	 * @see {@link #createARandomNumber(int, int)}<br>
	 *      {@link CompanyFacade#deleteCoupon(Coupon)}<br>
	 * @catch {@link CouponsSystemExceptions}
	 */
	private void deleteOneCouponImproperly(CompanyService company, Coupon coupon) {
		System.out.println("Delete a coupon improperly:\n");
		int randomId = createARandomNumber(100, 500);
		System.out.println("Example of entering an incorrect id (" + randomId + "):\n");
		coupon.setId(randomId);
		System.out.println(coupon.toString());
		try {
			company.deleteCoupon(coupon.getId());
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	/**
	 * This function receives a <code>company</code> and a <code>coupon</code>
	 * parameters,<br>
	 * and attempts to update the coupon's different attributes with incorrect data
	 * or methods.
	 * 
	 * @param company - CompanyFacade-typed.
	 * @param coupon  - Coupon-typed.
	 * @throws InterruptedException
	 * @catch {@link CouponsSystemExceptions}
	 * @see {@link #cloneCoupon(Coupon)}<br>
	 *      {@link #createARandomNumber(int, int)}<br>
	 *      {@link CompanyFacade#updateCoupon(Coupon)}<br>
	 *      {@link #createALongString(int)}<br>
	 *      {@link #createARandomNumber(int, int)}<br>
	 */
	private void updateOneCouponImproperly(CompanyService company, Coupon coupon) {
		System.out.println("Update a coupon improperly:\n");
		Coupon coupon1 = cloneCoupon(coupon);
		int randomId = createARandomNumber(100, 500);
		System.out.println("Example of entering an incorrect id (" + randomId + "):\n");
		coupon1.setId(randomId);
		System.out.println(coupon1.toString());
		try {
			company.updateCoupon(coupon1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering an empty value:\n");
		coupon1.setId(coupon.getId());
		coupon1.setTitle(null);
		System.out.println(coupon1.toString());
		try {
			company.updateCoupon(coupon1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering a value whose characters have exceeded the given capacity:\n");
		coupon1.setTitle(createALongString(46));
		System.out.println(coupon1.toString());
		try {
			company.updateCoupon(coupon1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of inserting a coupon whose expiration date is before its start date:\n");
		coupon1.setTitle(coupon.getTitle());
		Calendar startTime = Calendar.getInstance();
		startTime.set(Calendar.YEAR, createARandomNumber(2030, 2040));
		coupon1.setStartDate(startTime.getTime());
		System.out.println(coupon1.toString());
		try {
			company.updateCoupon(coupon1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering a value is equal to zero (amount):\n");
		coupon1 = cloneCoupon(coupon);
		coupon1.setAmount(0);
		System.out.println(coupon1.toString());
		try {
			company.updateCoupon(coupon1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering a value is equal to zero (price):\n");
		coupon1.setAmount(createARandomNumber(5, 200));
		coupon1.setPrice(0d);
		System.out.println(coupon1.toString());
		try {
			company.updateCoupon(coupon1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	/**
	 * This function receives a <code>company</code> parameter,<br>
	 * and attempts to get a coupon using incorrect data or methods.
	 * 
	 * @param company - CompayFacade-typed.
	 * @throws InterruptedException
	 * @catch {@link CouponsSystemExceptions}
	 * @see {@link #createARandomNumber(int, int)}<br>
	 *      {@link CompanyFacade#getOneCoupon(int)}<br>
	 */
	private Coupon getOneCouponImproperly(CompanyService company) {
		System.out.println("Get a coupon improperly:\n");
		int randomId = createARandomNumber(200, 600);
		System.out.println("Example of entering an incorrect id (" + randomId + "):\n");
		try {
			Coupon coupon = company.getOneCoupon(randomId);
			return coupon;
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		return null;
	}

	/**
	 * This function receives a <code>company</code> parameter,<br>
	 * and attempts to add a coupon using incorrect data or methods.
	 * 
	 * @param company - CompayFacade-typed.
	 * @throws InterruptedException
	 * @catch {@link CouponsSystemExceptions}
	 * @see {@link #createNewCoupon(CompanyFacade)}<br>
	 *      {@link CompanyFacade#addACoupon(Coupon)}<br>
	 *      {@link #createALongString(int)}<br>
	 *      {@link #createARandomNumber(int, int)}
	 */
	private void addCouponImproperly(CompanyService company) {
		Coupon coupon = createNewCoupon(company);
		System.out.println("Add a coupon improperly:\n");
		System.out.println("Example of entering an empty value:\n");
		coupon.setTitle(null);
		System.out.println(coupon.toString());
		try {
			company.addACoupon(coupon);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering a value whose characters have exceeded the given capacity:\n");
		coupon.setTitle(createALongString(46));
		System.out.println(coupon.toString());
		try {
			company.addACoupon(coupon);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering an already taken value (title):\n");
		coupon.setTitle("phone");
		System.out.println(coupon.toString());
		try {
			company.addACoupon(coupon);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of inserting a coupon whose start date is less than the current date:\n");
		coupon.setTitle("title" + createARandomNumber(100, 500));
		Calendar startTime = Calendar.getInstance();
		startTime.setTime(coupon.getStartDate());
		startTime.set(Calendar.YEAR, createARandomNumber(1990, 2010));
		coupon.setStartDate(startTime.getTime());
		System.out.println(coupon.toString());
		try {
			company.addACoupon(coupon);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of inserting a coupon whose expiration date is less than its start date:\n");
		startTime.setTime(coupon.getStartDate());
		startTime.set(Calendar.YEAR, createARandomNumber(2030, 2040));
		coupon.setStartDate(startTime.getTime());
		System.out.println(coupon.toString());
		try {
			company.addACoupon(coupon);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering a value is equal to zero (amount):\n");
		coupon = createNewCoupon(company);
		coupon.setAmount(0);
		System.out.println(coupon.toString());
		try {
			company.addACoupon(coupon);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering a value is equal to zero (price):\n");
		coupon.setAmount(createARandomNumber(5, 200));
		coupon.setPrice(0d);
		System.out.println(coupon.toString());
		try {
			company.addACoupon(coupon);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	/**
	 * This function receives an <code>admin</code> parameter,<br>
	 * and inserts incorrect values to the AdminFacade class functions.
	 * 
	 * @param admin - AdminFacade-typed.
	 * @ @throws InterruptedException
	 * @see {@link #addACompanyImproperly(AdminFacade)}<br>
	 *      {@link #getACompanyImproperly(AdminFacade)}<br>
	 *      {@link #updateACompanyImproperly(AdminFacade, Company)}<br>
	 *      {@link #deleteACompanyImproperly(AdminFacade, Company)}<br>
	 *      {@link #addACustomerImproperly(AdminFacade)}<br>
	 *      {@link #getACustomerImproperly(AdminFacade)}<br>
	 *      {@link #updateACustomerImproperly(AdminFacade, Customer)}<br>
	 *      {@link #deleteACustomerImproperly(AdminFacade, Customer)}
	 */
	private void adminsOptionsImproperly(AdminService admin) {
		addACompanyImproperly(admin);
		System.out.println("****************************************************************");
		getACompanyImproperly(admin);
		System.out.println("****************************************************************");
		Company company = admin.getCompany(112);
		updateACompanyImproperly(admin, company);
		System.out.println("****************************************************************");
		deleteACompanyImproperly(admin, company);
		System.out.println("****************************************************************");
		addACustomerImproperly(admin);
		System.out.println("****************************************************************");
		getACustomerImproperly(admin);
		System.out.println("****************************************************************");
		Customer customer = admin.getCustomer(9);
		updateACustomerImproperly(admin, customer);
		System.out.println("****************************************************************");
		deleteACustomerImproperly(admin, customer);
		System.out.println("****************************************************************");

	}

	/**
	 * This function receives a <code>admin</code> and a <code>customer</code>
	 * parameters,<br>
	 * and attempts to delete the received customer parameter using incorrect data
	 * or methods.
	 * 
	 * @param admin    - AdminFacade-typed.
	 * @param customer - Customer-typed.
	 * @throws InterruptedException
	 * @catch {@link CouponsSystemExceptions}<br>
	 * @see {@link #createARandomNumber(int, int)}<br>
	 *      {@link AdminFacade#removeCustomer(Customer)}
	 */
	private void deleteACustomerImproperly(AdminService admin, Customer customer) {
		System.out.println("Delete a customer improperly:\n");
		int randomId = createARandomNumber(100, 500);
		System.out.println("Example of entering an incorrect id (" + randomId + "):\n");
		customer.setId(randomId);
		System.out.println(customer.toString());
		try {
			admin.removeCustomer(customer.getId());
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	/**
	 * This function receives a <code>admin</code> and a <code>customer</code>
	 * parameters,<br>
	 * and attempts to update the customer's attributes with incorrect data or
	 * methods.
	 * 
	 * @param admin    - AdminFacade-typed.
	 * @param customer - Customer typed.
	 * @throws InterruptedException
	 * @catch {@link CouponsSystemExceptions}
	 * @see {@link #cloneCustomer(Customer)}<br>
	 *      {@link #createARandomNumber(int, int)}<br>
	 *      {@link AdminFacade#updateCustomer(Customer)}<br>
	 */
	private void updateACustomerImproperly(AdminService admin, Customer customer) {
		System.out.println("Update a customer improperly:\n");
		Customer customer1 = cloneCustomer(customer);
		int randomId = createARandomNumber(100, 500);
		System.out.println("Example of entering an incorrect id (" + randomId + "):\n");
		customer1.setId(randomId);
		System.out.println(customer1.toString());
		try {
			admin.updateCustomer(customer1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering an empty value:\n");
		customer1.setId(customer.getId());
		customer1.setEmail(null);
		System.out.println(customer1.toString());
		try {
			admin.updateCustomer(customer1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering a value whose characters have exceeded the given capacity:\n");
		customer1.setEmail(createALongString(46));
		System.out.println(customer1.toString());
		try {
			admin.updateCustomer(customer1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering an already taken value (email):\n");
		customer1.setEmail("idocohen@gmail.com");
		System.out.println(customer1.toString());
		try {
			admin.updateCustomer(customer1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	/**
	 * This function receives an <code>admin</code> parameter,<br>
	 * and attempts to get a customer object using incorrect data or methods.
	 * 
	 * @param admin - AdminFacade-typed.
	 * @throws InterruptedException
	 * @catch {@link CouponsSystemExceptions}
	 * @see {@link #createARandomNumber(int, int)}<br>
	 *      {@link AdminFacade#getCustomer(int)}
	 */
	private Customer getACustomerImproperly(AdminService admin) {
		System.out.println("Get a customer improperly:\n");
		int randomId = createARandomNumber(100, 500);
		System.out.println("Example of entering an incorrect id (" + randomId + "):\n");
		try {
			Customer customer = admin.getCustomer(randomId);
			return customer;
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		return null;
	}

	/**
	 * This function receives an <code>admin</code> parameter,<br>
	 * and attempts to add a customer to the admin's customers list using incorrect
	 * data or methods.
	 * 
	 * @param admin - AdminFacade-typed.
	 * @return a Customer-typed object.
	 * @throws InterruptedException
	 * @catch {@link CouponsSystemExceptions}
	 * @see {@link #createNewCustomer()}<br>
	 *      {@link AdminFacade#addCustomer(Customer)}<br>
	 *      {@link #createALongString(int)}
	 */
	private Customer addACustomerImproperly(AdminService admin) {
		Customer customer = createANewCustomer();
		System.out.println("Add a customer improperly:\n");
		System.out.println("Example of entering an empty value:\n");
		customer.setEmail(null);
		System.out.println(customer.toString());
		try {
			admin.addCustomer(customer);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering a value whose characters have exceeded the given capacity:\n");
		customer.setEmail(createALongString(46));
		System.out.println(customer.toString());
		try {
			admin.addCustomer(customer);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering an already taken value (email):\n");
		customer.setEmail("yossilevi15@outlook.com");
		System.out.println(customer.toString());
		try {
			admin.addCustomer(customer);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		return customer;
	}

	/**
	 * This function receives an <code>admin</code> and a <code>company</code>
	 * parameters,<br>
	 * and attempts to delete the company from the admin's companies list using
	 * incorrect data or methods.
	 * 
	 * @param admin   - AdminFacade-typed.
	 * @param company - Company-typed.
	 * @throws InterruptedException
	 * @catch {@link CouponsSystemExceptions}
	 * @see {@link #createARandomNumber(int, int)}<br>
	 *      {@link AdminFacade#removeCompany(Company)}<br>
	 */
	private void deleteACompanyImproperly(AdminService admin, Company company) {
		System.out.println("Delete a company improperly:\n");
		int randomId = createARandomNumber(100, 500);
		System.out.println("Example of entering an incorrect id (" + randomId + "):\n");
		company.setId(randomId);
		System.out.println(company.toString());
		try {
			admin.removeCompany(company.getId());
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	/**
	 * This function receives an <code>admin</code> and a <code>company</code>
	 * parameters,<br>
	 * and attempts to update the company's attributes using incorrect data or
	 * methods.
	 * 
	 * @param admin   - AdminFacade-typed
	 * @param company - Company-typed.
	 * @throws InterruptedException
	 * @catch {@link CouponsSystemExceptions}
	 * @see {@link #cloneCompany(Company)} <br>
	 *      {@link #createARandomNumber(int, int)}<br>
	 *      {@link AdminFacade#updateCompany(Company)}<br>
	 *      {@link #createALongString(int)}
	 */
	private void updateACompanyImproperly(AdminService admin, Company company) {
		System.out.println("Update a company improperly:\n");
		Company company1 = cloneCompany(company);
		int randomId = createARandomNumber(100, 500);
		System.out.println("Example of entering an incorrect id (" + randomId + "):\n");
		company1.setId(randomId);
		System.out.println(company1.toString());
		try {
			admin.updateCompany(company1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering an empty value:\n");
		company1.setId(company.getId());
		company1.setEmail(null);
		System.out.println(company1.toString());
		try {
			admin.updateCompany(company1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering a value whose characters have exceeded the given capacity:\n");
		company1.setEmail(createALongString(46));
		System.out.println(company1.toString());
		try {
			admin.updateCompany(company1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering a different name:\n");
		company1.setEmail(company.getEmail());
		company1.setName("name" + createARandomNumber(100, 500));
		System.out.println(company1.toString());
		try {
			admin.updateCompany(company1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering an already taken value (email):\n");
		company1.setName(company.getName());
		company1.setEmail("drink123@gmail.com");
		System.out.println(company1.toString());
		try {
			admin.updateCompany(company1);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	/**
	 * This function receives an <code>admin</code> parameter, <br>
	 * and inserts an incorrect id parameter to the company's id, and catches the
	 * appropriate exception.
	 * 
	 * @param admin - AdminFacade-typed.
	 * @throws InterruptedException
	 * @ @see {@link #createARandomNumber(int, int)}
	 *   {@link AdminFacade#getCompany(int)}
	 */
	private Company getACompanyImproperly(AdminService admin) {
		System.out.println("Get a company improperly:\n");
		int randomId = createARandomNumber(200, 600);
		System.out.println("Example of entering an incorrect id (" + randomId + "):\n");
		try {
			Company company = admin.getCompany(randomId);
			return company;
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		return null;
	}

	/**
	 * This function receives an <code>admin</code> parameter and uses the admin's
	 * options to put incorrect, or unmatched, info into the dataase.
	 * 
	 * @param admin - AdminFacade-typed.
	 * @throws InterruptedException
	 * @see {@link #createNewCompany()} {@link #createARandomNumber(int, int)}
	 *      {@link #createALongString(int)} {@link AdminFacade#addCompany(Company)}
	 */
	private void addACompanyImproperly(AdminService admin) {
		Company company = createANewCompany();
		System.out.println("Add a company improperly:\n");
		System.out.println("Example of entering an empty value:\n");
		company.setEmail(null);
		System.out.println(company.toString());
		try {
			admin.addCompany(company);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering a value whose characters have exceeded the given capacity:\n");
		company.setEmail(createALongString(46));
		System.out.println(company.toString());
		try {
			admin.addCompany(company);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering an already taken value (email):\n");
		company.setEmail("drink123@gmail.com");
		System.out.println(company.toString());
		try {
			admin.addCompany(company);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		System.out.println("\nExample of entering an already taken value (name):\n");
		company.setName("drink");
		company.setEmail("email" + createARandomNumber(100, 500));
		System.out.println(company.toString());
		try {
			admin.addCompany(company);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	/**
	 * This function receives a <code>company</code> parameter,<br>
	 * and instantiates a new coupon belonging to this company.
	 * 
	 * @param company - CompanyFacade-typed.
	 * @return a Coupon-typed object belonging to the company received in the
	 *         function
	 * @see {@link #createARandomNumber(int, int)}
	 */
	private Coupon createNewCoupon(CompanyService company) {
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		startDate.add(Calendar.MONTH, 2);
		endDate.add(Calendar.YEAR, createARandomNumber(5, 7));
		return new Coupon(company.getCompany(), categoryRepository.findById(createARandomNumber(1, 4)).get(),
				"title" + createARandomNumber(100, 500), "description" + createARandomNumber(100, 500),
				startDate.getTime(), endDate.getTime(), createARandomNumber(5, 200),
				(double) createARandomNumber(20, 1000), "image" + createARandomNumber(100, 500));
	}

	/**
	 * This function receives a <code>company</code> parameter,<br>
	 * and returns a clone of the company parameter received in the function.
	 * 
	 * @param company - Company-typed.
	 * @return a clone of the company object received.
	 */
	private Company cloneCompany(Company company) {
		return new Company(company.getId(), company.getName(), company.getEmail(), company.getPassword());
	}

	/**
	 * This function receives a <code>customer</code> parameter,<br>
	 * and returns a clone of the customer parameter received in the function.
	 * 
	 * @param customer - Customer-typed.
	 * @return a clone of the customer object received.
	 */
	private Customer cloneCustomer(Customer customer) {
		return new Customer(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(),
				customer.getPassword());
	}

	/**
	 * * This function receives a <code>coupon</code> parameter,<br>
	 * and returns a clone of the coupon parameter received in the function.
	 * 
	 * @param coupon - Coupon-typed.
	 * @return a clone of the coupon object received.
	 */
	private Coupon cloneCoupon(Coupon coupon) {
		return new Coupon(coupon.getId(), coupon.getCompany(), coupon.getCategory(), coupon.getTitle(),
				coupon.getDescription(), coupon.getStartDate(), coupon.getEndDate(), coupon.getAmount(),
				coupon.getPrice(), coupon.getImage());
	}

	/**
	 * This function receives a <code>length</code> parameter,<br>
	 * than adds dots as the number of the length parameter received in the
	 * function.
	 * 
	 * @param length - int-typed.
	 * @return a random, dots-composed string.
	 */
	private String createALongString(int length) {
		String result = "";
		for (int i = 0; i < length; i++) {
			result += ".";
		}
		return result;
	}

	/**
	 * This function receives a <code>firstNumber</code> and a
	 * <code>secondNumber</code> parameters,<br>
	 * then, it makes sure that the secondNumber is bigger than firstNumber, and
	 * returns a random number.
	 * 
	 * @param firstNumber  - int - typed.
	 * @param secondNumber - int -typed.
	 * @return a random, int-typed number.
	 */
	private int createARandomNumber(int firstNumber, int secondNumber) {
		if (firstNumber > secondNumber) {
			int temp = firstNumber;
			firstNumber = secondNumber;
			secondNumber = temp;
		}
		return (int) ((secondNumber - firstNumber) * Math.random() + firstNumber);
	}

	/**
	 * This function receives a <code>CustomerFacade</code> parameter and uses all
	 * customer options on this customer.
	 * 
	 * @param customer - Customer-typed.
	 * @ @throws InterruptedException
	 * @see {@link #purchaseACoupon(CustomerFacade)}<br>
	 *      {@link #removeCouponPurchase(CustomerFacade, Coupon)}<br>
	 *      {@link #getCouponsPurchased(CustomerFacade)}<br>
	 *      {@link #getCustomerDetails(CustomerFacade)}
	 * 
	 * 
	 */
	private void customersOptionsProperly(CustomerService customer) {
		Coupon coupon = purchaseACoupon(customer);
		System.out.println("****************************************************************");
		removeCouponPurchase(customer, coupon);
		System.out.println("****************************************************************");
		getCouponsPurchased(customer);
		System.out.println("****************************************************************");
		getCustomerDetails(customer);
		System.out.println("****************************************************************");
		System.out.println("****************************************************************");
	}

	/**
	 * This function receives a (CustomerFacade) parameter and prints the customer's
	 * details.
	 * 
	 * @param customer - CustomerFacade-typed.
	 * @ @throws InterruptedException
	 * @See {@link CustomerFacade#getCustomerDetails()}
	 */
	private void getCustomerDetails(CustomerService customer) {
		System.out.println("Get customer details:");
		System.out.println(customer.getCustomerDetails());
	}

	/**
	 * This function receives a (CustomerFacade) parameter and prints the customer's
	 * coupons purchased,<br>
	 * once by category, once by maximum price, and once without restraints.
	 * 
	 * @param customer - Customer-typed.
	 * @ @throws InterruptedException
	 * @see {@link CustomerFacade#getCustomerCoupons(Category)}<br>
	 *      {@link CustomerFacade#getCustomerCoupons(double)}<br>
	 *      {@link CustomerFacade#getCustomerCoupons()}
	 * 
	 * 
	 */
	private void getCouponsPurchased(CustomerService customer) {
		System.out.println("Get coupons purchased by category (vacation):");
		for (Coupon coupon : customer.getCustomerCoupons(categoryRepository.findById(4).get())) {
			System.out.println(coupon.toString());
		}
		System.out.println("\nGet coupons purchased by max price (100):");
		for (Coupon coupon : customer.getCustomerCoupons(100d)) {
			System.out.println(coupon.toString());
		}
		System.out.println("\nGet all coupons:");
		for (Coupon coupon : customer.getCustomerCoupons()) {
			System.out.println(coupon.toString());
		}
		System.out.println();
	}

	/**
	 * This function receives a <code>CustomerFacade</code> and a
	 * <code>Coupon</code> parameters, <br>
	 * and makes sure the Coupon gets deleted, along with its purchase history.
	 * 
	 * @param customer - Customer-typed.
	 * @param coupon   - Coupon-typed.
	 * @ @throws InterruptedException
	 * 
	 * @see {@link buisnesslogic.CustomerFacade#removeCouponPurchase(Coupon)}
	 *      {@link buisnesslogic.CustomerFacade#getOneCoupon(int)}
	 */
	private void removeCouponPurchase(CustomerService customer, Coupon coupon) {
		System.out.println("Remove coupon purchase");
		customer.removeCouponPurchase(coupon.getId());
//			coupon = customer.getOneCoupon(coupon.getId());
		System.out.println("The coupon whose purchase was removed is:\n" + coupon.toString());
	}

	/**
	 * This function receives a <code>CustomerFacade</code> parameter,<br>
	 * then purchase an arbitrary coupon as the customer.
	 * 
	 * @param customer - Customer-typed.
	 * @return
	 * @ @throws InterruptedException
	 * @see {@link buisnesslogic.CustomerFacade#getOneCoupon(int)}<br>
	 *      {@link buisnesslogic.CustomerFacade#purchaseCoupon(Coupon)}
	 */
	private Coupon purchaseACoupon(CustomerService customer) {
		System.out.println("Coupon purchase");
		Coupon coupon = customer.getOneCoupon(15);
		customer.purchaseCoupon(coupon);
		System.out.println("The coupon has been purchased is:\n" + coupon.toString());
		return coupon;
	}

	/**
	 * This function receives a <code>CompanyFacade</code> parameter and runs all
	 * the functions that Company can run.
	 * 
	 * @param company - CompanyFacade-typed.
	 * @ @throws InterruptedException
	 * @see {@link #addCoupon(CompanyFacade)}<br>
	 *      {@link #getOneCoupon(CompanyFacade, Coupon)}<br>
	 *      {@link #updateOneCoupon(CompanyFacade, Coupon)}<br>
	 *      {@link #deleteOneCoupon(CompanyFacade, Coupon)}<br>
	 *      {@link #getCoupons(CompanyFacade)}<br>
	 *      {@link #getCompanyDetails(CompanyFacade)}
	 */
	private void companysOptionsProperly(CompanyService company) {
		Coupon coupon = addCoupon(company);
		System.out.println("****************************************************************");
		getOneCoupon(company, coupon);
		System.out.println("****************************************************************");
		updateOneCoupon(company, coupon);
		System.out.println("****************************************************************");
		deleteOneCoupon(company, coupon);
		System.out.println("****************************************************************");
		getCoupons(company);
		System.out.println("****************************************************************");
		getCompanyDetails(company);
		System.out.println("****************************************************************");
		System.out.println("****************************************************************");
	}

	/**
	 * This function receives a (CompanyFacade) parameter and prints the company's
	 * details.
	 * 
	 * @param company - CompanyFacade - typed.
	 * @ @throws InterruptedException
	 * @see {@link CompanyFacade#getCompanyDetails()}
	 */
	private void getCompanyDetails(CompanyService company) {
		System.out.println("Get company details:");
		System.out.println(company.getCompanyDetails());
	}

	/**
	 * This function receives a (CompanyFacade) and a (Coupon) parameters and
	 * deletes the coupon from the received company's coupons list while also
	 * printing which coupon has been deleted.
	 * 
	 * @param company - Company-typed.
	 * @param coupon  - Coupon - typed.
	 * @ @throws InterruptedException
	 * @see {@link CompanyFacade#deleteCoupon(Coupon)}
	 */
	private void deleteOneCoupon(CompanyService company, Coupon coupon) {
		System.out.println("Delete a coupon:");
		company.deleteCoupon(coupon.getId());
		System.out.println("The coupon that was deleted:\n" + coupon.toString());
	}

	/**
	 * This function receives a <code>CompanyFacade</code> and a <code>Coupon</code>
	 * parameters and update the coupon's details in an arbitrary way.
	 * 
	 * @param company - CompanyFacade-typed.
	 * @param coupon  - Coupon-typed.
	 * @ @throws InterruptedException
	 * @see {@link CompanyFacade#updateCoupon(Coupon)}
	 * 
	 */
	private void updateOneCoupon(CompanyService company, Coupon coupon) {
		System.out.println("Update a coupon:");
		coupon.setCategory(categoryRepository.findById(createARandomNumber(1, 4)).get());
		coupon.setTitle("title" + createARandomNumber(100, 500));
		coupon.setDescription("description" + createARandomNumber(100, 500));
		coupon.setAmount(createARandomNumber(100, 500));
		coupon.setPrice((double) createARandomNumber(100, 500));
		coupon.setImage("image" + createARandomNumber(100, 500));
		company.updateCoupon(coupon);
		System.out.println("The coupon that was updeted:\n" + coupon.toString());
	}

	/**
	 * This function receives a <code>CompanyFacade</code> and a <code>Coupon</code>
	 * parameters, and prints the coupon.
	 * 
	 * @param company - CompanyFacade- typed.
	 * @param coupon  - Coupon-typed.
	 * @ @throws InterruptedException
	 * @see {@link CompanyFacade#getOneCoupon(int)}
	 */
	private void getOneCoupon(CompanyService company, Coupon coupon) {
		System.out.println("Get a coupon:");
		System.out.println(company.getOneCoupon(coupon.getId()));
	}

	/**
	 * This function receives a <code>CompanyFacade</code> parameter and prints all
	 * the coupons the company :<br>
	 * once by Category.<br>
	 * once by maximum price.<br>
	 * and once without restraints.<br>
	 * 
	 * @param company - CompanyFacade-typed.
	 * @ @throws InterruptedException
	 * @see {@link CompanyFacade#getCouponsByCategory(Category)}<br>
	 *      {@link CompanyFacade#getCouponsByPrice(double)}<br>
	 *      {@link CompanyFacade#getAllCompanyCoupons()}
	 */
	private void getCoupons(CompanyService company) {
		int randomCategoryId = createARandomNumber(1, 2);
		System.out.println("Get coupons by category (" + categoryRepository.findById(randomCategoryId) + "):");
		for (Coupon coupon : company.getCouponsByCategory(categoryRepository.findById(randomCategoryId).get())) {
			System.out.println(coupon.toString());
		}
		double randomNum = createARandomNumber(100, 500);
		System.out.println("\nGet coupons by max price (" + randomNum + "):");
		for (Coupon coupon : company.getCouponsByPrice(randomNum)) {
			System.out.println(coupon.toString());
		}
		System.out.println("\nGet all coupons:");
		for (Coupon coupon : company.getAllCompanyCoupons()) {
			System.out.println(coupon.toString());
		}
		System.out.println();
	}

	/**
	 * This function receives a company parameter and instantiates a new coupon with
	 * pre-set, arbitrary values,<br>
	 * then it returns the coupon.
	 * 
	 * @param company - Company-typed.
	 * @return
	 * @ @throws InterruptedException
	 * @see {@link CompanyFacade#addACoupon(Coupon)}
	 */
	private Coupon addCoupon(CompanyService company) {
		System.out.println("Add a coupon:");
		Coupon coupon = createNewCoupon(company);
		company.addACoupon(coupon);
		System.out.println("The coupon that was added:\n" + coupon.toString());
		return coupon;
	}

	/**
	 * This function receives an <code>AdminFacade </code> parameter and runs all
	 * the functions that administrator can run.
	 * 
	 * 
	 * @param admin - AdminFacade-typed.
	 * @ @throws InterruptedException
	 * 
	 * @see {@link #addACompany(AdminFacade)}<br>
	 *      {@link #getACompany(AdminFacade, Company)}<br>
	 *      {@link #updateACompany(AdminFacade, Company)}<br>
	 *      {@link #deleteACompany(AdminFacade, Company)}<br>
	 *      {@link #getAllCompanies(AdminFacade)}<br>
	 *      {@link #addACustomer(AdminFacade)}<br>
	 *      {@link #getACustomer(AdminFacade, Customer)}<br>
	 *      {@link #updateACustomer(AdminFacade, Customer)}<br>
	 *      {@link #deleteACustomer(AdminFacade, Customer)}<br>
	 *      {@link #getAllCustomers(AdminFacade)}
	 */
	private void adminsOptionsProperly(AdminService admin) {
		System.out.println();
		Company company = addACompany(admin);
		System.out.println("****************************************************************");
		getACompany(admin, company);
		System.out.println("****************************************************************");
		updateACompany(admin, company);
		System.out.println("****************************************************************");
		deleteACompany(admin, company);
		System.out.println("****************************************************************");
		getAllCompanies(admin);
		System.out.println("****************************************************************");
		Customer customer = addACustomer(admin);
		System.out.println("****************************************************************");
		getACustomer(admin, customer);
		System.out.println("****************************************************************");
		updateACustomer(admin, customer);
		System.out.println("****************************************************************");
		deleteACustomer(admin, customer);
		System.out.println("****************************************************************");
		getAllCustomers(admin);
		System.out.println("****************************************************************");
		System.out.println("****************************************************************");

	}

	/**
	 * This function receives an <code>AdminFacade</code> parameter and a
	 * <code>Customer</code> parameters, <br>
	 * makes sure the received customer gets removed, and then prints it.
	 * 
	 * @param admin    - AdminFacade-typed.
	 * @param customer - Customer-typed.
	 * @ @throws InterruptedException
	 * @see {@link AdminFacade#removeCustomer(Customer)}
	 */
	private void deleteACustomer(AdminService admin, Customer customer) {
		System.out.println("Delete a customer:");
		admin.removeCustomer(customer.getId());
		System.out.println("The customer that was deleted:\n" + customer.toString());
	}

	/**
	 * This function receives an <code>AdminFacade</code> and a
	 * <code>Customer</code> parameters and sets new, arbitrary values to the
	 * received customer parameter , then prints it.
	 *
	 * @param admin    - Admin-typed.
	 * @param customer - Customer-typed.
	 * @ @throws InterruptedException
	 * @see {@link AdminFacade#updateCustomer(Customer)}
	 */
	private void updateACustomer(AdminService admin, Customer customer) {
		System.out.println("Update a customer:");
		customer.setFirstName("firstName" + createARandomNumber(100, 500));
		customer.setLastName("lastName" + createARandomNumber(100, 500));
		customer.setEmail("companyEmail" + createARandomNumber(100, 500));
		customer.setPassword("password" + createARandomNumber(100, 500));
		admin.updateCustomer(customer);
		System.out.println("The company that was updeted:\n" + customer.toString());
	}

	/**
	 * this function receives an <code>AdminFacade</code> and a <code>Company</code>
	 * parameters,<br>
	 * makes sure the company gets deleted, and prints it.
	 * 
	 * @param admin   - AdminFacade-typed.
	 * @param company - Company-typed.
	 * @ @throws InterruptedException
	 * @see {@link AdminFacade#removeCompany(Company)}
	 */
	private void deleteACompany(AdminService admin, Company company) {
		System.out.println("Delete a company:");
		admin.removeCompany(company.getId());
		System.out.println("The company that was deleted:\n" + company.toString());
	}

	/**
	 * This function receives a <code>AdminFacade</code> and a <code>Company</code>
	 * parameters,<br>
	 * then it sets their email and password values to semi-random values, and
	 * prints it.
	 * 
	 * @param admin   - AdminFacade-typed.
	 * @param company -Company-typed.
	 * @ @throws InterruptedException
	 * @see {@link AdminFacade#updateCustomer(Company)}
	 */
	private void updateACompany(AdminService admin, Company company) {
		System.out.println("Update a company:");
		company.setEmail("companyEmail" + createARandomNumber(100, 500));
		company.setPassword("password" + createARandomNumber(100, 500));
		admin.updateCompany(company);
		System.out.println("The company that was updeted:\n" + company.toString());
	}

	/**
	 * This function receives an <code>AdminFacade</code> parameter and prints all
	 * the customer objects that it finds in the Database.
	 * 
	 * @param admin - AdminFacade-typed.
	 * @ @throws InterruptedException
	 * @see {@link AdminFacade#getAllCustomers()}
	 */
	private void getAllCustomers(AdminService admin) {
		System.out.println("Get all customers:");
		for (Customer customer : admin.getAllCustomers()) {
			System.out.println(customer.toString());
		}
	}

	/**
	 * This function receives an <code>AdminFacade</code> and a
	 * <code>Customer</code> parameters and prints the customer.
	 * 
	 * @param admin-   AdminFacade-typed.
	 * @param customer - CustomerFacade-typed.
	 * @ @throws InterruptedException
	 * @see {@link AdminFacade#getCustomer(int)}
	 */
	private void getACustomer(AdminService admin, Customer customer) {
		System.out.println("Get a customer:");
		System.out.println(admin.getCustomer(customer.getId()).toString());
	}

	/**
	 * This function receives an <code>AdminFacade</code> parameter and adds a new
	 * Customer object to the system.
	 * 
	 * @param admin - AdminFacade-typed.
	 * @return The Customer-typed object that has been added to the receievd admin's
	 *         customers list.
	 * @ @throws InterruptedException
	 * @see {@link AdminFacade#addCustomer(Customer)}
	 */
	private Customer addACustomer(AdminService admin) {
		System.out.println("Add a customer:");
		Customer customer = createANewCustomer();
		admin.addCustomer(customer);
		System.out.println("The customer that was added:\n" + customer.toString());
		return customer;
	}

	/**
	 * This function receives an <code>AdminFacade</code> parameter and prints all
	 * the companies objects that it finds in the Database.
	 * 
	 * @param admin - AdminFacade-typed.
	 * @ @throws InterruptedException
	 * @see {@link AdminFacade#getAllCompanies()}
	 */
	private void getAllCompanies(AdminService admin) {
		System.out.println("Get all companies:");
		for (Company company : admin.getAllCompanies()) {
			System.out.println(company.toString());
		}
	}

	/**
	 * This function receives an <code>AdminFacade</code> and a <code>Company</code>
	 * parameters and prints a Company object from the Database.
	 * 
	 * @param admin   - AdminFacade-typed.
	 * @param company - Company-typed.
	 * @ @throws InterruptedException
	 * @see {@link AdminFacade#getCompany(int)}
	 */
	private void getACompany(AdminService admin, Company company) {
		System.out.println("Get a company:");
		System.out.println(admin.getCompany(company.getId()).toString());
	}

	/**
	 * This function receives an <code>AdminFacade</code> parameter, <br>
	 * instantiates a new Company object with arbitrary values and adds it to the
	 * Database.
	 * 
	 * @param admin AdminFacade-typed.
	 * @return The company who has been added to the Database.
	 * @ @throws InterruptedException
	 * @see {@link AdminFacade#addCompany(Company) }
	 */
	private Company addACompany(AdminService admin) {
		System.out.println("Add a company:");
		Company company = createANewCompany();
		admin.addCompany(company);
		System.out.println("The company that was added:\n" + company.toString());
		return company;
	}

	private Company createANewCompany() {
		return new Company("name" + createARandomNumber(100, 500), "email" + createARandomNumber(100, 500),
				"password" + createARandomNumber(100, 500));
	}

	private Customer createANewCustomer() {
		return new Customer("firstName" + createARandomNumber(100, 500), "lastName" + createARandomNumber(100, 500),
				"email" + createARandomNumber(100, 500), "password" + createARandomNumber(100, 500));
	}
}
