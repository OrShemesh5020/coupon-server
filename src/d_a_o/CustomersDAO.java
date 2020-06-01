package d_a_o;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Customer;

/**
 * @author o_g_i.
 * @see {@link d.a.o.ConnectionPool #getConnection() }
 *      {@link d.a.o.ConnectionPool #restoreConnection(Connection)}
 */
public interface CustomersDAO {
	/**
	 * This function receives an <code>email</code> and <code>password</code>
	 * parameters<br>
	 * and checks if the client exists in the <strong>Database</strong> to allow
	 * logging in.
	 * 
	 * @param email    - String-typed.
	 * @param password - String-typed.
	 * @return true - if the client exists, false if he doesn't.
	 */
	public boolean isCustomerExists(String email, String password) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Customer</code> parameter and adds it to the
	 * <strong>Database</strong>.
	 * 
	 * @param customer - Customer-typed.
	 */
	public void addCustomer(Customer customer) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Customer</code> parameter and update the
	 * details of the customer, in the <strong>Database</strong>, whose id equals
	 * the received customer's id.
	 * 
	 * @param customer - Customer-typed.
	 * @throws SQLException
	 */
	public void updateCustomer(Customer customer) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>customer's Id</code> parameter and deletes the
	 * Customer whose id equals the Customer Id received while also deleting all the
	 * specified customer's purchasing history from the <strong>Database</strong>.
	 * 
	 * @param customerId- int-typed.
	 * @throws SQLException
	 */
	public void deleteACustomer(int customerId) throws SQLException, InterruptedException;

	/**
	 * This function accesses the <strong>Database</strong>,<br>
	 * creates a List of all the objects in the Database that are
	 * <code>Customer</code>, then returns it.
	 * 
	 * @return all the customers that exist in the database in an ArrayList form.
	 * @throws SQLException
	 */
	public ArrayList<Customer> getAllCustomers() throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Customer's id</code> parameter,<br>
	 * then access the <strong>Database</strong> and return the customer whose id is
	 * identical to the received id parameter.
	 * 
	 * @param customerId- int-typed.
	 * @return Customer-typed object that holds all the information of the object in
	 *         the data whose ID equals the received ID
	 * @throws SQLException
	 */
	public Customer getOneCustomer(int customerId) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Customer's id</code> and an <code>email</code>
	 * parameters<br>
	 * and checks if the received email belongs to the customer whose id attribute
	 * is the same as the received id.
	 * 
	 * @param id    - int typed.
	 * @param email - String typed.
	 * @return true- if it belongs to the customer, false- if it doesn't.
	 */
	public boolean isThisTheCustomersEmail(int customerId, String email) throws SQLException, InterruptedException;

	/**
	 * This function receives an <code>email</code> parameter and checks if it
	 * exists within the <strong>Database</strong>.
	 * 
	 * @param email - String typed.
	 * @return true if the email exist in the Database. false if it doesn't.
	 */
	public boolean isEmailTaken(String email) throws SQLException, InterruptedException;

	/**
	 * This function receives an <code>email</code> parameter<br>
	 * and checks the <strong>Database</strong> to look for the specific customer
	 * who owns it.
	 * 
	 * @param email - String-typed
	 * @return the id of the customer who owns the mail.
	 * @throws SQLException
	 */
	public int getIdByEmail(String email) throws SQLException, InterruptedException;

	/**
	 * This function recieves a <code>customerId</code> parameter <br>
	 * and checks if it exists in the database.
	 * 
	 * @param customerId - int-typed.
	 * @return true - if the customer exists in the database, false - if it doesn't.
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public boolean theCustomerExist(int customerId) throws SQLException, InterruptedException;

}
