package d_a_o;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Category;
import model.Coupon;

public class CouponsDBDAO implements CouponsDAO {
	private ConnectionPool connectionPool;

	public CouponsDBDAO() throws SQLException {
		super();
		this.connectionPool = ConnectionPool.getInstance();
	}

	@Override
	public boolean nameAlreadyTaken(int companyId, String title) throws SQLException, InterruptedException {
		String select = "select title from coupon_system.coupons where (company_id= ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setInt(1, companyId);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				String couponName = executeQuery.getString("title");
				if (couponName.equals(title)) {
					return true;
				}
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return false;
	}

	@Override
	public void addACoupon(Coupon coupon) throws SQLException, InterruptedException {
		ResultSet keys = null;
		int lastKey = 0;
		String insert = "INSERT INTO `coupon_system`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForInsert = conn.prepareStatement(insert,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatementForInsert.setInt(1, coupon.getCompanyId());
			preparedStatementForInsert.setInt(2, (coupon.getCategory().ordinal() + 1));
			preparedStatementForInsert.setString(3, coupon.getTitle());
			preparedStatementForInsert.setString(4, coupon.getDescription());
			// DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			preparedStatementForInsert.setDate(5, java.sql.Date.valueOf(getDate(coupon.getStartDate())));
			preparedStatementForInsert.setDate(6, java.sql.Date.valueOf(getDate(coupon.getEndDate())));
			preparedStatementForInsert.setInt(7, coupon.getAmount());
			preparedStatementForInsert.setDouble(8, coupon.getPrice());
			preparedStatementForInsert.setString(9, coupon.getImage());
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
		coupon.setId(lastKey);
	}

	private String getDate(Calendar date) {
		return date.get(Calendar.YEAR) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.DAY_OF_MONTH);
	}

	@Override
	public void updateACoupon(Coupon coupon) throws SQLException, InterruptedException {
		String update = "UPDATE `coupon_system`.`coupons` SET `category_id` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForUpdate = conn.prepareStatement(update);
			preparedStatementForUpdate.setInt(1, (coupon.getCategory().ordinal() + 1));
			preparedStatementForUpdate.setString(2, coupon.getTitle());
			preparedStatementForUpdate.setString(3, coupon.getDescription());
			// DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			preparedStatementForUpdate.setDate(4, java.sql.Date.valueOf(getDate(coupon.getStartDate())));
//			System.out.println("CouponsDBDAO.updateCoupon");
//			System.out.println(getDate(coupon.getEndDate()));
			preparedStatementForUpdate.setDate(5, java.sql.Date.valueOf(getDate(coupon.getEndDate())));
			preparedStatementForUpdate.setInt(6, coupon.getAmount());
			preparedStatementForUpdate.setDouble(7, coupon.getPrice());
			preparedStatementForUpdate.setString(8, coupon.getImage());
			preparedStatementForUpdate.setInt(9, coupon.getId());
			int executeUpdate = preparedStatementForUpdate.executeUpdate();

		} finally {
			connectionPool.restoreConnection(conn);
		}
	}

	@Override
	public void updateACouponWithoutStartTime(Coupon coupon) throws SQLException, InterruptedException {
		String update = "UPDATE `coupon_system`.`coupons` SET `category_id` = ?, `title` = ?, `description` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForUpdate = conn.prepareStatement(update);
			preparedStatementForUpdate.setInt(1, (coupon.getCategory().ordinal() + 1));
			preparedStatementForUpdate.setString(2, coupon.getTitle());
			preparedStatementForUpdate.setString(3, coupon.getDescription());
//			System.out.println("CouponsDBDAO.updateCoupon");
//			System.out.println(getDate(coupon.getEndDate()));
			preparedStatementForUpdate.setDate(4, java.sql.Date.valueOf(getDate(coupon.getEndDate())));
			preparedStatementForUpdate.setInt(5, coupon.getAmount());
			preparedStatementForUpdate.setDouble(6, coupon.getPrice());
			preparedStatementForUpdate.setString(7, coupon.getImage());
			preparedStatementForUpdate.setInt(8, coupon.getId());
			int executeUpdate = preparedStatementForUpdate.executeUpdate();
		} finally {
			connectionPool.restoreConnection(conn);
		}
	}

	@Override
	public void deleteCouponByCoupon(int couponId) throws SQLException, InterruptedException {
		String delete = "DELETE FROM `coupon_system`.`coupons` WHERE (`id` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForDelete = conn.prepareStatement(delete);
			deleteAllCouponPurchases(couponId);
			preparedStatementForDelete.setInt(1, couponId);
			int executeUpdate = preparedStatementForDelete.executeUpdate();
		} finally {
			connectionPool.restoreConnection(conn);
		}
	}

	@Override
	public void deleteCouponsByCompany(int companyId) throws SQLException, InterruptedException {
		String select = "select * from coupon_system.coupons where (`company_id`= ?);";
		String delete = "DELETE FROM `coupon_system`.`coupons` WHERE (`company_id` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			PreparedStatement preparedStatementForDelete = conn.prepareStatement(delete);
			preparedStatementForSelect.setInt(1, companyId);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				deleteAllCouponPurchases(id);
			}
			preparedStatementForDelete.setInt(1, companyId);
			int executeUpdate = preparedStatementForDelete.executeUpdate();
		} finally {
			connectionPool.restoreConnection(conn);
		}
	}

	@Override
	public ArrayList<Coupon> getAllCoupons() throws SQLException, InterruptedException {
		ArrayList<Coupon> coupons = new ArrayList<>();
		String select = "SELECT * FROM coupon_system.coupons;";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				int companyId = executeQuery.getInt("company_id");
				int categoryId = executeQuery.getInt("category_id");
				String title = executeQuery.getString("title");
				String description = executeQuery.getString("description");
				Date startTime = executeQuery.getDate("start_date");
				Calendar startDate = Calendar.getInstance();
				startDate.setTime(startTime);
				startDate.add(Calendar.MONTH, 1);
				Date endTime = executeQuery.getDate("end_date");
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(endTime);
				endDate.add(Calendar.MONTH, 1);
				int amount = executeQuery.getInt("amount");
				double price = executeQuery.getDouble("price");
				String image = executeQuery.getString("image");
				Coupon coupon = new Coupon(id, companyId, Category.values()[categoryId - 1], title, description,
						startDate, endDate, amount, price, image);
				coupons.add(coupon);
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return coupons;
	}

	@Override
	public ArrayList<Coupon> getAllCompanyCoupons(int companyId) throws SQLException, InterruptedException {
		ArrayList<Coupon> coupons = new ArrayList<>();
		String select = "SELECT * FROM coupon_system.coupons WHERE (`company_id` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setInt(1, companyId);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				int categoryId = executeQuery.getInt("category_id");
				String title = executeQuery.getString("title");
				String description = executeQuery.getString("description");
				Date startTime = executeQuery.getDate("start_date");
				Calendar startDate = Calendar.getInstance();
				startDate.setTime(startTime);
				startDate.add(Calendar.MONTH, 1);
				Date endTime = executeQuery.getDate("end_date");
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(endTime);
				endDate.add(Calendar.MONTH, 1);
				int amount = executeQuery.getInt("amount");
				double price = executeQuery.getDouble("price");
				String image = executeQuery.getString("image");
				Coupon coupon = new Coupon(id, companyId, Category.values()[categoryId - 1], title, description,
						startDate, endDate, amount, price, image);
				coupons.add(coupon);
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return coupons;
	}

	@Override
	public ArrayList<Coupon> getCompanyCouponsByPrice(int companyId, double price)
			throws SQLException, InterruptedException {
		ArrayList<Coupon> coupons = new ArrayList<>();
		String select = "select * from coupon_system.coupons where(`company_id`=? and `price`<=?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setInt(1, companyId);
			preparedStatementForSelect.setDouble(2, price);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				int categoryId = executeQuery.getInt("category_id");
				String title = executeQuery.getString("title");
				String description = executeQuery.getString("description");
				Date startTime = executeQuery.getDate("start_date");
				Calendar startDate = Calendar.getInstance();
				startDate.setTime(startTime);
				startDate.add(Calendar.MONTH, 1);
				Date endTime = executeQuery.getDate("end_date");
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(endTime);
				endDate.add(Calendar.MONTH, 1);
				int amount = executeQuery.getInt("amount");
				double couponPrice = executeQuery.getDouble("price");
				String image = executeQuery.getString("image");
				Coupon coupon = new Coupon(id, companyId, Category.values()[categoryId - 1], title, description,
						startDate, endDate, amount, couponPrice, image);
				coupons.add(coupon);
			}
			return coupons;
		} finally {
			connectionPool.restoreConnection(conn);
		}
	}

	@Override
	public List<Coupon> getCompanyCouponsByCategory(int companyId, int categoryId)
			throws SQLException, InterruptedException {
		ArrayList<Coupon> coupons = new ArrayList<>();
		String select = "select * from coupon_system.coupons where(`company_id`=? and `category_id`=?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setInt(1, companyId);
			preparedStatementForSelect.setInt(2, categoryId);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				String title = executeQuery.getString("title");
				String description = executeQuery.getString("description");
				Date startTime = executeQuery.getDate("start_date");
				Calendar startDate = Calendar.getInstance();
				startDate.setTime(startTime);
				startDate.add(Calendar.MONTH, 1);
				Date endTime = executeQuery.getDate("end_date");
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(endTime);
				endDate.add(Calendar.MONTH, 1);
				int amount = executeQuery.getInt("amount");
				double price = executeQuery.getDouble("price");
				String image = executeQuery.getString("image");
				Coupon coupon = new Coupon(id, companyId, Category.values()[categoryId - 1], title, description,
						startDate, endDate, amount, price, image);
				coupons.add(coupon);
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return coupons;
	}

	@Override
	public Coupon getOneCoupon(int couponId) throws SQLException, InterruptedException {
		String select = "select * from coupon_system.coupons where (id= ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setInt(1, couponId);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				int companyId = executeQuery.getInt("company_id");
				int categoryId = executeQuery.getInt("category_id");
				String title = executeQuery.getString("title");
				String description = executeQuery.getString("description");
				Date startTime = executeQuery.getDate("start_date");
				Calendar startDate = Calendar.getInstance();
				startDate.setTime(startTime);
				startDate.add(Calendar.MONTH, 1);
				startDate.add(Calendar.DAY_OF_MONTH, 1);
				Date endTime = executeQuery.getDate("end_date");
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(endTime);
				endDate.add(Calendar.MONTH, 1);
				endDate.add(Calendar.DAY_OF_MONTH, 1);
				int amount = executeQuery.getInt("amount");
				double price = executeQuery.getDouble("price");
				String image = executeQuery.getString("image");
				return new Coupon(id, companyId, Category.values()[categoryId - 1], title, description, startDate,
						endDate, amount, price, image);
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return null;
	}

	@Override
	public ArrayList<Coupon> getAllcouponsPurchased(int customerId) throws SQLException, InterruptedException {
		ArrayList<Coupon> coupons = new ArrayList<>();
		String select = "select * from coupon_system.customers_vs_coupocns where (CUSTOMER_ID= ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setInt(1, customerId);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int couponId = executeQuery.getInt("COUPON_ID");
				coupons.add(getOneCoupon(couponId));
			}
			return coupons;
		} finally {
			connectionPool.restoreConnection(conn);
		}
	}

	@Override
	public Coupon getOneCoupon(int companyId, String title) throws SQLException, InterruptedException {
		String select = "select * from coupon_system.coupons where (`company_id`= ? and `title`= ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setInt(1, companyId);
			preparedStatementForSelect.setString(2, title);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				int categoryId = executeQuery.getInt("category_id");
				String description = executeQuery.getString("description");
				Date startTime = executeQuery.getDate("start_date");
				Calendar startDate = Calendar.getInstance();
				startDate.setTime(startTime);
				startDate.add(Calendar.MONTH, 1);
				Date endTime = executeQuery.getDate("end_date");
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(endTime);
				endDate.add(Calendar.MONTH, 1);
				int amount = executeQuery.getInt("amount");
				double price = executeQuery.getDouble("price");
				String image = executeQuery.getString("image");
				return new Coupon(id, companyId, Category.values()[categoryId - 1], title, description, startDate,
						endDate, amount, price, image);
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return null;
	}

	@Override
	public void addCouponPurchase(int customerId, int couponId) throws SQLException, InterruptedException {
		int amount = 0;
		String select = "select * from coupon_system.coupons where (id= ?);";
		String update = "UPDATE `coupon_system`.`coupons` SET `amount` = ? WHERE (`id` = ?);";
		String insert = "INSERT INTO `coupon_system`.`customers_vs_coupocns` (`CUSTOMER_ID`, `COUPON_ID`) VALUES (?, ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			PreparedStatement preparedStatementForUpdate = conn.prepareStatement(update);
			PreparedStatement preparedStatementForInsert = conn.prepareStatement(insert);
			preparedStatementForSelect.setInt(1, couponId);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				amount = executeQuery.getInt("amount");
			}
			amount--;
			preparedStatementForUpdate.setInt(1, amount);
			preparedStatementForUpdate.setInt(2, couponId);
			int executeUpdateForUpdate = preparedStatementForUpdate.executeUpdate();
			preparedStatementForInsert.setInt(1, customerId);
			preparedStatementForInsert.setInt(2, couponId);
			int executeUpdateForInsert = preparedStatementForInsert.executeUpdate();
		} finally {
			connectionPool.restoreConnection(conn);
		}
	}

	@Override
	public void deleteCouponPurchase(int customerId, int couponId) throws SQLException, InterruptedException {
		int amount = 0;
		String select = "select * from coupon_system.coupons where (id= ?);";
		String update = "UPDATE `coupon_system`.`coupons` SET `amount` = ? WHERE (`id` = ?);";
		String delete = "DELETE FROM `coupon_system`.`customers_vs_coupocns` WHERE (`CUSTOMER_ID` = ?) and (`COUPON_ID` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			PreparedStatement preparedStatementForUpdate = conn.prepareStatement(update);
			PreparedStatement preparedStatementForDelete = conn.prepareStatement(delete);
			preparedStatementForDelete.setInt(1, customerId);
			preparedStatementForDelete.setInt(2, couponId);
			int executeUpdateForDelete = preparedStatementForDelete.executeUpdate();
			preparedStatementForSelect.setInt(1, couponId);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				amount = executeQuery.getInt("amount");
			}
			amount++;
			preparedStatementForUpdate.setInt(1, amount);
			preparedStatementForUpdate.setInt(2, couponId);
			int executeUpdateForUpdate = preparedStatementForUpdate.executeUpdate();
		} finally {
			connectionPool.restoreConnection(conn);
		}
	}

	@Override
	public void deleteAllCouponPurchases(int couponId) throws SQLException, InterruptedException {
		String delete = "DELETE FROM `coupon_system`.`customers_vs_coupocns` WHERE (`COUPON_ID` = ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForDelete = conn.prepareStatement(delete);
			preparedStatementForDelete.setInt(1, couponId);
			int executeUpdate = preparedStatementForDelete.executeUpdate();
		} finally {
			connectionPool.restoreConnection(conn);
		}
	}

	@Override
	public boolean hasPurchasedCoupon(int customerId, int coupontId) throws SQLException, InterruptedException {
		String select = "select * from coupon_system.customers_vs_coupocns where (CUSTOMER_ID= ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setInt(1, customerId);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("COUPON_ID");
				if (id == coupontId) {
					return true;
				}
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return false;
	}

	@Override
	public boolean companyHasTheCoupon(int companyId, int couponId) throws SQLException, InterruptedException {
		String select = "select id from coupon_system.coupons where (company_id= ?);";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			preparedStatementForSelect.setInt(1, companyId);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				if (id == couponId) {
					return true;
				}
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return false;
	}

	@Override
	public boolean theCouponExist(int couponId) throws SQLException, InterruptedException {
		String select = "select id from coupon_system.coupons;";
		Connection conn = connectionPool.getConnection();
		try {
			PreparedStatement preparedStatementForSelect = conn.prepareStatement(select);
			ResultSet executeQuery = preparedStatementForSelect.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				if (id == couponId) {
					return true;
				}
			}
		} finally {
			connectionPool.restoreConnection(conn);
		}
		return false;
	}
}
