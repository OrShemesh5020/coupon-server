package d_a_o;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Coupon;

/**
 * @author o_g_i.
 * @see {@link d.a.o.ConnectionPool #getConnection() }
 *      {@link d.a.o.ConnectionPool #restoreConnection(Connection)}
 */
public interface CouponsDAO {
	/**
	 * This function receives a <code>Coupon</code> parameter and adds it to the
	 * <strong>Database</strong>.
	 * 
	 * @param coupon - Coupon-typed.
	 */
	public void addACoupon(Coupon coupon) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Coupon</code> parameter and updates it in the
	 * <strong>Database</strong>.
	 * 
	 * @param coupon - Coupon-typed.
	 * @throws SQLException
	 */
	public void updateACoupon(Coupon coupon) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Coupon</code> parameter and updates it in the
	 * database<br>
	 * <strong>without the <code>startTime</code> parameter</strong>.
	 * 
	 * @param coupon - Coupon-typed.
	 * @throws SQLException
	 */
	public void updateACouponWithoutStartTime(Coupon coupon) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Coupon's id</code> parameter<br>
	 * and deletes the coupon whose id equals the received id, from the
	 * <strong>Database</strong>,<br>
	 * while also sending it to a function , to make sure the purchases history gets
	 * deleted.
	 * 
	 * @param couponId - int-typed
	 * @see {@link #deleteAllCouponPurchases(int)}
	 */
	public void deleteCouponByCoupon(int couponId) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Company's id</code> parameter<br>
	 * and deletes the coupons whose company's id equals the received id, from the
	 * <strong>Database</strong>,<br>
	 * while also sending it to a function, to make sure the purchases history gets
	 * deleted.
	 * 
	 * @param companyId - int-typed.
	 * @see {@link #deleteAllCouponPurchases(int)}
	 */
	public void deleteCouponsByCompany(int companyId) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Company's id</code> and a <code>price</code>
	 * parameters <br>
	 * and creates a list of the company's coupons whose prices are up to (or
	 * equals) the received price parameter.
	 * 
	 * @param companyId - int-typed.
	 * @param price     - double-typed.
	 * @return an ArraList of coupons.
	 */
	public ArrayList<Coupon> getCompanyCouponsByPrice(int companyId, double price)
			throws SQLException, InterruptedException;

	/**
	 * This function creates a list of All coupons that exists in the
	 * <strong>Database</strong>, then returns it.
	 * 
	 * @return a list of All coupons.
	 */
	public ArrayList<Coupon> getAllCoupons() throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Coupon's id</code> parameter <br>
	 * and than gets the coupon whose id equals the received id, from
	 * <strong>Database</strong>.
	 * 
	 * @param couponId- Coupon-typed.
	 * @return a specific coupon.
	 */
	public Coupon getOneCoupon(int couponId) throws SQLException, InterruptedException;

	/**
	 * This function receives <code>customer's id</code> and
	 * <code>Coupon's id</code> parameters <br>
	 * and adds the coupon's id , and the purchasing customer's id to the purchases
	 * history log.
	 * 
	 * @param customerId - int-typed.
	 * @param couponId   - int-typed.
	 */
	public void addCouponPurchase(int customerId, int couponId) throws SQLException, InterruptedException;

	/**
	 * This function receives <code>customer's id</code> and
	 * <code>Coupon's id</code> parameters <br>
	 * and deletes the coupon's id , and the purchasing customer's id from the
	 * purchases history log.
	 * 
	 * @param customerId - int-typed.
	 * @param couponId   - int-typed.
	 */
	public void deleteCouponPurchase(int customerId, int couponId) throws SQLException, InterruptedException;

	/**
	 * This function receives an <code>Coupon's id</code> parameter <br>
	 * and deletes the coupon whose id equals the received id, from the
	 * <strong>Database</strong>,<br>
	 * while also deleting all of the coupon's purchase histories.
	 * 
	 * @param couponId - int-typed.
	 */
	public void deleteAllCouponPurchases(int couponId) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Company's id</code> and a <code>title</code>
	 * parameters,<br>
	 * then checks if the title already exists in the database.
	 * 
	 * @param companyId - int-typed.
	 * @param title     - String-typed.
	 * @return true- if the title already exists in the Database, false if it
	 *         doesn't.
	 */
	public boolean nameAlreadyTaken(int companyId, String title) throws SQLException, InterruptedException;

	/**
	 * This function receives an <code>Company's id</code> parameter, creates an
	 * ArrayList of coupons <br>
	 * and gets from the <strong>Database</strong> all the coupons whose company's
	 * id equals the received company id parameter.
	 * 
	 * @param companyId - int typed.
	 * @return an ArrayList of all the company's coupons.
	 */
	public ArrayList<Coupon> getAllCompanyCoupons(int companyId) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Company's id</code> and a
	 * <code>category's id</code> parameters<br>
	 * and uses them to create a list of coupons with the same company's id and
	 * category's id.
	 * 
	 * @param companyId  - int-typed.
	 * @param categoryId - int-typed.
	 * @return a list of Coupons of a specific company and category.
	 */
	public List<Coupon> getCompanyCouponsByCategory(int companyId, int categoryId)
			throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>Company's id</code> and a <code>title</code>
	 * parameters <br>
	 * and returns the coupon whose company's id equals the received company id and
	 * title equals the received title.
	 * 
	 * @param companyId - int-typed.
	 * @param title     - String-typed.
	 * @return a specific coupon.
	 */
	Coupon getOneCoupon(int companyId, String title) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>customer's id</code> and
	 * <code>Coupon's id</code> parameters<br>
	 * to check if the customer has bought the coupon.
	 * 
	 * @param customerId - int - typed.
	 * @param coupontId  - int - typed.
	 * @return true - if the customer has bought the coupon, false if he hasn't.
	 */
	public boolean hasPurchasedCoupon(int customerId, int coupontId) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>customer's id</code> parameter,<br>
	 * then returns all the coupons whose buying customer id equals the received<br>
	 * customer id parameter in a ArrayList form.
	 * 
	 * @param customerId - int-typed.
	 * @return a ArrayList of all coupons purchased by a specific client.
	 * @see {@link #getOneCoupon(int)}
	 */
	public ArrayList<Coupon> getAllcouponsPurchased(int customerId) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>companyId</code> and a <code>couponId</code>
	 * parameters <br>
	 * and checks the database to determine if the company owns the coupon.
	 * 
	 * @param companyId - int-typed.
	 * @param couponId  - int-typed.
	 * @return true - if the company owns the coupon, false if it doesn't.
	 * @throws SQLException
	 */
	public boolean companyHasTheCoupon(int companyId, int couponId) throws SQLException, InterruptedException;

	/**
	 * This function receives a <code>couponId</code> parameter <br>
	 * and checks if it exists in the database.
	 * 
	 * @param couponId - int-typed.
	 * @return true - if the coupon exists in the database, false if it doesn't.
	 * @throws SQLException
	 */
	public boolean theCouponExist(int couponId) throws SQLException, InterruptedException;

}
