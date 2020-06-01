package app;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Scanner;

import automatic.managment.CouponExpirationDailyJob;
import buisnesslogic.AdminFacade;
import buisnesslogic.ClientFacade;
import buisnesslogic.ClientType;
import buisnesslogic.CompanyFacade;
import buisnesslogic.CustomerFacade;
import buisnesslogic.LoginManager;
import d_a_o.ConnectionPool;
import exceptions.CouponsSystemExceptions;
import model.Category;
import model.Company;
import model.Coupon;
import model.Customer;

public class InteractiveQuestionnaire {
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
		try {
			ConnectionPool connectionPool = ConnectionPool.getInstance();
			CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();
			Thread thread = new Thread(dailyJob, "daily job");
			thread.start();
			LoginManager login = LoginManager.getInstance();
			Scanner reader = new Scanner(System.in);
			int clientSelection = 1;
			while ((clientSelection > 0 && clientSelection < 4)) {
				System.out.println("Would you like to connect as:\n1. admin\n2. company\n3. customer");
				clientSelection = reader.nextInt();
				switch (clientSelection) {
				case 1:
					AdminFacade admin = (AdminFacade) signIn(login, ClientType.ADMINISTRATOR);
					if (admin != null) {
						getAdminsOptions(admin);
					}
					break;
				case 2:
					CompanyFacade company = (CompanyFacade) signIn(login, ClientType.COMPANY);
					if (company != null) {
						getCompanysOptions(company);
					}
					break;
				case 3:
					CustomerFacade customer = (CustomerFacade) signIn(login, ClientType.CUSTOMER);
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
			dailyJob.stop();
			connectionPool.closeAllConnections();
		} catch (SQLException | InterruptedException e) {
			System.out.println("something wrong in the database..");
		}
	}

	private static void getCustomersOptions(CustomerFacade customer) throws SQLException, InterruptedException {
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
				updateCustomerDetaild(customer);
				break;
			default:
				System.out.println("No choice was made");
				break;
			}

		}
	}

	private static void updateCustomerDetaild(CustomerFacade customerFacade) throws SQLException, InterruptedException {
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

	private static void SeeCustomerDetails(CustomerFacade customer) throws SQLException, InterruptedException {
		try {
			System.out.println(customer.getCustomerDetails().toString());
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	private static void ShowTheCouponsPurchased(CustomerFacade customer) throws SQLException, InterruptedException {
		Scanner reader = new Scanner(System.in);
		System.out.println(
				"Choose one of the three options below:\n1. By category\n2. By maximum price\n3. Show all coupons");
		int answer = reader.nextInt();
		boolean hasCoupon = false;
		switch (answer) {
		case 1:
			hasCoupon = showCouponsPurchasedByCategory(customer);
			break;
		case 2:
			hasCoupon = showCouponsPurchasedByMaxPrice(customer);
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

	private static void removeCouponPurchase(CustomerFacade customer) throws SQLException, InterruptedException {
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
					customer.removeCouponPurchase(coupon);
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

	private static boolean showAllCouponsPurchased(CustomerFacade customer) throws SQLException, InterruptedException {
		try {
			for (Coupon coupon : customer.getCustomerCoupons()) {
				System.out.println(coupon.toString());
			}
			return true;
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		return false;
	}

	private static boolean showCouponsPurchasedByMaxPrice(CustomerFacade customer)
			throws SQLException, InterruptedException {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter a maximum price:");
		double maxPrice = reader.nextDouble();
		try {
			for (Coupon coupon : customer.getCustomerCoupons(maxPrice)) {
				System.out.println(coupon.toString());
			}
			return true;
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		return false;
	}

	private static boolean showCouponsPurchasedByCategory(CustomerFacade customer)
			throws SQLException, InterruptedException {
		try {
			for (Coupon coupon : customer.getCustomerCoupons(setCategory())) {
				System.out.println(coupon.toString());
			}
			return true;
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		return false;
	}

	private static void buyACoupon(CustomerFacade customer) throws SQLException, InterruptedException {
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
	private static void getCompanysOptions(CompanyFacade company) throws SQLException, InterruptedException {
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

	private static void updateCompanyDetails(CompanyFacade companyFacade) throws SQLException, InterruptedException {
		Scanner reader = new Scanner(System.in);
		try {
			Company company = companyFacade.getCompanyDetails();
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
				companyFacade.UpdateDetails(company);
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
	private static void getCoupons(CompanyFacade company) throws SQLException, InterruptedException {
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

	private static void getAllCoupons(CompanyFacade company) throws SQLException, InterruptedException {
		try {
			for (Coupon coupon : company.getAllCompanyCoupons()) {
				System.out.println(coupon.toString());
			}
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	private static void getCouponsByMaxPrice(CompanyFacade company) throws SQLException, InterruptedException {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter a maximum price:");
		double maxPrice = reader.nextDouble();
		try {
			for (Coupon coupon : company.getCouponsByPrice(maxPrice)) {
				System.out.println(coupon.toString());
			}
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	private static void getCouponsByCategory(CompanyFacade company) throws SQLException, InterruptedException {
		try {
			for (Coupon coupon : company.getCouponsByCategory(setCategory())) {
				System.out.println(coupon.toString());
			}
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	private static void getCompanyDetails(CompanyFacade company) throws SQLException, InterruptedException {
		try {
			System.out.println(company.getCompanyDetails().toString());
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	private static void getOneCoupon(CompanyFacade company) throws SQLException, InterruptedException {
		Scanner reader = new Scanner(System.in);
		Coupon coupon = null;
		System.out.println("Choose one of the options below to get a coupon:\n1. By id\n2. By title");
		int answer = reader.nextInt();
		switch (answer) {
		case 1:
			System.out.print("Enter the coupon's id: ");
			int id = reader.nextInt();
			try {
				coupon = company.getOneCoupon(id);
			} catch (CouponsSystemExceptions couponException) {
				System.out.println(couponException.toString());
			}
			break;
		case 2:
			System.out.print("Enter the coupon's title: ");
			String title = reader.next();
			try {
				coupon = company.getOneCoupon(title);
			} catch (CouponsSystemExceptions couponException) {
				System.out.println(couponException.toString());
			}
			break;
		default:
			System.out.println("No choice was made");
			break;
		}
		if (coupon != null) {
			System.out.println(coupon.toString());
			System.out.println(
					"\nWhat would you like to do with this coupon?\n1. Update the coupon\n2. Delete the coupon");
			answer = reader.nextInt();
			switch (answer) {
			case 1:
				updateACoupon(company, coupon);
				break;
			case 2:
				deleteACoupon(company, coupon);
				break;
			default:
				System.out.println("No choice was made");
				break;
			}
		}
	}

	private static void deleteACoupon(CompanyFacade company, Coupon coupon) throws SQLException, InterruptedException {
		try {
			company.deleteCoupon(coupon);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	private static void updateACoupon(CompanyFacade company, Coupon coupon) throws SQLException, InterruptedException {
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
		if (choice > 0 && choice < 9) {
			try {
				company.updateCoupon(coupon);
			} catch (CouponsSystemExceptions couponException) {
				System.out.println(couponException.toString());
			}
		}
	}

	private static void updateCouponImageLink(CompanyFacade company, Coupon coupon) {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter a coupon image link: ");
		String image = reader.next();
		coupon.setImage(image);
	}

	private static void updateCouponPrice(CompanyFacade company, Coupon coupon) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter a coupon price: ");
		double price = reader.nextDouble();
		coupon.setPrice(price);
	}

	private static void updateCouponAmount(CompanyFacade company, Coupon coupon) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter the coupon amount: ");
		int amount = reader.nextInt();
		coupon.setAmount(amount);
	}

	private static void updateDurationOfCouponLife(CompanyFacade company, Coupon coupon) {
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

	private static void updateCouponExpiryDate(CompanyFacade company, Coupon coupon) {
		System.out.println("Update expiry date: ");
		coupon.setEndDate(setTime());
	}

	private static void updateCouponStartDate(CompanyFacade company, Coupon coupon) {
		System.out.println("Update start date: ");
		coupon.setStartDate(setTime());
	}

	private static void updateCouponDescription(CompanyFacade company, Coupon coupon) {
		System.out.println("Enter a Coupon's description:");
		coupon.setDescription(setText());
	}

	private static void updateCouponTitle(CompanyFacade company, Coupon coupon) {
		System.out.print("Enter a Coupon's title:");
		coupon.setTitle(setText());
	}

	private static String setText() {
		Scanner reader = new Scanner(System.in);
		String text = reader.nextLine();
		return text;
	}

	private static void updateCouponCategory(CompanyFacade company, Coupon coupon) {
		Category category = setCategory();
		coupon.setCategory(category);
	}

	private static void addCoupon(CompanyFacade company) throws SQLException, InterruptedException {
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
				company.addACoupon(new Coupon(company.getCompanyId(), category, title, description, startDate, endDate,
						amount, price, image));
			} catch (CouponsSystemExceptions couponException) {
				System.out.println(couponException.toString());
			}
		}
	}

	private static Calendar setTime() {
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

	private static Category setCategory() {
		Scanner reader = new Scanner(System.in);
		System.out.println("Select coupon category:\n1. Food\n2. Electrictiy\n3. Restaurant\n4. Vacation");
		int companySelection = reader.nextInt();
		switch (companySelection) {
		case 1:
			return Category.FOOD;
		case 2:
			return Category.ELECTRICITY;
		case 3:
			return Category.RESTAURANT;
		case 4:
			return Category.VACATION;
		default:
			System.out.println("No choice was made");
			break;
		}
		return null;
	}

	private static void getAdminsOptions(AdminFacade admin) throws SQLException, InterruptedException {
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

	private static void getAllCustomers(AdminFacade admin) throws SQLException, InterruptedException {
		try {
			for (Customer customer : admin.getAllCustomers()) {
				System.out.println(customer.toString());
			}
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}

	}

	private static void getAllCompanies(AdminFacade admin) throws SQLException, InterruptedException {
		try {
			for (Company company : admin.getAllCompanies()) {
				System.out.println(company.toString());
			}
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}

	}

	private static void getACustomer(AdminFacade admin) throws SQLException, InterruptedException {
		Scanner reader = new Scanner(System.in);
		Customer customer = null;
		System.out.print("Enter a customer id: ");
		int id = reader.nextInt();
		try {
			customer = admin.getCustomer(id);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
		if (customer != null) {
			System.out.println(customer.toString());
			System.out.println(
					"\nWhat would you like to do with this customer?\n1. Update the customer\n2. Delete the customer");
			int answer = reader.nextInt();
			switch (answer) {
			case 1:
				updateACustomer(admin, customer);
				break;
			case 2:
				deleteACustomer(admin, customer);
				break;
			default:
				System.out.println("No choice has been made");
				break;
			}
		}
	}

	private static void deleteACustomer(AdminFacade admin, Customer customer)
			throws SQLException, InterruptedException {
		try {
			admin.removeCustomer(customer);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}

	}

	private static void updateACustomer(AdminFacade admin, Customer customer)
			throws SQLException, InterruptedException {
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

	private static void updateCustomerName(Customer customer) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter a first name: ");
		String firstName = reader.nextLine();
		customer.setFirstName(firstName);
		System.out.print("Enter a last name: ");
		String lastName = reader.nextLine();
		customer.setLastName(lastName);
	}

	private static void updateCustomerEmail(Customer customer) {
		String email = setEmail();
		customer.setEmail(email);
	}

	private static void updateCustomerPassword(Customer customer) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter a new password: ");
		String pass = reader.next();
		customer.setPassword(pass);

	}

	private static void getACompany(AdminFacade admin) throws SQLException, InterruptedException {
		Scanner reader = new Scanner(System.in);
		Company company = null;
		System.out.println("Choose one of the options below to get a company:\n1. By id\n2. By name");
		int answer = reader.nextInt();
		switch (answer) {
		case 1:
			System.out.print("Enter the company id: ");
			int id = reader.nextInt();
			try {
				company = admin.getCompany(id);
			} catch (CouponsSystemExceptions couponException) {
				System.out.println(couponException.toString());
			}
			break;
		case 2:
			System.out.print("Enter the company name: ");
			String name = reader.next();
			try {
				company = admin.getCompany(name);
			} catch (CouponsSystemExceptions couponException) {
				System.out.println(couponException.toString());
			}
			break;
		default:
			System.out.println("No choice was made");
			break;
		}
		if (company != null) {
			System.out.println(company.toString());
			System.out.println(
					"\nWhat would you like to do with this company?\n1. Update the company\n2. Delete the company");
			answer = reader.nextInt();
			switch (answer) {
			case 1:
				updateACompany(admin, company);
				break;
			case 2:
				deleteACompany(admin, company);
				break;
			default:
				System.out.println("No choice was made");
				break;
			}
		}

	}

	private static void deleteACompany(AdminFacade admin, Company company) throws SQLException, InterruptedException {
		try {
			admin.removeCompany(company);
		} catch (CouponsSystemExceptions couponException) {
			System.out.println(couponException.toString());
		}
	}

	private static void addACompany(AdminFacade admin) throws SQLException, InterruptedException {
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

	private ClientFacade signIn(LoginManager login, ClientType client) throws SQLException, InterruptedException {
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

	private static void addACustomer(AdminFacade admin) throws SQLException, InterruptedException {
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

	public static void updateACompany(AdminFacade admin, Company company) throws SQLException, InterruptedException {
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

	private static void updateCompanyPassword(Company company) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter a new password: ");
		String pass = reader.next();
		company.setPassword(pass);
	}

	private static void updateCompanyEmail(Company company) {
		String email = setEmail();
		company.setEmail(email);
	}

	public static String setEmail() {
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
