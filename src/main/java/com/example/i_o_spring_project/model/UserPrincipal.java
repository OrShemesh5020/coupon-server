package com.example.i_o_spring_project.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal {

	private Integer id;
//	private String email;
//	private String password;
	private ClientType clientType;
//	private Date expirationDate;

}
