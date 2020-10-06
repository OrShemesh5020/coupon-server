package com.example.i_o_spring_project.authority;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
public class AuthorityManager {

	private List<Authorities> adminRole;

	private List<Authorities> companyRole;

	private List<Authorities> customerRole;

	public AuthorityManager() {
		System.err.println("AuthorityManager constructor");
		adminRole = new ArrayList<>();
		companyRole = new ArrayList<>();
		customerRole = new ArrayList<>();
		System.out.println(companyRole);
		fillAdminRole();
		fillCompanyRole();
		fillCustomerRole();
		System.out.println(companyRole);
	}

	private void fillCustomerRole() {
		customerRole.add(Authorities.PURCHASE_COUPON);
		customerRole.add(Authorities.REMOVE_PURCHASED_COUPON);
		customerRole.add(Authorities.GET_COUPON);
		customerRole.add(Authorities.GET_COUPONS);
		customerRole.add(Authorities.GET_CUSTOMER);
		customerRole.add(Authorities.UPDATE_CUSTOMER);
	}

	private void fillCompanyRole() {
		companyRole.add(Authorities.ADD_COUPON);
		companyRole.add(Authorities.DELETE_COUPON);
		companyRole.add(Authorities.GET_COUPON);
		companyRole.add(Authorities.GET_COUPONS);
		companyRole.add(Authorities.UPDATE_COUPON);
		companyRole.add(Authorities.GET_COMPANY);
		companyRole.add(Authorities.UPDATE_COMPANY);
	}

	private void fillAdminRole() {
		adminRole.add(Authorities.ADD_COMPANY);
		adminRole.add(Authorities.ADD_CUSTOMER);
		adminRole.add(Authorities.GET_ALL_COMPANIES);
		adminRole.add(Authorities.GET_ALL_CUSTOMERS);
		adminRole.add(Authorities.GET_COMPANY);
		adminRole.add(Authorities.GET_CUSTOMER);
		adminRole.add(Authorities.REMOVE_COMPANY);
		adminRole.add(Authorities.REMOVE_CUSTOMER);
		adminRole.add(Authorities.UPDATE_COMPANY);
		adminRole.add(Authorities.UPDATE_CUSTOMER);
	}

}
