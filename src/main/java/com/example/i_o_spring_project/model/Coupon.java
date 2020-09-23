package com.example.i_o_spring_project.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Component
@NoArgsConstructor
@Table(name = "coupons")
public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JsonIgnore
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
	@JsonIgnore
	@ManyToMany(mappedBy = "coupons", fetch = FetchType.EAGER)
	private List<Customer> customers;

	@Override
	public String toString() {
		return "Coupon [id: " + id + ", company id: " + company.getId() + ", category: " + category + ", title: "
				+ title + ", description: " + description + ", startDate:" + startDate + ", endDate: " + endDate
				+ ", amount: " + amount + ", price: " + price + ", image: " + image + "]";
	}

	public Coupon(Company company, Category category, String title, String description, Date startDate, Date endDate,
			Integer amount, Double price, String image) {
		this.company = company;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public Coupon(Integer id, Company company, Category category, String title, String description, Date startDate,
			Date endDate, Integer amount, Double price, String image) {
		this(company, category, title, description, startDate, endDate, amount, price, image);
		this.id = id;
	}

}
