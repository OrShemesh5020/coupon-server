package com.example.i_o_spring_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;
import com.example.i_o_spring_project.model.ClientType;
import com.example.i_o_spring_project.model.User;
import com.example.i_o_spring_project.tokens.TokenServiceImpl;

@Service
public class TokenFacade {

	@Autowired
	private TokenServiceImpl tokenServiceImpl;

	public User generateUser(Integer id, String email, String password, ClientType clientType) {
		User user = new User(id, email, password, clientType);
		String token = tokenServiceImpl.createJWT(user);
		user.setToken(token);
		return user;
	}

	public void doesTheTokenBelong(String tokenType, ClientType authorizedType) {
		if (!authorizedType.name().toLowerCase().equals(tokenType)) {
			throw new CouponsSystemExceptions(SystemExceptions.INVALID_TOKEN, "token type does not match!");
		}
	}
}
