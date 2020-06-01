package automatic.managment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import d_a_o.CouponsDAO;
import d_a_o.CouponsDBDAO;
import model.Coupon;

/**
 * This class starts running at the start of the program, and stop running at
 * the end of it.<br>
 * 
 * @see {@link #run()}<br>
 *      {@link #stop()}
 */

public class CouponExpirationDailyJob implements Runnable {
	
	private CouponsDAO couponDAO;

	private boolean quit;
	/**
	 * This constructor set quit as false - to allow run() to run,<br> and when it turns true, the function run() stops.
	 * @throws SQLException 
	 * @see
	 * {@link d_a_o.CouponsDAO}
	 */
	public CouponExpirationDailyJob() throws SQLException {
		couponDAO = new CouponsDBDAO();
		quit = false;
	}

	/**
	 *
	 * The function automatically delete the coupons whose expiration date has
	 * passed from the system,<br>
	 * it runs at the background and repeat this action every 24/h, in condition the
	 * program continue running.
	 * 
	 * @see {@link d_a_o.CouponsDAO#getAllCoupons()}<br>
	 *      {@link d_a_o.CouponsDAO#deleteCouponByCoupon(int)}
	 */
	@Override
	public void run() {
		while (!quit) {
			System.out.println("start working");
			Calendar now = Calendar.getInstance();
			setTime(now);
			try {
				ArrayList<Coupon> allCoupons = couponDAO.getAllCoupons();
				for (Coupon coupon : allCoupons) {
					if (coupon.getEndDate().getTimeInMillis() < now.getTimeInMillis()) {
						couponDAO.deleteCouponByCoupon(coupon.getId());
					}
				}
				for (int i = 0; i < 86400 && !quit; i++) {
					Thread.sleep(1000);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("GoodBye");
	}
	/**
	 *
	 * this function receives a Calendar-typed object 'now' and sets the time's
	 * HOUR,MINUTE,SECOND,and MILLISECOND to 0 and also adds +1 to the MONTH value
	 * because the Calendar's default value of January is 0;
	 * 
	 * @param now =Calendar-type object = {@link Calendar} 
	 */
	private void setTime(Calendar time) {
		time.set(Calendar.HOUR, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
		time.add(Calendar.MONTH, 1);
	}

	/**
	 * This function stops the run() function.
	 * 
	 */
	public void stop() {
		quit = true;
	}

}
