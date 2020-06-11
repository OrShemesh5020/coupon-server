package com.example.i_o_spring_project.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "companies")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;

	@OneToMany(mappedBy = "companyId")
	private List<Coupon> coupons;

	public Company(Integer id, String name, String email, String password) {
		this(name, email, password);
		this.id = id;

	}

	public Company(String name, String email, String password) {
		coupons = new ArrayList<>();
		this.name = name;
		this.email = email;
		this.password = password;
	}
}
