package com.example.i_o_spring_project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.i_o_spring_project.model.Category;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	public Optional<Coupon> findById(int id);

	public List<Optional<Coupon>> findByCompany(Company company);

	@Query("select coupon from Coupon coupon where (coupon.company=:company and coupon.title=:title)")
	public Optional<Coupon> getOneCoupon(Company company, String title);

	@Query("select coupon from Coupon coupon where (coupon.company=:company and coupon.price<=:price)")
	public List<Optional<Coupon>> getCompanyCouponsByPrice(Company company, double price);

	@Query("select coupon from Coupon coupon where (coupon.company=:company and coupon.category=:category)")
	public List<Optional<Coupon>> getCompanyCouponsByCategory(Company company, Category category);

}
