package d_a_o;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Customer;

public class CustomersDBDAO implements CustomersDAO {
	private ConnectionPool connectionPool;

	public CustomersDBDAO() throws SQLException {
		super();
		this.connectionPool = ConnectionPool.getInstance();
	}

	@Override
	public boolean isCustomerExists(String email, String password) throws SQLException, InterruptedException {
		String select = "select email, password from coupon_system.customers;";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				String mail = executeQuery.getString("email");
				String pass = executeQuery.getString("password");

				if (mail.equals(email) && pass.equals(password)) {
					return true;
				}
			}

		} finally {
			connectionPool.restoreConnection(conn);
		}
		return false;
	}

	@Override
	public boolean theCustomerExist(int customerId) throws SQLException, InterruptedException {
		String select = "select id from coupon_system.customers;";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				if (id == customerId) {
					return true;
				}
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return false;
	}

	@Override
	public boolean isThisTheCustomersEmail(int customerId, String email) throws SQLException, InterruptedException {
		String select = "select email from coupon_system.customers WHERE (`id` = ? );";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setInt(1, customerId);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				String mail = executeQuery.getString("email");
				if (mail.equals(email)) {
					return true;
				}
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return false;
	}

	@Override
	public boolean isEmailTaken(String email) throws SQLException, InterruptedException {
		String select = "select email from coupon_system.customers;";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				String mail = executeQuery.getString("email");
				if (mail.equals(email)) {
					return true;
				}
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return false;
	}

	@Override
	public int getIdByEmail(String email) throws SQLException, InterruptedException {
		String select = "select id from coupon_system.customers WHERE (`email` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setString(1, email);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				return executeQuery.getInt("id");
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return 0;
	}

	@Override
	public void addCustomer(Customer customer) throws SQLException, InterruptedException {
		ResultSet keys = null;
		int lastKey = 0;
		String insert = "INSERT INTO `coupon_system`.`customers` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES (?, ?, ?, ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForInsert = conn.prepareStatement(insert,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatementForInsert.setString(1, customer.getFirstName());
			preparedStatementForInsert.setString(2, customer.getLastName());
			preparedStatementForInsert.setString(3, customer.getEmail());
			preparedStatementForInsert.setString(4, customer.getPassword());
			int executeUpdate = preparedStatementForInsert.executeUpdate();
			if (executeUpdate == 1) {
				keys = preparedStatementForInsert.getGeneratedKeys();
				while (keys.next()) {
					lastKey = keys.getInt(1);
				}
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		customer.setId(lastKey);
	}

	@Override
	public void updateCustomer(Customer customer) throws SQLException, InterruptedException {
		String update = "UPDATE `coupon_system`.`customers` SET `FIRST_NAME` = ?, `LAST_NAME` = ?, `EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForUpdate = conn.prepareStatement(update);
			preparedStatementForUpdate.setString(1, customer.getFirstName());
			preparedStatementForUpdate.setString(2, customer.getLastName());
			preparedStatementForUpdate.setString(3, customer.getEmail());
			preparedStatementForUpdate.setString(4, customer.getPassword());
			preparedStatementForUpdate.setInt(5, customer.getId());
			int executeUpdate = preparedStatementForUpdate.executeUpdate();
		} finally {
			connectionPool.restoreConnection(conn);
		}
	}

	@Override
	public void deleteACustomer(int customerId) throws SQLException, InterruptedException {
		String delete = "DELETE FROM `coupon_system`.`customers` WHERE (`ID` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForDelete = conn.prepareStatement(delete);
			deleteCustomerCouponPurchase(conn, customerId);
			preparedStatementForDelete.setInt(1, customerId);
			int executeUpdate = preparedStatementForDelete.executeUpdate();
		} finally {
			connectionPool.restoreConnection(conn);
		}
	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws SQLException, InterruptedException {
		ArrayList<Customer> customers = new ArrayList<>();
		String select = "select * from coupon_system.customers;";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				String firstName = executeQuery.getString("first_name");
				String lastName = executeQuery.getString("last_name");
				String email = executeQuery.getString("email");
				String password = executeQuery.getString("password");
				Customer customer = new Customer(id, firstName, lastName, email, password);
				customers.add(customer);
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return customers;
	}

	@Override
	public Customer getOneCustomer(int customerId) throws SQLException, InterruptedException {
		String select = "select * from coupon_system.customers WHERE (`id` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setInt(1, customerId);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				String firstName = executeQuery.getString("first_name");
				String lastName = executeQuery.getString("last_name");
				String email = executeQuery.getString("email");
				String password = executeQuery.getString("password");
				Customer customer = new Customer(id, firstName, lastName, email, password);
				return customer;
			}

		} finally {
			connectionPool.restoreConnection(conn);
		}
		return null;
	}

	private void deleteCustomerCouponPurchase(Connection conn, int customerId) throws SQLException {
		String delete = "DELETE FROM `coupon_system`.`customers_vs_coupocns` WHERE (`CUSTOMER_ID` = ?);";
		PreparedStatement preparedStatementForDelete = conn.prepareStatement(delete);
		preparedStatementForDelete.setInt(1, customerId);
		int executeUpdate = preparedStatementForDelete.executeUpdate();
	}

}
