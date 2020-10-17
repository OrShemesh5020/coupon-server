package com.example.i_o_spring_project.modelDTO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.model.Customer;

@Service
public class ModelToDTOConverter {

	public CouponDTO convertCoupon(Coupon coupon) {
		return new CouponDTO(coupon.getId(), coupon.getCompany().getName(), coupon.getCategory().getName(),
				coupon.getTitle(), coupon.getDescription(), coupon.getStartDate(), coupon.getEndDate(),
				coupon.getAmount(), coupon.getPrice(), coupon.getImage());
	}

	public CompanyDTO convertCompany(Company company) {
		return new CompanyDTO(company.getId(), company.getName(), company.getEmail(), company.getPassword());
	}

	public CustomerDTO convertCustomer(Customer customer) {
		return new CustomerDTO(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(),
				customer.getPassword());

	}

	public List<CouponDTO> convertCouponList(List<Coupon> coupons) {
		List<CouponDTO> dTOList = new ArrayList<>();
		for (Coupon coupon : coupons) {
			dTOList.add(convertCoupon(coupon));
		}
		return dTOList;
	}

	public List<CompanyDTO> convertCompanyList(List<Company> companies) {
		List<CompanyDTO> dTOList = new ArrayList<>();
		for (Company company : companies) {
			dTOList.add(convertCompany(company));
		}
		return dTOList;
	}

	public List<CustomerDTO> convertCustomerList(List<Customer> customers) {
		List<CustomerDTO> dTOList = new ArrayList<>();
		for (Customer customer : customers) {
			dTOList.add(convertCustomer(customer));
		}
		return dTOList;
	}
}
