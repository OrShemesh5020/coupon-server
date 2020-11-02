package com.example.i_o_spring_project.modelDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.service.AdminService;
import com.example.i_o_spring_project.service.CompanyService;

@Component
public class DTOToModelConverter {

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	public Coupon convertToCoupon(CouponDTO couponDTO) {
		return new Coupon(couponDTO.getId(), adminService.getCompany(couponDTO.getCompanyName()),
				companyService.getCategory(couponDTO.getCategoryName()), couponDTO.getTitle(),
				couponDTO.getDescription(), couponDTO.getStartDate(), couponDTO.getEndDate(), couponDTO.getAmount(),
				couponDTO.getPrice(), couponDTO.getImage());
	}
//
//	public Customer convertToCustomer(CustomerDTO customerDTO) {
//		return new Customer(customerDTO.getId(), customerDTO.getFirstName(), customerDTO.getLastName(),
//				customerDTO.getEmail(), customerDTO.getPassword());
//	}
//
//	public Company convertToCompany(CompanyDTO companyDTO) {
//		return new Company(companyDTO.getId(), companyDTO.getName(), companyDTO.getEmail(), companyDTO.getPassword());
//	}
}
