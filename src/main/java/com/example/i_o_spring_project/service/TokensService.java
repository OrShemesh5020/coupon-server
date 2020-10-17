package com.example.i_o_spring_project.service;

import com.example.i_o_spring_project.model.User;
import com.example.i_o_spring_project.model.UserPrincipal;

public interface TokensService {

	String createJWT(User user);

	UserPrincipal parseToken(String token);
}
