package buisnesslogic;

import java.sql.SQLException;

import annotations.NotNull;
import d_a_o.CompaniesDAO;
import d_a_o.CompaniesDBDAO;
import d_a_o.CouponsDAO;
import d_a_o.CouponsDBDAO;
import d_a_o.CustomersDAO;
import d_a_o.CustomersDBDAO;
import exceptions.CouponsSystemExceptions;

public abstract class ClientFacade {
	protected CompaniesDAO companiesDAO;
	protected CustomersDAO customersDAO;
	protected CouponsDAO couponsDAO;

	public ClientFacade() throws SQLException {
		this.companiesDAO = new CompaniesDBDAO();
		this.customersDAO = new CustomersDBDAO();
		this.couponsDAO = new CouponsDBDAO();
	}

	/**
	 * This function receives an <code>email</code> and a <code>password</code> parameters,<br>
	 * and checks if the parameters inserted, match.
	 * 
	 * @param email    - String-typed.
	 * @param password - String-typed.
	 * @return true if the parameters match, false if they don't.
	 * @throws InterruptedException 
	 * @see this function has multiple overrides as follows  :  
	 * {@link AdminFacade#login(String, String)}
	 * {@link CompanyFacade#login(String, String)}
	 * {@link CustomerFacade#login(String, String)}
	 */
	public abstract boolean login(String email, String password) throws SQLException, InterruptedException;
}
