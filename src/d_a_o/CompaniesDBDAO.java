package d_a_o;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Company;

public class CompaniesDBDAO implements CompaniesDAO {
	private ConnectionPool connectionPool;

	public CompaniesDBDAO() throws SQLException {
		this.connectionPool = ConnectionPool.getInstance();
	}

	@Override
	public boolean doesCompanyExists(String email, String password) throws SQLException, InterruptedException {
		String select = "select email, password from coupon_system.companies;";
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
	public boolean theCompanyExist(int companyId) throws SQLException, InterruptedException {
		String select = "select id from coupon_system.companies;";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				if (id == companyId) {
					return true;
				}
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return false;
	}

	@Override
	public boolean areElementsTaken(String name, String email) throws SQLException, InterruptedException {
		String select = "select name, email from coupon_system.companies;";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				String companyName = executeQuery.getString("name");
				String mail = executeQuery.getString("email");
				if (companyName.equals(name) && mail.equals(email)) {
					return true;
				}
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return false;
	}

	@Override
	public boolean areElementsTaken(String email) throws SQLException, InterruptedException {
		String select = "select email from coupon_system.companies;";
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
	public boolean isTheNameTaken(String name) throws SQLException, InterruptedException {
		String select = "select name from coupon_system.companies;";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				String companyName = executeQuery.getString("name");
				if (companyName.equals(name)) {
					return true;
				}
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return false;
	}

	@Override
	public boolean isThisTheCompanysEmail(int companyId, String email) throws SQLException, InterruptedException {
		String select = "select email from coupon_system.companies WHERE (`id` = ? );";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setInt(1, companyId);
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
	public boolean isThisTheCompanysName(int companyId, String name) throws SQLException, InterruptedException {
		String select = "select name from coupon_system.companies WHERE (`id` = ? );";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setInt(1, companyId);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				String companyName = executeQuery.getString("name");
				if (companyName.equals(name)) {
					return true;
				}
			}
			return false;
		} finally {
			connectionPool.restoreConnection(conn);
		}
	}

	@Override
	public void addCompany(Company company) throws SQLException, InterruptedException {
		ResultSet keys = null;
		int lastKey = 0;
		String insert = "INSERT INTO `coupon_system`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForInsert = conn.prepareStatement(insert,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatementForInsert.setString(1, company.getName());
			preparedStatementForInsert.setString(2, company.getEmail());
			preparedStatementForInsert.setString(3, company.getPassword());
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
		company.setId(lastKey);
	}

	@Override
	public void updateCompany(Company company) throws SQLException, InterruptedException {
		String update = "UPDATE `coupon_system`.`companies` SET `email` = ?, `password` = ? WHERE (`id` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForUpdate = conn.prepareStatement(update);
			preparedStatementForUpdate.setString(1, company.getEmail());
			preparedStatementForUpdate.setString(2, company.getPassword());
			preparedStatementForUpdate.setInt(3, company.getId());
			int executeUpdate = preparedStatementForUpdate.executeUpdate();
		} finally {
			connectionPool.restoreConnection(conn);
		}
	}

	@Override
	public void deleteCompany(int companyId) throws SQLException, InterruptedException {
		String delete = "DELETE FROM `coupon_system`.`companies` WHERE (`id` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForDelete = conn.prepareStatement(delete);
			preparedStatementForDelete.setInt(1, companyId);
			int executeUpdate = preparedStatementForDelete.executeUpdate();
		} finally {
			connectionPool.restoreConnection(conn);
		}
	}

	@Override
	public ArrayList<Company> getAllCompanies() throws SQLException, InterruptedException {
		ArrayList<Company> companies = new ArrayList<>();
		String select = "select * from coupon_system.companies;";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				String name = executeQuery.getString("name");
				String email = executeQuery.getString("email");
				String password = executeQuery.getString("password");
				Company company = new Company(id, name, email, password);
				companies.add(company);
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return companies;
	}

	@Override
	public int getIdByEmail(String email) throws SQLException, InterruptedException {
		String select = "select id from coupon_system.companies WHERE (`email` = ?);";
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
	public int getIdByName(String name) throws SQLException, InterruptedException {
		String select = "select id from coupon_system.companies WHERE (`name` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setString(1, name);
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
	public Company getOneCompany(int companyId) throws SQLException, InterruptedException {
		String select = "select * from coupon_system.companies WHERE (`id` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setInt(1, companyId);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				String name = executeQuery.getString("name");
				String email = executeQuery.getString("email");
				String password = executeQuery.getString("password");
				return new Company(id, name, email, password);
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		// If the program came here then you should throw an notFoundCompanyException
		// that no suitable company is found
		return null;
	}

	@Override
	public Company getOneCompany(String companyName) throws SQLException, InterruptedException {
		String select = "select * from coupon_system.companies WHERE (`name` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setString(1, companyName);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				String name = executeQuery.getString("name");
				String email = executeQuery.getString("email");
				String password = executeQuery.getString("password");
				return new Company(id, name, email, password);
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		// If the program came here then you should throw an notFoundCompanyException
		// that no suitable company is found
		return null;
	}

}
