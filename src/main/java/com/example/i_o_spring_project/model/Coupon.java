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
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@Table(name = "coupons")
public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

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

	@ToString.Exclude
	@ManyToMany(mappedBy = "coupons")
	private List<Customer> customers;

	@Override
	public String toString() {
		return "Coupon [id: " + id + ", company id: " + company.getId() + ", category: " + category + ", title: " + title
				+ ", description: " + description + ", startDate:" + startDate + ", endDate: " + endDate + ", amount: "
				+ amount + ", price: " + price + ", image: " + image + "]";
	}

	
}
