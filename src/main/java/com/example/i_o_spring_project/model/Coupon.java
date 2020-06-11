package com.example.i_o_spring_project.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "coupons")
public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(targetEntity = Coupon.class)
	@JoinColumn(name = "company_id")
	private Integer companyId;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "end_date")
	private Date endDate;
	@Column(name = "amount")
	private Integer amount;
	@Column(name = "price")
	private Double price;
	@Column(name = "image")
	private String image;

	@ManyToMany(mappedBy = "coupons")
	private List<Customer> customers;

	public Coupon(Integer companyId, Category category, String title, String description, Date startDate, Date endDate,
			Integer amount, double price, String image) {
		this.companyId = companyId;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public Coupon(Integer id, Integer companyId, Category category, String title, String description, Date startDate,
			Date endDate, Integer amount, double price, String image) {
		this(companyId, category, title, description, startDate, endDate, amount, price, image);
		this.id = id;
	}
}
