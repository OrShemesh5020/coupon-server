package com.example.i_o_spring_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.i_o_spring_project.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public Optional<Customer> findByEmail(String email);

	public Optional<Customer> findById(int id);

//	@Query("select coupon from Customer customer where ")
//	public List<Optional<Coupon>> getAllCustomersCoupon(int id);

//	public List<Optional<Coupon>> findAllCoupons(int id);
}
