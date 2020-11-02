package com.example.i_o_spring_project.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

	private Integer id;
	private String email;
	private String password;
	private ClientType clientType;
	@JsonIgnore
	private String token;

	public User(Integer id, String email, String password, ClientType clientType) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.clientType = clientType;
	}

}
