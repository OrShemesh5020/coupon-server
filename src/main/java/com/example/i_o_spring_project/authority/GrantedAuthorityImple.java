package com.example.i_o_spring_project.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class GrantedAuthorityImple implements GrantedAuthority {

	@Autowired
	private AuthorityManager authorityManager;

	private List<Authorities> authorities;

	public List<Authorities> getAuthorities() {
		return authorities;
	}

	@Override
	public void setAuthorities(String authorityType) {
		System.err.println("setAuthorities: " + authorityManager);
		switch (authorityType) {
		case "ADMIN_ROLE":
			authorities = authorityManager.getAdminRole();
			break;
		case "COMPANY_ROLE":
			authorities = authorityManager.getCompanyRole();
			break;
		case "CUSTOMER_ROLE":
			authorities = authorityManager.getCustomerRole();
			break;

		default:
			break;
		}
	}

}
