package buisnesslogic;

import java.sql.SQLException;
import java.util.List;

import d_a_o.CompaniesDAO;
import exceptions.CouponsSystemExceptions;
import exceptions.SystemExceptions;
import model.Company;
import model.Customer;

public class AdminFacade extends ClientFacade {

	private ObjectValidation objectValidation;

	public AdminFacade() throws SQLException {
		super();
		objectValidation = new ObjectValidation();
	}

	/**
	 * This function runs the received <code>email and password</code>
	 * parameters<br>
	 * against "admin@admin.com" and "admin" in a hard-coded way.
	 * 
	 * @throws CouponsSystemExceptions
	 */
	@Override
	public boolean login(String email, String password) throws SQLException {

		if (!email.equals("admin@admin.com") && !password.equals("admin")) {
			System.err.println("email and password are incorrect ");
			return false;
		}

		if (!email.equals("admin@admin.com")) {
			System.err.println("email is incorrect");
			return false;
		}
		if (!password.equals("admin")) {
			System.err.println("password is incorrect");
			return false;
		}
		return true;

	}

	/**
	 * This function receives a <code>company</code> parameter and checks if the
	 * given company's name and email already exists in the Database, and doesn't
	 * allow a creation of an already-existing name or email.
	 * 
	 * @param company - Company-typed
	 * @throws InterruptedException 
	 * @throws CouponsSystemException.
	 * @see {@link CompaniesDAO#areElementsTaken(String, String)}<br>
	 *      {@link CompaniesDAO#addCompany(Company)}
	 */
	public void addCompany(Company company) throws SQLException, CouponsSystemExceptions, InterruptedException {
		objectValidation.isTheObjectEmpty(company);
		if (objectValidation.charactersHasExceeded(company)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"characters have exceeded the attribute's given capacity");
		}
		if (companiesDAO.areElementsTaken(company.getName(), company.getEmail())) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE,
					"The name and the email are already taken");
		}
		if (companiesDAO.isTheNameTaken(company.getName())) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This name is already taken!");
		}
		if (companiesDAO.areElementsTaken(company.getEmail())) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
		}
		companiesDAO.addCompany(company);
		System.out.println("\n--This company has been added--\n");
	}

	/**
	 * This function checks the received <code>company</code> parameter's 'name' and
	 * 'email' for a match against the Database, and doesn't allow changes if the
	 * parameters doesn't match. <br>
	 * The function does'nt allow changes to the name parameter.
	 * 
	 * @param company - Company-typed.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link CompaniesDAO #isThisTheCompanysName(int, String)}<br>
	 *      {@link CompaniesDAO #isThisTheCompanysEmail(int, String)}<br>
	 *      {@link CompaniesDAO#areElementsTaken(String)}<br>
	 *      {@link CompaniesDAO#updateCompany(Company)}
	 */
	public void updateCompany(Company company) throws SQLException, CouponsSystemExceptions, InterruptedException {
		if (!companiesDAO.theCompanyExist(company.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This company does not exist");
		}
		objectValidation.isTheObjectEmpty(company);
		if (objectValidation.charactersHasExceeded(company)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"characters have exceeded the attribute's given capacity");
		}
		if (!companiesDAO.isThisTheCompanysName(company.getId(), company.getName())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"You can't change the name of the company");
		}
		if (!companiesDAO.isThisTheCompanysEmail(company.getId(), company.getEmail())) {
			if (companiesDAO.areElementsTaken(company.getEmail())) {
				throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
			}
			System.out.println("\n--This company has been updated--\n");
			companiesDAO.updateCompany(company);
		} else {
			System.out.println("\n--This company has been updated--\n");
			companiesDAO.updateCompany(company);
		}
	}

	/**
	 * This function receives a <code>company</code> parameter, and makes sure that
	 * it gets deleted, <br>
	 * while also making sure that all the company's coupons gets deleted as well.
	 * 
	 * @param company - Company-typed.
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CouponsDAO#deleteCouponsByCompany(int)}
	 *      {@link CompaniesDAO#deleteCompany(int)}
	 */
	public void removeCompany(Company company) throws SQLException, CouponsSystemExceptions, InterruptedException {

		if (!companiesDAO.theCompanyExist(company.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED, "This company does not exist");
		}
		couponsDAO.deleteCouponsByCompany(company.getId());
		companiesDAO.deleteCompany(company.getId());
		System.out.println("\n--This company has been deleted--\n");
	}

	/**
	 * This function receives a list from the getAllCompanies function, and returns
	 * it if its not null.
	 * 
	 * @return all the Company-typed objects in the Database, in a List form , or
	 *         null.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CompaniesDAO#getAllCompanies()}
	 */
	public List<Company> getAllCompanies() throws SQLException, CouponsSystemExceptions, InterruptedException {
		List<Company> companies = companiesDAO.getAllCompanies();
		if (companies != null) {
			return companies;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COMPANYS_NOT_FOUND);
	}

	/**
	 * This function receives an <code>id</code> parameter,<br>
	 * and return the company whose id parameter equals the id parameter received in
	 * the function.
	 * 
	 * @param companyId - int-typed.
	 * @return an id-specific Company-typed object.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CompaniesDAO#getOneCompany(int)}
	 */
	public Company getCompany(int companyId) throws SQLException, CouponsSystemExceptions, InterruptedException {
		Company company = companiesDAO.getOneCompany(companyId);
		if (company != null) {
			return company;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COMPANY_NOT_FOUND);
	}

	/**
	 * This function receives a <code>name</code> parameter, and returns the company
	 * whose name parameter equals the name parameter received in the function.
	 * 
	 * @param name - String typed.
	 * @return the name-specific Company-typed object.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CompaniesDAO#getOneCompany(String)}
	 */
	public Company getCompany(String name) throws SQLException, CouponsSystemExceptions, InterruptedException {
		Company company = companiesDAO.getOneCompany(name);
		if (company != null) {
			return company;
		}
		throw new CouponsSystemExceptions(SystemExceptions.COMPANY_NOT_FOUND);
	}

	/**
	 * This function receives a <code>customer</code> parameter and checks if the
	 * customer's wanted email is available, <br>
	 * and if it is, it adds that customer to the Database.
	 * 
	 * @param customer - Customer-typed.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CustomersDAO#isEmailTaken(String)}
	 *      {@link d.a.o.CustomersDAO#addCustomer(Customer)}
	 */
	public void addCustomer(Customer customer) throws SQLException, CouponsSystemExceptions, InterruptedException {
		objectValidation.isTheObjectEmpty(customer);
		if (objectValidation.charactersHasExceeded(customer)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"characters have exceeded the attribute's given capacity");
		}
		if (customersDAO.isEmailTaken(customer.getEmail())) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
		}
		customersDAO.addCustomer(customer);
		System.out.println("\n--This customer has been added--\n");
	}

	/**
	 * This function receives a <code>customer</code> parameter, checks its' email
	 * <br>
	 * against the Database and doesn't allow the customer to change their email
	 * into an email that <strong> already exists in the Database</strong>.
	 * 
	 * @param customer- Customer-typed.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * 
	 * @see {@link d.a.o.CustomersDAO#isEmailTaken(String)}
	 *      {@link d.a.o.CustomersDAO #isThisTheCustomersEmail(int, String)}
	 *      {@link d.a.o.CustomersDAO#updateCustomer(Customer)}
	 */
	public void updateCustomer(Customer customer) throws SQLException, CouponsSystemExceptions, InterruptedException {
		if (!customersDAO.theCustomerExist(customer.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"This customer does not exist");
		}
		objectValidation.isTheObjectEmpty(customer);
		if (objectValidation.charactersHasExceeded(customer)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"characters have exceeded the attribute's given capacity");
		}
		if (!customersDAO.isThisTheCustomersEmail(customer.getId(), customer.getEmail())) {
			if (customersDAO.isEmailTaken(customer.getEmail())) {
				throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE, "This email is already taken!");
			}
			customersDAO.updateCustomer(customer);
			System.out.println("\n--This customer has been updated--\n");
		} else {
			customersDAO.updateCustomer(customer);
			System.out.println("\n--This customer has been updated--\n");
		}
	}

	/**
	 * This function receives a <code>customer</code> parameter and deletes it from
	 * the Database.
	 * 
	 * @param customer-Customer-typed.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CustomersDAO #deleteACustomer(int)}
	 */
	public void removeCustomer(Customer customer) throws SQLException, CouponsSystemExceptions, InterruptedException {
		if (!customersDAO.theCustomerExist(customer.getId())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"This customer does not exist");
		}
		customersDAO.deleteACustomer(customer.getId());
		System.out.println("\n--This customer has been deleted--\n");
	}

	/**
	 * This function returns the list it receives from the function
	 * getAllCustomers() in a List form , as long as its not empty.
	 * 
	 * @return all customers that exists in the database, in List form.
	 * @throws SQLException
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException 
	 * @see {@link d.a.o.CustomersDAO#getAllCustomers()}
	 */
	public List<Customer> getAllCustomers() throws SQLException, CouponsSystemExceptions, InterruptedException {
		List<Customer> customers = customersDAO.getAllCustomers();
		if (customers != null) {
			return customers;
		}
		throw new CouponsSystemExceptions(SystemExceptions.CUSTOMERS_NOT_FOUND);

	}

	/**
	 * This function receives an <code>id</code> parameter, <br>
	 * and returns the Customer whose id parameter equals the id parameter received
	 * in the function.
	 * 
	 * @param customerId - int-typed.
	 * @return an id-specific Customer-typed object.
	 * @throws CouponsSystemExceptions
	 * @throws InterruptedException
	 * @see {@link d.a.o.CustomersDAO#getOneCustomer(int)}
	 */
	public Customer getCustomer(int customerId) throws SQLException, CouponsSystemExceptions, InterruptedException {
		Customer customer = customersDAO.getOneCustomer(customerId);
		if (customer != null) {
			return customer;
		}
		throw new CouponsSystemExceptions(SystemExceptions.CUSTOMER_NOT_FOUND);
	}

}
