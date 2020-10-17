package com.example.i_o_spring_project.modelDTO;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {

	private Integer id;

	private String companyName;

	private String categoryName;

	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private Integer amount;
	private Double price;
	private String image;

}
