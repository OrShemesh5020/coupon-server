package d_a_o;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Company;

/**
 * 
 * @author o_g_i.
 * @see {@link d.a.o.ConnectionPool #getConnection() }
 *      {@link d.a.o.ConnectionPool #restoreConnection(Connection)}
 */
public interface CompaniesDAO {
	/**
	 * This function receives an <code>email</code> and <code>password</code>
	 * parameters,<br>
	 * then checks if the company exist in the <strong>Database</strong> to allow
	 * login.
	 * 
	 * @param email    - String-typed.
	 * @param password - String-typed.
	 * @return true if the company exist in the Database, false if it doesn't.
	 */
	public boolean doesCompanyExists(String email, String password) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>name</code> and <code>email</code> parameters
	 * <br>
	 * and checks the <strong>Database</strong> to determine if both of the
	 * parameters is currently unavailable.
	 * 
	 * @param name  - String-typed
	 * @param email - String-typed.
	 * @return true if both of the parameters is unavailable, false if they are both
	 *         available.
	 */
	public boolean areElementsTaken(String name, String email) throws SQLException, InterruptedException;

	/**
	 * This function receives an <code>email</code> parameter,<br>
	 * then checks if the email already exists in the <strong>Database</strong>.
	 * 
	 * @param email - String typed.
	 * @return true if the email exists in the Database, false if its not.
	 */
	public boolean areElementsTaken(String email) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Company's id</code> and <code>email</code>
	 * parameters,<br>
	 * then checks if the received email matches the company with the id received in
	 * the function, in the <strong>Database</strong>.
	 * 
	 * @param companyId - int-typed
	 * @param email     - String-typed.
	 * @return true if it matches, false if it doesn't.
	 * @throws SQLException
	 */
	public boolean isThisTheCompanysEmail(int companyId, String email) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Company's id</code> and <code>name</code>
	 * parameters,<br>
	 * then checks if the received name matches the company with the id received in
	 * the function, in the <strong>Database</strong>.
	 * 
	 * @param companyId - int-typed.
	 * @param name      - String-typed.
	 * @return true if it matches, false if it doesn't.
	 * @throws SQLException
	 */
	public boolean isThisTheCompanysName(int companyId, String name) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Company</code> parameter, then adds the
	 * received company into the <strong>Database</strong>.
	 * 
	 * @param company - Company-typed.
	 */
	public void addCompany(Company company) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Company</code> parameter, then updates the
	 * received company into the <strong>Database</strong>.
	 * 
	 * @param company - Company-typed.
	 * @throws SQLException
	 */
	public void updateCompany(Company company) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Company's id</code> parameter and deletes the
	 * company,<br>
	 * whose id is identical to the received id, from the <strong>Database</strong>.
	 * 
	 * @param companyId - int-typed.
	 */
	public void deleteCompany(int companyId) throws SQLException, InterruptedException;

	/**
	 * This function accesses the <strong>Database</strong>,<br>
	 * creates a List of all the objects in the Database that are
	 * <code>Company</code>, then returns it.
	 * 
	 * @returns an <em>Arraylist</em> of all <code>Company</code> objects.
	 */
	public ArrayList<Company> getAllCompanies() throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Company's id</code> parameter,<br>
	 * then access the <strong>Database</strong> and return the company whose id is
	 * identical to the received id parameter.
	 * 
	 * @param companyId - int-typed.
	 * @return the <code>Company</code> whose id equals the id parameter received.
	 */
	public Company getOneCompany(int companyId) throws SQLException, InterruptedException;

	/**
	 * This function receives an <code>name</code> parameter, then access the
	 * <strong>Database</strong><br>
	 * and return the company whose name is identical to the received name
	 * parameter.
	 * 
	 * @param companyName - String-typed.
	 * @return the <code>Company</code> whose name equals the name parameter
	 *         received.
	 */
	public Company getOneCompany(String companyName) throws SQLException, InterruptedException;

	/**
	 * This function receives an <code>email</code> parameter<br>
	 * and return the Id parameter of the company whose email equals the received
	 * email parameter.
	 * 
	 * @param email - String-typed.
	 * @return an int-typed companyId.
	 */
	public int getIdByEmail(String email) throws SQLException, InterruptedException;

	/**
	 * this function receives a <code>name</code> parameter<br>
	 * and return the Id parameter of the company whose name equals the received
	 * name parameter.
	 * 
	 * @param name - String-typed.
	 * @return an int-typed companyId.
	 */
	public int getIdByName(String name) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code> name</code> parameter<br>
	 * then checks it for a match against the database.
	 * 
	 * @param name - String-typed.
	 * @return true - if it finds a match, false - if it doesn't.
	 */
	public boolean isTheNameTaken(String name) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code> companyId</code> parameter<br>
	 * then checks it for a match against the database.
	 * 
	 * @param companyId - int-typed.
	 * @return true - if it finds a match, false - if it doesn't.
	 * @throws InterruptedException
	 */
	public boolean theCompanyExist(int companyId) throws SQLException, InterruptedException;

}
