package com.example.i_o_spring_project.authority;

import java.util.List;

public interface GrantedAuthority {

	public List<Authorities> getAuthorities();

	public void setAuthorities(String authorityType);

}
