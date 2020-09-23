package com.example.i_o_spring_project;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.model.Category;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.model.Customer;
import com.example.i_o_spring_project.repository.CategoryRepository;
import com.example.i_o_spring_project.service.AdminService;
import com.example.i_o_spring_project.service.ClientService;
import com.example.i_o_spring_project.service.ClientType;
import com.example.i_o_spring_project.service.CompanyService;
import com.example.i_o_spring_project.service.CustomerService;
import com.example.i_o_spring_project.service.LoginManager;

@Component
public class InteractiveQuestionnaire {

	@Autowired
	private ConfigurableApplicationContext applicationContext;

	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * this function instantiates a LoginManager-typed object, than it loops in a
	 * way that if a client has typed a number between 1-3 the program will continue
	 * running, than it divides the client's choice to deteremine whether its a
	 * company, a client, or the admin, afterwards - it allows the chosen client's
	 * type to access its' personal client-typed options, in case that the
	 * information that he has entered is correct.
	 * 
	 */
	public void testAll() {
//			CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();
//			Thread thread = new Thread(dailyJob, "daily job");
//			thread.start();
		LoginManager loginManager = applicationContext.getBean(LoginManager.class);
		Scanner reader = new Scanner(System.in);
		int clientSelection = 1;
		while ((clientSelection > 0 && clientSelection < 4)) {
			System.out.println("Would you like to connect as:\n1. admin\n2. company\n3. customer");
			clientSelection = reader.nextInt();
			switch (clientSelection) {
			case 1:
				AdminService admin = (AdminService) signIn(loginManager, ClientType.ADMINISTRATOR);
				if (admin != null) {
					getAdminsOptions(admin);
				}
				break;
			case 2:
				CompanyService company = (CompanyService) signIn(loginManager, ClientType.COMPANY);
				if (company != null) {
					getCompanysOptions(company);
				}
				break;
			case 3:
				CustomerService customer = (CustomerService) signIn(loginManager, ClientType.CUSTOMER);
				if (customer != null) {
					getCustomersOptions(customer);
				}
				break;
			default:
				System.out.println("No choice was made");
				break;
			}
		}
		System.out.println("Have a nice day :)");
//			dailyJob.stop();

	}

	private void getCustomersOptions(CustomerService customer) {
		Scanner reader = new Scanner(System.in);
		int customerSelection = 1;
		while (customerSelection > 0 && customerSelection < 5) {
			System.out.println(
					"\nChoose one of the four options below:\n1. Buy a coupon\n2. Show the coupons purchased\n3. See customer details\n4. update customer detaild");
			customerSelection = reader.nextInt();
			switch (customerSelection) {
			case 1:
				buyACoupon(customer);
				break;
			case 2:
				ShowTheCouponsPurchased(customer);
				break;
			case 3:
				SeeCustomerDetails(customer);
				break;
			case 4:
				updateCustomerDetails(customer);
				break;
			default:
				System.out.println("No choice was made");
				break;
			}
		}
	}

	private void updateCustomerDetails(CustomerService customerFacade) {
		Scanner reader = new Scanner(System.in);
		try {
			Customer customer = customerFacade.getCustomerDetails();
			System.out.println(
					"Choose one of the four options below you would like to update:\n1. name\n2. Email\n3. Password\n4. All of them");
			int choice = reader.nextInt();
			switch (choice) {
			case 1:
				updateCustomerName(customer);
				break;
			case 2:
				updateCustomerEmail(customer);
				break;
			case 3:
				updateCustomerPassword(customer);
				break;
			case 4:
				updateCustomerName(customer);
				updateCustomerEmail(customer);
				updateCustomerPassword(customer);
				break;
			default:
				System.out.println("No choice was made");
				break;
			}
			if (0 < choice && choice < 5) {
				customerFacade.UpdateDetails(customer);
			}
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}

	}

	private void SeeCustomerDetails(CustomerService customer) {
		try {
			System.out.println(customer.getCustomerDetails().toString());
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	private void ShowTheCouponsPurchased(CustomerService customer) {
		Scanner reader = new Scanner(System.in);
		System.out.println(
				"Choose one of the three options below:\n1. By category\n2. By maximum price\n3. Show all coupons");
		int answer = reader.nextInt();
		boolean hasCoupon = false;
		switch (answer) {
		case 1:
			showCouponsPurchasedByCategory(customer);
			break;
		case 2:
			showCouponsPurchasedByMaxPrice(customer);
			break;
		case 3:
			hasCoupon = showAllCouponsPurchased(customer);
			break;
		default:
			System.out.println("No choice was made");
			break;

		}
		if (hasCoupon) {
			removeCouponPurchase(customer);
		}
	}

	private void removeCouponPurchase(CustomerService customer) {
		Scanner reader = new Scanner(System.in);
		System.out.println("Would you like to remove a coupon purchase?\n1. Yes\n2. No");
		int customerSelection = reader.nextInt();
		switch (customerSelection) {
		case 1:
			System.out.print("Enter the coupon's id you would like to remove: ");
			int id = reader.nextInt();
			try {
				Coupon coupon = customer.getOneCoupon(id);
				System.out.println(coupon.toString());
				System.out.println("Would you like to remove this coupon purchase?\n1. Yes\n2. No");
				int answer = reader.nextInt();
				switch (answer) {
				case 1:
					customer.removeCouponPurchase(id);
					break;
				case 2:
					System.out.println("As you wish");
					break;
				default:
					System.out.println("No choice was made");
					break;
				}
			} catch (CouponsSystemExceptions couponException) {
				System.out.println(couponException.toString());
			}
			break;
		case 2:
			System.out.println("As you wish");
			break;
		default:
			System.out.println("No choice was made");
			break;
		}
	}

	private boolean showAllCouponsPurchased(CustomerService customer) {
		if (!customer.getCustomerCoupons().isEmpty()) {
			for (Coupon coupon : customer.getCustomerCoupons()) {
				System.out.println(coupon.toString());
			}
			return true;
		}
		return false;
	}

	private void showCouponsPurchasedByMaxPrice(CustomerService customer) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter a maximum price:");
		double maxPrice = reader.nextDouble();
		for (Coupon coupon : customer.getCustomerCoupons(maxPrice)) {
			System.out.println(coupon.toString());
		}
	}

	private void showCouponsPurchasedByCategory(CustomerService customer) {
		for (Coupon coupon : customer.getCustomerCoupons(setCategory())) {
			System.out.println(coupon.toString());
		}
	}

	private void buyACoupon(CustomerService customer) {
		Scanner reader = new Scanner(System.in);
		try {
			for (Coupon coupon : customer.getAllCoupons()) {
				System.out.println(coupon.toString());
			}
			System.out.print("\nEnter the coupon id you want to buy: ");
			int id = reader.nextInt();
			Coupon coupon = customer.getOneCoupon(id);
			System.out.println(coupon.toString());
			System.out.println("\nDo you want to buy this coupon?\n1. Yes\n2. No");
			int customerSelection = reader.nextInt();
			switch (customerSelection) {
			case 1:
				customer.purchaseCoupon(coupon);
				break;
			case 2:
				System.out.println("As you wish");
				break;
			default:
				System.out.println("No choice was made");
				break;
			}
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	/**
	 * this function declares a scanner, than it loops in a way that if a client has
	 * typed a number between 1-4 the program will continue running, after the
	 * accessing company had chosen an option it sends the company object to its'
	 * corresponding function, in case an invalid entry has been made, it prints the
	 * message "no choice has been made"
	 * 
	 * @param company, CompanyFacade-typed.
	 * @throws SQLException * @throws InterruptedException
	 * @see {@link #addCoupon(CompanyFacade)}<br>
	 *      {@link #getOneCoupon(CompanyFacade)}<br>
	 *      {@link #getCoupons(CompanyFacade)}<br>
	 *      {@link #getCompanyDetails()}
	 */
	private void getCompanysOptions(CompanyService company) {
		Scanner reader = new Scanner(System.in);
		int companySelection = 1;
		while (companySelection > 0 && companySelection < 5) {
			System.out.println(
					"\nChoose one of the five options below:\n1. Add coupon\n2. Get a coupon\n3. Show coupons\n4. See company details\n5. update company detaild");
			companySelection = reader.nextInt();
			switch (companySelection) {
			case 1:
				addCoupon(company);
				break;
			case 2:
				getOneCoupon(company);
				break;
			case 3:
				getCoupons(company);
				break;
			case 4:
				getCompanyDetails(company);
				break;
			case 5:
				updateCompanyDetails(company);
				break;
			default:
				System.out.println("No choice was made");
				break;
			}
		}
	}

	private void updateCompanyDetails(CompanyService companyService) {
		Scanner reader = new Scanner(System.in);
		try {
			Company company = companyService.getCompanyDetails();
			System.out.println(
					"Choose one of the three options below you would like to update in the company:\n1. Email\n2. Password\n3. Both of them");
			int choice = reader.nextInt();
			switch (choice) {
			case 1:
				updateCompanyEmail(company);
				break;
			case 2:
				updateCompanyPassword(company);
				break;
			case 3:
				updateCompanyEmail(company);
				updateCompanyPassword(company);
				break;
			default:
				System.out.println("No choice was made");
				break;
			}
			if (0 < choice && choice < 4) {
				companyService.updateDetails(company);
			}
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	/**
	 * this function receives a CompanyFacade-typed object, then declares a new
	 * scanner, and then get the wanted parameters out of the company coupons' list.
	 * 
	 * @param company
	 * @throws SQLException         it uses the functions:
	 *                              {@link #getCouponsByCategory(CompanyFacade)}
	 * @throws InterruptedException
	 */
	private void getCoupons(CompanyService company) {
		Scanner reader = new Scanner(System.in);
		System.out.println(
				"Choose one of the three options below:\n1. By category\n2. By maximum price\n3. Show all coupons");
		int answer = reader.nextInt();
		switch (answer) {
		case 1:
			getCouponsByCategory(company);
			break;
		case 2:
			getCouponsByMaxPrice(company);
			break;
		case 3:
			getAllCoupons(company);
			break;
		default:
			System.out.println("No choice was made");
			break;
		}
	}

	private void getAllCoupons(CompanyService company) {
		for (Coupon coupon : company.getAllCompanyCoupons()) {
			System.out.println(coupon.toString());
		}
	}

	private void getCouponsByMaxPrice(CompanyService company) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter a maximum price:");
		double maxPrice = reader.nextDouble();
		for (Coupon coupon : company.getCouponsByPrice(maxPrice)) {
			System.out.println(coupon.toString());
		}
	}

	private void getCouponsByCategory(CompanyService company) {
		for (Coupon coupon : company.getCouponsByCategory(setCategory())) {
			System.out.println(coupon.toString());
		}
	}

	private void getCompanyDetails(CompanyService company) {
		try {
			System.out.println(company.getCompanyDetails().toString());
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	private void getOneCoupon(CompanyService company) {
		Scanner reader = new Scanner(System.in);
		Coupon coupon = null;
		System.out.println("Choose one of the options below to get a coupon:\n1. By id\n2. By title");
		int answer = reader.nextInt();
		switch (answer) {
		case 1:
			coupon = getCouponById(company);
			break;
		case 2:
			coupon = getCouponByTitle(company);
			break;
		default:
			System.out.println("No choice was made");
			break;
		}
		if (coupon != null) {
			couponChanges(company, coupon);
		}
	}

	private void couponChanges(CompanyService company, Coupon coupon) {
		Scanner reader = new Scanner(System.in);
		int answer;
		System.out.println(coupon.toString());
		System.out.println("\nWhat would you like to do with this coupon?\n1. Update the coupon\n2. Delete the coupon");
		answer = reader.nextInt();
		switch (answer) {
		case 1:
			updateACoupon(company, coupon);
			break;
		case 2:
			deleteACoupon(company, coupon.getId());
			break;
		default:
			System.out.println("No choice was made");
			break;
		}
	}

	private Coupon getCouponByTitle(CompanyService company) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter the coupon's title: ");
		String title = reader.next();
		try {
			return company.getOneCoupon(title);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		return null;
	}

	private Coupon getCouponById(CompanyService company) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter the coupon's id: ");
		int id = reader.nextInt();
		try {
			return company.getOneCoupon(id);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		return null;
	}

	private void deleteACoupon(CompanyService company, int couponId) {
		try {
			company.deleteCoupon(couponId);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	private void updateACoupon(CompanyService company, Coupon coupon) {
		Scanner reader = new Scanner(System.in);
		System.out.println(
				"Choose one of the eight options below you would like to update in the coupon:\n1. Category\n2. Title\n3. Description\n4. Duration of a coupon's life\n5. amount\n6. price\n7. The coupon image link\n8. All of them");
		int choice = reader.nextInt();
		switch (choice) {
		case 1:
			updateCouponCategory(company, coupon);
			break;
		case 2:
			updateCouponTitle(company, coupon);
			break;
		case 3:
			updateCouponDescription(company, coupon);
			break;
		case 4:
			updateDurationOfCouponLife(company, coupon);
			break;
		case 5:
			updateCouponAmount(company, coupon);
			break;
		case 6:
			updateCouponPrice(company, coupon);
			break;
		case 7:
			updateCouponImageLink(company, coupon);
			break;
		case 8:
			updateCouponCategory(company, coupon);
			updateCouponTitle(company, coupon);
			updateCouponDescription(company, coupon);
			updateDurationOfCouponLife(company, coupon);
			updateCouponAmount(company, coupon);
			updateCouponPrice(company, coupon);
			updateCouponImageLink(company, coupon);
			break;
		default:
			System.out.println("No choice was made");
			break;
		}
		if (0 < choice && choice < 9) {
			try {
				company.updateCoupon(coupon);
			} catch (CouponsSystemExceptions couponException) {
				System.out.println(couponException.toString());
			}
		}
	}

	private void updateCouponImageLink(CompanyService company, Coupon coupon) {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter a coupon image link: ");
		String image = reader.next();
		coupon.setImage(image);
	}

	private void updateCouponPrice(CompanyService company, Coupon coupon) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter a coupon price: ");
		double price = reader.nextDouble();
		coupon.setPrice(price);
	}

	private void updateCouponAmount(CompanyService company, Coupon coupon) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter the coupon amount: ");
		int amount = reader.nextInt();
		coupon.setAmount(amount);
	}

	private void updateDurationOfCouponLife(CompanyService company, Coupon coupon) {
		Scanner reader = new Scanner(System.in);
		System.out.println(
				"Choose one of the three options below that you would like to update:\n1. Start date\n2. Enter expiry date\n3. Both of them");
		int choice = reader.nextInt();
		switch (choice) {
		case 1:
			updateCouponStartDate(company, coupon);
			break;
		case 2:
			updateCouponExpiryDate(company, coupon);
			break;
		case 3:
			updateCouponStartDate(company, coupon);
			updateCouponExpiryDate(company, coupon);
			break;
		default:
			System.out.println("No choice was made");
			break;
		}
	}

	private void updateCouponExpiryDate(CompanyService company, Coupon coupon) {
		System.out.println("Update expiry date: ");
		coupon.setEndDate(setTime().getTime());
	}

	private void updateCouponStartDate(CompanyService company, Coupon coupon) {
		System.out.println("Update start date: ");
		coupon.setStartDate(setTime().getTime());
	}

	private void updateCouponDescription(CompanyService company, Coupon coupon) {
		System.out.println("Enter a Coupon's description:");
		coupon.setDescription(setText());
	}

	private void updateCouponTitle(CompanyService company, Coupon coupon) {
		System.out.print("Enter a Coupon's title:");
		coupon.setTitle(setText());
	}

	private String setText() {
		Scanner reader = new Scanner(System.in);
		String text = reader.nextLine();
		return text;
	}

	private void updateCouponCategory(CompanyService company, Coupon coupon) {
		Category category = setCategory();
		coupon.setCategory(category);
	}

	private void addCoupon(CompanyService company) {
		Scanner reader = new Scanner(System.in);
		Category category = setCategory();
		if (category != null) {
			System.out.print("Enter a Coupon's title: ");
			String title = setText();
			System.out.println("Enter a Coupon's description:");
			String description = setText();
			System.out.println("Enter a start date: ");
			Calendar startDate = setTime();
			System.out.println("Enter expiry date: ");
			Calendar endDate = setTime();
			System.out.print("Enter the coupon amount: ");
			int amount = reader.nextInt();
			System.out.print("Enter a coupon price: ");
			double price = reader.nextDouble();
			System.out.println("Enter a coupon image link: ");
			String image = reader.next();
			try {
				company.addACoupon(new Coupon(company.getCompany(), category, title, description, startDate.getTime(),
						endDate.getTime(), amount, price, image));
			} catch (CouponsSystemExceptions couponException) {
				System.out.println(couponException.toString());
			}
		}
	}

	private Calendar setTime() {
		Scanner reader = new Scanner(System.in);
		int num;
		Calendar time = Calendar.getInstance();
		System.out.print("Enter day: ");
		num = reader.nextInt();
		time.set(Calendar.DAY_OF_MONTH, num);
		System.out.print("Enter month: ");
		num = reader.nextInt();
		time.set(Calendar.MONTH, num);
		System.out.print("Enter year: ");
		num = reader.nextInt();
		time.set(Calendar.YEAR, num);
		return time;
	}

	private Category setCategory() {
		Scanner reader = new Scanner(System.in);
		System.out.println("Select coupon category:\n1. Food\n2. Electrictiy\n3. Restaurant\n4. Vacation");
		int companySelection = reader.nextInt();
		Optional<Category> category = categoryRepository.findById(companySelection);
		if (category.isPresent()) {
			return category.get();
		}
		System.out.println("No choice was made");
		return null;
	}

	private void getAdminsOptions(AdminService admin) {
		Scanner reader = new Scanner(System.in);
		int adminSelection = 1;
		while (adminSelection > 0 && adminSelection < 7) {
			System.out.println(
					"\nChoose one of the six options below:\n1. Add a company\n2. Get a company\n3. Get all companies\n4. Add a customer\n5. Get a customer\n6. Get all customers");
			adminSelection = reader.nextInt();
			switch (adminSelection) {
			case 1:
				addACompany(admin);
				break;
			case 2:
				getACompany(admin);
				break;
			case 3:
				getAllCompanies(admin);
				break;
			case 4:
				addACustomer(admin);
				break;
			case 5:
				getACustomer(admin);
				break;
			case 6:
				getAllCustomers(admin);
				break;
			default:
				System.out.println("No choice was made");
				break;
			}
		}
	}

	private void getAllCustomers(AdminService admin) {
		try {
			for (Customer customer : admin.getAllCustomers()) {
				System.out.println(customer.toString());
			}
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	private void getAllCompanies(AdminService admin) {
		for (Company company : admin.getAllCompanies()) {
			System.out.println(company.toString());
		}
	}

	private void getACustomer(AdminService admin) {
		Customer customer = null;
		customer = getCustomerById(admin);
		if (customer != null) {
			customerChanges(admin, customer);
		}
	}

	private void customerChanges(AdminService admin, Customer customer) {
		Scanner reader = new Scanner(System.in);
		System.out.println(customer.toString());
		System.out.println(
				"\nWhat would you like to do with this customer?\n1. Update the customer\n2. Delete the customer");
		int answer = reader.nextInt();
		switch (answer) {
		case 1:
			updateACustomer(admin, customer);
			break;
		case 2:
			deleteACustomer(admin, customer.getId());
			break;
		default:
			System.out.println("No choice has been made");
			break;
		}
	}

	private Customer getCustomerById(AdminService admin) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter a customer id: ");
		int id = reader.nextInt();
		try {
			return admin.getCustomer(id);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		return null;
	}

	private void deleteACustomer(AdminService admin, int customerId) {
		try {
			admin.removeCustomer(customerId);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	private void updateACustomer(AdminService admin, Customer customer) {
		Scanner reader = new Scanner(System.in);
		System.out.println(
				"Choose one of the four options below you would like to update in the customer:\n1. name\n2. Email\n3. Password\n4. All of them");
		int choice = reader.nextInt();
		switch (choice) {
		case 1:
			updateCustomerName(customer);
			break;
		case 2:
			updateCustomerEmail(customer);
			break;
		case 3:
			updateCustomerPassword(customer);
			break;
		case 4:
			updateCustomerName(customer);
			updateCustomerEmail(customer);
			updateCustomerPassword(customer);
			break;
		default:
			System.out.println("No choice was made");
			break;
		}
		if (0 < choice && choice < 5) {
			try {
				admin.updateCustomer(customer);
			} catch (CouponsSystemExceptions couponException) {
				System.out.println(couponException.toString());
			}
		}

	}

	private void updateCustomerName(Customer customer) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter a first name: ");
		String firstName = reader.nextLine();
		customer.setFirstName(firstName);
		System.out.print("Enter a last name: ");
		String lastName = reader.nextLine();
		customer.setLastName(lastName);
	}

	private void updateCustomerEmail(Customer customer) {
		customer.setEmail(setEmail());
	}

	private void updateCustomerPassword(Customer customer) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter a new password: ");
		String pass = reader.next();
		customer.setPassword(pass);
	}

	private void getACompany(AdminService admin) {
		Scanner reader = new Scanner(System.in);
		Company company = null;
		System.out.println("Choose one of the options below to get a company:\n1. By id\n2. By name");
		int answer = reader.nextInt();
		switch (answer) {
		case 1:
			company = getCompanyById(admin);
			break;
		case 2:
			company = getCompanyByName(admin);
			break;
		default:
			System.out.println("No choice was made");
			break;
		}
		if (company != null) {
			changesInCompany(admin, company);
		}

	}

	private void changesInCompany(AdminService admin, Company company) {
		Scanner reader = new Scanner(System.in);
		int answer;
		System.out.println(company.toString());
		System.out.println(
				"\nWhat would you like to do with this company?\n1. Update the company\n2. Delete the company");
		answer = reader.nextInt();
		switch (answer) {
		case 1:
			updateACompany(admin, company);
			break;
		case 2:
			deleteACompany(admin, company.getId());
			break;
		default:
			System.out.println("No choice was made");
			break;
		}
	}

	private Company getCompanyByName(AdminService admin) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter the company name: ");
		String name = reader.next();
		try {
			return admin.getCompany(name);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		return null;
	}

	private Company getCompanyById(AdminService admin) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter the company id: ");
		int id = reader.nextInt();
		try {
			return admin.getCompany(id);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		return null;
	}

	private void deleteACompany(AdminService admin, int companyId) {
		try {
			admin.removeCompany(companyId);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	private void addACompany(AdminService admin) {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter a Company's name:");
		String name = reader.nextLine();
		String email = setEmail();
		System.out.println("Enter a new password: ");
		String password = reader.next();
		try {
			admin.addCompany(new Company(name, email, password));
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	private ClientService signIn(LoginManager login, ClientType client) {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter an email:");
		String email = reader.next();
		System.out.println("Enter a password:");
		String password = reader.next();
		try {
			return login.login(email, password, client);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		return null;
	}

	private void addACustomer(AdminService admin) {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter a first name:");
		String firstName = reader.nextLine();
		System.out.println("Enter a last name:");
		String lastName = reader.nextLine();
		String email = setEmail();
		System.out.println("Enter a new password: ");
		String password = reader.next();
		try {
			admin.addCustomer(new Customer(firstName, lastName, email, password));
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	public void updateACompany(AdminService admin, Company company) {
		Scanner reader = new Scanner(System.in);
		System.out.println(
				"Choose one of the three options below you would like to update in the company:\n1. Email\n2. Password\n3. Both of them");
		int choice = reader.nextInt();
		switch (choice) {
		case 1:
			updateCompanyEmail(company);
			break;
		case 2:
			updateCompanyPassword(company);
			break;
		case 3:
			updateCompanyEmail(company);
			updateCompanyPassword(company);
			break;
		default:
			System.out.println("No choice was made");
		}
		if (0 < choice && choice < 4) {
			try {
				admin.updateCompany(company);
			} catch (CouponsSystemExceptions couponException) {
				System.out.println(couponException.toString());
			}
		}
	}

	private void updateCompanyPassword(Company company) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter a new password: ");
		String pass = reader.next();
		company.setPassword(pass);
	}

	private void updateCompanyEmail(Company company) {
		company.setEmail(setEmail());
	}

	public String setEmail() {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter your email address: ");
		String emailAddress = reader.next();
		String emailServiceProviders;
		System.out.println("What is your email service providers?\n1. Gmail\n2. Walla\n3. Outlook\n4. Yahoo");
		int choice = reader.nextInt();
		switch (choice) {
		case 1:
			emailServiceProviders = "gmail";
			break;
		case 2:
			emailServiceProviders = "walla";
			break;
		case 3:
			emailServiceProviders = "outlook";
			break;
		case 4:
			emailServiceProviders = "yahoo";
			break;
		default:
			System.out.println("No choice was made");
			return null;
		}
		return emailAddress + "@" + emailServiceProviders + ".com";
	}

}
