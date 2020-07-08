package com.example.i_o_spring_project.repository;

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
	@Modifying
	public void deleteByCompany(Company company);


}
