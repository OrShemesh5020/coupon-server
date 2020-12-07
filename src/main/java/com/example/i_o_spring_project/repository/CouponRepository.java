package com.example.i_o_spring_project.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.i_o_spring_project.model.Category;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	public Optional<Coupon> findById(int id);

	public List<Coupon> findByCompany(Company company);

	public Optional<Company> findByTitle(String title);

	@Query("select coupon from Coupon coupon where (coupon.company=:company and coupon.id=:id)")
	public Optional<Coupon> getOneCoupon(Company company, int id);

	@Query("select coupon from Coupon coupon where (coupon.company=:company and coupon.title=:title)")
	public Optional<Coupon> getOneCoupon(Company company, String title);

	@Query("select coupon from Coupon coupon where (coupon.company=:company and coupon.price<=:price)")
	public List<Coupon> getCompanyCouponsByPrice(Company company, double price);

	@Query("select coupon from Coupon coupon where (coupon.company=:company and coupon.category=:category)")
	public List<Coupon> getCompanyCouponsByCategory(Company company, Category category);

	@Query(value = "SELECT count(COUPON_ID) from coupon_system.customers_vs_coupons where COUPON_ID=?", nativeQuery = true)
	public int howManyCouponsWereSold(int couponId);

	@Query(value = "SELECT Count(COUPON_ID) FROM customers_vs_coupons LEFT JOIN coupons ON customers_vs_coupons.COUPON_ID = coupons.id where company_id=?", nativeQuery = true)
	public int howManyCompanyCouponsWereSold(int companyId);

	@Query(value = "SELECT sum(price) FROM customers_vs_coupons LEFT JOIN coupons ON customers_vs_coupons.COUPON_ID = coupons.id where company_id=?", nativeQuery = true)
	public double getTheCouponsSumOfSales(int companyId);

	@Modifying
	@Query(value = "delete cvc from customers_vs_coupons cvc join coupons c on cvc.COUPON_ID=c.id where c.end_date < current_date()", nativeQuery = true)
	public void deleteExpiredCouponsFromCustomers();

	@Modifying
	@Query(value = "delete from coupons c where c.end_date < current_date()", nativeQuery = true)
	public void deleteExpiredCoupons();

	@Modifying
	@Query(value = "delete cvc from customers_vs_coupons cvc join coupons c on cvc.COUPON_ID=c.id where c.company_id=?", nativeQuery = true)
	public void deleteByCompanyAllPurchasedCoupons(int companyId);

	@Modifying
	@Query(value = "DELETE FROM coupon_system.customers_vs_coupons WHERE (COUPON_ID =?)", nativeQuery = true)
	public void deleteAllPurchasedCoupon(int couponId);

	@Modifying
	public void deleteByCompany(Company company);

//	public void deleteCompanyCouponsByCustomer() {

//	}

}
